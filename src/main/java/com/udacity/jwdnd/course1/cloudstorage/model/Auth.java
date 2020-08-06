package com.udacity.jwdnd.course1.cloudstorage.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Auth {

        Integer credentialId;
        String url;
        String username;
        String key;
        String password;
        Integer userId;


        @Override
        public String toString() {
            return "Auth{" +
                    "credentialId=" + credentialId +
                    ", url='" + url + '\'' +
                    ", username='" + username + '\'' +
                    ", key='" + key + '\'' +
                    ", password='" + password + '\'' +
                    ", userId=" + userId +
                    '}';
        }
    }


