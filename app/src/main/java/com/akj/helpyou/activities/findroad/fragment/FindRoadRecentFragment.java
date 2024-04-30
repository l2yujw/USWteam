package com.akj.helpyou.activities.findroad.fragment;

import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akj.helpyou.R;
import com.akj.helpyou.db.search.RecentDatabase;
import com.akj.helpyou.activities.findroad.adapter.Recent;
import com.akj.helpyou.activities.findroad.adapter.RecentAdapter;

public class FindRoadRecentFragment extends Fragment {

    RecyclerView recyclerView;
    private RecentAdapter adapter;

    TextView area;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ViewGroup rootView = (ViewGroup)inflater.inflate(R.layout.fragment_findroad_recent, container, false);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView = rootView.findViewById(R.id.rv_findroad_recent);

        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecentAdapter();
        recyclerView.setAdapter(adapter);

        area = rootView.findViewById(R.id.tv_findroad_recent_start);

        getRouteList();

        return rootView;
    }

    private void getRouteList(){
        adapter.removeAllItem();

        final RecentDatabase recentDb = new RecentDatabase(getActivity().getApplicationContext(), "recent.db", null, 1);

        Cursor cursor = recentDb.getRouteList();

        while (cursor.moveToNext()){
            Recent vo = new Recent();
            vo.setArea(cursor.getString(0));
            vo.setTimeStamp(cursor.getString(1));
            adapter.addItem(vo);
        }

        adapter.notifyDataSetChanged();
    }

}