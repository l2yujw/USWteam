package com.akj.helpyou.activities;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;

import java.util.List;

public class SubwayDStationAdapter extends RecyclerView.Adapter<SubwayDStationAdapter.ViewHolder> {

    private List<SubwayDStation> subwayDStationList;

    private SubwayDStationAdapter.OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(SubwayDStationAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }

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

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();

                    if(position != RecyclerView.NO_POSITION){
                        if(mListener !=null){
                            mListener.onItemClick(v,position);
                        }
                    }

                }
            });
        }
    }


}
