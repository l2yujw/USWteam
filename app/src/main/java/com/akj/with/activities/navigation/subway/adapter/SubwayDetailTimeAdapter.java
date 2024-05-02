package com.akj.with.activities.navigation.subway.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.with.R;

import java.util.List;

public class SubwayDetailTimeAdapter extends RecyclerView.Adapter<SubwayDetailTimeAdapter.TimeViewHolder> {

    private List<SubwayDetailTime> timeList;

    public SubwayDetailTimeAdapter(List<SubwayDetailTime> timeList) {
        this.timeList = timeList;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subway_detail_time, viewGroup, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder viewHolder, int i) {
        SubwayDetailTime time = timeList.get(i);
        viewHolder.tvStartMinute.setText(time.getStartMinute());
        viewHolder.tvStartTime.setText(time.getStartTime());
    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView tvStartTime;
        TextView tvStartMinute;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStartTime = itemView.findViewById(R.id.tv_subway_detail_starttime);
            tvStartMinute = itemView.findViewById(R.id.tv_subway_detail_startminute);
        }
    }

}
