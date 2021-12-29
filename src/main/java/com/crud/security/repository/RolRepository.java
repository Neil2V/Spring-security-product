package com.crud.security.repository;

import com.crud.security.entity.Rol;
import com.crud.security.enums.RolEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RolRepository extends JpaRepository<Rol, Integer> {
    Optional<Rol> findByRolname(RolEnum rolname);
}
