package com.myhost.spyros.rommjetpack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

public class EditNoteViewModel extends AndroidViewModel {
    //operation needed in every ViewModel-----------------------------------------------------------
    String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase db;
    //----------------------------------------------------------------------------------------------

    public EditNoteViewModel(@NonNull Application application) {
        super(application);
        //operation needed in every ViewModel-------------------------------------------------------
        Log.i(TAG,"Edit ViewModel");
        db = NoteRoomDatabase.getDatabase(application);
        noteDao = db.noteDao();
        //------------------------------------------------------------------------------------------
    }

    //wrapper function
    public LiveData<Note> getNote(String noteId){
        return noteDao.getNote(noteId);
    }
}
