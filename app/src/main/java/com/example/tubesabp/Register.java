package com.example.tubesabp;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class Register extends AppCompatActivity {

    DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);

        final ImageView lonceng = findViewById(R.id.lonceng);
        final ImageView info = findViewById(R.id.btn_info);
        final EditText usernameEditText = findViewById(R.id.username2);
        final EditText emailEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password6);
        final Button updateButton = (Button) findViewById(R.id.update);
        final Button logoutButton = (Button) findViewById(R.id.keluar);
        final Button hapusButton = (Button) findViewById(R.id.hapus_akun);
        final View home = findViewById(R.id.btn_bintang2);
        final View jadwal = findViewById(R.id.btn_jadwal);
        final View userView = findViewById(R.id.userview);

        info.setVisibility(View.INVISIBLE);
        lonceng.setVisibility(View.INVISIBLE);
        updateButton.setVisibility(View.INVISIBLE);
        hapusButton.setVisibility(View.INVISIBLE);
        home.setVisibility(View.GONE);
        jadwal.setVisibility(View.GONE);
        userView.setVisibility(View.GONE);

        logoutButton.setText("BUAT AKUN");

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                if (!isEmailValid(email) || username == null || password == null){
                    Toast.makeText(getApplicationContext(),"Terdapat kesalahan pengisian data", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.insertUser(username,email,password)){
                        finish();
                        Toast.makeText(getApplicationContext(),"Berhasil Buat akun", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Gagal Buat akun", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (!email.contains("@")){
            return false;
        }
        else {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        }
    }
}
