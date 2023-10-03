package com.kawuma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.kawuma.util.AppUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.kawuma.dao.CourseDao;
import com.kawuma.dto.Course;
import com.kawuma.dto.CourseRequestDTO;
import com.kawuma.dto.CourseResponseDTO;
import com.kawuma.entity.CourseEntity;

import lombok.AllArgsConstructor;

@Component
@Service
@AllArgsConstructor
public class CourseService {

    //H2 ,DERBY ,AeroSpike-> Inmemory Database
    private CourseDao courseDao;

    // private List<Course> courseDatabase = new ArrayList<>();

    // create course object in DB
    public CourseResponseDTO onboardNewCourse(CourseRequestDTO courseRequesDTO) {

      // convert DTO TO ENTITY
        CourseEntity courseEntity = AppUtils.mapDTOToEntity(courseRequesDTO);


        CourseEntity entity = courseDao.save(courseEntity);

         // convert ENTITY TO DTO  

         CourseResponseDTO courseResponseDTO =AppUtils.mapEntityToDTO(entity);
         courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
         return courseResponseDTO;
 
    }
//load all the courses in the database
    public List<CourseResponseDTO> viewAllCourses() {

        Iterable<CourseEntity> courseEntities = courseDao.findAll();
        return StreamSupport.stream(courseEntities.spliterator(), false)
              .map(courseEntity ->AppUtils.mapEntityToDTO(courseEntity))
              .collect(Collectors.toList());
    
    }

    // finding course by id
    public CourseResponseDTO findByCourseId(Integer courseId) {
        CourseEntity courseEntity = courseDao.findById(courseId)
        .orElseThrow(() -> new RuntimeException(courseId +"not valid"));
   return AppUtils.mapEntityToDTO(courseEntity);
    }

    // delete course
    public void deleteCourse(int courseId) {
      courseDao.deleteById(courseId);

    }

    // update the course
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequestDTO) {
       CourseEntity existingCourseEntity = courseDao.findById(courseId).orElseThrow(null);
//       existingCourseEntity.setCourseId(courseRequestDTO.getCourseId());
       existingCourseEntity.setName(courseRequestDTO.getName());
       existingCourseEntity.setTrainerName(courseRequestDTO.getTrainerName());
       existingCourseEntity.setDuration(courseRequestDTO.getDuration());
       existingCourseEntity.setStartDate(courseRequestDTO.getStartDate());
       existingCourseEntity.setCourseType(courseRequestDTO.getCourseType());
       existingCourseEntity.setFees(courseRequestDTO.getFees());
       existingCourseEntity.setCertifiedAvailable(courseRequestDTO.isCertifiedAvailable());
       existingCourseEntity.setDescription(courseRequestDTO.getDescription());
      CourseEntity updatedCourseEntity =courseDao.save(existingCourseEntity);
        return AppUtils.mapEntityToDTO(updatedCourseEntity);
    }


}