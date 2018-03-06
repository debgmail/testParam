/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.service;

import com.web.spring.entities.AppUser;
import com.web.spring.repo.UserRepo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 330085
 */
@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
    
    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser findById(int id) {
        
        return userRepo.findById(id);
    }

    @Override
    public AppUser findBySSO(String sso) {
        
        return userRepo.findBySsoId(sso);
    }

    @Override
    public void saveUser(AppUser user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepo.save(user);
    }

    @Override
    public void updateUser(AppUser user) {
        userRepo.save(user);
    }

    @Override
    public void deleteUserBySSO(String sso) {
        userRepo.removeByssoId(sso);
    }

    @Override
    public List<AppUser> findAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public boolean isUserSSOUnique(Integer id, String sso) {
        AppUser user = findBySSO(sso);
        return ( user == null || ((id != null) && (user.getId().equals(id))));
    }
    
}
