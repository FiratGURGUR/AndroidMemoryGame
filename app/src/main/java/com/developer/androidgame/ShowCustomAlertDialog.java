package com.developer.androidgame;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;

public class ShowCustomAlertDialog extends DialogFragment {
    AlertDialog dialogKayitOl;
    public String message;
    public Context context;
    public int drawable;


    //Fragmentlarda context "getApplicationContext" olacak Activitylerde "this" olacak
    public ShowCustomAlertDialog(int drawable, String message, Context context) {
        this.message = message;
        this.context = context;
        this.drawable = drawable;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builderRegister = new AlertDialog.Builder(context);
        View viewRegister = getActivity().getLayoutInflater().inflate(R.layout.uyari_kard_dialog,null);
        builderRegister.setView(viewRegister);
        dialogKayitOl = builderRegister.create();
        dialogKayitOl.requestWindowFeature(Window.FEATURE_NO_TITLE);
        WindowManager.LayoutParams wmlpRegister = dialogKayitOl.getWindow().getAttributes();
        wmlpRegister.gravity = Gravity.CENTER;

        TextView txt = viewRegister.findViewById(R.id.textView22);
        ImageView imageView = viewRegister.findViewById(R.id.imageView22);

        Glide.with(context)
                .load(drawable)
                .into(imageView);

        txt.setText(message);

        Button kapat = viewRegister.findViewById(R.id.button25);


        dialogKayitOl.show();
        dialogKayitOl.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        kapat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogKayitOl.dismiss();
            }
        });


        // Disable the back button
        DialogInterface.OnKeyListener keyListener = new DialogInterface.OnKeyListener() {

            @Override
            public boolean onKey(DialogInterface dialog, int keyCode,
                                 KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    return true;
                }
                return false;
            }

        };

        dialogKayitOl.setOnKeyListener(keyListener);
        return dialogKayitOl;
    }
}
