/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.spring.entities.AppUser;

/**
 *
 * @author 330085
 */
public interface UserRepo extends JpaRepository<AppUser, Long>{
    
    public AppUser findById(int id);
    
    public AppUser findBySsoId(String ssoId);
    
    public void removeByssoId(String ssoId);
    
    public void removeByFirstName(String firstName);   
    
}
