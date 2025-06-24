package com.ecom.productcatalog.Service.Impl;

import com.ecom.productcatalog.model.Clip;
import com.ecom.productcatalog.Repository.ClipRepository;
import com.ecom.productcatalog.Service.ClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class ClipServiceImpl implements ClipService {

    @Autowired
    private ClipRepository clipRepository;

    private static final String VIDEO_UPLOAD_DIR = "uploads/videos/";

    @Override
public Clip uploadClip(MultipartFile file, String title, String description) throws IOException {
    // Define the upload directory
    String uploadDir = "uploads/videos";
    
    // Create the directory if it doesn't exist
    File dir = new File(uploadDir);
    if (!dir.exists()) {
        dir.mkdirs();  // Create folders
    }

    // Create a unique file name
    String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();

    // Full path where the file will be saved
    Path filePath = Paths.get(uploadDir, fileName);
    
    // Save the file
    Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

    // Save to DB
    Clip clip = new Clip();
    clip.setTitle(title);
    clip.setDescription(description);
    clip.setVideoPath(filePath.toString());
    return clipRepository.save(clip);
}


    @Override
    public List<Clip> getAllClips() {
        return clipRepository.findAll();
    }

    @Override
    public Clip likeClip(Long id) {
        Clip clip = clipRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Clip not found"));
        clip.setLikes(clip.getLikes() + 1);
        return clipRepository.save(clip);
    }
}
