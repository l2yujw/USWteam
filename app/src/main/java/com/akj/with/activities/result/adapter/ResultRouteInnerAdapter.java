package com.akj.with.activities.result.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.with.R;

import java.util.List;

public class ResultRouteInnerAdapter extends RecyclerView.Adapter<ResultRouteInnerAdapter.ResultRouteInnerViewHolder> {

    private List<ResultRouteInner> routeInnerList;

    ResultRouteInnerAdapter(List<ResultRouteInner> resultRouteInnerList){
        this.routeInnerList = resultRouteInnerList;
    }

    @NonNull
    @Override
    public ResultRouteInnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_result_route_inner, viewGroup, false);
        return new ResultRouteInnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRouteInnerViewHolder viewHolder, int i) {
        ResultRouteInner routeInner = routeInnerList.get(i);
        viewHolder.iv.setImageResource(routeInner.getImg());
        viewHolder.tvVehicle.setText(routeInner.getVehicle());
        viewHolder.tvStart.setText(routeInner.getStartPoint());

    }

    @Override
    public int getItemCount() {
        return routeInnerList.size();
    }

    class ResultRouteInnerViewHolder extends RecyclerView.ViewHolder{
        TextView tvVehicle;
        TextView tvStart;
        ImageView iv;

        public ResultRouteInnerViewHolder(@NonNull View itemView) {
            super(itemView);
            tvVehicle = itemView.findViewById(R.id.tv_result_route_inner_vehicle);
            tvStart = itemView.findViewById(R.id.tv_result_route_inner_start);
            iv = itemView.findViewById(R.id.iv_result_route_inner);
        }
    }
}
