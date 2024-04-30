package com.akj.helpyou.activities.result.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;

import java.util.List;

public class ResultRouteAdapter extends RecyclerView.Adapter<ResultRouteAdapter.ResultRouteViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ResultRoute> resultRouteList;

    private OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public ResultRouteAdapter(List<ResultRoute> infList){
        this.resultRouteList = infList;
    }

    @NonNull
    @Override
    public ResultRouteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_result_route, viewGroup, false);
        return new ResultRouteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRouteViewHolder viewHolder, int i) {
        ResultRoute resultRoute = resultRouteList.get(i);
        viewHolder.tvTotal.setText(resultRoute.getTotal());
        viewHolder.tvTime.setText(resultRoute.getTime());
        viewHolder.tvCost.setText(resultRoute.getCost());

        // 자식 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                viewHolder.rv.getContext(),
                LinearLayoutManager.HORIZONTAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(resultRoute.getResultRouteInnerList().size());

        // 자식 어답터 설정
        ResultRouteInnerAdapter resultRouteInnerAdapter = new ResultRouteInnerAdapter(resultRoute.getResultRouteInnerList());

        viewHolder.rv.setLayoutManager(layoutManager);
        viewHolder.rv.setAdapter(resultRouteInnerAdapter);
        viewHolder.rv.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return resultRouteList.size();
    }

    class ResultRouteViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTotal;
        private TextView tvTime;
        private TextView tvCost;
        private RecyclerView rv;

        public ResultRouteViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTotal = itemView.findViewById(R.id.tv_result_route_total);
            tvTime = itemView.findViewById(R.id.tv_result_route_time);
            tvCost = itemView.findViewById(R.id.tv_result_route_cost);
            // 자식아이템 영역
            rv = itemView.findViewById(R.id.rv_result_route);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();

                if(position != RecyclerView.NO_POSITION){
                    if(mListener !=null){
                        mListener.onItemClick(v,position);
                    }
                }
            });
        }
    }
}
