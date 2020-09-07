package com.myhost.spyros.rommjetpack;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NoteListAdapter extends RecyclerView.Adapter<NoteListAdapter.NoteViewHolder> {
    //delete interface
    public interface onDeleteClickListener{
        void onDeleteClickListener(Note myNote);
    }

    //code to inflate the recycler view with the notes-----------------
    private final LayoutInflater layoutInflater;
    private Context mContext;
    //after this we go to constructor----------------------------------
    //List of notes
    private List<Note> mNotes;
    private onDeleteClickListener onDeleteClickListener;

    public NoteListAdapter(Context context,onDeleteClickListener listener) {
        layoutInflater = LayoutInflater.from(context);
        mContext = context;
        this.onDeleteClickListener = listener;
    }

    @NonNull
    @Override
    public NoteListAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.list_item,parent,false);
        NoteViewHolder viewHolder = new NoteViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListAdapter.NoteViewHolder holder, int position) {
        if(mNotes != null){
            Note note = mNotes.get(position);
            holder.setData(note.getNote(),position);
            holder.setListeners();
        }
        else{
            //covers the case of data not being ready yet
            holder.noteItemView.setText("No Notes available");
        }
    }

    @Override
    public int getItemCount() {
        if(mNotes != null)
            return mNotes.size();
        else
            return 0;
    }

    public void setNotes(List<Note> notes){
        mNotes = notes;
        notifyDataSetChanged();
    }

    //method to get position of swiped element to delete
    public Note getNoteAt(int position){
        return mNotes.get(position);
    }


    public class NoteViewHolder extends RecyclerView.ViewHolder{

        private TextView noteItemView;
        private ImageView imgDelete, imgEdit;
        private int mPosition;


        public NoteViewHolder(View itemView) {
            super(itemView);
            noteItemView = itemView.findViewById(R.id.txvNote);
            imgDelete = itemView.findViewById(R.id.ivRowDelete);
            imgEdit = itemView.findViewById(R.id.ivRowEdit);
        }

        public void setData(String note, int position){
            noteItemView.setText(note);
            mPosition = position;
        }

        public void setListeners() {
            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(onDeleteClickListener != null){
                        onDeleteClickListener.onDeleteClickListener(mNotes.get(mPosition));
                    }
                }
            });

            imgEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //We need to fetch the id of the note and send it to our edit activity
                    //then we will fetch the note and populate our edit activity for which the edit activity was clicked
                    //first we will init the intent using which we will start edit activity and pass the data to it
                    Intent intent = new Intent(mContext,EditNoteActivity.class);//mContext is the context of our main activity
                    intent.putExtra("note_id",mNotes.get(mPosition).getId());
                    ((Activity)mContext).startActivityForResult(intent,MainActivity.UPDATE_ACTIVITY_REQUEST_CODE);
                }
            });
        }
    }
}
