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

public class InfDAdapter extends RecyclerView.Adapter<InfDAdapter.InfDViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<InfD> infDList;

    InfDAdapter(List<InfD> infDList){
        this.infDList = infDList;
    }
    @NonNull
    @Override
    public InfDViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_infd, viewGroup, false);
        return new InfDAdapter.InfDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfDViewHolder infDViewHolder, int i) {
        InfD infD = infDList.get(i);
        infDViewHolder.duration_time.setText(infD.getDuration_time());
        infDViewHolder.result_time.setText(infD.getResult_time());
        infDViewHolder.cost.setText(infD.getCost());

        // 자식 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                infDViewHolder.rvInfD.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(infD.getInfD2List().size());

        // 자식 어답터 설정
        InfD2Adapter infD2Adapter = new InfD2Adapter(infD.getInfD2List());

        infDViewHolder.rvInfD.setLayoutManager(layoutManager);
        infDViewHolder.rvInfD.setAdapter(infD2Adapter);
        infDViewHolder.rvInfD.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return infDList.size();
    }

    class InfDViewHolder extends RecyclerView.ViewHolder{

        private TextView duration_time;
        private TextView result_time;
        private TextView cost;
        private RecyclerView rvInfD;

        public InfDViewHolder(@NonNull View itemView) {
            super(itemView);
            duration_time = itemView.findViewById(R.id.infd_duration_time);
            result_time = itemView.findViewById(R.id.infd_result_time);
            cost = itemView.findViewById(R.id.infd_cost);
            // 자식아이템 영역
            rvInfD = itemView.findViewById(R.id.recyclerView_infd2);
        }
    }
}
