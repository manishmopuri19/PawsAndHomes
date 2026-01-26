package com.PawsAndHomes.Backend.Service.impl;

import java.util.Optional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.PawsAndHomes.Backend.Dto.requsetDto.adopterLoginDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.adopterRegitrationDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.ngoLoginDto;
import com.PawsAndHomes.Backend.Dto.requsetDto.ngoRegistartionDto;
import com.PawsAndHomes.Backend.Models.Adopter;
import com.PawsAndHomes.Backend.Models.Ngo;
import com.PawsAndHomes.Backend.Repository.AdopterRepo;
import com.PawsAndHomes.Backend.Repository.NgoRepo;
import com.PawsAndHomes.Backend.Service.AuthService;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class AuthservicesImpl implements AuthService {


    private  AdopterRepo adopterRepo;
    private  NgoRepo ngoRepo;
    private  BCryptPasswordEncoder passwordEncoder;
    public AuthservicesImpl(AdopterRepo adopterRepo,BCryptPasswordEncoder passwordEncoder) {
        this.adopterRepo = adopterRepo;
        this.passwordEncoder = passwordEncoder;
    }

    
    //Adopter registration
    @Override
    public Adopter registerAdopter(adopterRegitrationDto dto) {
       
        if(adopterRepo.existsByEmail(dto.getEmail())){
            throw new DataIntegrityViolationException("email already exist");
        }
        Adopter adopter=new Adopter(dto.getName(),dto.getEmail(),dto.getMobile(), passwordEncoder.encode(dto.getPassword()));

        return  adopterRepo.save(adopter);
    }

    //Adopter login
    @Override
    public boolean LoginAdopter(adopterLoginDto dto) {
        Optional<Adopter> opt=adopterRepo.findByEmail(dto.getEmail());

        return opt.isPresent() && passwordEncoder.matches(dto.getPassword(), opt.get().getPassword());
    }


    //ngo Registration
    @Override
    public Ngo registerNgo(ngoRegistartionDto dto){
        if(ngoRepo.existsByEmail(dto.getEmail())){
            throw new DataIntegrityViolationException("email already exist");
        }
        Ngo ngo=new Ngo(dto.getOrgname(), dto.getEmail(), dto.getMobile(), dto.getLocation(),dto.getPassword());
        return ngoRepo.save(ngo);
    }

    //ngo login

    @Override
    public boolean LoginNgo(ngoLoginDto dto){
        Optional<Ngo> opt=ngoRepo.findByEmail(dto.getEmail());

        return opt.isPresent() && passwordEncoder.matches(dto.getPassword(),opt.get().getPassword());
    }

}
