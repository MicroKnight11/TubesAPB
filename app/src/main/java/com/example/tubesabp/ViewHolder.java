package com.example.tubesabp;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import static androidx.core.content.ContextCompat.startActivity;
import static com.example.tubesabp.MainActivity.EXTRA_MESSAGE;

public class ViewHolder extends RecyclerView.ViewHolder {
    View view;

    public ViewHolder(@NonNull View itemView){
        super(itemView);
        view = itemView;
    }

    public void setDetail(Context context, String nama, String alamat, String gambar) {
        TextView Vnama = view.findViewById(R.id.text_lapangan);
        TextView Valamat = view.findViewById(R.id.text_alamat);
        ImageView Vgambar = view.findViewById(R.id.gambar_lapangan);

        Vnama.setText(nama);
        Valamat.setText(alamat);
        Picasso.get().load(gambar).into(Vgambar);
    }
}
