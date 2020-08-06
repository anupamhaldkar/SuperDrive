package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.AuthMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Auth;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class AuthService {
    private AuthMapper authMapper;

    public List<Auth> getAuths(int userId){
        return authMapper.getAuthByUser(userId);
    }

    public Auth getAuth(int credentialId){
        return authMapper.getAuthById(credentialId);
    }

    public int addAuth(Auth auth){
        return authMapper.addAuth(auth);
    }

    public int updateAuth(Auth auth){
        return authMapper.updateAuth(auth);
    }

    public int deleteAuth(int credentialId){
        return authMapper.deleteAuth(credentialId);
    }
}
