package com.goit.service;


import com.goit.entities.Role;

public interface RoleService {

    Role findByName(String name);
}
