package br.com.app.ecommerce.service;

import br.com.app.ecommerce.config.JwtTokenProvider;
import br.com.app.ecommerce.dto.LoginRequestDto;
import br.com.app.ecommerce.dto.LoginResponseDto;
import br.com.app.ecommerce.dto.UserResponseDto;
import br.com.app.ecommerce.entity.UserEntity;
import br.com.app.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    public LoginResponseDto login(LoginRequestDto loginRequestDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequestDTO.getUsername(),
                        loginRequestDTO.getPassword()
                )
        );
        
        UserEntity user = userRepository.findByUsername(loginRequestDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
        
        String token = jwtTokenProvider.generateToken(authentication, user.getUserType());

        UserResponseDto userResponseDTO = new UserResponseDto();
        userResponseDTO.setId(user.getId());
        userResponseDTO.setUsername(user.getUsername());
        userResponseDTO.setName(user.getName());
        userResponseDTO.setEmail(user.getEmail());
        userResponseDTO.setUserType(user.getUserType());
        
        return new LoginResponseDto(token, userResponseDTO);
    }
}