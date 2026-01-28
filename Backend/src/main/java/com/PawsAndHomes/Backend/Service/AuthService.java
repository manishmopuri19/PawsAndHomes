package com.PawsAndHomes.Backend.Service;

import com.PawsAndHomes.Backend.Dto.Auth.AuthResponseDTO;
import com.PawsAndHomes.Backend.Dto.Auth.LoginRequestDTO;
import com.PawsAndHomes.Backend.Dto.Auth.RegisterRequestDTO;

public interface AuthService {
    AuthResponseDTO login(LoginRequestDTO request);
    AuthResponseDTO register(RegisterRequestDTO request);
}