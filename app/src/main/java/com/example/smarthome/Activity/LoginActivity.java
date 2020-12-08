package com.example.smarthome.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarthome.R;
import com.example.smarthome.Utils.FirebaseUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.textInputEmail)
    TextInputLayout textInputEmail;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.textInputPassword)
    TextInputLayout textInputPassword;
    @BindView(R.id.got_password)
    TextView gotPassword;
    @BindView(R.id.cirLoginButton)
    CircularProgressButton cirLoginButton;
    @BindView(R.id.new_user)
    TextView newUser;
    @BindView(R.id.add)
    ImageView add;
    private FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";
    FirebaseUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //for changing status bar icon colors
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAuth = FirebaseAuth.getInstance();
        utils = new FirebaseUtils();
    }


    @OnClick({R.id.got_password, R.id.cirLoginButton, R.id.new_user, R.id.add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.got_password:
                utils.forGotPassword(this, mAuth, editTextEmail.getText().toString());
                break;
            case R.id.cirLoginButton:
                utils.signIn(this, mAuth, editTextEmail.getText().toString(), editTextPassword.getText().toString());
                startActivity(new Intent(this,HomeActivity.class ));
                break;
            case R.id.new_user:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.add:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
        }
        overridePendingTransition(R.anim.slide_in_right, R.anim.stay);
    }
}
