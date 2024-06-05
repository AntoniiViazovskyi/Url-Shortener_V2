package com.goit.service.impl;

import com.goit.entities.Role;
import com.goit.repositories.RoleRepository;
import com.goit.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role findByName(String name) {
        return roleRepository.findByName(name).orElse(null);
    }
}
