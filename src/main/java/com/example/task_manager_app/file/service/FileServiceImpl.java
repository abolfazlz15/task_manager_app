package com.example.task_manager_app.file.service;

import com.example.task_manager_app.file.interfaces.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    public static final String FILE_DIRECTORY="src/main/resources/static/files/";

    @Override
    public String saveFileToStorage(MultipartFile file) throws IOException {
        String uniqueFileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path uploadPath = Path.of(FILE_DIRECTORY);
        Path filePath = uploadPath.resolve(uniqueFileName);

        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        return uniqueFileName;
    }

    @Override
    public byte[] getFile(String fileName) throws IOException {
        Path filePath = Path.of(FILE_DIRECTORY, fileName);

        if (Files.exists(filePath)) {
            return Files.readAllBytes(filePath);
        } else {
            return null;
        }
    }

    @Override
    public void deleteFile(String fileName) throws IOException {
        Path path = Path.of(FILE_DIRECTORY, fileName);

        if (Files.exists(path)) {
            Files.delete(path);
        }
    }
}
