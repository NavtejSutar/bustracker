package com.nexusbus.tracker.Controller;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.nexusbus.tracker.DTO.UsersDto;
import com.nexusbus.tracker.Entities.Users;
import com.nexusbus.tracker.Repository.UsersRepo;


@RestController
public class UsersController {

    private final UsersRepo usersRepo;
    private final PasswordEncoder passwordEncoder;

    public UsersController(UsersRepo usersRepo,PasswordEncoder passwordEncoder){
        this.usersRepo=usersRepo;
        this.passwordEncoder=passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<UsersDto> registerUsers(@RequestBody Users user) {
        try{
            user.setRole("User");
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            Users savedUsers=usersRepo.save(user);
            UsersDto usersDto=new UsersDto(
                savedUsers.getFName(),
                savedUsers.getLName(),
                savedUsers.getEmailId(),
                savedUsers.getRole()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(usersDto);
        }catch(DataIntegrityViolationException e){  //409 for not being unique
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/me")
    public ResponseEntity<UsersDto> getUsersDetails(Authentication auth){
        Users users= usersRepo.findByEmailId(auth.getName()).orElseThrow();
        UsersDto userDetail=new UsersDto(
            users.getFName(),
            users.getLName(),
            users.getEmailId(),
            users.getRole()
        );

        return ResponseEntity.ok(userDetail);
    }
    
}
