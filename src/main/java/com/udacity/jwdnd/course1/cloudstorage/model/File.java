package com.udacity.jwdnd.course1.cloudstorage.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class File {
    Integer fileId;
    String fileName;
    String contentType;
    Long fileSize;
    Integer userId;
    byte[] fileData;

}
