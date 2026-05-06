package com.example.subtrack.ui.addedit;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subtrack.R;
import com.example.subtrack.data.model.SubscriptionTemplate;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.TemplateViewHolder> {

    private final List<SubscriptionTemplate> templates;
    private final OnTemplateClickListener listener;

    public interface OnTemplateClickListener {
        void onTemplateClick(SubscriptionTemplate template);
    }

    public TemplateAdapter(List<SubscriptionTemplate> templates, OnTemplateClickListener listener) {
        this.templates = templates;
        this.listener = listener;
    }

    @NonNull
    @Override
    public TemplateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_template, parent, false);
        return new TemplateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TemplateViewHolder holder, int position) {
        SubscriptionTemplate template = templates.get(position);
        holder.bind(template, listener);
    }

    @Override
    public int getItemCount() {
        return templates.size();
    }

    static class TemplateViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView cardLogo;
        TextView textInitial;
        TextView textName;
        TextView textPrice;

        public TemplateViewHolder(@NonNull View itemView) {
            super(itemView);
            cardLogo = itemView.findViewById(R.id.cardLogo);
            textInitial = itemView.findViewById(R.id.textInitial);
            textName = itemView.findViewById(R.id.textName);
            textPrice = itemView.findViewById(R.id.textPrice);
        }

        public void bind(SubscriptionTemplate template, OnTemplateClickListener listener) {
            textName.setText(template.name);
            textPrice.setText("₹" + template.defaultCost);
            
            if (template.name != null && !template.name.isEmpty()) {
                textInitial.setText(String.valueOf(template.name.charAt(0)));
            }
            
            try {
                cardLogo.setCardBackgroundColor(Color.parseColor(template.colorHex));
            } catch (Exception e) {
                cardLogo.setCardBackgroundColor(Color.GRAY);
            }

            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    listener.onTemplateClick(template);
                }
            });
        }
    }
}
