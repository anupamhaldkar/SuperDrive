package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@AllArgsConstructor
public class File {
    private UserService userService;
    private StorageService storageService;

    @PostMapping("/upload-file")
    public String upload(@RequestParam("fileUpload")MultipartFile multipartFile, Model model, Authentication authentication){
        String error = null;
        if(!storageService.isUnique(userService.getUserId(authentication.getName()),multipartFile.getOriginalFilename()))
            error = "Filename Exist";
        if(multipartFile.isEmpty())
            error = "File not selected";
        if(error == null) {
            try {
                if (storageService.store(multipartFile, userService.getUserId(authentication.getName())) < 0)
                    error = "Error while uploading";
            } catch (IOException e) {
                error = "Error while uploading";
            }
        }
        if(error!=null)
            model.addAttribute("uploadFail",error);
        else
            model.addAttribute("uploadSuccess",true);
    return "result";
    }

    @GetMapping("/save-file/{fileName:.+}")
    public ResponseEntity saveFile(@PathVariable String fileName,Authentication authentication){
        com.udacity.jwdnd.course1.cloudstorage.model.File file = storageService.getFile(fileName,userService.getUserId(authentication.getName()));
                if(file!=null)
                    return  ResponseEntity.ok().contentType(MediaType.parseMediaType(file.getContentType()))
                            .header(HttpHeaders.CONTENT_DISPOSITION,"attachment;fileName=\""+fileName+"\"")
                            .body(file.getFileData());

                return null;
    }

    @GetMapping("/delete-file/{fileId:.+}")
    public String deleteFile(@PathVariable int fileId,Model model){
        String error = null;
        if(storageService.deleteFile(fileId)<1)
            error = "Unable to delete";
        if(error !=null)
            model.addAttribute("updateFail",error);
        else
            model.addAttribute("updateSuccess",true);
    return "result";
    }
}
