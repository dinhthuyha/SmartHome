package com.example.smarthome.Activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthome.R;
import com.example.smarthome.Utils.FirebaseUtils;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.editTextName)
    EditText editTextName;
    @BindView(R.id.textInputName)
    TextInputLayout textInputName;
    @BindView(R.id.editTextEmail)
    EditText editTextEmail;
    @BindView(R.id.textInputEmail)
    TextInputLayout textInputEmail;
    @BindView(R.id.editTextMobile)
    EditText editTextMobile;
    @BindView(R.id.textInputMobile)
    TextInputLayout textInputMobile;
    @BindView(R.id.editTextPassword)
    EditText editTextPassword;
    @BindView(R.id.textInputPassword)
    TextInputLayout textInputPassword;
    @BindView(R.id.cirRegisterButton)
    CircularProgressButton cirRegisterButton;
    @BindView(R.id.already_account)
    TextView alreadyAccount;
    @BindView(R.id.back_login)
    ImageView backLogin;
    private FirebaseAuth mAuth;
    FirebaseUtils utils;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        changeStatusBarColor();
        mAuth = FirebaseAuth.getInstance();
        utils = new FirebaseUtils();
    }

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
            window.setStatusBarColor(getResources().getColor(R.color.register_bk_color));
        }
    }

    @OnClick({R.id.cirRegisterButton, R.id.already_account, R.id.back_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cirRegisterButton:
                utils.signUp(this,mAuth,editTextEmail.getText().toString(),editTextPassword.getText().toString());
                break;
            case R.id.already_account:
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.back_login:
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        overridePendingTransition(R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
