package com.delpipi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.delpipi.entities.AppRole;

@Repository
public interface AppRoleRepository extends JpaRepository<AppRole, Long>{
    AppRole findByRoleName(String roleName);
}
