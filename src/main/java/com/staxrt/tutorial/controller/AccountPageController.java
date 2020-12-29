package com.staxrt.tutorial.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AccountPageController {

  @GetMapping("/welcome")
  public String welcome(){
    return "welcome";
  }
}

