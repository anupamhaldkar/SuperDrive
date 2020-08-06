package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("select * from NOTES where userid = #{userId}")
    public List<Note> getNotesByUser(int userId);

    @Insert("insert into NOTES(notetitle, notedescription, userid) values(#{noteTitle}, #{noteDescription}, #{userId})")
    @Options(useGeneratedKeys = true, keyProperty = "noteId")
    public int addNote(Note note);

    @Select("select * from NOTES where noteid = #{noteId}")
    public Note getNoteById(int noteId);

    @Delete("delete from NOTES where noteid = #{noteId}")
    public int deleteNoteById(int noteId);

    @Select("select * from NOTES where userid = #{userId} and noteTitle = #{noteTitle}")
    public Note getNote(String noteTitle, int userId);

    @Update("update NOTES set notetitle=#{noteTitle}, noteDescription=#{noteDescription}" +
            " where noteid=#{noteId}")
    public int updateNote(Note note);


}
