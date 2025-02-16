package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser_integration() {
        User user = new User();
        user.setName("Integration Test User");
        User savedUser = userRepository.save(user);

        // Assert: Kullanıcının ID'sinin oluşturulup oluşturulmadığını kontrol etme
        Assertions.assertNotNull(savedUser.getId());
    }

    @Test
    public void testUpdateUserIntegration() {
        // 1. Yeni kullanıcı oluştur ve kaydet (Builder ile)
        User user = User.builder()
                .name("OldName")
                .email("old@example.com")
                .build();
        user = userRepository.save(user);

        // 2. Kullanıcının bilgilerini güncelle
        user.setName("NewName");
        user.setEmail("new@example.com");
        User updatedUser = userRepository.save(user);

        // 3. Güncellenen bilgileri doğrula
        Assertions.assertNotNull(updatedUser.getId());
        Assertions.assertEquals("NewName", updatedUser.getName());
        Assertions.assertEquals("new@example.com", updatedUser.getEmail());
    }
}