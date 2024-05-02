package com.akj.with.activities.findroad.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.with.R;
import com.akj.with.db.search.RecentDatabase;

import java.util.ArrayList;

public class RecentAdapter extends RecyclerView.Adapter<RecentAdapter.ViewHolder> {
    String area = "";

    RecentDatabase recentDb;

    AlertDialog.Builder builder;

    ArrayList<Recent> items = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemview = inflater.inflate(R.layout.item_findroad_recent, viewGroup, false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int mPosition = viewHolder.getAdapterPosition();
        Recent item = items.get(mPosition);
        viewHolder.setItem(item);

        viewHolder.cv.setOnClickListener(v -> {
            Context context = v.getContext();

            recentDb = new RecentDatabase(context.getApplicationContext(), "recent.db", null, 1);

            area = items.get(mPosition).getArea();

            builder = new AlertDialog.Builder(context);
            builder.setTitle("삭제");
            builder.setMessage("해당 항목을 삭제하시겠습니까?");
            builder.setPositiveButton("예",
                    (dialog, which) -> {
                        deleteItem(mPosition);
                        notifyDataSetChanged();
                        recentDb.delete(area);
                    });
            builder.setNegativeButton("아니오",
                    (dialog, which) -> dialog.cancel());
            builder.show();
        });


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Recent area){
        items.add(area);
    }

    public void deleteItem(int position){
        items.remove(position);
    }

    public void removeAllItem(){
        items.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvStart;
        TextView tvDate;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStart = itemView.findViewById(R.id.tv_findroad_recent_start);
            tvDate = itemView.findViewById(R.id.tv_findroad_recent_date);
            cv = itemView.findViewById(R.id.cv_findroad_recent);
        }

        public void setItem(Recent vo){
            tvStart.setText(vo.getArea());
            tvDate.setText(vo.getTimeStamp());
        }
    }
}
