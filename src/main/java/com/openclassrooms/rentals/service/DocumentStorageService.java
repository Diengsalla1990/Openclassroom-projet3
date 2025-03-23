package com.openclassrooms.rentals.service;


import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

@Service
public class DocumentStorageService {
    private final File fileStorageLocation;
    private final Path fileconv;
    private final String fileServerLocation = "http://localhost:8000/";
    private final String dirName = "images";
    private final File homeDir;
    
    
  
    public DocumentStorageService() throws IOException {
    	
    	
    	this.homeDir = new File(System.getProperty("user.home"));
    	this.fileStorageLocation = new File(this.homeDir,dirName);
    	if (!this.fileStorageLocation.exists() && !this.fileStorageLocation.mkdirs()) {
            throw new IOException("Unable to create " + this.fileStorageLocation.getAbsolutePath());
        }
    	this.fileconv = this.fileStorageLocation.toPath();
    }
    
    public void Storage() {
    	
    }

    
    /**
     * 
     * @param file
     * @return
     * @throws IOException
     */
    
    public String storeFile(MultipartFile file) throws IOException {
        // Normalize file name
    	
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        
        
       // File originalFileName = file.getOriginalFilename();
        // Copy file to the target location (Replacing existing file with the same name)
        Path  targetLocation = this.fileconv.resolve(originalFileName);
        Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

        return fileServerLocation+originalFileName;
    }


}

