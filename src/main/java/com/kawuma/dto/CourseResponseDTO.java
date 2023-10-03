package com.kawuma.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CourseResponseDTO {
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
   private String courseUniqueCode;
   private  String  emailId;
   private String contact;
    
}
