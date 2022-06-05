package com.online.store.entity;


import com.online.store.entity.enums.Role;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

import static com.online.store.util.Constant.ROLES;

@Setter
@Getter
@Entity
public class Roles extends IdHolder {

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private Role role;

    @ManyToMany(mappedBy = ROLES)
    private Set<User> users = new HashSet<>();

}
