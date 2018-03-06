/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.service;

import com.web.spring.entities.AppUser;
import java.util.List;

/**
 *
 * @author 330085
 */
public interface UserService {
    
    AppUser findById(int id);
     
    AppUser findBySSO(String sso);
     
    void saveUser(AppUser user);
     
    void updateUser(AppUser user);
     
    void deleteUserBySSO(String sso);
 
    List<AppUser> findAllUsers(); 
     
    boolean isUserSSOUnique(Integer id, String sso);
}
