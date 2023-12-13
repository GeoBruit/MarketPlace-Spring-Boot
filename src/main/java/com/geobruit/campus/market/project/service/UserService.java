package com.geobruit.campus.market.project.service;

import com.geobruit.campus.market.project.model.Product;
import com.geobruit.campus.market.project.model.Role;
import com.geobruit.campus.market.project.model.User;
import com.geobruit.campus.market.project.repository.ProductRepository;
import com.geobruit.campus.market.project.repository.RoleRepository;
import com.geobruit.campus.market.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.security.PublicKey;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    UserRepository userRepository;
    ProductRepository productRepository;
    RoleRepository roleRepository;
    PasswordEncoder passwordEncoder;
    public UserService(){}

    @Autowired
    public UserService(UserRepository tempUserRepository, ProductRepository theProductRepository,
                       RoleRepository theRoleRepository, PasswordEncoder thePasswordEncoder){
        userRepository = tempUserRepository;
        productRepository = theProductRepository;
        roleRepository = theRoleRepository;
        passwordEncoder = thePasswordEncoder;
    }

    //TODO check for other ways of doing it
    /*
    *
    * */
    public User findUserById(Long userId){

        try{
            return userRepository.getUserById(userId);

        } catch (Exception e){
            System.out.println("User with ID " + userId + " Not found!");
            e.printStackTrace();
            return null;
        }
    }

    public List<User> findAllUsers(){

        try{
            return userRepository.findAll();
        } catch (Exception e) {
            e.getCause();

            return null;
        }
    }

    public User findUserByUsername(String userName){
        try{
            return userRepository.findByUsername(userName);

        } catch (UsernameNotFoundException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public List<Product> getAllProductsForUser(Long userId){

        try{
            return userRepository.findById(userId).get().getProducts();

        }catch (Exception e){
            return null;
        }

    }
    public boolean saveUser(User tempUser){
        try {
            // encrypt the password using spring security before we save the user
            tempUser.setPassword(passwordEncoder.encode(tempUser.getPassword()));

            Role role = roleRepository.findByName("ROLE_ADMIN");
            if(role == null){
                role = checkRoleExist();
            }
            // Assign the role to the user (passing a list with a single role) //TODO check if we can simplify this :)
            tempUser.setRoles(Collections.singletonList(role));


            userRepository.save(tempUser);
            return true;
        } catch (Exception e){
            //e.printStackTrace();
            System.out.println("User was not added to the data base");
            return false;
        }
    }
    public void addProductForUser(Long userId, Product theProduct){
        try{
            User tempUser = userRepository.getUserById(userId);

            tempUser.addProduct(theProduct);
            System.out.println("Product added successfully");

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void deleteProductForUser(User user, Long productId){

        user.getProducts().removeIf(product -> product.getId().equals(productId));
        productRepository.deleteById(productId);
    }



    private Role checkRoleExist(){
        Role role = new Role();
        role.setName("ROLE_ADMIN");
        return roleRepository.save(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user.getRoles())
        );
    }

    private List<SimpleGrantedAuthority> getAuthorities(List<Role> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());
    }
}
