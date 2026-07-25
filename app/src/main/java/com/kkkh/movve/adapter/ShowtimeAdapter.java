package com.kkkh.movve.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.card.MaterialCardView;
import com.kkkh.movve.R;
import com.kkkh.movve.model.ShowTime;

import java.util.List;

public class ShowtimeAdapter extends RecyclerView.Adapter<ShowtimeAdapter.ViewHolder> {

    private Context context;
    private List<ShowTime> list;

    private int cinemaIndex;
    private OnShowtimeClickListener listener;

    private boolean active = true;

    public ShowtimeAdapter(Context context,
                           List<ShowTime> list,
                           int cinemaIndex,
                           OnShowtimeClickListener listener) {

        this.context = context;
        this.list = list;
        this.cinemaIndex = cinemaIndex;
        this.listener = listener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_showtime, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        ShowTime item = list.get(position);

        holder.txtTime.setText(item.getTime());

        if (active) {

            holder.card.setAlpha(1f);

        } else {

            holder.card.setAlpha(0.45f);

        }

        if (item.isSelected()) {

            holder.card.setCardBackgroundColor(
                    Color.parseColor("#7903AB"));

            holder.card.setStrokeWidth(0);

        } else {

            holder.card.setCardBackgroundColor(
                    Color.parseColor("#1F1F1F"));

            holder.card.setStrokeWidth(1);

            holder.card.setStrokeColor(
                    Color.parseColor("#444444"));

        }

        holder.itemView.setOnClickListener(v -> {

            if (listener != null) {

                listener.onClick(cinemaIndex,
                        holder.getAdapterPosition());

            }

        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    //Disable & Enable selected time between cinemas
    public void setEnabled(boolean enabled) {

        for (ShowTime time : list) {

            time.setEnabled(enabled);

            if (!enabled) {
                time.setSelected(false);
            }

        }

        notifyDataSetChanged();
    }

    public void setActive(boolean active) {

        this.active = active;
        notifyDataSetChanged();

    }

    public void selectPosition(int position) {

        for (int i = 0; i < list.size(); i++) {

            list.get(i).setSelected(position == i);

        }

        notifyDataSetChanged();

    }

    public interface OnShowtimeClickListener {
        void onClick(int cinemaIndex, int position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView card;
        TextView txtTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            card = itemView.findViewById(R.id.cardTime);
            txtTime = itemView.findViewById(R.id.txtTime);
        }
    }
}