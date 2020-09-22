package com.coupon.demo.facade;

import org.springframework.stereotype.Component;

@Component
public abstract class ClientFacade {

    public abstract boolean login(String email, String password);

}
