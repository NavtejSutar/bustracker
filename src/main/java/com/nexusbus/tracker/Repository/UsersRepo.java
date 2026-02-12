package com.nexusbus.tracker.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nexusbus.tracker.Entities.Users;

public interface UsersRepo extends JpaRepository<Users, Integer> {
    Optional<Users> findByEmailId(String emailId);
}
