package com.akj.helpyou.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;

import java.util.List;

public class Inf2Adapter extends RecyclerView.Adapter<Inf2Adapter.Inf2ViewHolder> {

    private List<Inf2> inf2List;

    Inf2Adapter(List<Inf2> inf2List){
        this.inf2List = inf2List;
    }

    @NonNull
    @Override
    public Inf2ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inf2, viewGroup, false);
        return new Inf2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Inf2ViewHolder inf2ViewHolder, int i) {
        Inf2 inf2 = inf2List.get(i);
        inf2ViewHolder.vhnum.setText(inf2.getVhnum());
        inf2ViewHolder.startpoint.setText(inf2.getStartpoint());

    }

    @Override
    public int getItemCount() {
        return inf2List.size();
    }

    class Inf2ViewHolder extends RecyclerView.ViewHolder{
        TextView vhnum;
        TextView startpoint;

        public Inf2ViewHolder(@NonNull View itemView) {
            super(itemView);
            vhnum = itemView.findViewById(R.id.inf2_vhnum);
            startpoint = itemView.findViewById(R.id.inf2_startpoint);
        }
    }
}
