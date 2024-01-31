package com.akj.helpyou.activities.Inf;

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
        return new InfD2ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfD2ViewHolder infD2ViewHolder, int i) {
        InfD2 infD2 = infD2List.get(i);
        infD2ViewHolder.stop.setText(infD2.getStop());

    }

    @Override
    public int getItemCount() {
        return infD2List.size();
    }

    class InfD2ViewHolder extends RecyclerView.ViewHolder{
        TextView stop;

        public InfD2ViewHolder(@NonNull View itemView) {
            super(itemView);
            stop = itemView.findViewById(R.id.infd2_stop);
        }
    }
}
