package com.example.grademaster;

import java.util.ArrayList;

public class Exams {

    String examID;
    String examMode;
    String moduleName;
    String roomNumber;
    String building;
    String lecturerName;
    String lecturerEmail;
    String onlineExamURL;
    String date;
    String startTime;
    String endTime;
    String userID;
    String userEmail;

    public Exams() {
    }

    public Exams(String examID, String examMode, String moduleName, String roomNumber,
                 String building, String lecturerName, String lecturerEmail, String onlineExamURL,
                 String date, String startTime, String endTime, String userID, String userEmail) {
        this.examID = examID;
        this.examMode = examMode;
        this.moduleName = moduleName;
        this.roomNumber = roomNumber;
        this.building = building;
        this.lecturerName = lecturerName;
        this.lecturerEmail = lecturerEmail;
        this.onlineExamURL = onlineExamURL;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.userID = userID;
        this.userEmail = userEmail;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getExamMode() {
        return examMode;
    }

    public void setExamMode(String examMode) {
        this.examMode = examMode;
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
        return onlineExamURL;
    }

    public void setOnlineClassURL(String onlineExamURL) {
        this.onlineExamURL = onlineExamURL;
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
