package com.petdoc.backend;

import com.petdoc.backend.repository.UserRepository; // Importe o repositório
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean; // Importe o MockBean

@SpringBootTest
@EnableAutoConfiguration(exclude = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class
})
class PetdocAppApplicationTests {

    // Esta anotação diz ao Spring: "Para este teste, crie um
    // bean falso do tipo UserRepository e o injete onde for necessário".
    @MockBean
    private UserRepository userRepository;

    @Test
    void contextLoads() {

    }

}