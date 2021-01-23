package com.example.smarthome.Utils;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.smarthome.Model.FirebaseModel;
import com.example.smarthome.Model.HomeTypeModel;
import com.example.smarthome.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class DatabaseFirebase {

    public static int a = 0;
    public static FirebaseDatabase firebaseDatabase;
    private static final String TAG = "DatabaseFirebase";

    public static void pushDataFirebase(FirebaseModel firebaseModel, String id, String name, String feature) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        databaseReference.child(id).child(name).child(feature).setValue(firebaseModel.code);
    }



    public static List<FirebaseModel> Read(String name) {
        List<FirebaseModel> list = new ArrayList<>();
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference(name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    FirebaseModel firebaseModel = data.getValue(FirebaseModel.class);
                    list.add(firebaseModel);
                }
                EventBus.getDefault().postSticky(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
        return list;
    }

    public static void PushRoom(String nameRoom) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Room House");
        databaseReference.push().setValue(nameRoom);
    }

    public static void getRoom(String name) {

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference reference = firebaseDatabase.getReference(name);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<HomeTypeModel> list = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Log.d(TAG, "onDataChange: "+ data.getKey());
                    String name = data.getValue(String.class);
                    list.add(new HomeTypeModel(R.raw.bathroom, name));
                    Log.d(TAG, "onDataChange: " + list.size());

                }
                EventBus.getDefault().postSticky(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }
}
