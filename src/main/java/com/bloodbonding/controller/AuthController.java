package com.bloodbonding.controller;

import java.util.Optional;

import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;

import com.bloodbonding.entity.User;
import com.bloodbonding.service.AuthService;
import com.bloodbonding.entity.DonorProfile;
import com.bloodbonding.service.DonorService;

@Controller
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DonorService donorService;

    @GetMapping("/register")
    public String registerPage() {
        return "register";
    }

    @PostMapping("/register")
    public String register(

            @RequestParam String username,
            @RequestParam String password,

            @RequestParam String name,
            @RequestParam Integer age,
            @RequestParam String gender,
            @RequestParam String phone,
            @RequestParam String bloodGroup,
            @RequestParam String city,
            @RequestParam String diseaseHistory,
            @RequestParam String email) {

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);

        User savedUser = authService.register(user);

        DonorProfile donorProfile = new DonorProfile();

        donorProfile.setName(name);
        donorProfile.setAge(age);
        donorProfile.setGender(gender);
        donorProfile.setPhone(phone);
        donorProfile.setBloodGroup(bloodGroup);
        donorProfile.setCity(city);
        donorProfile.setDiseaseHistory(diseaseHistory);
        donorProfile.setEmail(email);

        donorProfile.setUser(savedUser);

        donorService.saveProfile(donorProfile);

        return "login";
    }

    @GetMapping("/")
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(
            @RequestParam String username,
            @RequestParam String password,
            HttpSession session,
            Model model) {

        Optional<User> user = authService.login(username, password);

        if (user.isPresent()) {

            session.setAttribute("userId", user.get().getId());
            session.setAttribute("username", user.get().getUsername());

            model.addAttribute("name", user.get().getUsername());

            return "donor-receiver";
        }

        model.addAttribute("error", "Invalid username or password");

        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {

        session.invalidate();

        return "redirect:/";
    }
}