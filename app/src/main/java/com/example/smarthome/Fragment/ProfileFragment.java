package com.example.smarthome.Fragment;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.smarthome.Activity.LoginActivity;
import com.example.smarthome.Activity.ResetActivity;
import com.example.smarthome.Model.AccountModel;
import com.example.smarthome.Model.DataAccount;
import com.example.smarthome.R;
import com.example.smarthome.Utils.DatabaseFirebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.royrodriguez.transitionbutton.TransitionButton;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


public class ProfileFragment extends Fragment {
    private static final String TAG = "ProfileFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final int WRITE_EXTERNAL = 1;
    private static final int SELECT_IMAGE = 3;
    private String mParam1;
    private String mParam2;
    Unbinder unbinder;
    DataAccount account;


    @BindView(R.id.icon_edit)
    ImageView iconEdit;

    @BindView(R.id.imgage_profile)
    ImageView imageProfile;

    @BindView(R.id.edit_text)
    TextView contact;

    @BindView(R.id.txt_name)
    TextView name;
    @BindView(R.id.txt_sdt)
    TextView SDT;

    @BindView(R.id.txt_email)
    TextView email;

    @BindView(R.id.txt_location)
    TextView location;

    @BindView(R.id.log_out)
    Button logOut;

    @BindView(R.id.reset_pass)
    Button resetPass;

    @BindView(R.id.imgage_background)
    ImageView imageBackground;

    public ProfileFragment() {
    }

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        unbinder = ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
       getDataAccount();
        initPermission();
        return view;
    }

    private boolean initPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (getContext().checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.v(TAG, "Permission is granted1");
                return true;
            } else {

                Log.v(TAG, "Permission is revoked1");
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, WRITE_EXTERNAL);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1");
            return true;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL) {
            if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(
                        getActivity(),
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        WRITE_EXTERNAL
                );
            }
        }
    }

    @OnClick({ R.id.icon_edit, R.id.imgage_profile, R.id.edit_text, R.id.log_out, R.id.reset_pass})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.icon_edit:
                Log.d(TAG, "onViewClicked: ");
                changeImageBackground();
                break;
            case R.id.imgage_profile:
                changeImageBackground();
                break;
            case R.id.edit_text:
                Log.d(TAG, "onViewClicked: edit contact");
                openDialog();
                break;
            case R.id.log_out:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "Log Out Success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.reset_pass:
                Intent intent = new Intent(getActivity(), ResetActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void changeImageBackground() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, SELECT_IMAGE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SELECT_IMAGE && data != null) {
            try {

                InputStream i = getContext().getContentResolver().openInputStream(data.getData());
                imageBackground.setImageURI(data.getData());

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    @Subscribe(sticky = true)
    public void onReceivedData(AccountModel accountModel) {
        Log.d(TAG, "onReceivedAccount: " + accountModel.getMail());
    }

    public void getDataAccount() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("account");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d(TAG, "onDataChange: " + snapshot.getValue());


//                Log.d(TAG, "onDataChange: " + dataAccount.email);
                for (DataSnapshot data : snapshot.getChildren()) {
                    DataAccount account = data.getValue(DataAccount.class);
                    email.setText(account.getEmail());
                    SDT.setText(account.getPhone());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void openDialog() {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.edit_profile);
        EditText editEmail = dialog.findViewById(R.id.mail);
        EditText phone = dialog.findViewById(R.id.phone);
        EditText txtname= dialog.findViewById(R.id.name);
        dialog.setCancelable(false);
        TransitionButton transitionButton = dialog.findViewById(R.id.transition_button);
        transitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email.setText(editEmail.getText().toString());
                SDT.setText(phone.getText().toString());
                name.setText(txtname.getText().toString());
                account = new DataAccount(email.getText().toString(), SDT.getText().toString(),name.getText().toString() );
                DatabaseFirebase.pushAccountFirebase(account);
                Log.d(TAG, "email: " + editEmail.getText());
                Log.d(TAG, "phone: " + phone.getText());
                transitionButton.startAnimation();

                final Handler handler = new Handler();
                handler.postAtTime(new Runnable() {
                    @Override
                    public void run() {
                        boolean isSuccessful = true;

                        if (isSuccessful) {
                            transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.EXPAND, new TransitionButton.OnAnimationStopEndListener() {
                                @Override
                                public void onAnimationStopEnd() {
                                    dialog.cancel();
                                }
                            });
                        } else {
                            transitionButton.stopAnimation(TransitionButton.StopAnimationStyle.SHAKE, null);
                        }
                    }
                }, 20000);
            }
        });
        dialog.show();
    }


}