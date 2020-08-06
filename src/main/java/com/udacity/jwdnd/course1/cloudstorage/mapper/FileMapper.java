package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {

    @Select("select * from FILES where userid = #{userId}")
    public List<File> getFiles(Integer userId);

    @Select("select * from FILES where fileid = #{fileId}")
    public File getFileById(Integer fileId);

    @Select("select * from FILES where filename = #{fileName} and userid = #{userId}")
    public File getFile(String fileName, Integer userId);

    @Insert("insert into FILES(filename, contenttype, filesize, userid, filedata) " +
            "values(#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    public int storeFile(File file);

    @Delete("delete from FILES where fileid = #{fileId}")
    public int deleteFileById(int fileId);

}
