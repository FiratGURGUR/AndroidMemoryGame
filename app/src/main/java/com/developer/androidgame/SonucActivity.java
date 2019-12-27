package com.developer.androidgame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SonucActivity extends AppCompatActivity {

    TextView sonuccc;
    Button tekrarOyna;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sonuc);

        sonuccc = findViewById(R.id.textView4);
        tekrarOyna = findViewById(R.id.button2);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            String gelensonuc = extras.getString("gameSonuc");
            if (gelensonuc.equals("win")){
                sonuccc.setText("You Win...");
            }else {
                sonuccc.setText("Game Over...");
            }
        }


        tekrarOyna.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent tekrar = new Intent(SonucActivity.this,MainActivity.class);
                startActivity(tekrar);
                finish();
            }
        });


    }
}
