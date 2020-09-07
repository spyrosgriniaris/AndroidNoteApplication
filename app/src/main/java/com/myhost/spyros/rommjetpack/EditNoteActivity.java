package com.myhost.spyros.rommjetpack;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditNoteActivity extends AppCompatActivity {

    public static final String NOTE_ID = "note_id";
    static final String UPDATED_NOTE = "note_text";
    private EditText etNote;
    EditNoteViewModel noteModel;
    private Bundle bundle;
    private String noteId;
    private LiveData<Note> note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        etNote = findViewById(R.id.etNote);

        //fetch the data we just passed, using bundle
        bundle = getIntent().getExtras();
        if(bundle != null){
            noteId = bundle.getString("note_id");
        }

        //init viewModel we will use
        noteModel = ViewModelProviders.of(this).get(EditNoteViewModel.class);
        note = noteModel.getNote(noteId);
        note.observe(this, new Observer<Note>() {
            @Override
            public void onChanged(@Nullable Note note) {
                etNote.setText(note.getNote());
            }
        });
    }

    public void updateNote(View view){
        String updatedNote = etNote.getText().toString();
        Intent resultIntent = new Intent();
        resultIntent.putExtra(NOTE_ID,noteId);
        resultIntent.putExtra(UPDATED_NOTE,updatedNote);
        setResult(RESULT_OK,resultIntent);
        finish();
    }

    public void cancelUpdate(View view){
        finish();
    }
}
