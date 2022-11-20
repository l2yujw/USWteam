package com.akj.transport3.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.transport3.R;

import java.util.List;

public class SubwayDStationAdapter extends RecyclerView.Adapter<SubwayDStationAdapter.ViewHolder> {

    private List<SubwayDStation> subwayDStationList;

    SubwayDStationAdapter(List<SubwayDStation> subwayDStationList){
        this.subwayDStationList = subwayDStationList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subwayd_station, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        SubwayDStation subwayDStation = subwayDStationList.get(i);
        holder.line.setText(subwayDStation.getLine());
    }

    @Override
    public int getItemCount() {
        return subwayDStationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView line;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            line = itemView.findViewById(R.id.subwayd_line);
        }
    }


}
