package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByEmail(String email);

}
