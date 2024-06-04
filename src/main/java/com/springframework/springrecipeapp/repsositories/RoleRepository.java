package com.springframework.springrecipeapp.repsositories;


import com.springframework.springrecipeapp.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}