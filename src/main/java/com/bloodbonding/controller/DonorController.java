package com.bloodbonding.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DonorController {

    @GetMapping("/donate")
    public String donatePage() {
        return "donate";
    }

    @GetMapping("/receive")
    public String receivePage() {
        return "receive";
    }
}