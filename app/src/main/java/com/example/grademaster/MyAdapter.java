package com.example.grademaster;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    private static final int VIEW_TYPE_CLASS = 0;
    private static final int VIEW_TYPE_EXAM = 1;

    private Context context;
    private ArrayList<Object> combinedList; // Use Object to store both Classes and Exams
    ArrayList<Classes> classesList;
    ArrayList<Classes> classesOriginalList; // Add this to keep a copy of the original data
    ArrayList<Exams> examsList;
    ArrayList<Exams> examsOriginalList; // Add this to keep a copy of the original data

    public MyAdapter(Context context, ArrayList<Object> combinedList, ArrayList<Classes> classesList,
                     ArrayList<Exams> examsList) {
        this.context = context;
        this.combinedList = combinedList;
        this.classesList = classesList;
        this.examsList = examsList;
        this.classesOriginalList = new ArrayList<>(classesList); // Store a separate copy for filtering
        this.examsOriginalList = new ArrayList<>(examsList); // Store a separate copy for filtering
        //this.classesRecyclerViewInterface = classesRecyclerViewInterface;
    }

    @Override
    public int getItemViewType(int position) {
        // Determine if the item is a Class or an Exam
        if (combinedList.get(position) instanceof Classes) {
            return VIEW_TYPE_CLASS;
        } else if (combinedList.get(position) instanceof Exams) {
            return VIEW_TYPE_EXAM;
        }
        return -1;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_CLASS) {
            View view = LayoutInflater.from(context).inflate(R.layout.classes_card, parent, false);
            return new ClassViewHolder(view);
        } else if (viewType == VIEW_TYPE_EXAM) {
            View view = LayoutInflater.from(context).inflate(R.layout.exams_card, parent, false);
            return new ExamViewHolder(view);
        }
        throw new IllegalArgumentException("Invalid view type");
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if (holder.getItemViewType() == VIEW_TYPE_CLASS) {
            Classes classes = (Classes) combinedList.get(position);
            ((ClassViewHolder) holder).bind(classes);
        } else if (holder.getItemViewType() == VIEW_TYPE_EXAM) {
            Exams exams = (Exams) combinedList.get(position);
            ((ExamViewHolder) holder).bind(exams);
        }
    }

    @Override
    public int getItemCount() {
        return combinedList.size();
    }

    // Separate ViewHolder for Class items
    public class ClassViewHolder extends MyViewHolder {
        TextView moduleName, modeText, locationText, timeText, lecturerNameText;
        ImageView activityIcon;

        public ClassViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleName = itemView.findViewById(R.id.moduleName);
            modeText = itemView.findViewById(R.id.modeText);
            locationText = itemView.findViewById(R.id.locationText);
            timeText = itemView.findViewById(R.id.timeText);
            lecturerNameText = itemView.findViewById(R.id.lecturerNameText);
            activityIcon = itemView.findViewById(R.id.activityIcon);
        }

        public void bind(Classes classes) {
            moduleName.setText(classes.moduleName);
            modeText.setText(classes.classMode);
            timeText.setText(classes.startTime + " - " + classes.endTime);
            lecturerNameText.setText(classes.lecturerName);
            locationText.setText(classes.classMode.equals("In Person") ? classes.roomNumber + ", " + classes.building : "-");
            activityIcon.setImageResource(R.drawable.class_3d_icon);

            String occurence  = classes.getIsRepeating();



            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ClassesCardDetailsActivity.class);

                intent.putExtra("classID", classes.classID != null ? classes.classID : "N/A");
                intent.putExtra("classMode", classes.classMode != null ? classes.classMode : "N/A");
                intent.putExtra("moduleName", classes.moduleName != null ? classes.moduleName : "N/A");
                intent.putExtra("roomNumber", classes.roomNumber != null ? classes.roomNumber : "N/A");
                intent.putExtra("building", classes.building != null ? classes.building : "N/A");
                intent.putExtra("startTime", classes.startTime != null ? classes.startTime : "N/A");
                intent.putExtra("endTime", classes.endTime != null ? classes.endTime : "N/A");
                intent.putExtra("lecturerName", classes.lecturerName != null ? classes.lecturerName : "N/A");
                intent.putExtra("lecturerEmail", classes.lecturerEmail != null ? classes.lecturerEmail : "N/A");
                intent.putExtra("onlineClassURL", classes.onlineClassURL != null ? classes.onlineClassURL : "N/A");
                intent.putExtra("occurrence", occurence);
                intent.putExtra("days", classes.days != null ? classes.days : new ArrayList<String>());
                intent.putExtra("date", classes.date != null ? classes.date : "N/A");

                context.startActivity(intent);
            });
        }
    }

    // Separate ViewHolder for Exam items
    public class ExamViewHolder extends MyViewHolder {
        TextView moduleName, modeText, locationText, timeText, lecturerNameText;
        ImageView activityIcon;

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            moduleName = itemView.findViewById(R.id.moduleName);
            modeText = itemView.findViewById(R.id.modeText);
            locationText = itemView.findViewById(R.id.locationText);
            timeText = itemView.findViewById(R.id.timeText);
            lecturerNameText = itemView.findViewById(R.id.lecturerNameText);
            activityIcon = itemView.findViewById(R.id.activityIcon);
        }

        public void bind(Exams exams) {
            moduleName.setText(exams.moduleName);
            modeText.setText(exams.examMode);
            timeText.setText(exams.startTime + " - " + exams.endTime);
            locationText.setText(exams.examMode.equals("In Person") ? exams.roomNumber + ", " + exams.building : "-");
            lecturerNameText.setText(exams.lecturerName);
            activityIcon.setImageResource(R.drawable.exam_3d_icon);

            itemView.setOnClickListener(view -> {
                Intent intent = new Intent(context, ExamsCardDetailsActivity.class);

                intent.putExtra("examID", exams.examID != null ? exams.examID : "N/A");
                intent.putExtra("examMode", exams.examMode != null ? exams.examMode : "N/A");
                intent.putExtra("moduleName", exams.moduleName != null ? exams.moduleName : "N/A");
                intent.putExtra("roomNumber", exams.roomNumber != null ? exams.roomNumber : "N/A");
                intent.putExtra("building", exams.building != null ? exams.building : "N/A");
                intent.putExtra("startTime", exams.startTime != null ? exams.startTime : "N/A");
                intent.putExtra("endTime", exams.endTime != null ? exams.endTime : "N/A");
                intent.putExtra("lecturerName", exams.lecturerName != null ? exams.lecturerName : "N/A");
                intent.putExtra("lecturerEmail", exams.lecturerEmail != null ? exams.lecturerEmail : "N/A");
                intent.putExtra("onlineExamURL", exams.onlineExamURL != null ? exams.onlineExamURL : "N/A");
                intent.putExtra("date", exams.date != null ? exams.date : "N/A");

                context.startActivity(intent);
            });
        }
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

