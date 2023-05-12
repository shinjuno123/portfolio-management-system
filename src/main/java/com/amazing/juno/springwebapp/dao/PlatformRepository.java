package com.amazing.juno.springwebapp.dao;

import com.amazing.juno.springwebapp.entity.Platform;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlatformRepository extends JpaRepository<Platform, UUID> {
}
