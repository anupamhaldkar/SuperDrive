package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@AllArgsConstructor
@Service
public class StorageService {
    private FileMapper fileMapper;

    public List<File> getFilesForUser(int userId){
        return fileMapper.getFiles(userId);
    }

    public int store(MultipartFile multipartFile, int userId) throws IOException {
        File file = new File(null, multipartFile.getOriginalFilename(), multipartFile.getContentType(),
                multipartFile.getSize(), userId, multipartFile.getBytes());
        return fileMapper.storeFile(file);
    }

    public File getFile(String fileName, int userId){
        return fileMapper.getFile(fileName, userId);
    }


    public int deleteFile(int fileId){
        return fileMapper.deleteFileById(fileId);
    }

    public boolean isUnique(int userId,String fileName){
        return fileMapper.getFile(fileName,userId) == null;
    }

}
