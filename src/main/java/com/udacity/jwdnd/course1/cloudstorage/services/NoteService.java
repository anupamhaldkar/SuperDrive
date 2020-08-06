package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NoteService {
    private NoteMapper noteMapper;


    public int addNote(Note note){
        return noteMapper.addNote(note);
    }

    public Note getNote(int noteId){
        return noteMapper.getNoteById(noteId);
    }

    public List<Note> getNotes(int userId){
        return noteMapper.getNotesByUser(userId);
    }

    public Note getNote(String noteTitle, int userId){
        return noteMapper.getNote(noteTitle, userId);
    }

    public boolean noteExists(int noteId){
        return noteMapper.getNoteById(noteId)!=null;
    }

    public int updateNote(Note note){
        return noteMapper.updateNote(note);
    }

    public boolean isUniqueNoteName(String noteTitle,int userId){
        return noteMapper.getNote(noteTitle,userId) == null;
    }

    public int deleteNote(int noteId){
        return noteMapper.deleteNoteById(noteId);
    }

}
