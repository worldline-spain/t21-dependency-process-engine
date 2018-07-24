package com.worldline.t21dependencysample;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

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
        textView.setText(item.getId() + " " + item.getDescription());
        if (item.isSuccess()) {
            timelineView.setIndicatorColor(Color.parseColor("green"));
        } else if (item.isError()) {
            timelineView.setIndicatorColor(Color.parseColor("red"));
        } else {
            timelineView.setIndicatorColor(Color.parseColor("white"));
        }
    }
}
