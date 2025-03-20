package br.com.app.ecommerce.dto;


import br.com.app.ecommerce.enuns.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String username;
    private String name;
    private String email;
    private UserType userType;
}
