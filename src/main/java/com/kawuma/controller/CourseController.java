package com.kawuma.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kawuma.dto.Course;
import com.kawuma.dto.CourseRequestDTO;
import com.kawuma.dto.CourseResponseDTO;
import com.kawuma.dto.ServiceResponse;
import com.kawuma.service.CourseService;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/course")
@AllArgsConstructor
public class CourseController {
       private CourseService courseService;

    // public CourseController(CourseService courseService) {
    //     this.courseService = courseService;

    // }
    @PostMapping("/add")
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO) {
      //validate request
       // validateRequesrPayload(courseRequestDTO);
     ServiceResponse<CourseResponseDTO> serviceResponse= new ServiceResponse<>();
       try {
         CourseResponseDTO newCourse = courseService.onboardNewCourse(courseRequestDTO);
        serviceResponse.setStatus(HttpStatus.CREATED);
        serviceResponse.setResponse(newCourse);  

       } catch (Exception e) {
       serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
       }

        //return new ServiceResponse<>( HttpStatus.CREATED,newCourse);// 201
        return serviceResponse;

    }

    @GetMapping
    public ServiceResponse<List<CourseResponseDTO>> findAllCourses() {
       List<CourseResponseDTO>courseResponseDTOS = courseService.viewAllCourses();
        return new ServiceResponse<>(HttpStatus.OK,courseResponseDTOS);// 200
    }

    @GetMapping("/search/path/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourse(@PathVariable Integer courseId) {
 CourseResponseDTO  responseDTO =courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDTO);
    }

    @GetMapping("/search/request")
    public ServiceResponse<CourseResponseDTO> findCourseUsingRequestParam(@RequestParam(required = false,defaultValue ="1") Integer courseId) {

        CourseResponseDTO  responseDTO =courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDTO);
    }

    // delete Course
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);

    }
    // Update the Course

    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody  CourseRequestDTO courseRequestDTO) {
        //validate request
       // validateRequesrPayload(courseRequestDTO);
         CourseResponseDTO courseResponseDTO =courseService.updateCourse(courseId, courseRequestDTO) ;
        return new ServiceResponse <>( HttpStatus.OK,courseResponseDTO);
    }

    //Method to Validate Payload
    private void validateRequesrPayload(CourseRequestDTO courseRequestDTO){

        if (courseRequestDTO.getDuration()== null || courseRequestDTO.getDuration().isEmpty()){
            throw new  RuntimeException("Duration Feild needs to be passed");

        }
        if(courseRequestDTO.getFees()==0){
             throw new  RuntimeException("Fees value must be provided");  


        }
    }

}
