package com.sanjeethdev.aquarim.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanjeethdev.aquarim.Models.LiquidRecordModel;
import com.sanjeethdev.aquarim.R;
import com.sanjeethdev.aquarim.Interfaces.RecordItemInterface;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LiquidRecordAdapter extends RecyclerView.Adapter<LiquidRecordAdapter.ViewHolder>
{
    // Array of LiquidRecordModel objects => date, liquid, quantity.
    private final ArrayList<LiquidRecordModel> localData;

    private final RecordItemInterface recordItemInterface;

    // Implemented methods of Recycler View.
    // Create new view (invoked by the layout manager).
    @NonNull
    @Override
    public LiquidRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        // Create a new view, which defines the UI of the list item.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.liquid_record_item_view, parent, false);
        return new ViewHolder(view, recordItemInterface);
    }

    // Set the values of each element in the item view.
    @Override
    public void onBindViewHolder(@NonNull LiquidRecordAdapter.ViewHolder holder, int position)
    {
        // converts long date format to readable - Tue 09:00 April 25, 2024
        Date date = new Date(localData.get(position).getDatetime());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss", Locale.US);
        holder.datetime.setText(simpleDateFormat.format(date));

        String quantity = String.valueOf(localData.get(position).getQuantity()).replace(".0", "");
        holder.quantity.setText(quantity);
        holder.liquid.setText(localData.get(position).getLiquid());
    }

    @Override
    public int getItemCount() {
        return localData.size();
    }

    // Creates new view for each set of LiquidRecordModel.
    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView liquid, datetime, quantity;

        public ViewHolder(@NonNull View itemView, RecordItemInterface recordItemInterface) {
            super(itemView);
            liquid = itemView.findViewById(R.id.liquid_record_item_liquid);
            datetime = itemView.findViewById(R.id.liquid_record_item_datetime);
            quantity = itemView.findViewById(R.id.liquid_record_item_quantity);

            itemView.setOnClickListener(view ->
            {
                if (recordItemInterface != null)
                {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION)
                    {
                        recordItemInterface.onItemClick(position);
                    }
                }
            });
        }
    }

    // Initialize the dataset of the adapter.
    public LiquidRecordAdapter(ArrayList<LiquidRecordModel> data, RecordItemInterface recordItemInterface) {
        this.localData = data;
        this.recordItemInterface = recordItemInterface;
    }
}
