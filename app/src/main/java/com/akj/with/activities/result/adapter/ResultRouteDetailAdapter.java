package com.akj.with.activities.result.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.with.R;

import java.util.List;

public class ResultRouteDetailAdapter extends RecyclerView.Adapter<ResultRouteDetailAdapter.ResultRouteDetailViewHolder> {

    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
    private List<ResultRouteDetail> routeDetailList;
    private static boolean[][] checkNum = {{true},{true},{true},{true},{true},{true},{true},{true}};
    private ResultRouteDetailAdapter.OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    public ResultRouteDetailAdapter(List<ResultRouteDetail> routeDetailList){
        this.routeDetailList = routeDetailList;
    }
    @NonNull
    @Override
    public ResultRouteDetailViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_result_route_detail, viewGroup, false);
        return new ResultRouteDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRouteDetailViewHolder viewHolder, int i) {
        ResultRouteDetail routeDetail = routeDetailList.get(i);
        viewHolder.tvVehicle.setText(routeDetail.getVehicle());
        viewHolder.tvStart.setText(routeDetail.getStartPoint());
        viewHolder.tvEnd.setText(routeDetail.getEndPoint());
        viewHolder.tvTime.setText(routeDetail.getTime());
        viewHolder.tvEtc.setText(routeDetail.getEtc());
        viewHolder.tvDetails.setText(routeDetail.getDetails());

        // 자식 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                viewHolder.rv.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(routeDetail.getRouteDetailInnerList().size());

        // 자식 어답터 설정
        ResultRouteDetailInnerAdapter adapter = new ResultRouteDetailInnerAdapter(routeDetail.getRouteDetailInnerList());
        viewHolder.rv.setLayoutManager(layoutManager);
        viewHolder.tvDetails.setOnClickListener(v -> {
            if(checkNum[i][0]){
                viewHolder.rv.setVisibility(View.GONE);
                checkNum[i][0] = false;
            }
            else{
                viewHolder.rv.setVisibility(View.VISIBLE);
                checkNum[i][0] = true;
            }
        });

        viewHolder.rv.setAdapter(adapter);
        viewHolder.rv.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return routeDetailList.size();
    }

    class ResultRouteDetailViewHolder extends RecyclerView.ViewHolder{

        private TextView tvVehicle;
        private TextView tvStart;
        private TextView tvEnd;
        private TextView tvTime;
        private TextView tvEtc;
        private TextView tvDetails;
        private RecyclerView rv;

        public ResultRouteDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicle = itemView.findViewById(R.id.tv_result_route_detail_vehicle);
            tvStart = itemView.findViewById(R.id.tv_result_route_detail_start);
            tvEnd = itemView.findViewById(R.id.tv_result_route_detail_end);
            tvTime = itemView.findViewById(R.id.tv_result_route_detail_time);
            tvEtc = itemView.findViewById(R.id.tv_result_route_detail_etc);
            tvDetails = itemView.findViewById(R.id.tv_result_route_detail_details);
            // 자식아이템 영역
            rv = itemView.findViewById(R.id.rv_result_route_detail_inner);

            tvDetails.setOnClickListener(v -> {
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
