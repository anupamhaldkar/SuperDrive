package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Auth;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AuthMapper {
    @Select("select * from CREDENTIALS where userid = #{userId}")
    public List<Auth> getAuthByUser(int credentialId);

    @Select("select * from CREDENTIALS where credentialId = #{credentialId}")
    public Auth getAuthById(int credentialId);

    @Insert("insert into CREDENTIALS(url, username, key, password, userid) " +
            "values(#{url}, #{username}, #{key}, #{password}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialId")
    public int addAuth(Auth credential);

    @Update("update CREDENTIALS set url=#{url}, username=#{username}, key=#{key}, password=#{password}" +
            " where credentialId=#{credentialId}")
    public int updateAuth(Auth credential);

    @Delete("delete from CREDENTIALS where credentialId = #{credentialId}")
    public int deleteAuth(int credentialId);

}
