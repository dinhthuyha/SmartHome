package com.example.smarthome.Utils;

import androidx.annotation.NonNull;

import com.example.smarthome.Model.FirebaseModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class databaseFirebase {

    public static FirebaseDatabase firebaseDatabase;

    public static void pushDataFirebase(FirebaseModel firebaseModel){
        firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference databaseReference= firebaseDatabase.getReference("");
        databaseReference.child(firebaseModel.code).setValue(new FirebaseModel(
                firebaseModel.code,
                firebaseModel.cmd
        ));
    }

    public static List<FirebaseModel> Read(){
        List<FirebaseModel> list= new ArrayList<>();

        firebaseDatabase= FirebaseDatabase.getInstance();
        DatabaseReference reference= firebaseDatabase.getReference("");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot data: dataSnapshot.getChildren()){

                    FirebaseModel firebaseModel= data.getValue(FirebaseModel.class);
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
}
