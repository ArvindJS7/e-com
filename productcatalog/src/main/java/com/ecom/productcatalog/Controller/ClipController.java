package com.ecom.productcatalog.Controller;

import com.ecom.productcatalog.model.Clip;
import com.ecom.productcatalog.Service.ClipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/clips")
@CrossOrigin(origins = "*")
public class ClipController {

    @Autowired
    private ClipService clipService;

    @PostMapping("/upload")
    public ResponseEntity<?> uploadClip(
            @RequestParam("file") MultipartFile file,
            @RequestParam("title") String title,
            @RequestParam("description") String description
    ) {
        try {
            Clip clip = clipService.uploadClip(file, title, description);
            return ResponseEntity.ok(clip);
        } catch (Exception e) {
    e.printStackTrace(); // Add this line to log the root cause
    return ResponseEntity.internalServerError().body("Upload failed: " + e.getMessage());
}
    }

    @GetMapping
    public ResponseEntity<List<Clip>> getAllClips() {
        List<Clip> clips = clipService.getAllClips();
        Collections.shuffle(clips);
        return ResponseEntity.ok(clips);
    }

    @PostMapping("/{id}/like")
    public ResponseEntity<Clip> likeClip(@PathVariable Long id) {
        return ResponseEntity.ok(clipService.likeClip(id));
    }
}