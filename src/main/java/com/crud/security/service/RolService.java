package com.crud.security.service;

import com.crud.security.entity.Rol;
import com.crud.security.enums.RolEnum;
import com.crud.security.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.Access;
import java.util.Optional;

@Service
public class RolService {

    @Autowired
    RolRepository rolRepository;

    public Optional<Rol> findByRolname(RolEnum rolname){
        return rolRepository.findByRolname(rolname);
    }

    public void save(Rol rol){
        rolRepository.save(rol);
    }
}
