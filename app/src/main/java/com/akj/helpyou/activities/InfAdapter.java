package com.akj.helpyou.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;

import java.util.List;

public class InfAdapter extends RecyclerView.Adapter<InfAdapter.InfViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<Inf> infList;

    InfAdapter(List<Inf> infList){
        this.infList = infList;
    }
    @NonNull
    @Override
    public InfViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_inf, viewGroup, false);
        return new InfViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfViewHolder infViewHolder, int i) {
        Inf inf = infList.get(i);
        infViewHolder.duration_time.setText(inf.getDuration_time());
        infViewHolder.result_time.setText(inf.getResult_time());
        infViewHolder.cost.setText(inf.getCost());

        // 자식 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                infViewHolder.rvInf.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(inf.getInf2List().size());

        // 자식 어답터 설정
        Inf2Adapter inf2Adapter = new Inf2Adapter(inf.getInf2List());

        infViewHolder.rvInf.setLayoutManager(layoutManager);
        infViewHolder.rvInf.setAdapter(inf2Adapter);
        infViewHolder.rvInf.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return infList.size();
    }

    class InfViewHolder extends RecyclerView.ViewHolder{
        private TextView duration_time;
        private TextView result_time;
        private TextView cost;
        private RecyclerView rvInf;

        public InfViewHolder(@NonNull View itemView) {
            super(itemView);
            duration_time = itemView.findViewById(R.id.inf_duration_time);
            result_time = itemView.findViewById(R.id.inf_result_time);
            cost = itemView.findViewById(R.id.inf_cost);
            // 자식아이템 영역
            rvInf = itemView.findViewById(R.id.recyclerView_inf2);
        }
    }
}
