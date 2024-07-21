package com.sanjeethdev.aquarim;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class EntryRecordAdapter extends RecyclerView.Adapter<EntryRecordAdapter.ViewHolder> {

    EntryRecordModel localEntryRecordModel;

    @NonNull
    @Override
    public EntryRecordAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.entry_record_item_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EntryRecordAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return localEntryRecordModel.getSize();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView liquid, datetime, quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            liquid = itemView.findViewById(R.id.entry_record_item_liquid);
            datetime = itemView.findViewById(R.id.entry_record_item_datetime);
            quantity = itemView.findViewById(R.id.entry_record_item_quantity);
        }
    }

    public EntryRecordAdapter(EntryRecordModel data) {
        localEntryRecordModel = data;
    }


}
