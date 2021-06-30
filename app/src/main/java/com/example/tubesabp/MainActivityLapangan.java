package com.example.tubesabp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Script;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tubesabp.data.model.Lapangan;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.invoke.ConstantCallSite;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.tubesabp.MainActivity.EXTRA_MESSAGE;

public class MainActivityLapangan extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recycler;
    FirebaseDatabase firebase;
    DatabaseReference reference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapangan);
        View back_btn = findViewById(R.id.back_btn);

        //ambil intent sebelumnya
        Intent i = getIntent();
        String olahraga = i.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //mengganti text_olahraga (atas kiri) sesuai dengan text card pada intent sebelumnya
        TextView text_olahraga = findViewById(R.id.text_olahraga);
        text_olahraga.setText(olahraga);

//        LinearLayout linear = findViewById(R.id.linear_layout);
        recycler = findViewById(R.id.recyclerView);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));

        firebase = FirebaseDatabase.getInstance();
        reference = firebase.getReference("Data/" + olahraga);
        FirebaseRecyclerAdapter<Lapangan,ViewHolder>firebaseRecyclerAdapter =
                new FirebaseRecyclerAdapter<Lapangan, ViewHolder>(
                        Lapangan.class,
                        R.layout.card_lapangan,
                        ViewHolder.class,
                        reference
                ) {
                    @Override
                    protected void populateViewHolder(ViewHolder viewHolder, Lapangan lapangan, int i) {
                        viewHolder.setDetail(getApplicationContext(),lapangan.getNama(),lapangan.getAlamat(),lapangan.getGambar());
                    }
                };
        recycler.setAdapter(firebaseRecyclerAdapter);

        back_btn.setOnClickListener(new View.OnClickListener() {
            //mendestroy intent saat ini
            @Override
            public void onClick(View v) {
                        finish();
                    }
        });

        recycler.addOnItemTouchListener(new RecyclerItemClickListener(this, recycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent i = new Intent(MainActivityLapangan.this,DetailActivity.class);
                i.putExtra("Olahraga", olahraga);
                i.putExtra("Id", Integer.toString(position+1));
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {
            }
        }));

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
                DialogMasukan masukan = new DialogMasukan(MainActivityLapangan.this);
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
