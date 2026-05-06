package com.example.foodsafe.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.foodsafe.R;
import com.example.foodsafe.database.ScanHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {

    private List<ScanHistory> historyList = new ArrayList<>();
    private OnItemClickListener listener;

    public interface OnItemClickListener {
        void onItemClick(ScanHistory history);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setHistoryList(List<ScanHistory> historyList) {
        this.historyList = historyList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recent_scan, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ScanHistory history = historyList.get(position);
        holder.productName.setText(history.getProductName());
        holder.brandName.setText(history.getBrandName());
        holder.healthScore.setText("Score: " + history.getHealthScore());

        Glide.with(holder.itemView.getContext())
                .load(history.getImageUrl())
                .placeholder(android.R.drawable.ic_menu_gallery)
                .into(holder.productImage);

        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onItemClick(history);
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage;
        TextView productName, brandName, healthScore;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.productImage);
            productName = itemView.findViewById(R.id.productName);
            brandName = itemView.findViewById(R.id.brandName);
            healthScore = itemView.findViewById(R.id.healthScore);
        }
    }
}
