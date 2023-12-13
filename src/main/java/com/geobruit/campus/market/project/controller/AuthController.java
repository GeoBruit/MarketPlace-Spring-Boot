package com.geobruit.campus.market.project.controller;


import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;


@Controller
public class AuthController {

    UserService userService;

    @Autowired
    public AuthController(UserService theUserService){
        userService = theUserService;
    }

    /**
     * Home page view (no auth required)
     * @return the home.html template
     */
    @GetMapping("/home")
    public String showHomePage(){
        return "home";
    }

    /**
     *  Shows the user registration form for the user
     * @param model the model attribute
     * @return the registerUser.html template
     */
    @GetMapping("/register")
    public String showRegisterUserForm(Model model) {
        model.addAttribute("user", new User());
        return "registerUser";
    }

    /**
     * Processing the registration for user
     * @param user user object to be added to the database
     * @return
     */
    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user) {
        userService.saveUser(user);
        return "redirect:/list";
    }


    //Custom login view
    @GetMapping("/login-page")
    public String login(){return "login";}
}
