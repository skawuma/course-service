package com.kawuma.dto;

import java.util.Date;

public class Course {

   private int courseId;
   private String name;
   private String trainerName;
   private String duration;//days
   private Date startDate;
   private boolean isCertifiedAvailable;
   private String discription;
   

   
public Course() {

}




public Course(int courseId, String name, String trainerName, String duration, Date startDate,
        boolean isCertifiedAvailable, String discription) {
    this.courseId = courseId;
    this.name = name;
    this.trainerName = trainerName;
    this.duration = duration;
    this.startDate = startDate;
    this.isCertifiedAvailable = isCertifiedAvailable;
    this.discription = discription;
}




public int getCourseId() {
    return courseId;
}



public void setCourseId(int courseId) {
    this.courseId = courseId;
}



public String getName() {
    return name;
}



public void setName(String name) {
    this.name = name;
}



public String getTrainerName() {
    return trainerName;
}



public void setTrainerName(String trainerName) {
    this.trainerName = trainerName;
}



public String getDuration() {
    return duration;
}



public void setDuration(String duration) {
    this.duration = duration;
}



public Date getStartDate() {
    return startDate;
}



public void setStartDate(Date startDate) {
    this.startDate = startDate;
}



public boolean isCertifiedAvailable() {
    return isCertifiedAvailable;
}



public void setCertifiedAvailable(boolean isCertifiedAvailable) {
    this.isCertifiedAvailable = isCertifiedAvailable;
}



public String getDiscription() {
    return discription;
}



public void setDiscription(String discription) {
    this.discription = discription;
}


   

    
    
}
