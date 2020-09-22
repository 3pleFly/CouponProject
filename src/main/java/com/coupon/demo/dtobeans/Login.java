package com.coupon.demo.dtobeans;


import com.coupon.demo.service.ClientType;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class Login {

    private String email;
    private String password;
    private ClientType clientType;

    public Login(String email, String password, ClientType clientType) {
        this.email = email;
        this.password = password;
        this.clientType = clientType;
    }


}
