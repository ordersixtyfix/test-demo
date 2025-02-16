package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

@WebMvcTest(UserController.class) // Sadece controller katmanını yüklüyoruz.
public class UserControllerE2ETest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testGetUserById_e2e() throws Exception {
        // Arrange: Servis davranışını tanımlama
        User mockUser = new User();
        mockUser.setId(1L);
        mockUser.setName("E2E Test User");

        Mockito.when(userService.getUserById(1L)).thenReturn(Optional.of(mockUser));

        // Act & Assert: GET isteği gönderme ve yanıtı kontrol etme
        mockMvc.perform(MockMvcRequestBuilders.get("/users/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("E2E Test User"));
    }



    @Test
    public void testUpdateUserEndpoint() throws Exception {
        // Güncellenecek kullanıcı verilerini oluştur (id belirtilmez)
        User updatedUser = User.builder()
                .name("NewName")
                .email("new@example.com")
                .build();

        // Servisin update sonrası döndüreceği kullanıcı (id atanmış)
        User savedUser = User.builder()
                .id(1L)
                .name("NewName")
                .email("new@example.com")
                .build();

        // userService.updateUser metodunun davranışını tanımlıyoruz.
        Mockito.when(userService.updateUser(Mockito.eq(1L), Mockito.any(User.class)))
                .thenReturn(Optional.of(savedUser));

        // PUT isteği gönderip, yanıtı kontrol ediyoruz.
        mockMvc.perform(MockMvcRequestBuilders.put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedUser)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("NewName"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("new@example.com"));
    }
}