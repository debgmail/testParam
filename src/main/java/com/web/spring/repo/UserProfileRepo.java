/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.repo;

import com.web.spring.entities.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 330085
 */
public interface UserProfileRepo extends JpaRepository<UserProfile, Integer>{
    
    public UserProfile findById(int id);
    
    public UserProfile findByType(String type);
}
