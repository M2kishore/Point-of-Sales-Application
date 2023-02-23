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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            return new ModelAndView("redirect:/site/signup");
        }
        if(!isStrongPassword(signupForm.getPassword())){
            return new ModelAndView("redirect:/site/signup");
        }
        UserPojo userPojo = convert(signupForm);
        userService.add(userPojo);
        info.setSignupMessage("");
        info.setMessage("User Added, Now you can login");
        return new ModelAndView("redirect:/site/login");
    }

    private boolean isStrongPassword(String password) throws ApiException {
        if(password.length() < 8){
            info.setSignupMessage("The password length is too short");
            return false;
        }
        String regex = "^(?=.*[a-z])(?=."
                + "*[A-Z])(?=.*\\d)"
                + "(?=.*[-+_!@#$%^&*., ?]).+$";

        // Compile the ReGex
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(password);

        // Print Yes if string
        // matches ReGex
        if (m.matches()){
            return true;
        }
        info.setSignupMessage("Make sure atleast there is 1 Uppercase, 1 Lowercase, 1 Number and 1 Special Character");
        return false;
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
