/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.web.spring.repo;

import com.web.spring.entities.AppUser;
import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author 330085
 */
@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
@Transactional(propagation = Propagation.REQUIRED)
public class UserRepoTest {

    protected static final Logger LOGGER = Logger.getLogger(UserRepoTest.class);

    @Autowired
    private UserRepo userRepo;

    public UserRepoTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        AppUser user = new AppUser();
        user.setSsoId("sam");
        user.setPassword("password");
        user.setFirstName("Sam");
        user.setLastName("Smith");
        user.setEmail("samy@xyz.com");

        assertNull(user.getId());
        userRepo.save(user);
        assertNotNull(user.getId());
        
        AppUser user1 = new AppUser();
        user1.setSsoId("sam1");
        user1.setPassword("password");
        user1.setFirstName("abc");
        user1.setLastName("Smith");
        user1.setEmail("samy@xyz.com");

        assertNull(user1.getId());
        userRepo.save(user1);
        assertNotNull(user1.getId());
    }

    @After
    public void tearDown() {
    }

    

    /**
     * Test of findBySsoId method, of class UserRepo.
     */
    @Test
    public void testFindBySsoId() {
        LOGGER.info("findBySsoId");
        String ssoId = "sam";
        AppUser result = userRepo.findBySsoId(ssoId);
        assertNotNull(result);
    }

    /**
     * Test of deleteBySsoId method, of class UserRepo.
     */
    @Test
    public void testDeleteBySsoId() {
        System.out.println("deleteBySsoId");
        try {
            String ssoId = "sam";

            userRepo.removeByFirstName("Sam");
            AppUser result = userRepo.findBySsoId(ssoId);
            assertNull(result);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    
    @Test
    public void testFindAllOrderByFirstNameAsc(){
        System.out.println("findAllOrderByFirstNameAsc");
        List<AppUser> userList = userRepo.findAll(sortByFirstNameAsc());
        assertNotNull(userList);
    }

    private Sort sortByFirstNameAsc(){
        return new Sort(Sort.Direction.ASC, "firstName");
    }
}
