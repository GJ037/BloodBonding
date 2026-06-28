package com.bloodbonding.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bloodbonding.entity.User;
import com.bloodbonding.entity.DonorProfile;
import com.bloodbonding.service.AuthService;
import com.bloodbonding.service.DonorService;

import jakarta.servlet.http.HttpSession;

@Controller
public class DonorController {

    @Autowired
    private AuthService authService;

    @Autowired
    private DonorService donorService;

    @GetMapping("/donate")
    public String donatePage(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/";
        }

        Optional<DonorProfile> profile =
                donorService.getProfileByUserId(userId, authService);

        if (profile.isEmpty()) {
            session.invalidate();
            return "redirect:/";
        }

        model.addAttribute("profile", profile.get());

        return "donate";
    }

    @GetMapping("/receive")
    public String receivePage(HttpSession session, Model model) {

        Long userId = (Long) session.getAttribute("userId");

        if (userId == null) {
            return "redirect:/";
        }

        Optional<DonorProfile> currentProfile =
                donorService.getProfileByUserId(userId, authService);

        if (currentProfile.isEmpty()) {
            session.invalidate();
            return "redirect:/";
        }

        String bloodGroup = currentProfile.get().getBloodGroup();

        List<DonorProfile> donors =
                donorService.findByBloodGroup(bloodGroup);

        donors.removeIf(donor ->
                donor.getUser().getId().equals(userId));

        model.addAttribute("bloodGroup", bloodGroup);
        model.addAttribute("donors", donors);

        return "receive";
    }
}