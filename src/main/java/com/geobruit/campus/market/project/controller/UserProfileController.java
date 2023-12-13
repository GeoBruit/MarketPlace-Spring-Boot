package com.geobruit.campus.market.project.controller;

import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.model.UserProfile;
import com.geobruit.campus.market.project.service.UserProfileService;
import com.geobruit.campus.market.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserProfileController {

    UserProfileService userProfileService;
    UserService userService;

    @Autowired
    public UserProfileController(UserProfileService theUserProfileService, UserService theUserService){
        userProfileService = theUserProfileService;
        userService = theUserService;
    }

    /*
    * Displaying the for to create a user profile
    * we provide the user Id in the url just now
    * TODO change to  session id after authentification is implemented
    * */
    @GetMapping("/create-profile/{userId}")
    public String showUserProfileForm(@PathVariable Long userId, Model model){

        model.addAttribute("user",userService.findUserById(userId));

        return "userProfileForm";
    }

    /*
    * Saving the user profile for a  user
    * model attribute 'user'
    * we access the userService.setUserProfile and give it the user instance plus the profile created
    * */
    @PostMapping("/save-profile")
    public String saveUserProfile(@ModelAttribute("user") User theUser){

        userProfileService.setUserProfile(theUser, theUser.getUserProfile());

        return "redirect:/list";
    }
}
