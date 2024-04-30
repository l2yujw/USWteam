package com.akj.helpyou.activities.findroad.fragment;

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
import com.akj.helpyou.db.search.RouteDatabase;
import com.akj.helpyou.activities.findroad.adapter.Route;
import com.akj.helpyou.activities.findroad.adapter.RouteAdapter;


public class FindRoadRouteFragment extends Fragment {

    RecyclerView recyclerView;
    private RouteAdapter adapter;
    TextView tvStart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_findroad_route, container, false);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = rootView.findViewById(R.id.rv_findroad_route);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RouteAdapter();
        recyclerView.setAdapter(adapter);
        tvStart = rootView.findViewById(R.id.tv_findroad_route_start);

        getRouteList();

        return rootView;
    }

    private void getRouteList(){
        adapter.removeAllItem();

        final RouteDatabase routeDb = new RouteDatabase(getActivity().getApplicationContext(), "route.db", null, 1);
        Cursor cursor = routeDb.getRouteList();

        while (cursor.moveToNext()) {
            Route vo = new Route();
            vo.setStart(cursor.getString(0));
            vo.setEnd(cursor.getString(1));
            vo.setTimeStamp(cursor.getString(2));
            adapter.addItem(vo);
        }
        adapter.notifyDataSetChanged();
    }
}