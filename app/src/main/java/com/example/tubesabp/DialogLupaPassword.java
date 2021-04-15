package com.example.tubesabp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DialogLupaPassword extends DialogMasukan {

    public DialogLupaPassword(Activity a) {
        super(a);
        setOwnerActivity(a);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_lupapassword);

        EditText input_email = (EditText) findViewById(R.id.input_lupa);
        Button btn_reset = (Button) findViewById(R.id.btn_reset);

        btn_reset.setOnClickListener(new View.OnClickListener() {
            String[] email = {input_email.getText().toString()};
            @Override
            public void onClick(View v) {
                if (!isEmailValid(email[0])){
                    Toast.makeText(getOwnerActivity(), "Email Invalid", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    composeEmail(email);
                    dismiss();
                    AlertDialog.Builder builder = new AlertDialog.Builder(getOwnerActivity());
                    builder.setMessage("Tolong cek email anda untuk mereset password");
                    builder.setNeutralButton("OK", null);
                    AlertDialog sukses = builder.create();
                    sukses.show();
                }
            }
        });
    }

    private void composeEmail(String[] addresses) {
        String body = "Please resert password here";
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Reset Password");
        intent.putExtra(Intent.EXTRA_TEXT, body);
        try {
            getOwnerActivity().startActivity(intent);
            Toast.makeText(getOwnerActivity(), "Terkirim", Toast.LENGTH_SHORT).show();
        }catch (ActivityNotFoundException e){
            Log.getStackTraceString(e);
        }
    }

    private static boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

}
