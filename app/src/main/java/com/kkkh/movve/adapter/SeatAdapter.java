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
import com.kkkh.movve.model.Seat;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.ViewHolder> {

    private Context context;
    private List<Seat> seatList;

    public interface OnSeatSelectedListener {
        void onSeatChanged(ArrayList<String> selectedSeats);
    }

    private OnSeatSelectedListener listener;

    public SeatAdapter(Context context,
                       List<Seat> seatList,
                       OnSeatSelectedListener listener) {

        this.context = context;
        this.seatList = seatList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent,
                                         int viewType) {

        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_seat, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder,
                                 int position) {

        Seat seat = seatList.get(position);

        holder.txtSeat.setText(seat.getNumber());

        if (seat.isReserved()) {

            holder.cardSeat.setCardBackgroundColor(
                    Color.parseColor("#ED1111"));

        } else if (seat.isSelected()) {

            holder.cardSeat.setCardBackgroundColor(
                    Color.parseColor("#7903AB"));

        } else {

            holder.cardSeat.setCardBackgroundColor(
                    Color.parseColor("#2A2A2A"));

        }

        holder.itemView.setOnClickListener(v -> {

            if (seat.isReserved())
                return;

            seat.setSelected(!seat.isSelected());

            notifyItemChanged(position);

            ArrayList<String> selected = new ArrayList<>();

            for (Seat s : seatList) {

                if (s.isSelected()) {
                    selected.add(s.getNumber());
                }

            }

            listener.onSeatChanged(selected);

        });

    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView cardSeat;
        TextView txtSeat;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cardSeat = itemView.findViewById(R.id.cardSeat);
            txtSeat = itemView.findViewById(R.id.txtSeat);
        }
    }
}