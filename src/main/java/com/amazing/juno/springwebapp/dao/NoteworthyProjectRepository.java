package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.About;
import com.amazing.juno.springwebapp.entity.NoteworthyProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;


@Repository
public interface NoteworthyProjectRepository extends JpaRepository<NoteworthyProject, UUID> {

}
