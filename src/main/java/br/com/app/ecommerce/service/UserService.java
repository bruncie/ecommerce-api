package br.com.app.ecommerce.service;

import br.com.app.ecommerce.dto.UpdateUserDto;
import br.com.app.ecommerce.dto.UserRequestDto;
import br.com.app.ecommerce.dto.UserResponseDto;
import br.com.app.ecommerce.entity.UserEntity;
import br.com.app.ecommerce.enuns.UserType;
import br.com.app.ecommerce.exception.ResourceNotFoundException;
import br.com.app.ecommerce.exception.AlreadyExistsException;
import br.com.app.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDto createUser(UserRequestDto userRequestDTO) {
        if (userRepository.existsByUsername(userRequestDTO.getUsername())) {
            throw new AlreadyExistsException("Username já existe");
        }
        
        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new AlreadyExistsException("Email já está em uso");
        }

        UserEntity user = this.mapToEntity(userRequestDTO);

        return this.mapToDTO(userRepository.save(user));
    }

    public UserResponseDto updateUser(Long id, UpdateUserDto updateUserDTO) {
        UserEntity currentUser = this.getCurrentUser();
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));

        if (!currentUser.getUserType().equals(UserType.ADMIN) &&
            !currentUser.getId().equals(user.getId())) {
            throw new AccessDeniedException("Você não tem permissão para atualizar este usuário");
        }

        if (!user.getEmail().equals(updateUserDTO.getEmail()) && 
            userRepository.existsByEmail(updateUserDTO.getEmail())) {
            throw new AlreadyExistsException("Email já está em uso");
        }
        
        user.setName(updateUserDTO.getName());
        user.setEmail(updateUserDTO.getEmail());
        
        UserEntity updatedUser = userRepository.save(user);
        return mapToDTO(updatedUser);
    }
    
    public UserResponseDto findByUserName(String username) {
        UserEntity currentUser = getCurrentUser();
        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado: " + username));

        if (!currentUser.getUserType().equals(UserType.ADMIN)) {
            throw new AccessDeniedException("Você não tem permissão para visualizar este usuário");
        }
        
        return mapToDTO(user);
    }
    
    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado"));
    }

    private UserEntity mapToEntity(UserRequestDto userRequestDTO) {
        UserEntity user = new UserEntity();
        user.setUsername(userRequestDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        user.setName(userRequestDTO.getName());
        user.setEmail(userRequestDTO.getEmail());
        user.setUserType(userRequestDTO.getUserType());
        return user;
    }

    private UserResponseDto mapToDTO(UserEntity user) {
        return UserResponseDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .name(user.getName())
                .email(user.getEmail())
                .userType(user.getUserType())
                .build();
    }
}