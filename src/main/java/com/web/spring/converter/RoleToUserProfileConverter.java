/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.converter;

import com.web.spring.entities.UserProfile;
import com.web.spring.service.UserProfileService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

/**
 *
 * @author 330085
 */
@Component("roleToUserProfileConverter")
public class RoleToUserProfileConverter implements Converter<Object, UserProfile>{
    
    private static final Logger LOGGER = Logger.getLogger(RoleToUserProfileConverter.class);
    
    @Autowired
    private UserProfileService userProfileService;

    @Override
    public UserProfile convert(Object source) {
        
        Integer id = Integer.parseInt((String)source);
        UserProfile userProfile = userProfileService.findById(id);
        LOGGER.info("Profile : {}"+userProfile);
        return userProfile;
    }
    
}
