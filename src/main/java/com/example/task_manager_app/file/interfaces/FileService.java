package com.example.task_manager_app.file.interfaces;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface FileService {
    public String saveFileToStorage(MultipartFile file) throws IOException;
    public byte[] getFile(String fileName) throws IOException;
    public void deleteFile(String fileName) throws IOException;
}
