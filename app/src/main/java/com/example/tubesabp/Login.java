package com.example.tubesabp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tubesabp.data.model.LoggedInUser;


public class Login extends AppCompatActivity {
    DatabaseHelper db;
    LoggedInUser activeUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        db = new DatabaseHelper(this);

        setContentView(R.layout.activity_login);
        final EditText usernameEditText = findViewById(R.id.username);
        final EditText passwordEditText = findViewById(R.id.password);
        final Button loginButton = (Button) findViewById(R.id.login);
        final TextView lupa = findViewById(R.id.text_lupa);
        final TextView register = findViewById(R.id.text_daftar);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isUserNameValid(usernameEditText.getText().toString())){
                    Toast.makeText(getApplicationContext(),"Must use email", Toast.LENGTH_SHORT).show();
                }
                else {
                    login(usernameEditText.getText().toString(), passwordEditText.getText().toString());
                }
            }
        });

        lupa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //memunculkan dialog lupa password saat menekan text_lupa
                DialogLupaPassword dialog = new DialogLupaPassword(Login.this);
                dialog.show();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, Register.class);
                startActivity(i);
            }
        });
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (!username.contains("@")){
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        }
    }

    public void back(){
        finish();
    }

    private void login(String email, String password){
        if(db.checkLogin(email, password)){
            activeUser = db.getUser(email);
            Log.i("user", activeUser.getDisplayName());
            db.upgradeSession(activeUser.getUserId());
            finish();
        }
        else{
            Toast.makeText(getApplicationContext(),"Wrong Username or Password", Toast.LENGTH_SHORT).show();
        }
    }
}
