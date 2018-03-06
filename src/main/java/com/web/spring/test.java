/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author 330085
 */
public class Test {
    
    public static void main(String[] a){
        String password="";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        System.out.println("encode :"+encoder.encode(password));        
    }
}
