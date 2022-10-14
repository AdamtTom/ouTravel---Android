package com.example.androidproject;

import android.os.Bundle;

import androidx.fragment.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


public class Page6Fragment extends ListFragment {
    String[] statiscticsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        statiscticsList = getResources().getStringArray(R.array.page6_fragmentList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.page6_fragment, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(inflater.getContext(), android.R.layout.simple_list_item_1, statiscticsList);
        setListAdapter(adapter);
        return view;
    }

}
