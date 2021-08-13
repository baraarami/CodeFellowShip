package com.example.CodeFellowship.interfaces;

import com.example.CodeFellowship.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role , Long> {

    optional<Role> findRoleByName(String name);
}
