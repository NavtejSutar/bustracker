package com.nexusbus.tracker.Service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.nexusbus.tracker.Entities.Users;
import com.nexusbus.tracker.Repository.UsersRepo;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    private final UsersRepo usersRepo;

    public CustomerUserDetailsService(UsersRepo usersRepo){
        this.usersRepo=usersRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String emailId) throws UsernameNotFoundException{
        Optional<Users> userOpt= usersRepo.findByEmailId(emailId);
        Users user= userOpt.get();
        if(user==null){
            throw new UsernameNotFoundException("User not found");
        }else{
            return User.builder()
                    .username(emailId)
                    .password(user.getPassword())
                    .roles(user.getRole())
                    .build();
        }
        
    }
}
