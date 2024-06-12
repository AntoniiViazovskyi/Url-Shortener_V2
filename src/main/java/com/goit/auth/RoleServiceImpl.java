package com.goit.auth;


import com.goit.exception.LogEnum;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    @Override
    public Role findByName(String name) {
        log.info("{}: request on retrieving role by name {} was sent", LogEnum.SERVICE, name);
        return roleRepository.findByName(name).orElse(null);
    }
}
