package com.example.CodeFellowship.interfaces.services;

import com.example.CodeFellowship.domain.Role;

public interface SecurityService {
    Role findRoleById(Long roleId);
    Role findRoleByName(String name);

}
