package com.kentoes.jwtTechie.dto;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
public class AuthRequest implements Serializable {
    @NotEmpty(message = "username is required!")
    private String username;
    @NotEmpty(message = "password is required!")
    private String password;
}
