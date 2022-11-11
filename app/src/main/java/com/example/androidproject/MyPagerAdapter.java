package com.example.androidproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyPagerAdapter extends RecyclerView.Adapter<MyPagerAdapter.ViewHolder> {
    ArrayList<ViewPagerItem> viewPagerItemArrayList;

    public MyPagerAdapter(ArrayList<ViewPagerItem> viewPagerItemArrayList) {
        this.viewPagerItemArrayList = viewPagerItemArrayList;
    }

    @NonNull
    @Override
    public MyPagerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPagerAdapter.ViewHolder holder, int position) {
        ViewPagerItem viewPagerItem = viewPagerItemArrayList.get(position);
        holder.cardLayout.setBackgroundResource(viewPagerItem.image);
        holder.tvCity.setText(viewPagerItem.city);
        holder.tvCountry.setText(viewPagerItem.country);
        holder.tvDescription.setText(viewPagerItem.description);
    }

    @Override
    public int getItemCount() {
        return viewPagerItemArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout cardLayout;
        TextView tvCity, tvCountry, tvDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardLayout = itemView.findViewById(R.id.cardBackground);
            tvCity = itemView.findViewById(R.id.cityName);
            tvCountry = itemView.findViewById(R.id.countryName);
            tvDescription = itemView.findViewById(R.id.description);
        }
    }
}
