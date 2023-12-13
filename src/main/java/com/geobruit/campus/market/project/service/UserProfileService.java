package com.geobruit.campus.market.project.service;

import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.model.UserProfile;
import com.geobruit.campus.market.project.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserProfileService {

    private UserProfileRepository userProfileRepository;

    public UserProfileService(){}

    @Autowired
    public UserProfileService(UserProfileRepository theUserProfileRepo){
        userProfileRepository = theUserProfileRepo;

    }

    public void setUserProfile(User theUser, UserProfile theUserProfile){
        try {
            theUser.setUserProfile(theUserProfile);
            userProfileRepository.save(theUserProfile);
            System.out.println("User Profile added");

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
