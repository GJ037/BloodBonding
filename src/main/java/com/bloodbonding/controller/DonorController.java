package com.bloodbonding.controller;

import java.util.ArrayList;
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

        List<DonorProfile> sameCityDonors = new ArrayList<>();
        List<DonorProfile> otherCityDonors = new ArrayList<>();

        String currentCity = currentProfile.get().getCity();

        for (DonorProfile donor : donors) {

            if (donor.getUser().getId().equals(userId)) {
                continue;
            }

            if (donor.getCity().equalsIgnoreCase(currentCity)) {
                sameCityDonors.add(donor);
            } else {
                otherCityDonors.add(donor);
            }
        }

        model.addAttribute("bloodGroup", bloodGroup);
        model.addAttribute("sameCityDonors", sameCityDonors);
        model.addAttribute("otherCityDonors", otherCityDonors);

        return "receive";
    }
}