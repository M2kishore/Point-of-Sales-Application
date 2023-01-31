package com.increff.employee.dao;

import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.AbstractUnitTest;
import com.increff.employee.service.ApiException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class UserDaoTest extends AbstractUnitTest {
    @Autowired
    UserDao userDao;
    @Test
    public void testInsert(){
        //add pojo
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail("exampleuser@gmail.com");
        userPojo.setPassword("examplepassword");
        userPojo.setRole("standard");

        userDao.insert(userPojo);
    }
    @Test
    public void testSingle() throws ApiException {
        //add pojo
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail("exampleuser@gmail.com");
        userPojo.setPassword("examplepassword");
        userPojo.setRole("standard");

        userDao.insert(userPojo);

        UserPojo receivedUserPojo = userDao.select("exampleuser@gmail.com");
        assertEquals(receivedUserPojo.getEmail(),userPojo.getEmail());
        assertEquals(receivedUserPojo.getRole(),userPojo.getRole());
        assertEquals(receivedUserPojo.getPassword(),userPojo.getPassword());
    }
    @Test
    public void testSelectAll() throws  ApiException{
        UserPojo userPojo1 = new UserPojo();
        userPojo1.setEmail("exampleuser1@gmail.com");
        userPojo1.setPassword("examplepassword1");
        userPojo1.setRole("standard");

        userDao.insert(userPojo1);

        UserPojo userPojo2 = new UserPojo();
        userPojo2.setEmail("exampleuser2@gmail.com");
        userPojo2.setPassword("examplepassword2");
        userPojo2.setRole("admin");

        userDao.insert(userPojo2);

        List<UserPojo> userPojoList = userDao.selectAll();
        assertEquals(2,userPojoList.size());
    }
    @Test
    public void testDelete() throws ApiException{
        //add pojo
        UserPojo userPojo1 = new UserPojo();
        userPojo1.setEmail("exampleuser1@gmail.com");
        userPojo1.setPassword("examplepassword1");
        userPojo1.setRole("standard");

        userDao.insert(userPojo1);

        int id = userDao.selectAll().get(0).getId();

        //delete pojo
        userDao.delete(id);
        //retrieve pojo
        List<UserPojo> userPojoList = userDao.selectAll();
        assertEquals(0,userPojoList.size());
    }
}
