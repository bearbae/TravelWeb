package com.bearcode.travelweb.services;

import com.bearcode.travelweb.DTO.UserDTO;
import com.bearcode.travelweb.models.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    String createAccount(UserDTO userDTO);

    Boolean isEmailAndPhoneValid(String email, String phoneNumber);

    UserDTO login(String email, String password);

    User updateAccount(Long id, UserDTO userDTO);

    User updatePassword(Long id, String currentPassword, String newPassword);
    ResponseEntity<User> setRoleAdmin(Long id) ;
    ResponseEntity<User> setRoleUser(Long id) ;
}
