package com.example.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    TextView userText;
    SharedPreferences sharedPreferences;
    String usernameKey = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("lab5", Context.MODE_PRIVATE);
        String user = sharedPreferences.getString(usernameKey, "");

        if (user.equals("")) {
            setContentView(R.layout.activity_main);
        } else {
            login();
        }
    }

    public void loginButtonClick(View view) {
        Log.i("Info", "Button pressed");
        EditText username = (EditText) findViewById(R.id.usernameText);
        EditText password = (EditText) findViewById(R.id.passwordText);

        sharedPreferences.edit()
                .putString("username", username.getText().toString()).apply();
        login();
    }

    public void login() {
        Intent intent = new Intent(this , LoggedIn.class);
        startActivity(intent);
    }
}