package com.kawuma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.kawuma.controller.CourseController;
import com.kawuma.exception.CourseServiceBusinessException;
import com.kawuma.util.AppUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CourseService {

    //H2 ,DERBY ,AeroSpike-> Inmemory Database
    @Autowired
    private CourseDao courseDao;
    Logger log = LoggerFactory.getLogger(CourseService.class);
    //private List<Course> courseDatabase = new ArrayList<>();

    // create course object in DB
    public CourseResponseDTO onboardNewCourse(CourseRequestDTO courseRequestDTO) {

        // convert DTO TO ENTITY
        CourseEntity courseEntity = AppUtils.mapDTOToEntity(courseRequestDTO);
        CourseEntity entity = null;
        log.info("CourseService:: onboardNewCourse method started");
        try {
            log.debug("CourseService::onboardNewCourse request payload {}",AppUtils.convertObjectToJson(courseEntity));
            entity = courseDao.save(courseEntity);
            log.debug("Course Entity response from Data{}", AppUtils.convertObjectToJson(entity));
            // convert ENTITY TO DTO
            CourseResponseDTO courseResponseDTO = AppUtils.mapEntityToDTO(entity);
            courseResponseDTO.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
            log.debug("Course Entity:: onboardNewCourse method response{}", AppUtils.convertObjectToJson(courseRequestDTO));
            log.info("CourseService::onboardNewCourse method execution ended");
            return courseResponseDTO;
        } catch (Exception exception) {
            log.error("CourseService::onboardNewCourse exception occurs while persisting the course Object to DB");
            throw new CourseServiceBusinessException("onboardNewCourse service method failed");
        }


    }

    //load all the courses in the database
    public List<CourseResponseDTO> viewAllCourses() {
        Iterable<CourseEntity> courseEntities = null;
        try {
            courseEntities = courseDao.findAll();
            return StreamSupport.stream(courseEntities.spliterator(), false)
                    .map(courseEntity -> AppUtils.mapEntityToDTO(courseEntity))
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new CourseServiceBusinessException("viewAllCourses service method failed");
        }


    }

    // finding course by id
    public CourseResponseDTO findByCourseId(Integer courseId) {
        CourseEntity courseEntity = courseDao.findById(courseId)
                .orElseThrow(() -> new CourseServiceBusinessException(courseId + " doesn't not exist in system"));
        return AppUtils.mapEntityToDTO(courseEntity);
    }

    // delete course
    public void deleteCourse(int courseId) {
        log.info("CourseService:: deleteCourse method started");
        try {
            log.debug("CourseService ::deleteCourse method input {}", courseId);
            courseDao.deleteById(courseId);
        } catch (Exception ex) {
            log.error("CourseService::deleteCourse exception occurs while deleting  the course Object {} ", ex.getMessage());
            throw new CourseServiceBusinessException("deleteCourse service method failed..");

        }
        log.info("CourseService:: deleteCourse method ended...");

    }


    // update the course
    public CourseResponseDTO updateCourse(int courseId, CourseRequestDTO courseRequestDTO) {

        log.info("CourseService:: updateCourse method started");
        try {
            log.debug("CourseService::updateCourse request payload {} & id {}",AppUtils.convertObjectToJson(courseRequestDTO),courseId );
            CourseEntity existingCourseEntity = courseDao.findById(courseId).orElseThrow(()-> new RuntimeException("Course Object is not present in Database with id"+ courseId));
            log.debug("CourseService::updateCourse getting existing  course object as  {}",AppUtils.convertObjectToJson(existingCourseEntity) );
            existingCourseEntity.setName(courseRequestDTO.getName());
            existingCourseEntity.setTrainerName(courseRequestDTO.getTrainerName());
            existingCourseEntity.setDuration(courseRequestDTO.getDuration());
            existingCourseEntity.setStartDate(courseRequestDTO.getStartDate());
            existingCourseEntity.setCourseType(courseRequestDTO.getCourseType());
            existingCourseEntity.setFees(courseRequestDTO.getFees());
            existingCourseEntity.setCertifiedAvailable(courseRequestDTO.isCertifiedAvailable());
            existingCourseEntity.setDescription(courseRequestDTO.getDescription());
            CourseEntity updatedCourseEntity = courseDao.save(existingCourseEntity);
            CourseResponseDTO courseResponseDTO= AppUtils.mapEntityToDTO(updatedCourseEntity);
            log.debug("CourseService::updateCourse getting updated  course object as  {}",AppUtils.convertObjectToJson(courseResponseDTO) );
            log.info("CourseService::updateCourse method execution ended");
            return courseResponseDTO;
            //AppUtils.mapEntityToDTO(updatedCourseEntity);
        } catch (Exception ex) {
            log.error("CourseService::updateCourse exception occurs while updating the course Object {}",ex.getMessage());
            throw new CourseServiceBusinessException("updatedCourse service method failed.." + ex.getMessage());
        }
    }


}