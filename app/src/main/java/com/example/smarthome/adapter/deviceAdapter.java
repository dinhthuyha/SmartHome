package com.example.smarthome.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthome.R;
import com.example.smarthome.model.Device;

import java.text.BreakIterator;
import java.util.ArrayList;

public class deviceAdapter extends  RecyclerView.Adapter<deviceAdapter.ViewHolder> {
    private  ArrayList<Device> list;
    public deviceAdapter(ArrayList<Device> mList) {
        this.list = mList;
    }

    @NonNull
    @Override
    public deviceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_cell,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull deviceAdapter.ViewHolder holder, int position) {
        Device device = list.get(position);
        holder.text_device.setText(device.getNameDevice());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder  {
        private View itemView;
        public TextView text_device;
        public ViewHolder(View view) {
            super(view);
            itemView = view;
            text_device=itemView.findViewById(R.id.text_device);
        }
    }
}
