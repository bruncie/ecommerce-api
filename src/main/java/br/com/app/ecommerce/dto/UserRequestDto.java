package br.com.app.ecommerce.dto;

import br.com.app.ecommerce.enuns.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDto {
    @NotBlank(message = "Username é obrigatório")
    private String username;

    @NotBlank(message = "Senha é obrigatória")
    private String password;

    @NotBlank(message = "Nome é obrigatório")
    private String name;

    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    private String email;

    @NotNull(message = "Perfil é obrigatório")
    private UserType userType;
}
