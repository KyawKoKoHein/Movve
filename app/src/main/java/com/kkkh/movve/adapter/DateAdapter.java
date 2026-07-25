package com.kkkh.movve.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.kkkh.movve.R;
import com.kkkh.movve.model.DateItem;

import android.widget.TextView;

import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private Context context;
    private List<DateItem> list;

    public DateAdapter(Context context, List<DateItem> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_date, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        DateItem item = list.get(position);

        holder.txtDate.setText(item.getDate());
        holder.txtDay.setText(item.getDay());

        if(item.isSelected()){

            holder.card.setCardBackgroundColor(
                    Color.parseColor("#7903AB"));

            holder.card.setStrokeWidth(0);

        }else{

            holder.card.setCardBackgroundColor(
                    Color.parseColor("#1F1F1F"));

            holder.card.setStrokeWidth(1);

            holder.card.setStrokeColor(
                    Color.parseColor("#444444"));
        }

        holder.itemView.setOnClickListener(v->{

            for(DateItem d : list)
                d.setSelected(false);

            item.setSelected(true);

            notifyDataSetChanged();

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        MaterialCardView card;
        TextView txtDate,txtDay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card=itemView.findViewById(R.id.cardDate);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtDay=itemView.findViewById(R.id.txtDay);
        }
    }
}