package com.myhost.spyros.rommjetpack;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

//This is DAO(Data Access Object and i create it after Note
//Here we will define the various SQL queries in formal functions
@Dao
public interface NoteDao {

    //we will write methods from inserting in the roomDatabase
    //then we need to create the wrapper for this insert method in our ViewModel
    @Insert
    void insert(Note note);

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM notes WHERE id=:noteId")
    LiveData<Note> getNote(String noteId);

    @Query("DELETE FROM notes")
    void deleteAllNotes();

    @Update
    void update(Note note);

    @Delete
    int delete(Note note);
}
