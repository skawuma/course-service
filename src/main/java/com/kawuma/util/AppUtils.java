package com.kawuma.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawuma.dto.CourseRequestDTO;
import com.kawuma.dto.CourseResponseDTO;
import com.kawuma.entity.CourseEntity;

public class AppUtils {

    // DTO -> ENTITY
    public static  CourseEntity mapDTOToEntity(CourseRequestDTO courseRequestDTO) {
        CourseEntity courseEntity = new CourseEntity();

        courseEntity.setName(courseRequestDTO.getName());
        courseEntity.setTrainerName(courseRequestDTO.getTrainerName());
        courseEntity.setDuration(courseRequestDTO.getDuration());
        courseEntity.setStartDate(courseRequestDTO.getStartDate());
        courseEntity.setCourseType(courseRequestDTO.getCourseType());
        courseEntity.setFees(courseRequestDTO.getFees());
        courseEntity.setCertifiedAvailable(courseRequestDTO.isCertifiedAvailable());
        courseEntity.setDescription(courseRequestDTO.getDescription());
        courseEntity.setEmailId(courseRequestDTO.getEmailId());
        courseEntity.setContact(courseRequestDTO.getContact());
        return courseEntity;


    }
 public static CourseResponseDTO mapEntityToDTO(CourseEntity courseEntity) {
     CourseResponseDTO courseResponseDTO = new CourseResponseDTO();
     courseResponseDTO.setCourseId(courseEntity.getCourseId());
     courseResponseDTO.setName(courseEntity.getName());
     courseResponseDTO.setTrainerName(courseEntity.getTrainerName());
     courseResponseDTO.setDuration(courseEntity.getDuration());
     courseResponseDTO.setStartDate(courseEntity.getStartDate());
     courseResponseDTO.setCourseType(courseEntity.getCourseType());
     courseResponseDTO.setFees(courseEntity.getFees());
     courseResponseDTO.setCertifiedAvailable(courseEntity.isCertifiedAvailable());
     courseResponseDTO.setDescription(courseEntity.getDescription());
     courseResponseDTO.setEmailId(courseEntity.getEmailId());
     courseResponseDTO.setContact(courseEntity.getContact());
     return courseResponseDTO;

 }

public static String convertObjectToJson(Object object)  {
    try{
       return  new ObjectMapper().writeValueAsString(object);
    }catch(JsonProcessingException e){
        e.printStackTrace();


    }
    return null;

     }




}
