package com.example.GreenStich.Repository;

import com.example.GreenStich.Models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface UserRepository extends JpaRepository<User,Integer> {
    User findByUname(String username);
}

