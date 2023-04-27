package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project,UUID> {

}
