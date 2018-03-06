/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.controller;

import com.web.spring.entities.AppUser;
import com.web.spring.entities.UserProfile;
import com.web.spring.service.UserProfileService;
import com.web.spring.service.UserService;
import java.util.List;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.rememberme.PersistentTokenBasedRememberMeServices;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author 330085
 */
@Controller
@RequestMapping("/")
@SessionAttributes("roles")
public class AppController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private MessageSource messageSource;
    
    @Autowired
    private UserProfileService userProfileService;
    
    @Autowired
    private AuthenticationTrustResolver authenticationTrustResolver;
    
    @Autowired
    private PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices;
    
    /**
     * List down all existing users
     * @param modelMap
     * @return 
     */
    @RequestMapping(value = {"/", "/list"}, method = RequestMethod.GET)
    public String listUsers(ModelMap modelMap){        
        List<AppUser> userList = userService.findAllUsers();
        modelMap.addAttribute("users", userList);
        modelMap.addAttribute("loggedinuser", getPrincipal());
        return "userlist";
    }
    
    /**
     * provide a medium for adding new user registration page
     * @param modelMap
     * @return 
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.GET)
    public String newUser(ModelMap modelMap) {
        AppUser user = new AppUser();
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("edit", false);
        modelMap.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }
    
    /**
     * This method will be called on form submission, handling POST request for
     * saving user in database. It also validates the user input
     * @param user
     * @param result
     * @param modelMap
     * @return 
     */
    @RequestMapping(value = {"/newuser"}, method = RequestMethod.POST)
    public String saveUser(@Valid AppUser user, BindingResult result, ModelMap modelMap){
        
        if(result.hasErrors()){
            return "registration";
        }
        
        /*
         * Preferred way to achieve uniqueness of field [sso] should be implementing custom @Unique annotation 
         * and applying it on field [sso] of Model class [User].
         * 
         * Below mentioned peace of code [if block] is to demonstrate that you can fill custom errors outside the validation
         * framework as well while still using internationalized messages.
         * 
         */
        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user", "ssoId", messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }
        
        userService.saveUser(user);
        modelMap.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " registered successfully");
        modelMap.addAttribute("loggedinuser", getPrincipal());
        return "registrationsuccess";
    }
    
    @RequestMapping(value = {"/edit-user-{ssoId}"}, method = RequestMethod.GET)
    public String editUser(@PathVariable String ssoId, ModelMap modelMap){
        AppUser user = userService.findBySSO(ssoId);
        modelMap.addAttribute("user", user);
        modelMap.addAttribute("edit", true);
        modelMap.addAttribute("loggedinuser", getPrincipal());
        return "registration";
    }
    
    @RequestMapping(value = {"/edit-user-{ssoId}"}, method = RequestMethod.POST)
    public String updateUser(@Valid AppUser user, BindingResult result, ModelMap modelMap, @PathVariable String ssoId){
        
        if(result.hasErrors()){
            return "registration";
        }
        //Uncomment below 'if block' if you WANT TO ALLOW UPDATING SSO_ID in UI which is a unique key to a User.
        if(!userService.isUserSSOUnique(user.getId(), user.getSsoId())){
            FieldError ssoError =new FieldError("user","ssoId",messageSource.getMessage("non.unique.ssoId", new String[]{user.getSsoId()}, Locale.getDefault()));
            result.addError(ssoError);
            return "registration";
        }
 
 
        userService.updateUser(user);
 
        modelMap.addAttribute("success", "User " + user.getFirstName() + " "+ user.getLastName() + " updated successfully");
        modelMap.addAttribute("loggedinuser", getPrincipal());
        return "registrationsuccess";
    }
    /**
     * This method will delete an user by it's SSOID value.
     * @param ssoId
     * @return 
     */
    @RequestMapping(value = {"/delete-user-{ssoId}"}, method = RequestMethod.GET)
    public String deleteUser(@PathVariable String ssoId){
        userService.deleteUserBySSO(ssoId);
        return "redirect:/list";
    }
    
    /**
     * This method will provide UserProfile list to views
     * @return 
     */
    @ModelAttribute("roles")
    public List<UserProfile> initializeProfiles(){
        return userProfileService.findAll();
    }
    
    /**
     * This method handles Access-Denied redirect.
     * @param modelMap
     * @return 
     */
    @RequestMapping(value = {"/Access_Denied"}, method = RequestMethod.GET)
    public String accessDeniedPage(ModelMap modelMap){
        modelMap.addAttribute("loggedinuser", getPrincipal());
        return "accessDenied";
    }
    
    /**
     * This method handles login GET requests.
     * If users is already logged-in and tries to goto login page again, will be redirected to list page.
     * @return 
     */
    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public String loginPage(){
        /**
         * check whether the user is already logged-in or not
         */
        if(isCurrentAuthenticationAnonymous()){
            return "login";
        }else{
            return "redirect:/list";
        }
    }
    
    /**
     * This method handles logout requests.
     * Toggle the handlers if you are RememberMe functionality is useless in your app.
     * @param request
     * @param response
     * @return 
     */
    @RequestMapping(value = {"/logout"}, method = RequestMethod.GET)
    public String logoutPage (HttpServletRequest request, HttpServletResponse response){
        Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        if(null != authentication){
            //new SecurityContextLogoutHandler().logout(request, response, authentication);
            persistentTokenBasedRememberMeServices.logout(request, response, authentication);
            SecurityContextHolder.getContext().setAuthentication(null);
        }
        return "redirect:/login?logout";
    }
    
    /**
     * This method returns true if users is already authenticated [logged-in], else false.
     * @return 
     */
    private Boolean isCurrentAuthenticationAnonymous(){
        final Authentication authentication =  SecurityContextHolder.getContext().getAuthentication();
        return authenticationTrustResolver.isAnonymous(authentication);
    }
    
    /**
     * This method returns the principal[user-name] of logged-in user.
     * @return 
     */
    private String getPrincipal(){        
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        if(principal instanceof UserDetails)
            return ((UserDetails) principal).getUsername();
        else
            return principal.toString();        
    }
}
