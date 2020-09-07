package com.myhost.spyros.rommjetpack;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {
    //we extend AndroidViewModel and not ViewModel, so we can get Application Context

    private String TAG = this.getClass().getSimpleName();
    private NoteDao noteDao;
    private NoteRoomDatabase noteDB;
    private LiveData<List<Note>> mAllNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);
        noteDB = NoteRoomDatabase.getDatabase(application);
        noteDao = noteDB.noteDao();
        //i use live data, so this operation will perform automatically in the background thread
        //so we don't need any async task for it
        mAllNotes = noteDao.getAllNotes();
    }

    //we need to create the wrapper for the insert operation
    public void insert(Note note){
        //we need to perform these operations in the non UI Thread
        //we will do this using the asyncTask
        new InsertAsyncTask(noteDao).execute(note);
    }

    //we will create a wrapper function that will return mAllNotes from our ViewModel
    LiveData<List<Note>> getAllNotes(){
        return mAllNotes;
    }

    //wrapper function
    public void update(Note note){
        new UpdateAsyncTask(noteDao).execute(note);
    }

    public void delete(Note note){
        new DeleteAsyncTask(noteDao).execute(note);
    }

    public void deleteAllNotes(){
        new DeleteAllNotesAsyncTask(noteDao).execute();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        Log.i(TAG,"ViewModel Destroyed");
    }


    private class OperationAsyncTask extends AsyncTask<Note,Void,Void>{

        NoteDao mAsyncTaskDao;

        OperationAsyncTask(NoteDao dao){
            this.mAsyncTaskDao = dao;
        }
        @Override
        protected Void doInBackground(Note... notes) {
            return null;
        }
    }

    private class InsertAsyncTask extends OperationAsyncTask{

        public InsertAsyncTask(NoteDao mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.insert(notes[0]);
            return null;
        }
    }

    private class UpdateAsyncTask extends OperationAsyncTask{

        public UpdateAsyncTask(NoteDao mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.update(notes[0]);
            return null;
        }
    }

    private class DeleteAsyncTask extends OperationAsyncTask{

        public DeleteAsyncTask(NoteDao mNoteDao) {
            super(mNoteDao);
        }

        @Override
        protected Void doInBackground(Note... notes) {
            mAsyncTaskDao.delete(notes[0]);
            return null;
        }
    }

    private class DeleteAllNotesAsyncTask extends OperationAsyncTask{

        DeleteAllNotesAsyncTask(NoteDao dao) {
            super(dao);
        }

        @Override
        protected Void doInBackground(Note[] notes) {
            mAsyncTaskDao.deleteAllNotes();
            return null;
        }
    }
}
