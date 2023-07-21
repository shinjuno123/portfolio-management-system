package com.amazing.juno.pmsrest.dao;

import com.amazing.juno.pmsrest.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByEmail(String email);

    User findUserByRole(String role);

}
