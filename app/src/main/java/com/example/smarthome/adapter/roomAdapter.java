package com.example.smarthome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;
import com.example.smarthome.model.Room;

import java.util.ArrayList;

public class roomAdapter extends  RecyclerView.Adapter<roomAdapter.ViewHolder> {
    private  ArrayList<Room> list;
    private OnNoteListener mOnNoteListener;
    public roomAdapter(ArrayList<Room> mList, OnNoteListener onNoteListener) {
        this.list = mList;
        this.mOnNoteListener = onNoteListener;
    }

    @NonNull
    @Override
    public roomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.room_cell,parent,false);
        ViewHolder viewHolder = new ViewHolder(view,mOnNoteListener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull roomAdapter.ViewHolder holder, int position) {
        Room room = list.get(position);
        holder.text_room.setText(room.getNameRoom());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private View itemView;
        public TextView text_room;
        OnNoteListener onNoteListener;
        public ViewHolder(View view, OnNoteListener onNoteListener) {
            super(view);
            itemView = view;
            this.onNoteListener = onNoteListener;
            text_room = itemView.findViewById(R.id.text_room);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
