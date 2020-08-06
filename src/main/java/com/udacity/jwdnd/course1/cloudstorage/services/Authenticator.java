package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class Authenticator implements AuthenticationProvider {
    private UserMapper userMapper;
    private HashService hashService;
    public Authenticator(UserMapper userMapper,HashService hashService){
        this.userMapper=userMapper;
        this.hashService=hashService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       String username = authentication.getName();
       String password = ""+authentication.getCredentials();
        User user = userMapper.getUser(username);
        if(user!=null){
            String encrypt = user.getSalt();
            String hashedPass = hashService.getHashedValue(password, encrypt);
            if(user.getPassword().equals(hashedPass)){
                return new UsernamePasswordAuthenticationToken(username, password, new ArrayList<>());
            }
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
