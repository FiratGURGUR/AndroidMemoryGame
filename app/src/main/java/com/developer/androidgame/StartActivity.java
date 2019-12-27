package com.developer.androidgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

public class StartActivity extends AppCompatActivity {
    EditText isim;
    Button basla;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_start);


        isim = findViewById(R.id.editText);
        basla = findViewById(R.id.button);



    }

    @Override
    protected void onResume() {
        super.onResume();
        sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);
        isim.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (charSequence.length()>3){
                    basla.setVisibility(View.VISIBLE);
                }else {
                    basla.setVisibility(View.INVISIBLE);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        basla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String name = isim.getText().toString();
                if (name.isEmpty() || name == null){
                    showProgressDialog(R.drawable.warning_circle,"İsim girmeniz gerekli");
                }else {
                    if (name.length()<3){
                        showProgressDialog(R.drawable.warning_circle,"İsim en az 3 karakter olmalıdır!");
                    }else {
                        editSharedPref("name",name);
                        Intent openGame = new Intent(StartActivity.this , MainActivity.class);
                        startActivity(openGame);
                    }


                }

            }
        });


    }




    ShowCustomAlertDialog pdf;
    protected void showProgressDialog(int drawable,String message) {
        pdf = new ShowCustomAlertDialog(drawable ,message, this);
        FragmentManager fm = getSupportFragmentManager();
        pdf.show(fm,"firat");
    }


    public void editSharedPref(String key ,String value) {
        SharedPreferences.Editor settingsEditor = sharedPreferences.edit(); //sharedpreferences update işlemi yapıyorum
        settingsEditor.putString(key, value);
        settingsEditor.commit();
    }

}
