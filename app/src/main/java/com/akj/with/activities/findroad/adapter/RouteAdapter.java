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
import com.akj.with.db.search.RouteDatabase;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    String startPoint = "";

    RouteDatabase routeDb;

    AlertDialog.Builder builder;

    ArrayList<Route> items = new ArrayList<>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemview = inflater.inflate(R.layout.item_findroad_route, viewGroup, false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int mPosition = viewHolder.getAdapterPosition();
        Route item = items.get(mPosition);
        viewHolder.setItem(item);

        viewHolder.cv.setOnClickListener(v -> {
            Context context = v.getContext();

            routeDb = new RouteDatabase(context.getApplicationContext(), "USER_INFO.db", null, 1);

            startPoint = items.get(mPosition).getStart();

            builder = new AlertDialog.Builder(context);
            builder.setTitle("삭제");
            builder.setMessage("[" + startPoint + "]해당 항목을 삭제하시겠습니까?");
            builder.setPositiveButton("예",
                    (dialog, which) -> {
                        deleteItem(mPosition);
                        notifyDataSetChanged();
                        routeDb.delete(startPoint);
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

    public void addItem(Route route){
        items.add(route);
    }

    public void deleteItem(int position){
        items.remove(position);
    }

    public void removeAllItem(){
        items.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvStart;
        TextView tvEnd;
        TextView tvDate;
        CardView cv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tvStart = itemView.findViewById(R.id.tv_findroad_route_start);
            tvEnd = itemView.findViewById(R.id.tv_findroad_route_end);
            tvDate = itemView.findViewById(R.id.tv_findroad_route_date);
            cv = itemView.findViewById(R.id.cv_findroad_route);
        }

        public void setItem(Route vo){
            tvStart.setText(vo.getStart());
            tvEnd.setText(vo.getEnd());
            tvDate.setText(vo.getTimeStamp());
        }
    }
}
