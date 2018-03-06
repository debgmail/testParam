/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.security;

import com.web.spring.entities.AppUser;
import com.web.spring.repo.UserRepo;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 330085
 */
@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{
    
    private static final Logger LOGGER = Logger.getLogger(CustomUserDetailsService.class);
    
    @Autowired
    private UserRepo userRepo;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepo.findBySsoId(username);
        LOGGER.info("User : {}"+ user);
        
        if(user == null){
            LOGGER.info("User not found");
            throw new UsernameNotFoundException("Username not found");
        }
        
        return new User(user.getSsoId(), user.getPassword(), true, true, true, true, getGrantedAuthorities(user));
    }
    
    private List<GrantedAuthority> getGrantedAuthorities(AppUser user){
        List<GrantedAuthority> authorities = new ArrayList<>();
         
        user.getUserProfileList().stream().map((userProfile) -> {
            LOGGER.info("UserProfile : {} "+ userProfile);
            return userProfile;
        }).forEachOrdered((userProfile) -> {
            authorities.add(new SimpleGrantedAuthority("ROLE_"+userProfile.getType()));
        });
        LOGGER.info("authorities : {}" + authorities);
        return authorities;
    }
}
