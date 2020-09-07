package com.myhost.spyros.rommjetpack;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

//This is my Entity Component class of Room Jetpack
//I change it to Entity by using the annotation
@Entity(tableName = "notes")// if i don't use a table name, name of class will be used as tablename
public class Note {
    @NonNull
    public String getId() {
        return id;
    }

    @NonNull
    public String getNote() {
        return this.mNote;
    }

    //here we will declare the columns that the note will have
    @PrimaryKey
    @NonNull
    private String id;

    @NonNull
    @ColumnInfo(name = "note")
    private String mNote;

    //constructor
    public Note(String id, String note){
        this.id = id;
        this.mNote = note;
    }
}
