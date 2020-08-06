package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@AllArgsConstructor
public class Auth {
    private EncryptionService encryptionService;
    private UserService userService;
    private AuthService authService;

    @PostMapping("/addCredential")
    public String addCredential(@ModelAttribute ("new-Credential") com.udacity.jwdnd.course1.cloudstorage.model.Auth auth, Model model, Authentication authentication){
        boolean isUpdate=false;
        String error = null;
        SecureRandom secureRandom = new SecureRandom();
        byte[] key = new byte[16];
        secureRandom.nextBytes(key);
        auth.setUserId(userService.getUserId(authentication.getName()));
        auth.setKey(Base64.getEncoder().encodeToString(key));
        auth.setPassword(encryptionService.encryptValue(auth.getPassword(),auth.getKey()));
        if(auth.getCredentialId()!=null){
            if(authService.updateAuth(auth)<1)
                error = "Error while updating";
            isUpdate = true;
     }else if(authService.addAuth(auth)<1)
        error = "Error while uploading";
    if(error == null){
        if(isUpdate)
            model.addAttribute("updateSuccess",true);
        else
            model.addAttribute("uploadSuccess",true);
    }else
        model.addAttribute("uploadError",error);

        return "result";
    }

    @GetMapping("/get-decrypted-password/{credId:.+}")
    public ResponseEntity<String> getDecryptedPassword(@PathVariable(value = "credId") String authenticateId){
        com.udacity.jwdnd.course1.cloudstorage.model.Auth auth = authService.getAuth(Integer.parseInt(authenticateId));
        String decPass = encryptionService.decryptValue(auth.getPassword(), auth.getKey());
        return ResponseEntity.ok(decPass);
    }

    @GetMapping("/delete-credential/{authId:.+}")
    public String deleteCredential(@PathVariable int authId,Model model){
        String error = null;
        if(authService.deleteAuth(authId)<1)
            error = "Unable to delete";
        if(error!=null)
            model.addAttribute("updateFail",error);
        else
            model.addAttribute("updateSuccess",true);
        return "result";
    }
}
