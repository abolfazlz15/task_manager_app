package com.example.task_manager_app.otp.service;

import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.otp.interfaces.OtpService;
import com.example.task_manager_app.otp.dto.CreateOtpDto;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Service
public class OtpServiceImpl implements OtpService {
    private final StringRedisTemplate template;
    private final SecureRandom secureRandom = new SecureRandom();

    @Value("${otp.default-length:5}")
    private int otpCodeLength;
    @Value("${otp.default-ttl:600}")
    private long otpDefaultTtl;

    @Autowired
    public OtpServiceImpl(StringRedisTemplate template) {
        this.template = template;
    }

    @Override
    public String createOtp(CreateOtpDto createOtpDto) {
        String otpKey = "%s-%s-%s".formatted(createOtpDto.getId(), createOtpDto.getType(), createOtpDto.getOperation());
        String attemptsKey = otpKey + ":attempts";

        long otpTtl = createOtpDto.getTtl() != null ? createOtpDto.getTtl() : otpDefaultTtl;
        long attemptsTtl = otpTtl + 10;

        if (createOtpDto.getTimeout() != null) {
            String previousOtp = template.opsForValue().get(otpKey);
            if (previousOtp != null) {
                try {
                    long createdTime = Long.parseLong(previousOtp.split(":", 2)[0]);
                    long currentTime = Instant.now().getEpochSecond();
                    if (createdTime >= currentTime - createOtpDto.getTimeout()) {
                        throw new BadRequestException("try later");
                    }
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
                    throw new BadRequestException("try later");
                }
            }
        }

        long time = Instant.now().getEpochSecond();
        String code = generateCode(createOtpDto.getType());
        String otp = time + ":" + code;

        template.opsForValue().set(attemptsKey, "0", Duration.ofSeconds(attemptsTtl));
        template.opsForValue().set(otpKey, otp, Duration.ofSeconds(otpTtl));

        return code;
    }

    private String generateCode(OtpTypeEnum type) {
        return switch (type) {
            case EMAIL, SMS -> {
                long otpLowerBound = (long) Math.pow(10, otpCodeLength - 1);
                long otpUpperBound = (long) Math.pow(10, otpCodeLength);

                long number = secureRandom.nextLong(otpLowerBound, otpUpperBound);
                yield String.format("%0" + otpCodeLength + "d", number);
            }
            default -> {
                yield UUID.randomUUID().toString().replace("-", "");
            }
        };
    }
}
