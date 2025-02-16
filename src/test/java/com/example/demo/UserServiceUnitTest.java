package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Optional;

@SpringBootTest
public class UserServiceUnitTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    public void testGetUserById_unit() {
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("Test User");
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(mockUser));

        Optional<User> userOpt = userService.getUserById(1L);
        Assertions.assertTrue(userOpt.isPresent());
        Assertions.assertEquals("Test User", userOpt.get().getName());
    }


    @Test
    public void testUpdateUser() {
        // Arrange: Mevcut kullanıcı oluşturuluyor.
        User existingUser = User.builder()
                .id(1L)
                .name("OldName")
                .email("old@example.com")
                .build();

        User updatedData = User.builder()
                .name("NewName")
                .email("new@example.com")
                .build();

        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(existingUser));
        // save metodu çağrıldığında gönderilen nesneyi geri döndür.
        Mockito.when(userRepository.save(Mockito.any(User.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));

        // Act: Update metodunu çağırıyoruz.
        Optional<User> result = userService.updateUser(1L, updatedData);

        // Assert: Güncellenmiş bilgilerin kontrolü
        Assertions.assertTrue(result.isPresent());
        Assertions.assertEquals("NewName", result.get().getName());
        Assertions.assertEquals("new@example.com", result.get().getEmail());
    }
}