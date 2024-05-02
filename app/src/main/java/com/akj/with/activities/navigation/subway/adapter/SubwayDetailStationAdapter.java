package com.akj.with.activities.navigation.subway.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.with.R;

import java.util.List;

public class SubwayDetailStationAdapter extends RecyclerView.Adapter<SubwayDetailStationAdapter.ViewHolder> {

    private List<SubwayDetailStation> stationList;

    private SubwayDetailStationAdapter.OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(SubwayDetailStationAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

    public SubwayDetailStationAdapter(List<SubwayDetailStation> stationList){
        this.stationList = stationList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_subway_detail_station, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
        SubwayDetailStation station = stationList.get(i);
        holder.tvLine.setText(station.getLine());
    }

    @Override
    public int getItemCount() {
        return stationList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvLine;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvLine = itemView.findViewById(R.id.tv_subway_detail_line);

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
