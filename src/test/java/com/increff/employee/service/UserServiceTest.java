package com.increff.employee.service;

import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserServiceTest extends AbstractUnitTest {

    @Autowired
    UserService userService;
    @Test
    public void testAddUser() throws ApiException{
        //add pojo
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail("exampleuser@gmail.com");
        userPojo.setPassword("examplepassword");
        userPojo.setRole("standard");

        userService.add(userPojo);
    }

    @Test
    public void testDuplicateAdd() throws ApiException{
        UserPojo userPojo1 = new UserPojo();
        userPojo1.setEmail("exampleuser@gmail.com");
        userPojo1.setPassword("examplepassword");
        userPojo1.setRole("standard");
        userService.add(userPojo1);

        UserPojo userPojo2 = new UserPojo();
        userPojo2.setEmail("exampleuser@gmail.com");
        userPojo2.setPassword("examplepassword");
        userPojo2.setRole("standard");
        try{
            userService.add(userPojo2);
        }catch (ApiException e){
            assertEquals(e.getMessage(),"User with given email already exists");
        }
    }

    @Test
    public void testGetSingle() throws ApiException{
        //add pojo
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail("exampleuser@gmail.com");
        userPojo.setPassword("examplepassword");
        userPojo.setRole("standard");

        userService.add(userPojo);

        UserPojo receivedUserPojo = userService.get("exampleuser@gmail.com");
        assertEquals(receivedUserPojo.getEmail(),userPojo.getEmail());
        assertEquals(receivedUserPojo.getRole(),userPojo.getRole());
        assertEquals(receivedUserPojo.getPassword(),userPojo.getPassword());
    }

    @Test
    public void testGetAll() throws  ApiException{
        UserPojo userPojo1 = new UserPojo();
        userPojo1.setEmail("exampleuser1@gmail.com");
        userPojo1.setPassword("examplepassword1");
        userPojo1.setRole("standard");

        userService.add(userPojo1);

        UserPojo userPojo2 = new UserPojo();
        userPojo2.setEmail("exampleuser2@gmail.com");
        userPojo2.setPassword("examplepassword2");
        userPojo2.setRole("admin");

        userService.add(userPojo2);

        List<UserPojo> userPojoList = userService.getAll();
        assertEquals(2,userPojoList.size());
    }

    @Test
    public void testDelete() throws ApiException{
        //add pojo
        UserPojo userPojo1 = new UserPojo();
        userPojo1.setEmail("exampleuser1@gmail.com");
        userPojo1.setPassword("examplepassword1");
        userPojo1.setRole("standard");

        userService.add(userPojo1);

        //delete pojo
        int id = userService.getAll().get(0).getId();
        userService.delete(id);
        //retrieve pojo
        List<UserPojo> userPojoList = userService.getAll();
        assertEquals(0,userPojoList.size());
    }

    @Test
    public void testNormalize() throws ApiException{
        UserPojo userPojo1 = new UserPojo();
        userPojo1.setEmail("EXAMPLEUSER@GMAIL.COM");
        userPojo1.setPassword("examplepassword1");
        userPojo1.setRole("Standard ");

        userService.normalize(userPojo1);

        assertEquals("exampleuser@gmail.com",userPojo1.getEmail());
        assertEquals("standard",userPojo1.getRole());

    }
}
