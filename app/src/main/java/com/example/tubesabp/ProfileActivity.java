package com.example.tubesabp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tubesabp.data.model.LoggedInUser;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    DatabaseHelper db;
    LoggedInUser user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);
        user = db.getUser();

        final EditText usernameEditText = findViewById(R.id.username2);
        final EditText emailEditText = findViewById(R.id.email);
        final EditText passwordEditText = findViewById(R.id.password6);
        final TextView passwordTextView = findViewById(R.id.text_password);
        final Button updateButton = (Button) findViewById(R.id.update);
        final Button logoutButton = (Button) findViewById(R.id.keluar);
        final Button hapusButton = (Button) findViewById(R.id.hapus_akun);
        final View home = findViewById(R.id.btn_bintang2);

        passwordEditText.setVisibility(View.GONE);
        passwordTextView.setVisibility(View.GONE);
        usernameEditText.setText(user.getDisplayName());
        emailEditText.setText(user.getEmail());

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                if (username == null || email == null){
                    Toast.makeText(getApplicationContext(),"Tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }
                else{
                    if (db.updateUser(username,email,user.getUserId())){
                        Toast.makeText(getApplicationContext(),"Update success", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Update failed", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.downgradeSession()){
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(),"Gagal keluar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        hapusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(db.downgradeSession()){
                    if (db.deleteUser(user.getUserId())){
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"Gagal Hapus Akun", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(),"Gagal keluar", Toast.LENGTH_SHORT).show();
                }
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void showPopUp(View v) {
        //untuk show pop up menu
        PopupMenu popupMenu = new PopupMenu(this, v);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.popup_menu);
        popupMenu.show();
    }

    public boolean onMenuItemClick(MenuItem item){
        //activity saat menu ditekan
        switch (item.getItemId()){
            case R.id.item1:
                // memunculkan dialog masukan
                DialogMasukan masukan = new DialogMasukan(ProfileActivity.this);
                masukan.show();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Hubungi Kami clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                //menuju playsotre
                GoToPlayStore();
                return true;
            default:
                return false;
        }
    }
    private void GoToPlayStore(){
        //implicit intent buat ke playstore
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.laparaga.app")));
        }
        catch (android.content.ActivityNotFoundException error) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.laparaga.app")));
        }
    }
}
