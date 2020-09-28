package com.coupon.demo.restservice;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class DefaultController {

    @GetMapping("/default")
    public ResponseEntity<String> hello() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "Welcome");
    }

    @GetMapping("/jake")
    public ResponseEntity<String> jakeOnly() {
        return ResponseEntity.status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).body(
                "THis is for jake's eyes only");
    }

}
