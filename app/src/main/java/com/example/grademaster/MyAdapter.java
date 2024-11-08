package com.example.grademaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    //private  final ClassesRecyclerViewInterface classesRecyclerViewInterface;

    Context context;
    ArrayList<Classes> list;

    public MyAdapter(Context context, ArrayList<Classes> list) {
        this.context = context;
        this.list = list;
        //this.classesRecyclerViewInterface = classesRecyclerViewInterface;
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
        holder.modeText.setText(classes.classMode);
        holder.timeText.setText(classes.startTime + " - " + classes.endTime);
        holder.lecturerNameText.setText(classes.lecturerName);

        if (holder.modeText.getText().toString().equals("In Person")){
            holder.locationText.setText(classes.roomNumber + ", " + classes.building);
        } else{
            holder.locationText.setText("-");
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView moduleName, modeText, locationText, timeText, lecturerNameText;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            moduleName = itemView.findViewById(R.id.moduleName);
            modeText = itemView.findViewById(R.id.modeText);
            locationText = itemView.findViewById(R.id.locationText);
            timeText = itemView.findViewById(R.id.timeText);
            lecturerNameText = itemView.findViewById(R.id.lecturerNameText);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        Classes selectedClass = list.get(position);

                        Intent intent = new Intent(context, ClassesCardDetailsActivity.class);
                        intent.putExtra("classMode", selectedClass.classMode != null ? selectedClass.classMode : "N/A");
                        intent.putExtra("moduleName", selectedClass.moduleName != null ? selectedClass.moduleName : "N/A");
                        intent.putExtra("roomNumber", selectedClass.roomNumber != null ? selectedClass.roomNumber : "N/A");
                        intent.putExtra("building", selectedClass.building != null ? selectedClass.building : "N/A");
                        intent.putExtra("startTime", selectedClass.startTime != null ? selectedClass.startTime : "N/A");
                        intent.putExtra("endTime", selectedClass.endTime != null ? selectedClass.endTime : "N/A");
                        intent.putExtra("lecturerName", selectedClass.lecturerName != null ? selectedClass.lecturerName : "N/A");
                        intent.putExtra("lecturerEmail", selectedClass.lecturerEmail != null ? selectedClass.lecturerEmail : "N/A");
                        intent.putExtra("onlineClassURL", selectedClass.onlineClassURL != null ? selectedClass.onlineClassURL : "N/A");
                        intent.putExtra("occurence", selectedClass.isRepeating != null ? selectedClass.isRepeating : false);
                        intent.putExtra("days", selectedClass.days != null ? selectedClass.days : new ArrayList<String>());
                        intent.putExtra("date", selectedClass.date != null ? selectedClass.date : "N/A");

                        context.startActivity(intent);
                    }

                }
            });
        }
    }
}
