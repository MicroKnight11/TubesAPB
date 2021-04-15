package com.example.tubesabp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static androidx.core.content.ContextCompat.startActivity;

public class DialogMasukan extends Dialog {

    public DialogMasukan(Activity a){
        super(a);
        setOwnerActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_masukan);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        Button btn_kirim = (Button) findViewById(R.id.btn_kirim);
        EditText input_email = (EditText) findViewById(R.id.input_email);
        EditText input_masukan = (EditText) findViewById(R.id.input_masukan);

        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //menjalankan intent composeEmail saat menekan tombol kirim
                String[] email = {input_email.getText().toString()};
                String subject = input_masukan.getText().toString();
                if (!isEmailValid(email[0])) {
                    Toast.makeText(getOwnerActivity(), "Email invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    if (subject == "") {
                        Toast.makeText(getOwnerActivity(), "Harap isi masukan", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        if (composeEmail(email,subject)){
                            dismiss();
                            AlertDialog.Builder builder = new AlertDialog.Builder(getOwnerActivity());
                            builder.setMessage("Terima kasih sudah memberi masukan :)");
                            builder.setNeutralButton("OK", null);
                            AlertDialog sukses = builder.create();
                            sukses.show();
                        }
                    }
                }
            }
        });
    }

    protected boolean composeEmail(String[] addresses, String body) {
        //implicit intent untuk mengirim email
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Masukan Tubes");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        try {
            getOwnerActivity().startActivity(intent);
            Toast.makeText(getOwnerActivity(), "Terkirim", Toast.LENGTH_SHORT).show();
            return true;
        }catch (ActivityNotFoundException e){
            Log.getStackTraceString(e);
            Toast.makeText(getOwnerActivity(), "Error", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    protected boolean isEmailValid(String email) {
        //check penulisan email
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}