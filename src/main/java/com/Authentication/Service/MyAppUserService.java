package com.Authentication.Service;

import com.Authentication.Model.MyAppUser;
import com.Authentication.Model.MyAppUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class MyAppUserService implements UserDetailsService {

    @Autowired
    private MyAppUserRepository myAppUserRepository;

    // This method is used by Spring Security to load user details by username
    @Override
    public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
        Optional <MyAppUser> user = myAppUserRepository.findByUsernameOrEmail(input, input);
        if(user.isPresent()){
            var userObj = user.get();
            return User.builder()
                    .username(userObj.getUsername()) // If you are getting an error here, just ignore it because I used Lombok's @Getter and @Setter annotations in MyAppUser class
                    .password(userObj.getPassword())// If you are getting an error here, just ignore it because I used Lombok's @Getter and @Setter annotations in MyAppUser class
                    .build();
        }else {
            throw new UsernameNotFoundException("User " + input + " not found");
        }
    }
}
