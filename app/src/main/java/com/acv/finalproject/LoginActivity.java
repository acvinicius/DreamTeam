package com.acv.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import com.acv.finalproject.dao.UserDAO;
import com.acv.finalproject.model.User;

import static com.acv.finalproject.R.string.username;

public class LoginActivity extends AppCompatActivity {

    private EditText user;
    private EditText password;
    private UserDAO userDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.user = (EditText) findViewById(R.id.etUsername);
        this.password = (EditText) findViewById(R.id.etPassword);
        isConnected();
    }

    private void isConnected() {
        SharedPreferences settings = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        Boolean isConnected = settings.getBoolean("continueConnected", false);
        if (isConnected.equals(Boolean.TRUE)) {
            openScreen();
        }
    }

    private void openScreen() {
        startActivity(new Intent(this, MainViewActivity.class));
        LoginActivity.this.finish();
    }

    public void login(View view) {
        if (user.equals(null) || user.getText().toString().equals("")) {
            user.setError(getString(R.string.user_required));
        }
        if (password.equals(null) || password.getText().toString().equals("")) {
            password.setError(getString(R.string.password_required));
        }
        if (user != null && password != null && !user.getText().toString().equals("") && !password.getText().toString().equals("")) {
            try {
                CheckBox ckbIsConnected = ((CheckBox) findViewById(R.id.ckb_connected));
                User loginUser = new User(this.user.getText().toString(), this.password.getText().toString());
                this.userDAO = new UserDAO(this);
                this.userDAO.open();
                User checkUser = this.userDAO.select(loginUser.getUsername(), loginUser.getPassword());
                this.userDAO.close();
                if (checkUser != null) {
                    savePreferences(checkUser, ckbIsConnected.isChecked());
                    openScreen();
                } else {
                    user.setError("Usuário ou senha inválido.");
                    password.setError("Usuário ou senha inválido.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void savePreferences(User user, Boolean continueConnected) {
        SharedPreferences preferences = getSharedPreferences("PREFERENCIAS", MODE_PRIVATE);
        SharedPreferences.Editor e = preferences.edit();
        e.putString("userID", String.valueOf(user.getId()));
        e.putBoolean("continueConnected", continueConnected);
        e.commit();
    }

}
