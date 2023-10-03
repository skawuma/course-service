package com.kawuma.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.kawuma.annotation.CourseTypeValidation;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CourseRequestDTO {
  @NotBlank(message = "Course Name cannot be Null or Empty")
   private String name;
  @NotEmpty(message ="Trainer name should be defined ")
   private String trainerName;
  @NotNull(message= " Duration must be specified")
   private String duration;// days
   // convert to a string Object
   @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyy")
   @Past(message ="Start Date cannot Appear  before Current date")
   private Date startDate;
   @CourseTypeValidation
   private String courseType;// Live or recording
   @Min(value =1500,message = "Course price cant be less than 1500")
   @Max(value =5000,message = "Course price cant be more than 5000")
   private double fees;
   private boolean isCertifiedAvailable;
   @NotEmpty(message= "Description must be Defined")
   @Length(min=5,max =100)
   private String description;
   @Email(message = "invalid email id")
   private  String  emailId;
   @Pattern(regexp ="^[0-9]{10}$")
   private String contact;
   
    
}
