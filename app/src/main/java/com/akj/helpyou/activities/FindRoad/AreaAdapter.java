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

public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {
    String area = "";

    DBHelper2 dbHelper2;

    AlertDialog.Builder builder;

    ArrayList<Area> items = new ArrayList<Area>();
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemview = inflater.inflate(R.layout.item_area, viewGroup, false);

        return new ViewHolder(itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        int mPosition = viewHolder.getAdapterPosition();
        Area item = items.get(mPosition);
        viewHolder.setItem(item);

        viewHolder.cardView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();

                dbHelper2 = new DBHelper2(context.getApplicationContext(), "USER_INFO2.db", null, 1);

                area = items.get(mPosition).getArea();

                builder = new AlertDialog.Builder(context);
                builder.setTitle("삭제");
                builder.setMessage("해당 항목을 삭제하시겠습니까?");
                builder.setPositiveButton("예",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteItem(mPosition);
                                notifyDataSetChanged();
                                dbHelper2.delete(area);
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

    public void addItem(Area area){
        items.add(area);
    }

    public void deleteItem(int position){
        items.remove(position);
    }

    public void removeAllItem(){
        items.clear();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView area;
        TextView when;
        CardView cardView2;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            area = itemView.findViewById(R.id.startpoint2);
            when = itemView.findViewById(R.id.when2);
            cardView2 = itemView.findViewById(R.id.cardView2);
        }

        public void setItem(Area vo){
            area.setText(vo.getArea());
            when.setText(vo.getTimestamp());
        }
    }
}
