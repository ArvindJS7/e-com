package com.ecom.productcatalog.Service;

import com.ecom.productcatalog.model.Clip;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ClipService {
    Clip uploadClip(MultipartFile file, String title, String description) throws Exception;
    List<Clip> getAllClips();
    Clip likeClip(Long id);
}

