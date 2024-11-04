package com.example.grademaster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context context;
    ArrayList<Classes> list;

    public MyAdapter(Context context, ArrayList<Classes> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.activities_card, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Classes classes = list.get(position);
        holder.moduleName.setText(classes.moduleName);
        holder.dayText.setText("Mon");
        holder.locationText.setText(classes.roomNumber + ", " + classes.building);
        holder.timeText.setText(classes.startTime + " - " + classes.endTime);
        holder.lecturerNameText.setText(classes.lecturerName);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView moduleName, dayText, locationText, timeText, lecturerNameText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.moduleName);
            dayText = itemView.findViewById(R.id.dayText);
            locationText = itemView.findViewById(R.id.locationText);
            timeText = itemView.findViewById(R.id.timeText);
            lecturerNameText = itemView.findViewById(R.id.lecturerNameText);
        }
    }
}
