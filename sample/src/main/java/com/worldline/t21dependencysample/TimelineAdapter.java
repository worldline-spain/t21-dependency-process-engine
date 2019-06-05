package com.worldline.t21dependencysample;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TimelineAdapter extends RecyclerView.Adapter<TimelineItemViewHolder> {

    private List<Item> items;

    private boolean processRunning;

    public TimelineAdapter() {
        items = new ArrayList<>();
    }

    @NonNull
    @Override
    public TimelineItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(parent.getContext());
        return new TimelineItemViewHolder(li.inflate(R.layout.item_main, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineItemViewHolder holder, int position) {
        holder.onBind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items != null ? items.size() : 0;
    }

    public void addItems(List<Item> items) {
        this.items.addAll(items);
        notifyDataSetChanged();
    }

    public void clear() {
        items.clear();
        notifyDataSetChanged();
    }

    public void finishWithSuccess(String pointTitle) {
        if (getItemCount() > 0) {
            processRunning = false;
            items.get(getItemCount() - 1).setSuccess(true);
            items.get(getItemCount() - 1).setDescription(pointTitle);
            for (Item item : items) {
                item.setProcessFinishedSuccessfully(true);
            }
            notifyDataSetChanged();
        }
    }

    public void finishWithError(String errorStates) {
        if (getItemCount() > 0) {
            processRunning = false;
            items.get(getItemCount() - 1).setError(true);
            items.get(getItemCount() - 1).setDescription(errorStates);
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void reset() {
        for (Item item : items) {
            item.setProcessFinishedSuccessfully(false);
            item.setError(false);
            item.setSuccess(false);
        }
        notifyDataSetChanged();
    }

    public void start() {
        if (getItemCount() > 0) {
            processRunning = true;
            items.get(0).setSuccess(true);
            notifyItemChanged(0);
        }
    }

    public boolean isProcessRunning() {
        return processRunning;
    }
}
