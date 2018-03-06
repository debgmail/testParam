/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.dao;

import com.web.spring.entities.PersistentLogins;
import com.web.spring.repo.PersistentLoginsRepo;
import java.util.Date;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 330085
 * PersistentTokenRepository implementation commented out since we are using in-memory remember me service
 */
@Repository("tokenRepositoryDao")
@Transactional
public class PersistentLoginsDao /*implements PersistentTokenRepository*/{

    private static final Logger LOGGER = Logger.getLogger(PersistentLoginsDao.class);

    @Autowired
    private PersistentLoginsRepo persistentLoginsRepo;

    
    //@Override
    public void createNewToken(PersistentRememberMeToken token) {
        LOGGER.info("Creating Token for user : {}"+token.getUsername());
        PersistentLogins persistentLogin = new PersistentLogins();
        persistentLogin.setUsername(token.getUsername());
        persistentLogin.setSeries(token.getSeries());
        persistentLogin.setToken(token.getTokenValue());
        persistentLogin.setLastUsed(token.getDate());
        persistentLoginsRepo.save(persistentLogin);

    }

    
    //@Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        LOGGER.info("Fetch Token if any for seriesId : {}"+ seriesId);
        try {
            
            PersistentLogins persistentLogin = persistentLoginsRepo.findBySeries(seriesId);

            return new PersistentRememberMeToken(persistentLogin.getUsername(), persistentLogin.getSeries(),
                    persistentLogin.getToken(), persistentLogin.getLastUsed());
        } catch (Exception e) {
            LOGGER.info("Token not found...");
            return null;
        }
    }
    
    //@Override
    public void removeUserTokens(String username) {
        LOGGER.info("Removing Token if any for user : {}"+ username);
        
        PersistentLogins persistentLogin = persistentLoginsRepo.findByUsername(username);
        if (persistentLogin != null) {
            LOGGER.info("rememberMe was selected");            
            persistentLoginsRepo.delete(persistentLogin);
        }
    }

    
    //@Override
    public void updateToken(String seriesId, String tokenValue, Date lastUsed) {
        LOGGER.info("Updating Token for seriesId : {}"+ seriesId);
        PersistentLogins persistentLogin = persistentLoginsRepo.findBySeries(seriesId);
        persistentLogin.setToken(tokenValue);
        persistentLogin.setLastUsed(lastUsed);
        persistentLoginsRepo.save(persistentLogin);
    }
}
