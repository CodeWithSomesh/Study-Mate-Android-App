package com.example.grademaster;

import java.util.ArrayList;
import java.util.List;

public class Classes {

    String classMode;
    String moduleName;
    String roomNumber;
    String building;
    String lecturerName;
    String lecturerEmail;
    String onlineClassURL;
    String isRepeating;
    String date;
    String startTime;
    String endTime;
    ArrayList<String> days;
    String userID;
    String userEmail;

    public Classes() {
    }

    public Classes(String classMode, String moduleName, String roomNumber, String building,
                   String lecturerName, String lecturerEmail, String onlineClassURL,
                   String isRepeating, String date, String startTime, String endTime,
                   List<String> days, String userID,
                   String userEmail) {
        this.classMode = classMode;
        this.moduleName = moduleName;
        this.roomNumber = roomNumber;
        this.building = building;
        this.lecturerName = lecturerName;
        this.lecturerEmail = lecturerEmail;
        this.onlineClassURL = onlineClassURL;
        this.isRepeating = isRepeating;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.days = new ArrayList<>(days);
        this.userID = userID;
        this.userEmail = userEmail;
    }

    public String getClassMode() {
        return classMode;
    }

    public void setClassMode(String classMode) {
        this.classMode = classMode;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getLecturerName() {
        return lecturerName;
    }

    public void setLecturerName(String lecturerName) {
        this.lecturerName = lecturerName;
    }

    public String getLecturerEmail() {
        return lecturerEmail;
    }

    public void setLecturerEmail(String lecturerEmail) {
        this.lecturerEmail = lecturerEmail;
    }

    public String getOnlineClassURL() {
        return onlineClassURL;
    }

    public void setOnlineClassURL(String lecturerEmail) {
        this.onlineClassURL = onlineClassURL;
    }

    public String getIsRepeating() {
        return isRepeating;
    }

    public void setIsRepeating(String isRepeating) {
        this.isRepeating = isRepeating;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public ArrayList<String> getDays() {
        return days;
    }

    public void setDays(ArrayList<String> days) {
        this.days = days;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
