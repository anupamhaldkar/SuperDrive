package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Auth;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/home")
public class Home {
    private StorageService storageService;
    private NoteService noteService;
    private AuthService authService;
    private UserService userService;

    private List<File> fileList;
    private List<Note> noteList;
    private List<Auth> authList;

    public Home(StorageService storageService, NoteService noteService, AuthService authService, UserService userService) {
        this.storageService = storageService;
        this.noteService = noteService;
        this.authService = authService;
        this.userService = userService;
    }

    @PostConstruct
    public void postConstruct(){
        fileList = new ArrayList<>();
        noteList = new ArrayList<>();
        authList = new ArrayList<>();
    }

    @GetMapping
    public String getHome(Model model, Authentication authentication){
        int id = userService.getUserId(authentication.getName());
        noteList = noteService.getNotes(id);
        fileList = storageService.getFilesForUser(id);
        authList = authService.getAuths(id);
        model.addAttribute("noteList",noteList);
        model.addAttribute("fileList",fileList);
        model.addAttribute("authList",authList);
        return "home";
    }

}
