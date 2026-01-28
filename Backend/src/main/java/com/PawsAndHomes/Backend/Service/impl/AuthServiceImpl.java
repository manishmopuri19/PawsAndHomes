package com.PawsAndHomes.Backend.Service.impl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.PawsAndHomes.Backend.Configs.JwtUtil;
import com.PawsAndHomes.Backend.Dto.Auth.AuthResponseDTO;
import com.PawsAndHomes.Backend.Dto.Auth.LoginRequestDTO;
import com.PawsAndHomes.Backend.Dto.Auth.RegisterRequestDTO;
import com.PawsAndHomes.Backend.Models.Adopter;
import com.PawsAndHomes.Backend.Models.Ngo;
import com.PawsAndHomes.Backend.Models.Role;
import com.PawsAndHomes.Backend.Models.User;
import com.PawsAndHomes.Backend.Repository.AdopterRepository;
import com.PawsAndHomes.Backend.Repository.NgoRepository;
import com.PawsAndHomes.Backend.Repository.UserRepository;
import com.PawsAndHomes.Backend.Service.AuthService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
@Transactional
public class AuthServiceImpl<authenticationManager> implements AuthService {
    
    private final UserRepository userRepository;
    private final AdopterRepository adopterRepository;
    private final NgoRepository ngoRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    
    @Override
    public AuthResponseDTO login(LoginRequestDTO request) {
       UserDetails userDetails = customUserDetailsService.loadUserByUsername(request.getEmail());

authenticationManager.authenticate(
    new UsernamePasswordAuthenticationToken(userDetails.getUsername(), request.getPassword())
);

User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

String token = jwtUtil.generateToken(userDetails); // FIXED

// Build response
AuthResponseDTO response = new AuthResponseDTO();
response.setToken(token);
response.setUserId(user.getId().toString());
response.setEmail(user.getEmail());
response.setName(user.getName());
response.setRole(user.getRole().name());

return response;

    }
    
 @Transactional
@Override
public AuthResponseDTO register(RegisterRequestDTO request) {

    if (userRepository.existsByEmail(request.getEmail())) {
        throw new RuntimeException("Email already exists");
    }

    Role role = Role.valueOf(request.getRole().toUpperCase());
    User user;

    if (role == Role.NGO) {
        Ngo ngo = new Ngo();
        ngo.setEmail(request.getEmail());
        ngo.setName(request.getName());
        ngo.setMobile(request.getMobile());
        ngo.setPassword(passwordEncoder.encode(request.getPassword()));
        ngo.setRole(Role.NGO);
        ngo.setEnabled(true); 
        ngo.setOrgName(request.getOrgName());
        ngo.setLocation(request.getLocation());
        user = ngoRepository.save(ngo);
    } else {
        Adopter adopter = new Adopter();
        adopter.setEmail(request.getEmail());
        adopter.setName(request.getName());
        adopter.setMobile(request.getMobile());
        adopter.setPassword(passwordEncoder.encode(request.getPassword()));
        adopter.setRole(Role.ADOPTER);
        adopter.setEnabled(true);
        user = adopterRepository.save(adopter);
    }

    UserDetails userDetails =
            customUserDetailsService.loadUserByUsername(user.getEmail());

    String token = jwtUtil.generateToken(userDetails);

    AuthResponseDTO response = new AuthResponseDTO();
    response.setToken(token);
    response.setUserId(user.getId().toString());
    response.setEmail(user.getEmail());
    response.setName(user.getName());
    response.setRole(user.getRole().name());

    return response;
}


}