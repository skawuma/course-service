package com.kawuma.util;

import com.kawuma.dto.CourseRequestDTO;
import com.kawuma.entity.CourseEntity;

public class AppUtils {

    // DTO -> ENTITY
    public CourseEntity mapDTOToEntity(CourseRequestDTO courseRequestDTO) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setCourseId(courseRequestDTO.getCourseId());
        courseEntity.setName(courseRequestDTO.getName());
        courseEntity.setTrainerName(courseRequestDTO.getTrainerName());
        courseEntity.setDuration(courseRequestDTO.getDuration());
        courseEntity.setStartDate(courseRequestDTO.getStartDate());
        courseEntity.setCourseType(courseRequestDTO.getCourseType());
        courseEntity.setFees(courseRequestDTO.getFees());
        courseEntity.setCertifiedAvailable(courseRequestDTO.isCertifiedAvailable());
        courseEntity.setDescription(courseRequestDTO.getDescription());
        return courseEntity;

    }

}
