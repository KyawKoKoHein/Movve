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

import java.util.ArrayList;
import java.util.List;

public class DateAdapter extends RecyclerView.Adapter<DateAdapter.ViewHolder> {

    private Context context;
    private ArrayList<DateItem> dateList;
    private OnDateSelectedListener listener;

    public interface OnDateSelectedListener {
        void onDateSelected(String date, String day);
    }

    public DateAdapter(Context context,
                       ArrayList<DateItem> dateList,
                       OnDateSelectedListener listener) {

        this.context = context;
        this.dateList = dateList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_date, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull ViewHolder holder,
            int position) {

        DateItem item = dateList.get(position);

        holder.txtDate.setText(item.getDate());
        holder.txtDay.setText(item.getDay());

        if (item.isSelected()) {

            holder.card.setCardBackgroundColor(
                    Color.parseColor("#7903AB"));

        } else {

            holder.card.setCardBackgroundColor(
                    Color.parseColor("#2A2A2A"));
        }

        holder.itemView.setOnClickListener(v -> {

            for (DateItem date : dateList) {
                date.setSelected(false);
            }

            item.setSelected(true);

            notifyDataSetChanged();

            if (listener != null) {
                listener.onDateSelected(
                        item.getDate(),
                        item.getDay());
            }
        });
    }

    @Override
    public int getItemCount() {
        return dateList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView card;
        TextView txtDate;
        TextView txtDay;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.cardDate);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtDay = itemView.findViewById(R.id.txtDay);
        }
    }
}