package com.example.subtrack.ui.list;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.subtrack.R;
import com.example.subtrack.data.model.Subscription;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class SubscriptionAdapter extends ListAdapter<Subscription, SubscriptionAdapter.SubscriptionViewHolder> {

    private OnItemClickListener listener;

    public SubscriptionAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Subscription> DIFF_CALLBACK = new DiffUtil.ItemCallback<Subscription>() {
        @Override
        public boolean areItemsTheSame(@NonNull Subscription oldItem, @NonNull Subscription newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Subscription oldItem, @NonNull Subscription newItem) {
            return oldItem.name.equals(newItem.name) &&
                    oldItem.cost == newItem.cost &&
                    oldItem.nextRenewalDate == newItem.nextRenewalDate &&
                    oldItem.isTrial == newItem.isTrial &&
                    (oldItem.paymentMethod == null ? newItem.paymentMethod == null : oldItem.paymentMethod.equals(newItem.paymentMethod));
        }
    };

    @NonNull
    @Override
    public SubscriptionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_subscription, parent, false);
        return new SubscriptionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionViewHolder holder, int position) {
        Subscription currentSub = getItem(position);
        holder.bind(currentSub);
    }

    public Subscription getSubscriptionAt(int position) {
        return getItem(position);
    }

    class SubscriptionViewHolder extends RecyclerView.ViewHolder {
        private final TextView textName;
        private final TextView textCategory;
        private final TextView textPaymentMethod;
        private final TextView textCost;
        private final TextView textBillingCycle;
        private final TextView textRenewalDate;
        private final TextView colorTag;
        private final TextView badgeTrial;

        public SubscriptionViewHolder(View itemView) {
            super(itemView);
            textName = itemView.findViewById(R.id.textName);
            textCategory = itemView.findViewById(R.id.textCategory);
            textPaymentMethod = itemView.findViewById(R.id.textPaymentMethod);
            textCost = itemView.findViewById(R.id.textCost);
            textBillingCycle = itemView.findViewById(R.id.textBillingCycle);
            textRenewalDate = itemView.findViewById(R.id.textRenewalDate);
            colorTag = itemView.findViewById(R.id.colorTag);
            badgeTrial = itemView.findViewById(R.id.badgeTrial);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }

        public void bind(Subscription sub) {
            textName.setText(sub.name);
            textCategory.setText(sub.category);

            if (sub.paymentMethod != null && !sub.paymentMethod.isEmpty()) {
                textPaymentMethod.setText("via " + sub.paymentMethod);
                textPaymentMethod.setVisibility(View.VISIBLE);
            } else {
                textPaymentMethod.setVisibility(View.GONE);
            }
            
            // Format cost
            String currencySymbol = (sub.currency != null && !sub.currency.isEmpty()) ? sub.currency : "₹";
            String costStr = String.format(Locale.getDefault(), "%s%.2f", currencySymbol, sub.cost);
            textCost.setText(costStr);
            
            textBillingCycle.setText("/" + (sub.billingCycle != null ? sub.billingCycle : "monthly"));
            
            // Format Date
            if (sub.nextRenewalDate > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
                String label = sub.isTrial ? "Trial Ends: " : "Renews: ";
                textRenewalDate.setText(label + sdf.format(new Date(sub.isTrial ? sub.trialEndDate : sub.nextRenewalDate)));
            } else {
                textRenewalDate.setText("");
            }

            badgeTrial.setVisibility(sub.isTrial ? View.VISIBLE : View.GONE);
            
            // Set Color and Logo Initial
            if (sub.colorTag != null && !sub.colorTag.isEmpty()) {
                try {
                    colorTag.getBackground().setTint(Color.parseColor(sub.colorTag));
                } catch (IllegalArgumentException e) {
                    // Ignore invalid colors
                }
            }
            
            if (sub.name != null && !sub.name.isEmpty()) {
                colorTag.setText(String.valueOf(sub.name.charAt(0)).toUpperCase());
            } else {
                colorTag.setText("");
            }
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Subscription subscription);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
