package com.kawuma.controller;

import java.util.List;

import com.kawuma.util.AppUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
public class CourseController {

    Logger log = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private final CourseService courseService;

    public CourseController(CourseService courseService) {
       this.courseService = courseService;
   }

    @Operation(summary ="Add a new Course to the System")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",description ="course Added Successfully",
                    content= {
                            @Content(mediaType = "application/json",schema = @Schema(implementation = CourseResponseDTO.class))

                    }),
            @ApiResponse(responseCode = "400", description = "Validation Error")
    }
    )
    @PostMapping("/add")
    public ServiceResponse<CourseResponseDTO> addCourse(@RequestBody @Valid CourseRequestDTO courseRequestDTO) {
        //validate request
        // validateRequestPayload(courseRequestDTO);
        log.info("CourseController:: addCourse method Request payload :{}", AppUtils.convertObjectToJson(courseRequestDTO));
        ServiceResponse<CourseResponseDTO> serviceResponse = new ServiceResponse<>();

        CourseResponseDTO newCourse = courseService.onboardNewCourse(courseRequestDTO);
        serviceResponse.setStatus(HttpStatus.CREATED);
        serviceResponse.setResponse(newCourse);
        log.info("CourseController:: addCourse method Request payload :{}", AppUtils.convertObjectToJson(serviceResponse));
        return new ServiceResponse<>( HttpStatus.CREATED,newCourse);// 201
        //return serviceResponse;

    }

    @Operation(summary ="Fetch All Course Objects")
    @GetMapping
    public ServiceResponse<List<CourseResponseDTO>> findAllCourses() {
        List<CourseResponseDTO> courseResponseDTOS = courseService.viewAllCourses();
        return new ServiceResponse<>(HttpStatus.OK, courseResponseDTOS);// 200
    }

    @Operation(summary ="Find course by courseId")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",description ="course found",
            content ={
                @Content(mediaType = "application/json",schema = @Schema(implementation = CourseResponseDTO.class))

            }),
            @ApiResponse(responseCode = "400", description = "course not found with given id")
    }
    )
    @GetMapping("/search/path/{courseId}")
    public ServiceResponse<CourseResponseDTO> findCourse(@PathVariable Integer courseId) {
        CourseResponseDTO responseDTO = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDTO);
    }
    @Operation(summary ="Search Course By CourseID using RequestParam")
    @GetMapping("/search/request")
    public ServiceResponse<CourseResponseDTO> findCourseUsingRequestParam(@RequestParam(required = false, defaultValue = "1") Integer courseId) {

        CourseResponseDTO responseDTO = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDTO);
    }

    // delete Course
    @Operation(summary ="Delete Course By CourseId")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        log.info("CourseController:: deleteCourse deleting a course with id {}", courseId);
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);

    }
    // Update the Course

    @Operation(summary ="Modify Course By CourseId")
    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDTO> updateCourse(@PathVariable int courseId, @RequestBody CourseRequestDTO courseRequestDTO) {
        //validate request
        // validateRequestPayload(courseRequestDTO);
        log.info("CourseController::update method Request payload :{} and {}", AppUtils.convertObjectToJson(courseRequestDTO), courseId);
        CourseResponseDTO courseResponseDTO = courseService.updateCourse(courseId, courseRequestDTO);
        ServiceResponse<CourseResponseDTO> response = new ServiceResponse<>(HttpStatus.OK, courseResponseDTO);
        log.info("CourseController:: addCourse method Request payload :{}", AppUtils.convertObjectToJson(response), courseId);
        return response;
    }


    //Method to Validate Payload
    private void validateRequestPayload(CourseRequestDTO courseRequestDTO) {
        if (courseRequestDTO.getDuration() == null || courseRequestDTO.getDuration().isEmpty()) {
            throw new RuntimeException("Duration Field needs to be passed");
        }
        if (courseRequestDTO.getFees() == 0) {
            throw new RuntimeException("Fees value must be provided");
        }
    }

    @GetMapping("/log")
    public String loggingLevel() {

        log.trace("trace message");
        log.debug("debug message");
        log.info("info message");
        log.warn("trace message");
        log.error("error message");

        return "log printed in console";
    }

}
