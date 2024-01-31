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


public class ListFragment extends Fragment {

    RecyclerView recyclerView;
    private RouteAdapter adapter;

    TextView startpoint;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_list, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = rootView.findViewById(R.id.recyclerView1);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new RouteAdapter();
        recyclerView.setAdapter(adapter);

        startpoint = rootView.findViewById(R.id.startpoint);

        getRouteList();

        return rootView;
    }

    private void getRouteList(){
        println("<<< ge tRouteList() >>>");

        adapter.removeAllItem();

        final DBHelper dbHelper = new DBHelper(getActivity().getApplicationContext(), "USER_INFO.db", null, 1);

        Cursor cursor = dbHelper.getRouteList();

        int count = 0;

        while (cursor.moveToNext()){
            Route vo = new Route();
            vo.setStartpoint(cursor.getString(0));
            vo.setEndpoint(cursor.getString(1));
            vo.setTimestamp(cursor.getString(2));
            adapter.addItem(vo);
            count++;
        }

        adapter.notifyDataSetChanged();
        println(""+adapter.getItemCount());
    }

    public void println(String msg){
        Log.d("listFragment", msg);
    }
}