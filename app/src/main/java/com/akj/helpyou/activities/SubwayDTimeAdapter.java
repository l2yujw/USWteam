package com.akj.helpyou.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;

import java.util.List;

public class SubwayDTimeAdapter extends RecyclerView.Adapter<SubwayDTimeAdapter.TimeViewHolder> {

    private List<SubwayDTime> subwayDTimeList;

    SubwayDTimeAdapter(List<SubwayDTime> subwayDTimeList) {
        this.subwayDTimeList = subwayDTimeList;
    }

    @NonNull
    @Override
    public TimeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subwayd_time, viewGroup, false);
        return new TimeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TimeViewHolder timeViewHolder, int i) {
        SubwayDTime subwayDTime = subwayDTimeList.get(i);
        timeViewHolder.startMinute.setText(subwayDTime.getStartMinute());
        timeViewHolder.startTime.setText(subwayDTime.getStartTime());
    }

    @Override
    public int getItemCount() {
        return subwayDTimeList.size();
    }

    class TimeViewHolder extends RecyclerView.ViewHolder {
        TextView startTime;
        TextView startMinute;

        public TimeViewHolder(@NonNull View itemView) {
            super(itemView);

            startTime = itemView.findViewById(R.id.subwayd_starttime);
            startMinute = itemView.findViewById(R.id.subwayd_startMinute);
        }
    }

}
