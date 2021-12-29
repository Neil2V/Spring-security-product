package com.crud.security.entity;

import com.crud.security.enums.RolEnum;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Rol {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private RolEnum rolname;

    public Rol() {
    }

    public Rol(@NotNull RolEnum rolname) {
        this.rolname = rolname;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public RolEnum getRolname() {
        return rolname;
    }

    public void setRolname(RolEnum rolname) {
        this.rolname = rolname;
    }
}
