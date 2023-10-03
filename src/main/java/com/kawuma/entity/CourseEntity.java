package com.kawuma.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Table(name="COURSE_TBL")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String name;
    private String trainerName;
    private String duration;// days
    // convert to a string Object
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
    private Date startDate;
    private String courseType;// Live or recording
    private double fees;
    private boolean isCertifiedAvailable;
    private String description;
    private  String  emailId;
    private String contact;

}