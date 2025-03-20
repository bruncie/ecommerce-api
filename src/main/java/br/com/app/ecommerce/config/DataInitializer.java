package br.com.app.ecommerce.config;

import br.com.app.ecommerce.entity.UserEntity;
import br.com.app.ecommerce.enuns.UserType;
import br.com.app.ecommerce.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        // Criar usuário ADMIN padrão se não existir
        if (!userRepository.existsByUsername("bruno_prime")) {
            UserEntity admin = new UserEntity();
            admin.setUsername("bruno_prime");
            admin.setPassword(passwordEncoder.encode("bruno_prime"));
            admin.setName("Bruno");
            admin.setEmail("bruno_prime@felicidade.com");
            admin.setUserType(UserType.ADMIN);
            userRepository.save(admin);
        }

        // Criar usuário USER padrão se não existir
        if (!userRepository.existsByUsername("user_beta")) {
            UserEntity user = new UserEntity();
            user.setUsername("user_beta");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setName("Usuário Padrão");
            user.setEmail("user_beta@beta.com");
            user.setUserType(UserType.USER);
            userRepository.save(user);
        }
    }
}