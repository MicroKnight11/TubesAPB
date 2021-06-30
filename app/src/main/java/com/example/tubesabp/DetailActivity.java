package com.example.tubesabp;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tubesabp.data.model.Detail;
import com.example.tubesabp.data.model.Maps;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity implements OnMapReadyCallback {

    private MapView mapView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        String olahraga = i.getStringExtra("Olahraga");
        String id = i.getStringExtra("Id");

        TextView Vnama = findViewById(R.id.text_lapang);
        TextView detail = findViewById(R.id.text_detail);
        ImageView gambar = findViewById(R.id.imageView2);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://tubes-3464a-default-rtdb.asia-southeast1.firebasedatabase.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCall apiCall = retrofit.create(ApiCall.class);
        Call<Detail> call = apiCall.getData(olahraga, id);
        call.enqueue(new Callback<Detail>() {
            @Override
            public void onResponse(Call<Detail> call, Response<Detail> response) {
                if(!response.isSuccessful()){
                    return;
                }
                String nama = response.body().getNama();
                Vnama.setText(nama);
                Picasso.get().load(response.body().getGambar()).into(gambar);
                String deskripsi = response.body().getAlamat() +"\n\n" + response.body().getDeskripsi();
                detail.setText(deskripsi);
                float lati = response.body().getLati();
                float longi = response.body().getLongi();
                setMaps(nama, lati, longi);
            }

            @Override
            public void onFailure(Call<Detail> call, Throwable t) {

            }
        });

        mapView = (MapView) findViewById(R.id.map);
        Bundle mapViewBundle = null;
        if (savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle("AIzaSyChEkfH1c8zld_rOttcmwuvfbBbAzH63qE");
        }
        mapView.onCreate(mapViewBundle);
        mapView.getMapAsync(this);

        ImageView back = (ImageView) findViewById(R.id.back_btn2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap map) {
        float lati = ((Maps) this.getApplication()).getLati();
        float longi = ((Maps) this.getApplication()).getLongi();
        String nama = ((Maps) this.getApplication()).getNama();
        LatLng lapang = new LatLng(lati,longi);
        map.addMarker(new MarkerOptions().position(lapang).title(nama));
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(lapang, 14.0f));
    }

    public void setMaps(String nama, float lati, float longi){
        ((Maps) this.getApplication()).setNama(nama);
        ((Maps) this.getApplication()).setLati(lati);
        ((Maps) this.getApplication()).setLongi(longi);
    }

}
