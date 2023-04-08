package com.amazing.juno.springwebapp.dao.admin;

import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NoteworthyProjectRepository extends JpaRepository<NoteworthyProject, UUID> {

}
