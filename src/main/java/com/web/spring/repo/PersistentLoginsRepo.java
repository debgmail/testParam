/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.repo;

import com.web.spring.entities.PersistentLogins;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author 330085
 */
public interface PersistentLoginsRepo extends JpaRepository<PersistentLogins, String>{
    
    public PersistentLogins findBySeries(String seriesId);
    
    public PersistentLogins findByUsername(String username);
}
