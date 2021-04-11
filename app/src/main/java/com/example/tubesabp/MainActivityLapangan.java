package com.example.tubesabp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

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

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
