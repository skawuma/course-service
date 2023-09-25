package com.kawuma.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.stereotype.Service;

import com.kawuma.dto.Course;

@Service
public class CourseService {

    private List<Course> courseDatabase = new ArrayList<>();

    // create course object in DB
    public Course onboardNewCourse(Course course) {

        course.setCourseId(new Random().nextInt(3756));
        courseDatabase.add(course);
        return course;

    }
//load all the courses in the database
    public List<Course> viewAllCourses() {
        return courseDatabase;
    }

    // finding course by id
    public Course findByCourseId(Integer courseId) {
        return courseDatabase.stream()
                .filter(course -> course.getCourseId() == courseId)
                .findFirst().orElse(null);

    }

    // delete course
    public void deleteCourse(int courseId) {
        Course course = findByCourseId(courseId);
        courseDatabase.remove(course);

    }

    // update the course
    public Course updatCourse(int courseId, Course course) {
        Course existingCourse = findByCourseId(courseId);
        courseDatabase.set(courseDatabase.indexOf(existingCourse),course);
        return course;
    }

}