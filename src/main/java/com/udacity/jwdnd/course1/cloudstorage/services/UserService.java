package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
@AllArgsConstructor
public class UserService {
    private HashService  hashService;
    private UserMapper userMapper;
    public int getUserId(String username){
        return userMapper.getUser(username).getUserId();
    }

    public int createUser(User user){
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        String encrpyt = Base64.getEncoder().encodeToString(salt);
        String password = hashService.getHashedValue(user.getPassword(), encrpyt);
    return userMapper.insert(new User(null,user.getUsername(),encrpyt,password,user.getFirstName(),user.getLastName()));
    }

    public boolean isUsernameAvailable(String username){
        return userMapper.getUser(username)==null;
    }
}
