package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class Note {

    private NoteService noteService;
    private UserService userService;

    @PostMapping("/upload-note")
    public String uploadNote(@ModelAttribute("newNote") com.udacity.jwdnd.course1.cloudstorage.model.Note note, Model model, Authentication authentication){
        boolean isUpdate=false;
        String error = null;
        if(note.getNoteId()!=null){
            if(noteService.updateNote(note)<1)
                error = "Error while Updating";
        isUpdate=true;
        }
        System.out.println(isUpdate);
        if(!isUpdate){
            if(!noteService.isUniqueNoteName(note.getNoteTitle(), userService.getUserId(authentication.getName())))
                error="Note name is already existing";
            if(error==null){
                note.setUserId(userService.getUserId(authentication.getName()));
                if(noteService.addNote(note)<0)
                    error="Error while Uploading";
            }
        }

        if(error == null){
            if(isUpdate)
                model.addAttribute("updateSuccess",true);
            else
                model.addAttribute("uploadSuccess",true);
        }
        else
            model.addAttribute("uploadFail",error);

        return "result";
    }

    @GetMapping("/delete-note/{noteId:.+}")
    public String deleteNote(@PathVariable int noteId,Model model){
        String error = null;
        System.out.println("fbf");
        if(noteService.deleteNote(noteId)< 1)
            error = "Unable to delete Note";
        if(error != null)
            model.addAttribute("updateFail",error);
        else
            model.addAttribute("updateSuccess",true);
        return "result";
    }
}
