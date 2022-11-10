package com.akj.helpyou.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;

import java.util.List;

public class InfD2Adapter extends RecyclerView.Adapter<InfD2Adapter.InfD2ViewHolder> {

    private List<InfD2> infD2List;

    InfD2Adapter(List<InfD2> infD2List){
        this.infD2List = infD2List;
    }

    @NonNull
    @Override
    public InfD2ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_infd2, viewGroup, false);
        return new InfD2Adapter.InfD2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfD2ViewHolder infD2ViewHolder, int i) {
        InfD2 infD2 = infD2List.get(i);
        infD2ViewHolder.vh.setText(infD2.getVh());
        infD2ViewHolder.startpoint.setText(infD2.getStartpoint());
        infD2ViewHolder.destination.setText(infD2.getDestination());
        infD2ViewHolder.vhnum.setText(infD2.getVhnum());
        infD2ViewHolder.details.setText(infD2.getDetails());
        infD2ViewHolder.duration.setText(infD2.getDuration());

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class InfD2ViewHolder extends RecyclerView.ViewHolder{
        private TextView vh;
        private TextView startpoint;
        private TextView destination;
        private TextView vhnum;
        private TextView details;
        private TextView duration;

        public InfD2ViewHolder(@NonNull View itemView) {
            super(itemView);

            vh = itemView.findViewById(R.id.infd2_vh);
            startpoint = itemView.findViewById(R.id.infd2_startpoint);
            destination = itemView.findViewById(R.id.infd2_destination);
            vhnum = itemView.findViewById(R.id.infd2_vhnum);
            details = itemView.findViewById(R.id.infd2_details);
            duration = itemView.findViewById(R.id.infd2_duration_time);
        }
    }
}
