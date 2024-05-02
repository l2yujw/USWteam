package com.akj.with.activities.result.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.with.R;

import java.util.List;

public class ResultRouteDetailInnerAdapter extends RecyclerView.Adapter<ResultRouteDetailInnerAdapter.ResultRouteInnerViewHolder> {

    private List<ResultRouteDetailInner> routeDetailInnerList;

    ResultRouteDetailInnerAdapter(List<ResultRouteDetailInner> routeDetailInnerList){
        this.routeDetailInnerList = routeDetailInnerList;
    }

    @NonNull
    @Override
    public ResultRouteInnerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_result_route_detail_inner, viewGroup, false);
        return new ResultRouteInnerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultRouteInnerViewHolder viewHolder, int i) {
        ResultRouteDetailInner routeDetailInner = routeDetailInnerList.get(i);
        viewHolder.stop.setText(routeDetailInner.getStop());

    }

    @Override
    public int getItemCount() {
        return routeDetailInnerList.size();
    }

    class ResultRouteInnerViewHolder extends RecyclerView.ViewHolder{
        TextView stop;

        public ResultRouteInnerViewHolder(@NonNull View itemView) {
            super(itemView);
            stop = itemView.findViewById(R.id.tv_result_route_detail_inner_stop);
        }
    }
}
