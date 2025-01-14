package com.airline.booking.domain.account;

import com.airline.booking.domain.account.component.UserValidator;
import com.airline.booking.domain.account.model.User;
import com.airline.booking.domain.account.repository.UserRepository;
import com.airline.booking.domain.account.service.IUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserValidator userValidator;
    @InjectMocks
    private IUserService userService;

    private User user;

    @BeforeEach
    void init() {
        user = new User(
                "user123",
                "John",
                "Doe",
                LocalDate.of(1995, 5, 15),
                "MALE",
                "USER",
                "user@example.com",
                "0969999999",
                "Password123@",
                false,
                true
        );
    }

    @Test
//    @Disabled
    void testRegister_shouldReturnUser() {
        when(userRepository.existsByPhoneNumber(user.getPhoneNumber()))
                .thenReturn(Boolean.FALSE);

        User registeredUser = userService.register(user);

        assertNotNull(registeredUser);
        assertEquals(user.getId(), registeredUser.getId());
        assertEquals(user.getPhoneNumber(), registeredUser.getPhoneNumber());
        assertEquals(user.getFirstName(), registeredUser.getFirstName());
        assertEquals(user.getLastName(), registeredUser.getLastName());
        assertEquals(user.getDateOfBirth(), registeredUser.getDateOfBirth());
        assertEquals(user.getGender(), registeredUser.getGender());
        assertEquals(user.getEmail(), registeredUser.getEmail());
        assertEquals(user.getPassword(), registeredUser.getPassword());
        assertEquals(user.getPhoneNumber(), registeredUser.getPhoneNumber());
    }
}
