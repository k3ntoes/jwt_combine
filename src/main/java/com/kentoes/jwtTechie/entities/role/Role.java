package com.kentoes.jwtTechie.entities.role;

import com.kentoes.jwtTechie.entities.BaseEntity;
import com.kentoes.jwtTechie.entities.enums.ERole;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "roles")
public class Role extends BaseEntity implements Serializable {
    @Enumerated(value = EnumType.STRING)
    private ERole roleName;
    
    public Role(ERole roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "Role{" +
                "roleName=" + roleName +
                '}';
    }
}
