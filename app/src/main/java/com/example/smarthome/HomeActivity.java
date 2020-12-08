package com.example.smarthome;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.example.smarthome.adapter.roomAdapter;
import com.example.smarthome.model.Room;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements roomAdapter.OnNoteListener {

    RecyclerView home_recycler;
    TextView home_txt;
    ArrayList<Room> list =new ArrayList<Room>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        home_recycler = findViewById(R.id.home_recycler);
        home_txt=findViewById(R.id.home_txt);
        list.add(new Room("Phong khach"));
        list.add(new Room("Phong ngu"));
        list.add(new Room("Phong ngu"));
        list.add(new Room("Phong ngu"));
        list.add(new Room("Phong ngu"));
        list.add(new Room("Phong ngu"));
        roomAdapter adapter = new roomAdapter(list,this);
        home_txt.setText("Home");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        home_recycler.setAdapter(adapter);
        home_recycler.setLayoutManager(linearLayoutManager);
    }


    @Override
    public void onNoteClick(int position) {

        Intent intent = new Intent(this,DeviceActivity.class);
        startActivity(intent);
    }
}
