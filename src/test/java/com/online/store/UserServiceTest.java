package com.online.store;

import com.online.store.dto.request.UserLoginRequest;
import com.online.store.dto.request.UserRequest;
import com.online.store.dto.response.UserLoginResponse;
import com.online.store.dto.response.UserResponse;
import com.online.store.entity.Roles;
import com.online.store.entity.User;
import com.online.store.entity.enums.Role;
import com.online.store.exception.AlreadyExistException;
import com.online.store.exception.NotFoundException;
import com.online.store.repository.ProductRepository;
import com.online.store.repository.RoleRepository;
import com.online.store.repository.UserRepository;
import com.online.store.service.UserService;
import com.online.store.service.impl.UserDetailsImpl;
import com.online.store.service.impl.UserServiceImpl;
import com.online.store.seсurity.JwtUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @InjectMocks
    private UserService userService = new UserServiceImpl();

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private ProductRepository productRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserRequest userRequest;
    private User user;
    private Long id;


    @BeforeEach
    public void setup() {
        userRequest = new UserRequest("password", "UserFirstName",
                "UserLastName", "user@gmail.com");
        user = new User("passwordUser", "UserFirst",
                "UserLast", "userTest@gmail.com");
        id = 1L;
    }


    @DisplayName("JUnit test for login user successfully")
    @Test
    public void shouldLoginUserSuccessFully() {

        UserDetails userDetails = new UserDetailsImpl(id, "password", "user@gmail.com", new ArrayList<>());

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("user@gmail.com");
        userLoginRequest.setPassword("password");
        when(userRepository.findUserByEmail(userLoginRequest.getEmail()))
                .thenReturn(Optional.of(userRequest.convertToUser(new User())));

        Authentication authentication = mock(Authentication.class);
        authentication.setAuthenticated(true);

        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(jwtUtils.generateJwtToken(authentication)).thenReturn("124");
        when(authentication.getPrincipal()).thenReturn(userDetails);

        UserLoginResponse loginUser = userService.loginUser(userLoginRequest);

        System.out.println(loginUser.toString());
        assertThat(loginUser.getToken()).isNotNull();
        assertThat(loginUser).isNotNull();
    }

    @DisplayName("JUnit test for login user which throws exception")
    @Test
    public void shouldThrowsExceptionWhenLoginAndUserEmailNotFound() {

        UserLoginRequest userLoginRequest = new UserLoginRequest();
        userLoginRequest.setEmail("user@gmail.com");
        userLoginRequest.setPassword("password");

        when(userRepository.findUserByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        Exception exception = assertThrows(NotFoundException.class, () -> userService.loginUser(userLoginRequest));

        verify(jwtUtils, never()).generateJwtToken(any(Authentication.class));

        String expectedMessage = "user not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("JUnit test for save user successfully")
    @Test
    public void shouldSaveUserSuccessFully() {

        when(userRepository.findUserByEmail(userRequest.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findByRole(Role.CUSTOMER)).thenReturn(Optional.of(new Roles(Role.CUSTOMER)));

        UserResponse savedUser = userService.createUser(userRequest);

        System.out.println(savedUser.toString());
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getLastName()).isEqualTo("UserLastName");
    }

    @DisplayName("JUnit test for save user which throws exception")
    @Test
    public void shouldThrowsExceptionWhenSaveUserWithExistingEmail() {

        when(userRepository.findUserByEmail(userRequest.getEmail())).thenReturn(Optional.of(user));
        Exception exception = assertThrows(AlreadyExistException.class, () -> userService.createUser(userRequest));

        verify(userRepository, never()).save(any(User.class));

        String expectedMessage = "user is already exists";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("JUnit test for findUserById successfully")
    @Test
    public void shouldGiveUserByIdSuccessFully() {

        user.setId(id);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponse savedUser = userService.getUserById(user.getId());

        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isEqualTo(id);
    }

    @DisplayName("JUnit test for findUserById which throws exception")
    @Test
    public void shouldThrowsExceptionWhenUserByIdNotFound() {

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NotFoundException.class, () -> userService.getUserById(id));

        String expectedMessage = "user not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @DisplayName("JUnit test for findAllUsers successfully")
    @Test
    public void shouldFindAllUsersSuccessFully() {

        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "firstName";

        user.setId(16L);

        User user2 = new User();
        user2.setId(15L);
        user2.setFirstName("First2");
        user2.setLastName("Last2");

        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user2);

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        Page<User> pagedUsers = new PageImpl(users);

        when(userRepository.findAll(pageRequest)).thenReturn(pagedUsers);

        List<UserResponse> userList = userService.findAllUsers(pageNumber, pageSize, sortBy);

        assertThat(userList).isNotNull();
        assertThat(userList.size()).isEqualTo(users.size());
    }

    @DisplayName("JUnit test for findAllUsers with empty list")
    @Test
    public void shouldGiveEmptyListWhenNoUsersInDb() {

        int pageNumber = 0;
        int pageSize = 10;
        String sortBy = "firstName";

        List<User> users = new ArrayList<>();

        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        Page<User> pagedUsers = new PageImpl(users);

        when(userRepository.findAll(pageRequest)).thenReturn(pagedUsers);

        List<UserResponse> userList = userService.findAllUsers(pageNumber, pageSize, sortBy);

        assertThat(userList).isEmpty();
    }

    @DisplayName("JUnit test for update user successfully")
    @Test
    public void shouldUpdateUserSuccessFully() {

        UserRequest user1 = new UserRequest();
        user1.setLastName("RandomLast");
        user1.setFirstName("RandomFirst");

        when(userRepository.findById(id)).thenReturn(Optional.of(userRequest.convertToUser(user)));

        UserResponse updatedUser = userService.modifyUser(user1, id);

        assertThat(updatedUser.getLastName()).isEqualTo("RandomLast");
        assertThat(updatedUser.getFirstName()).isEqualTo("RandomFirst");
    }

    @DisplayName("JUnit test for update user which throws exception")
    @Test
    public void shouldThrowsExceptionWhenUpdateAndUserIdNotFound() {

        UserRequest user1 = new UserRequest();
        user1.setLastName("RandomLast");
        user1.setFirstName("RandomFirst");

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NotFoundException.class, () -> userService.modifyUser(user1, id));

        String expectedMessage = "user not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(userRepository, never()).save(any(User.class));
    }

    @DisplayName("JUnit test for delete user successfully")
    @Test
    public void shouldDeleteUserSuccessFully() {
        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        userService.deleteUser(id);

        verify(userRepository, times(1)).delete(user);
    }

    @DisplayName("JUnit test for delete user which throws exception")
    @Test
    public void shouldThrowsExceptionWhenDeleteUser() {

        when(userRepository.findById(id)).thenReturn(Optional.empty());
        Exception exception = assertThrows(NotFoundException.class, () -> userService.deleteUser(id));

        String expectedMessage = "user not found";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        verify(userRepository, never()).delete(any(User.class));
    }


}
