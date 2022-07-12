package com.online.store.service.impl;

import com.online.store.dto.request.UserLoginRequest;
import com.online.store.dto.request.UserRequest;
import com.online.store.dto.response.UserLoginResponse;
import com.online.store.dto.response.UserResponse;
import com.online.store.entity.Order;
import com.online.store.entity.Roles;
import com.online.store.entity.User;
import com.online.store.entity.enums.Role;
import com.online.store.repository.RoleRepository;
import com.online.store.repository.UserRepository;
import com.online.store.service.UserService;
import com.online.store.seсurity.JwtUtils;
import com.online.store.util.Constant;
import com.online.store.util.UserConversionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.online.store.exception.AlreadyExistException.isExistsException;
import static com.online.store.exception.NotFoundException.notFoundException;
import static com.online.store.util.Constant.EMAIL;
import static com.online.store.util.Constant.USER;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;


    @Override
    public UserLoginResponse loginUser(UserLoginRequest userLoginRequest) {
        userRepository.findUserByEmail(userLoginRequest.getEmail())
                .orElseThrow(() -> notFoundException(USER));
        Authentication authentication = getAuthentication(userLoginRequest);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return new UserConversionUtil().getJwtResponse(jwtUtils.generateJwtToken(authentication), userDetails);
    }

    @Override
    public UserResponse createUser(UserRequest userRequest) {
        if (!userRepository.findUserByEmail(userRequest.getEmail()).isEmpty()) {
            throw isExistsException(USER);
        }
        User user = userRequest.convertToUser(new User());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        setDefault(user);
        userRepository.save(user);
        return new UserConversionUtil().fromUserToUserResponse(user);
    }

    @Override
    public UserResponse getUserById(Long id) {
        return new UserConversionUtil().fromUserToUserResponse(getUserByIdFromDB(id));
    }

    @Override
    public List<UserResponse> findAllUsers(int pageNumber, int pageSize, String sortBy) {
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize, Sort.by(Sort.Direction.ASC, sortBy));
        return getUserResponse(userRepository.findAll(pageRequest).stream().collect(Collectors.toList()));
    }

    @Override
    public UserResponse modifyUser(UserRequest userRequest, Long id) {
        User user = getUserByIdFromDB(id);
        changePassword(userRequest, user);
        userRequest.convertToUser(user);
        userRepository.save(user);
        return new UserConversionUtil().fromUserToUserResponse(user);
    }

    @Override
    public void deleteUser(Long id) {
        User user = getUserByIdFromDB(id);
        Optional<Roles> rolesOptional = user.getRoles().stream().findFirst();
        rolesOptional.ifPresent(user::removeRoles);
        userRepository.delete(user);
    }

    @Override
    public void setOrderToUser(Long id, Order order) {
        User user = getUserByIdFromDB(id);
        user.addOrder(order);
        userRepository.save(user);
    }

    @Override
    public User getUserByIdFromDB(Long id) {
        return userRepository.findById(id).orElseThrow(() -> notFoundException(USER));
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email).orElseThrow(() -> notFoundException(EMAIL));
    }

    private void setDefault(User user) {
        Roles role = roleRepository.findByRole(Role.CUSTOMER).orElseThrow(() -> notFoundException(Constant.ROLES));
        Set<Roles> rolesSet = new HashSet<>();
        rolesSet.add(role);
        user.setRoles(rolesSet);
        user.setCreateDate(LocalDateTime.now());
        user.setDiscount(3);
    }

    private Authentication getAuthentication(UserLoginRequest userLoginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userLoginRequest.getEmail(), userLoginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    private List<UserResponse> getUserResponse(List<User> userList) {
        return userList.stream()
                .map(user -> new UserConversionUtil().fromUserToUserResponse(user))
                .collect(Collectors.toList());
    }

    private void changePassword(UserRequest userRequest, User user) {
        if (userRequest.getEmail() != null) {
            user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        }
    }


}
