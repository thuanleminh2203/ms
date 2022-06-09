package com.selex.motor.ImageService.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class ImageController {

    @GetMapping
    public String getImage(){
        return "Hello service";
    }
}
