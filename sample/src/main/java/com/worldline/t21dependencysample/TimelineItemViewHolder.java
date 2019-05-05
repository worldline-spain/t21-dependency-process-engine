package com.worldline.t21dependencysample;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.alorma.timeline.TimelineView;

public class TimelineItemViewHolder extends RecyclerView.ViewHolder {

    TimelineView timelineView;

    TextView textView;

    public TimelineItemViewHolder(View itemView) {
        super(itemView);
        timelineView = itemView.findViewById(R.id.timeline);
        textView = itemView.findViewById(R.id.textView);
    }

    public void onBind(Item item) {
        timelineView.setTimelineType(item.getType());
        textView.setText(String.format("%s %s", item.getId(), item.getDescription()));
        if (item.isSuccess()) {
            timelineView.setIndicatorColor(Color.parseColor("green"));
        } else if (item.isError()) {
            timelineView.setIndicatorColor(Color.parseColor("red"));
        } else if (item.isProcessFinishedSuccessfully()) {
            //If the process finished successfully, and a task is neither success nor error,
            //that means that the task was skipped because it's optional (not included in the required states).
            timelineView.setIndicatorColor(Color.parseColor("yellow"));
        } else {
            timelineView.setIndicatorColor(Color.parseColor("white"));
        }
    }
}
