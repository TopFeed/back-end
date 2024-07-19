package com.hyun.topfeed.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

  @Value("${spring.security.x-api-key}")
  private String xApiKey;

  @GetMapping()
  public String main(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "index";
  }

  @GetMapping("/register")
  public String register(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "register";
  }

  @GetMapping("/withdraw")
  public String withdraw(Model model) {
    model.addAttribute("apiKey", xApiKey);
    return "withdraw";
  }

}
