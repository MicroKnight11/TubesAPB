package com.example.tubesabp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.example.tubesabp.data.model.LoggedInUser;


public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static final String EXTRA_MESSAGE = "message";
    DatabaseHelper db;
    LoggedInUser activeUser;
    Boolean checkSession;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(TAG, "onCreate");

        db = new DatabaseHelper(this);
        checkSession = db.checkSession("0");

        GridLayout gridLayout = findViewById(R.id.grid_layout);
        View user = findViewById(R.id.userview);
        user.setOnClickListener(new View.OnClickListener(){
            //pindah ke login saat menekean icon pada kanan bawah
            @Override
            public void onClick(View v) {
                if(checkSession){
                    GoToLogin();
                }
                else{
                    GoToProfile();
                }
            }
        });
        //set setiap cardview yang ada saat di click akan buat intent ke activity_lapangan
        for (int i=0; i<gridLayout.getChildCount(); i++){
            CardView card = (CardView) gridLayout.getChildAt(i);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GoToActivityLapangan(card);
                }
            });
        }
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
                DialogMasukan masukan = new DialogMasukan(MainActivity.this);
                masukan.show();
//                Toast.makeText(this,"item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Hubungi Kami clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
                //menuju playsotre
                GoToPlayStore();
//                Toast.makeText(this,"Nilai Kami clicked", Toast.LENGTH_SHORT).show();
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

    private void GoToActivityLapangan(CardView card){
        //explicit intent buat ke activity_lapangan
        Intent i = new Intent(MainActivity.this,MainActivityLapangan.class);
        ConstraintLayout constraint = (ConstraintLayout) card.getChildAt(0);
        TextView textView = (TextView) constraint.getChildAt(0);
        String olahraga = textView.getText().toString();
        i.putExtra(EXTRA_MESSAGE, olahraga);
        startActivity(i);
    }

    private void GoToLogin(){
        //explicit intent buat ke halaman login
        Intent i = new Intent(MainActivity.this, Login.class);
        startActivity(i);
    }

    private void GoToProfile(){
        //explicit intent buat ke halaman login
        Intent i = new Intent(MainActivity.this, ProfileActivity.class);
        startActivity(i);
    }

// logging

    public String TAG = "Messages";

    @Override
    protected void onStart() {
        super.onStart();

        TextView text1 = (TextView)findViewById(R.id.text_selamat);
        TextView text2 = (TextView)findViewById(R.id.text_masuk);
        TextView text3 = (TextView)findViewById(R.id.text_clickable);
        // session
        checkSession = db.checkSession("0");
        if(checkSession == false){
            activeUser = db.getUser();
            text1.setText("Hi, "+ activeUser.getDisplayName());
            text2.setText("Mau apa hari ini ?");
            text3.setVisibility(View.GONE);
        }
        else{
            text1.setText("Selamat Datang !");
            text2.setText("Silahkan Masuk atau Daftar ");
            text3.setVisibility(View.VISIBLE);
        }
        Log.i(TAG, "onStart");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.i(TAG, "onRestart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(TAG, "onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(TAG, "onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(TAG, "onDestroy");
    }
}