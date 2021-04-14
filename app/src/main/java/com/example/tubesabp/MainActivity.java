package com.example.tubesabp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.gridlayout.widget.GridLayout;

import android.animation.PropertyValuesHolder;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.net.URI;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    public static final String EXTRA_MESSAGE = "message";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout gridLayout = findViewById(R.id.grid_layout);

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
        switch (item.getItemId()){
            case R.id.item1:
                DialogMasukan masukan = new DialogMasukan(MainActivity.this);
//                Window window = masukan.getWindow();
//                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                window.setGravity(Gravity.CENTER);
                masukan.show();
//                Toast.makeText(this,"item 1 clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item2:
                Toast.makeText(this,"Hubungi Kami clicked", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.item3:
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
        //explicit intent buat ke activity_lapangan`
        Intent i = new Intent(MainActivity.this,MainActivityLapangan.class);
        ConstraintLayout constraint = (ConstraintLayout) card.getChildAt(0);
        TextView textView = (TextView) constraint.getChildAt(0);
        String olahraga = textView.getText().toString();
        i.putExtra(EXTRA_MESSAGE, olahraga);
        startActivity(i);
    }

    private String TAG = "Messages";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.i(TAG, "onCreate");

    }

    @Override
    protected void onStart() {
        super.onStart();
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