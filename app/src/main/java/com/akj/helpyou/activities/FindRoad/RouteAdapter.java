package com.akj.helpyou.activities.FindRoad;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.akj.helpyou.R;

import java.util.ArrayList;

public class RouteAdapter extends RecyclerView.Adapter<RouteAdapter.ViewHolder> {
    String startpoint = "";

    DBHelper dbHelper;

    AlertDialog.Builder builder;

    ArrayList<Route> items = new ArrayList<Route>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemview = inflater.inflate(R.layout.item_route, viewGroup, false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int mPosition = viewHolder.getAdapterPosition();
        Route item = items.get(mPosition);
        viewHolder.setItem(item);

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                dbHelper = new DBHelper(context.getApplicationContext(), "USER_INFO.db", null, 1);

                startpoint = items.get(mPosition).getStartpoint();

                builder = new AlertDialog.Builder(context);
                builder.setTitle("삭제");
                builder.setMessage("[" + startpoint + "]해당 항목을 삭제하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem(mPosition);
                                notifyDataSetChanged();
                                dbHelper.delete(startpoint);
                            }
                        });
                builder.setNegativeButton("아니오",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                builder.show();
            }
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
        TextView startpoint;
        TextView endpoint;
        TextView when;
        CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            startpoint = itemView.findViewById(R.id.startpoint);
            endpoint = itemView.findViewById(R.id.endpoint);
            when = itemView.findViewById(R.id.when);
            cardView = itemView.findViewById(R.id.cardView);
        }

        public void setItem(Route vo){
            startpoint.setText(vo.getStartpoint());
            endpoint.setText(vo.getEndpoint());
            when.setText(vo.getTimestamp());
        }
    }
}
