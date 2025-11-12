package com.example.task_manager_app.otp.service;

import java.security.SecureRandom;
import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.example.task_manager_app.common.exception.BadRequestException;
import com.example.task_manager_app.otp.dto.CreateOtpDto;
import com.example.task_manager_app.otp.dto.VerifyOtpDto;
import com.example.task_manager_app.otp.enums.OtpTypeEnum;
import com.example.task_manager_app.otp.interfaces.OtpService;

@Service
public class OtpServiceImpl implements OtpService {
    private final StringRedisTemplate template;
    private final SecureRandom secureRandom = new SecureRandom();
    private final int maxAttemptCount; 

    @Value("${otp.default-length:5}")
    private int otpCodeLength;
    @Value("${otp.default-ttl:600}")
    private long otpDefaultTtl;

    @Autowired
    public OtpServiceImpl(
            StringRedisTemplate template,
            @Value("${otp.max-attempt-length:5}") int maxAttemptCount
    ) {
        this.template = template;
        this.maxAttemptCount = maxAttemptCount;
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
    
    @Override
    public void verifyOtp(VerifyOtpDto verifyOtpDto) {
        String otpKey = "%s-%s-%s".formatted(
            verifyOtpDto.getId(),
            verifyOtpDto.getType(),
            verifyOtpDto.getOperation()
        );
        String attemptsKey = otpKey + ":attempts";

        String otpData = template.opsForValue().get(otpKey);
        if (otpData == null) {
            throw new BadRequestException("otp expired or does not exist");
        }
        String attemptData = template.opsForValue().get(attemptsKey);
        if (attemptData == null) {
            throw new BadRequestException("otp expired or does not exist");
        }

        long attempts;
        try {
            attempts = Long.parseLong(attemptData);
        } catch (NumberFormatException e) {
            throw new BadRequestException("otp expired or does not exist");
        }
        if (attempts >= maxAttemptCount) {
            throw new BadRequestException("otp expired or does not exist");
        }

        Long updatedAttempts = template.opsForValue().increment(attemptsKey);
        if (updatedAttempts == null) {
            throw new BadRequestException("otp expired or does not exist");
        }

        if (updatedAttempts > maxAttemptCount) {
            throw new BadRequestException("otp expired or does not exist");
        }

        String[] parts = otpData.split(":", 2);
        if (parts.length != 2) {
            throw new BadRequestException("otp expired or does not exist");
        }

        String storedCode = parts[1];
        if (!storedCode.equals(verifyOtpDto.getCode())) {
            throw new BadRequestException("otp expired or does not exist");
        }

        template.delete(otpKey);
        template.delete(attemptsKey);
    }
}
