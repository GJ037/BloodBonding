package com.bloodbonding.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bloodbonding.entity.DonorProfile;

public interface DonorProfileRepository extends JpaRepository<DonorProfile, Long> {

    List<DonorProfile> findByBloodGroup(String bloodGroup);

}