package com.akj.helpyou.activities.Inf;

import android.content.Context;
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

    private static boolean[][] checkNum = {{true},{true},{true},{true},{true},{true},{true},{true}};

    private InfDAdapter.OnItemClickListener mListener = null ;

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(InfDAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }



    public InfDAdapter(List<InfD> infDList){
        this.infDList = infDList;
    }
    @NonNull
    @Override
    public InfDViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_infd, viewGroup, false);
        return new InfDViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfDViewHolder infDViewHolder, int i) {
        InfD infD = infDList.get(i);
        infDViewHolder.vh.setText(infD.getVh());
        infDViewHolder.startpoint.setText(infD.getStartpoint());
        infDViewHolder.endpoint.setText(infD.getEndpoint());
        infDViewHolder.time.setText(infD.getTime());
        infDViewHolder.etc.setText(infD.getEtc());
        infDViewHolder.details.setText(infD.getDetails());

        // 자식 레이아웃 매니저 설정
        LinearLayoutManager layoutManager = new LinearLayoutManager(
                infDViewHolder.rvInfD2.getContext(),
                LinearLayoutManager.VERTICAL,
                false
        );
        layoutManager.setInitialPrefetchItemCount(infD.getInfD2List().size());

        // 자식 어답터 설정
        InfD2Adapter infD2Adapter = new InfD2Adapter(infD.getInfD2List());
        infDViewHolder.rvInfD2.setLayoutManager(layoutManager);
        infDViewHolder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkNum[i][0]){
                    infDViewHolder.rvInfD2.setVisibility(View.GONE);
                    checkNum[i][0] = false;
                }
                else{
                    infDViewHolder.rvInfD2.setVisibility(View.VISIBLE);
                    checkNum[i][0] = true;
                }
            }
        });

        infDViewHolder.rvInfD2.setAdapter(infD2Adapter);
        infDViewHolder.rvInfD2.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
        return infDList.size();
    }

    class InfDViewHolder extends RecyclerView.ViewHolder{
        Context context;

        private TextView vh;
        private TextView startpoint;
        private TextView endpoint;
        private TextView time;
        private TextView etc;
        private TextView details;
        private RecyclerView rvInfD2;

        public InfDViewHolder(@NonNull View itemView) {
            super(itemView);
            vh = itemView.findViewById(R.id.infd_vh);
            startpoint = itemView.findViewById(R.id.infd_startpoint);
            endpoint = itemView.findViewById(R.id.infd_endpoint);
            time = itemView.findViewById(R.id.infd_time);
            etc = itemView.findViewById(R.id.infd_etc);
            details = itemView.findViewById(R.id.infd_details);
            // 자식아이템 영역
            rvInfD2 = itemView.findViewById(R.id.recyclerView_infd2);


            details.setOnClickListener(new View.OnClickListener() {
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
