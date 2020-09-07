package com.myhost.spyros.rommjetpack;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = Note.class,version = 1)
public abstract class NoteRoomDatabase extends RoomDatabase {
    //i need to create a database object that i will use
    public abstract NoteDao noteDao();

    //now i need to create an instance of the database
    //instance object must be only one and singleton
    private static volatile NoteRoomDatabase noteRoomInstance;

    static NoteRoomDatabase getDatabase(final Context context){
        if(noteRoomInstance == null){
            synchronized (NoteRoomDatabase.class){
                if(noteRoomInstance == null){
                    noteRoomInstance = Room.databaseBuilder(context.getApplicationContext(),
                            NoteRoomDatabase.class,"note_database").build();
                }
            }
        }
        return noteRoomInstance;
    }


}
