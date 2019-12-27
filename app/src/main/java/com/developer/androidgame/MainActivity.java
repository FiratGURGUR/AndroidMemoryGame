package com.developer.androidgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    CardAdapter mAdapter;
    List<CardModel> mModelList;
    ProgressBar bar;
    Handler handler;
    boolean isRunning=false;
    SharedPreferences sharedPreferences;
    TextView kullanici;
    public int[] sayilar = {13,2,3,5,4,7,11,9,6};
    public int[] siralidizi;

    public ImageView canavar1;
    public ImageView canavar2;
    public ImageView canavar3;

    public int yanlisSayisi=0;
    public int dogruSayisi=0;
    public boolean isClickable = false;

    int diziSira =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        sharedPreferences = getSharedPreferences("MyApp", MODE_PRIVATE);

        kullanici = findViewById(R.id.textView);

        Kullanicikontrol();

        canavar1 = findViewById(R.id.imageView2);
        canavar2 = findViewById(R.id.imageView3);
        canavar3 = findViewById(R.id.imageView4);
        siralidizi =  diziSırala(sayilar);

        bar = findViewById(R.id.progressBar);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                bar.incrementProgressBy(5);
                if (bar.getProgress() == 100) {
                    bar.setProgress(0);
                    bar.setVisibility(View.GONE);
                    veriBos();
                    isClickable=true;
                }
            }
        };

        recyclerView = findViewById(R.id.card_recycler);
        mModelList = new ArrayList<>();

        veriDolu();
        veriDoldur();

    }


    @Override
    protected void onResume() {
        super.onResume();
        bar.setProgress(0);

        Thread backgroundThread = new Thread(new Runnable() {
            public void run() {
                try {
                    for (int i = 0; i < 20 && isRunning; i++) {
                        Thread.sleep(300);
                        handler.sendMessage(handler.obtainMessage());
                    }
                }
                catch (Throwable t) {

                }
            }
        });
        isRunning = true;
        backgroundThread.start();

    }

    public void onStop() {
        super.onStop();
        isRunning = false;
    }

    public void veriDolu(){
        mModelList.add(new CardModel("13"));
        mModelList.add(new CardModel("2"));
        mModelList.add(new CardModel("3"));
        mModelList.add(new CardModel("5"));
        mModelList.add(new CardModel("4"));
        mModelList.add(new CardModel("7"));
        mModelList.add(new CardModel("11"));
        mModelList.add(new CardModel("9"));
        mModelList.add(new CardModel("6"));
    }

    public void veriDoldur(){
        mAdapter =new CardAdapter(mModelList, this, new CustomItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {

                //Toast.makeText(MainActivity.this, mModelList.get(position).getSayi(), Toast.LENGTH_SHORT).show();

                if (isClickable){
                    Oyun(position);
                }else {
                    Toast.makeText(MainActivity.this, "Oyun henüz başlamadı", Toast.LENGTH_SHORT).show();
                }




            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(mAdapter);
    }

    public void veriBos(){
        for (int i=0;i<9;i++){
            verileriGizle(i);
        }
    }

    public void verileriGizle(int pos){
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
        View view = viewHolder.itemView;
        TextView etDesc = (TextView) view.findViewById(R.id.tvSayi);
        etDesc.setVisibility(View.INVISIBLE);
    }


    public void verileriGoster(int pos){
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
        View view = viewHolder.itemView;
        final TextView etDesc = (TextView) view.findViewById(R.id.tvSayi);
        etDesc.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                etDesc.setVisibility(View.INVISIBLE);
            }
        }, 1200);

    }


    public void kirmiziyak(int pos){
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
        View view = viewHolder.itemView;
        final CardView etDesc = (CardView) view.findViewById(R.id.itemcard);
        etDesc.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.wrongcolor)));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                etDesc.setBackgroundTintList(ColorStateList.valueOf(getColor(R.color.cardbackground)));
            }
        }, 1200);

    }


    public void Kullanicikontrol(){
        String s1="";
        s1 = sharedPreferences.getString("name","none");
        if (s1.equals("none") || s1.equals("") ){
            Intent open= new Intent(MainActivity.this,StartActivity.class);
            startActivity(open);
        }else {
            kullanici.setText(s1);
        }
    }


    public static int[] diziSırala(int dizi[]){
        for(int i=0;i<dizi.length - 1;i++){
            for(int j=0;j<dizi.length - (i+1);j++){
                if(dizi[j]>dizi[j+1]){
                    int tmp = dizi[j];
                    dizi[j] = dizi[j+1];
                    dizi[j+1] = tmp;
                }
            }
        }
        return dizi;
    }



    public void Oyun(int position){


        if (dogruSayisi >= 8){
            Intent sonuc = new Intent(MainActivity.this,SonucActivity.class);
            sonuc.putExtra("gameSonuc","win");
            startActivity(sonuc);
            finish();
        }else {
            if (yanlisSayisi <= 2){
                if (Integer.parseInt(mModelList.get(position).getSayi()) == siralidizi[diziSira]){
                    tersCevir(position);
                    diziSira++;
                    dogruSayisi++;
                }else {
                    verileriGoster(position);
                    yanlisSayisi++;
                    YanlisSayiKontrol(yanlisSayisi);
                    kirmiziyak(position);
                    if (yanlisSayisi >= 3){
                        Intent sonuc = new Intent(MainActivity.this,SonucActivity.class);
                        sonuc.putExtra("gameSonuc","gameover");
                        startActivity(sonuc);
                        finish();
                    }
                }
            }else {
                Intent sonuc = new Intent(MainActivity.this,SonucActivity.class);
                sonuc.putExtra("gameSonuc","gameover");
                startActivity(sonuc);
                finish();
            }
        }

    }


    public void tersCevir(int pos){
        RecyclerView.ViewHolder viewHolder = recyclerView.findViewHolderForAdapterPosition(pos);
        View view = viewHolder.itemView;
        final TextView etDesc = (TextView) view.findViewById(R.id.tvSayi);
        etDesc.setVisibility(View.VISIBLE);
        etDesc.setEnabled(false);
    }


    public void YanlisSayiKontrol(int deger){
        if (deger==1)
            canavar1.setBackgroundResource(R.drawable.monster2);
        if (deger==2)
            canavar2.setBackgroundResource(R.drawable.monster2);
        if (deger==3)
            canavar3.setBackgroundResource(R.drawable.monster2);
    }




}
