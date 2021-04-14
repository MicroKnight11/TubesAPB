package com.example.tubesabp;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

public class MainActivityLapangan extends MainActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lapangan);
        View back_btn = findViewById(R.id.back_btn);

        Intent i = getIntent();
        String olahraga = i.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView text_olahraga = findViewById(R.id.text_olahraga);
        text_olahraga.setText(olahraga);

        LinearLayout linear = findViewById(R.id.linear_layout);

        for (int j=0; j < linear.getChildCount(); j++){
            CardView card = (CardView) linear.getChildAt(j);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String a = "card clicked";
                    Toast.makeText(getApplicationContext(), a, Toast.LENGTH_SHORT).show();
                }
            });
        }

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
