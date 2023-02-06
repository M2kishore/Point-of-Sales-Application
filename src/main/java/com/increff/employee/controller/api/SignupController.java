package com.increff.employee.controller.api;

import com.increff.employee.model.data.InfoData;
import com.increff.employee.model.form.SignupForm;
import com.increff.employee.pojo.UserPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Api
@RestController
public class SignupController {
    @Autowired
    UserService userService;
    @Autowired
    private InfoData info;
    @Value("#{'${arrayOfEmails}'.split(',')}")
    private List<String> arrayOfSupervisorEmails = new ArrayList<String>();
    @ApiOperation(value = "Adds a user")
    @RequestMapping(path = "/session/signup", method = RequestMethod.POST,consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ModelAndView addUser(SignupForm signupForm) throws ApiException {
        //check user presence
        boolean isPresent = checkUserPresence(signupForm.getEmail());
        if(isPresent){
            return new ModelAndView("redirect:/site/login");
        }
        UserPojo userPojo = convert(signupForm);
        userService.add(userPojo);
        info.setSignupMessage("User Added, Now you can login");
        return new ModelAndView("redirect:/site/login");
    }
    private UserPojo convert(SignupForm signupForm) throws ApiException {
        //set the role
        String role = "operator";
        for(String email : arrayOfSupervisorEmails){
            if(email.equals(signupForm.getEmail())){
                role = "supervisor";
            }
        }
        UserPojo userPojo = new UserPojo();
        userPojo.setEmail(signupForm.getEmail());
        userPojo.setPassword(signupForm.getPassword());
        userPojo.setRole(role);
        return userPojo;
    }
    private boolean checkUserPresence(String email) throws ApiException {
        //check if email already present
        UserPojo revceivedUserPojo = userService.get(email);
        if(revceivedUserPojo != null){
            info.setSignupMessage("User Already Present");
            return true;
        }
        return false;
    }
}
