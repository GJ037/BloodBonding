package com.bloodbonding.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bloodbonding.entity.DonorProfile;
import com.bloodbonding.repository.DonorProfileRepository;

@Service
public class DonorService {

    @Autowired
    private DonorProfileRepository donorProfileRepository;

    public DonorProfile saveProfile(DonorProfile donorProfile) {
        return donorProfileRepository.save(donorProfile);
    }

    public List<DonorProfile> findByBloodGroup(String bloodGroup) {
        return donorProfileRepository.findByBloodGroup(bloodGroup);
    }

    public Optional<DonorProfile> findById(Long id) {
        return donorProfileRepository.findById(id);
    }
}