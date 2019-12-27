package com.developer.androidgame;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        public TextView aSayi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            aSayi = itemView.findViewById(R.id.tvSayi);
        }
    }

    private List<CardModel> card_list;
    private Context context;
    CustomItemClickListener listener;
    CardAdapter(List<CardModel> card_list, Context context,CustomItemClickListener listener){
        this.card_list = card_list;
        this.context = context;
        this.listener=listener;
    }

    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vr = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_row,parent,false);
        final  ViewHolder view_holder = new ViewHolder(vr);

        vr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.onItemClick(view,view_holder.getPosition());
            }
        });



        return view_holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.aSayi.setText(String.valueOf(card_list.get(position).getSayi()));

    }

    @Override
    public int getItemCount() {
        return card_list.size();
    }





}