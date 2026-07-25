package com.kkkh.movve.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.button.MaterialButton;
import com.kkkh.movve.CinemaScheduleActivity;
import com.kkkh.movve.R;
import com.kkkh.movve.model.Cinema;

import java.util.List;

public class CinemaAdapter extends RecyclerView.Adapter<CinemaAdapter.ViewHolder> {

    private Context context;
    private List<Cinema> cinemaList;
    private int selectedCinemaPosition = -1;
    private String selectedTime = "";

    public CinemaAdapter(Context context, List<Cinema> cinemaList) {
        this.context = context;
        this.cinemaList = cinemaList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cinema, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Cinema cinema = cinemaList.get(position);

        holder.imgCinema.setImageResource(cinema.getImage());
        holder.txtName.setText(cinema.getName());
        holder.txtLocation.setText(cinema.getLocation());

        holder.btnBuy.setOnClickListener(v -> {

            Intent intent = new Intent(context,
                    CinemaScheduleActivity.class);

            intent.putExtra("cinemaName", cinema.getName());

            intent.putExtra("cinemaImage", cinema.getImage());

            context.startActivity(intent);

        });

    }

    @Override
    public int getItemCount() {
        return cinemaList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgCinema;
        TextView txtName;
        TextView txtLocation;
        TextView txtScreen;
        MaterialButton btnBuy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgCinema = itemView.findViewById(R.id.imgCinema);
            txtName = itemView.findViewById(R.id.txtCinemaName);
            txtLocation = itemView.findViewById(R.id.txtLocation);
            btnBuy = itemView.findViewById(R.id.btnCinemaBuy);
        }
    }

    // To handle button in choosing time
    private void updateButton(MaterialButton button,
                              String time,
                              Cinema cinema,
                              int position) {

        if (selectedCinemaPosition == -1) {

            button.setEnabled(true);
            button.setAlpha(1f);

        } else if (selectedCinemaPosition == position) {

            button.setEnabled(true);
            button.setAlpha(1f);

        } else {

            button.setEnabled(false);
            button.setAlpha(0.4f);

        }

        if (position == selectedCinemaPosition &&
                time.equals(selectedTime)) {

            button.setBackgroundColor(Color.parseColor("#7903AB"));
            button.setTextColor(Color.WHITE);

        } else {

            button.setBackgroundColor(Color.parseColor("#2A2A2A"));
            button.setTextColor(Color.WHITE);

        }

        button.setOnClickListener(v -> {

            selectedCinemaPosition = position;
            selectedTime = time;

            cinema.setSelectedTime(time);

            notifyDataSetChanged();

        });
    }
}