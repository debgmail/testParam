/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.dao;

import com.web.spring.entities.UserProfile;
import com.web.spring.repo.UserProfileRepo;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 *
 * @author 330085
 */
@Repository("userProfileDao")
public class UserProfileDao {
    
    private static final Logger LOGGER = Logger.getLogger(UserProfileDao.class);
    
    @Autowired
    private UserProfileRepo userProfileRepo;
    
    public UserProfile findById(int id) {
        return userProfileRepo.findById(id);
    }
 
    public UserProfile findByType(String type) {        
        return userProfileRepo.findByType(type);
    }
    
    public List<UserProfile> findAll(){        
        return userProfileRepo.findAll();
    }
}
