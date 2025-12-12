package com.hw3.filestoring.service;

import com.hw3.filestoring.model.Work;
import com.hw3.filestoring.model.WorkStatus;
import com.hw3.filestoring.repository.WorkRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class FileStorageService {

    private final WorkRepository workRepository;
    private Path fileStorageLocation;

    @Value("${file.upload-dir:uploads}")
    private String uploadDir;

    @Autowired
    public FileStorageService(WorkRepository workRepository) {
        this.workRepository = workRepository;
    }

    @PostConstruct
    public void init() {
        try {
            this.fileStorageLocation = Paths.get(uploadDir).toAbsolutePath().normalize();
            Files.createDirectories(this.fileStorageLocation);
            System.out.println("File storage location created: " + this.fileStorageLocation);
        } catch (IOException e) {
            throw new RuntimeException("Could not create upload directory: " + uploadDir, e);
        }
    }

    public Work storeFile(MultipartFile file, String studentId, String assignmentId, String studentName) {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
        }

        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        try {
            Path targetLocation = this.fileStorageLocation.resolve(uniqueFileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            Work work = new Work();
            work.setStudentId(studentId);
            work.setStudentName(studentName);
            work.setAssignmentId(assignmentId);
            work.setFileName(originalFileName);
            work.setFilePath(targetLocation.toString());
            work.setFileSize(file.getSize());
            work.setFileType(file.getContentType());
            work.setStatus(WorkStatus.SUBMITTED);

            System.out.println("File stored at: " + targetLocation);
            return workRepository.save(work);
        } catch (IOException e) {
            throw new RuntimeException("Failed to store file: " + originalFileName, e);
        }
    }

    public Optional<Work> getWork(Long workId) {
        return workRepository.findById(workId);
    }

    public List<Work> getWorksByAssignment(String assignmentId) {
        return workRepository.findByAssignmentId(assignmentId);
    }

    public Resource loadFileAsResource(Long workId) {
        Optional<Work> workOpt = workRepository.findById(workId);

        if (workOpt.isEmpty()) {
            throw new RuntimeException("Work not found with id: " + workId);
        }

        Work work = workOpt.get();
        String filePath = work.getFilePath();

        if (filePath == null) {
            throw new RuntimeException("File path is null for work id: " + workId);
        }

        try {
            Path filePathObj = Paths.get(filePath).normalize();
            Resource resource = new UrlResource(filePathObj.toUri());

            if (resource.exists()) {
                return resource;
            } else {
                throw new RuntimeException("File not found at path: " + filePath);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("File not found: " + filePath, e);
        }
    }

    public String getFileContent(Long workId) {
        try {
            Resource resource = loadFileAsResource(workId);
            return new String(resource.getInputStream().readAllBytes());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file content for work id: " + workId, e);
        }
    }

    public List<Work> getAllWorks() {
        return workRepository.findAll();
    }

    public Work updateWorkStatus(Long workId, WorkStatus status) {
        Optional<Work> workOpt = workRepository.findById(workId);

        if (workOpt.isEmpty()) {
            throw new RuntimeException("Work not found with id: " + workId);
        }

        Work work = workOpt.get();
        work.setStatus(status);
        return workRepository.save(work);
    }

    public boolean isStorageAvailable() {
        return this.fileStorageLocation != null && Files.exists(this.fileStorageLocation);
    }
}