package com.akj.helpyou.activities.findroad;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akj.helpyou.R;

public class ListFragment2 extends Fragment {

    RecyclerView recyclerView;
    private AreaAdapter adapter;

    TextView area;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_list2, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = rootView.findViewById(R.id.recyclerView2);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new AreaAdapter();
        recyclerView.setAdapter(adapter);

        area = rootView.findViewById(R.id.startpoint2);

        getRouteList();

        return rootView;
    }

    private void getRouteList(){
        println("<<< getRouteList() >>>");

        adapter.removeAllItem();

        final DBHelper2 dbHelper2 = new DBHelper2(getActivity().getApplicationContext(), "USER_INFO2.db", null, 1);

        Cursor cursor = dbHelper2.getRouteList();

        int count = 0;

        while (cursor.moveToNext()){
            Area vo = new Area();
            vo.setArea(cursor.getString(0));
            vo.setTimestamp(cursor.getString(1));
            adapter.addItem(vo);
            count++;
        }

        adapter.notifyDataSetChanged();
        println(""+adapter.getItemCount());
    }

    public void println(String msg){
        Log.d("listFragment2", msg);
    }
}