//            moduleName = itemView.findViewById(R.id.moduleName);
//            modeText = itemView.findViewById(R.id.modeText);
//            locationText = itemView.findViewById(R.id.locationText);
//            timeText = itemView.findViewById(R.id.timeText);
//            lecturerNameText = itemView.findViewById(R.id.lecturerNameText);
//
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//
//                    int position = getAdapterPosition();
//                    if (position != RecyclerView.NO_POSITION) {
//                        Classes selectedClass = list.get(position);
//
//                        Intent intent = new Intent(context, ClassesCardDetailsActivity.class);
//
//                        intent.putExtra("classID", selectedClass.classID != null ? selectedClass.classID : "N/A");
//                        intent.putExtra("classMode", selectedClass.classMode != null ? selectedClass.classMode : "N/A");
//                        intent.putExtra("moduleName", selectedClass.moduleName != null ? selectedClass.moduleName : "N/A");
//                        intent.putExtra("roomNumber", selectedClass.roomNumber != null ? selectedClass.roomNumber : "N/A");
//                        intent.putExtra("building", selectedClass.building != null ? selectedClass.building : "N/A");
//                        intent.putExtra("startTime", selectedClass.startTime != null ? selectedClass.startTime : "N/A");
//                        intent.putExtra("endTime", selectedClass.endTime != null ? selectedClass.endTime : "N/A");
//                        intent.putExtra("lecturerName", selectedClass.lecturerName != null ? selectedClass.lecturerName : "N/A");
//                        intent.putExtra("lecturerEmail", selectedClass.lecturerEmail != null ? selectedClass.lecturerEmail : "N/A");
//                        intent.putExtra("onlineClassURL", selectedClass.onlineClassURL != null ? selectedClass.onlineClassURL : "N/A");
//                        intent.putExtra("occurence", selectedClass.isRepeating != null ? selectedClass.isRepeating : false);
//                        intent.putExtra("days", selectedClass.days != null ? selectedClass.days : new ArrayList<String>());
//                        intent.putExtra("date", selectedClass.date != null ? selectedClass.date : "N/A");
//
//                        context.startActivity(intent);
//                    }
//
//                }
//            });
        }
    }

    //Updates the Classes list displayed in the RecyclerView based on the filtered list.
    public void updateClassesList(ArrayList<Classes> filteredList) {
        this.combinedList.clear();
        this.combinedList.addAll(filteredList);
        notifyDataSetChanged(); // Notify that the data has changed and the UI should be updated
    }

    //Filters the original Classes list based on the module name and updates the RecyclerView with the filtered list.
    public void filterClassesByModuleName(String moduleName) {
        ArrayList<Classes> filteredList = new ArrayList<>();

        if (moduleName.contains("All Class Modules")) {
            filteredList.addAll(classesOriginalList); // Show all classes if no filter is selected
        } else {
            for (Classes classes : classesOriginalList) {
                if (moduleName.equals(classes.getModuleName())) {
                    filteredList.add(classes);
                }
            }
        }

        // Update the RecyclerView with the filtered list
        updateClassesList(filteredList);
    }

    //Updates the Exams list displayed in the RecyclerView based on the filtered list.
    public void updateExamsList(ArrayList<Exams> filteredList) {
        this.combinedList.clear();
        this.combinedList.addAll(filteredList);
        notifyDataSetChanged(); // Notify that the data has changed and the UI should be updated
    }

    //Filters the original Exams list based on the module name and updates the RecyclerView with the filtered list.
    public void filterExamsByModuleName(String moduleName) {
        ArrayList<Exams> filteredList = new ArrayList<>();

        if (moduleName.contains("All Exam Modules")) {
            filteredList.addAll(examsOriginalList); // Show all classes if no filter is selected
        } else {
            for (Exams exams : examsOriginalList) {
                if (moduleName.equals(exams.getModuleName())) {
                    filteredList.add(exams);
                }
            }
        }

        // Update the RecyclerView with the filtered list
        updateExamsList(filteredList);
    }
}
