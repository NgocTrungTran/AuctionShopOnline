package com.aso.model.dto;

import com.aso.model.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class RoleDTO {

    private Long id;

    private String code;

    private String name;

    public Role toRole() {
        return new Role ()
                .setId ( id )
                .setCode ( code )
                .setName ( name )
                ;
    }

}

