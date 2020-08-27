package com.news.handson.adepter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.news.handson.R;
import com.news.handson.model.CovidData;

import java.util.List;

public class adepterview extends RecyclerView.Adapter<adepterview.demodataViewholder> {

    Context context;
    List<CovidData> dataList;

    public adepterview(Context context, List<CovidData> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public demodataViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.list, parent, false);
        return new demodataViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull demodataViewholder holder, int position) {
        CovidData covidData = dataList.get(position);
        holder.stateName.setText(covidData.getLoc());
        holder.confirmed.setText(covidData.getTotalConfirmed());
        holder.recover.setText(covidData.getDischarged());
        holder.deaths.setText(covidData.getDeaths());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class demodataViewholder extends RecyclerView.ViewHolder{

        TextView stateName, confirmed, recover, deaths;

        public demodataViewholder(@NonNull View itemView) {
            super(itemView);
            stateName = itemView.findViewById(R.id.textStateName);
            confirmed = itemView.findViewById(R.id.textConfirmedcase);
            recover = itemView.findViewById(R.id.textRecoveredcase);
            deaths = itemView.findViewById(R.id.textDeathcase);
        }
    }
}
