package com.kawuma.dao;

import org.springframework.data.repository.CrudRepository;

import com.kawuma.entity.CourseEntity;

public interface CourseDao extends CrudRepository<CourseEntity,Integer >{
    
}
