package com.bearcode.travelweb.services.Impl;

import com.bearcode.travelweb.DTO.UserDTO;
import com.bearcode.travelweb.mappers.UserMapper;
import com.bearcode.travelweb.models.TourBooking;
import com.bearcode.travelweb.models.User;
import com.bearcode.travelweb.repositories.UserRepository;
import com.bearcode.travelweb.services.UserService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLOutput;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public String createAccount(UserDTO userDTO) {
        if(isEmailAndPhoneValid(userDTO.getEmail(), userDTO.getPhoneNumber())){
//            User u = new User();
//            u.setName(userDTO.getName());
//            u.setEmail(userDTO.getEmail());
//            u.setPassword(userDTO.getPassword());
//            u.setPhoneNumber(userDTO.getPhoneNumber());
//            u.setAddress(userDTO.getAddress());
              User u = userMapper.convertToEntity(userDTO);
              userRepository.save(u);
            return "Tạo Tài Khoản Thành Công!";
        } else {
            return "Tên Tài Khoản hoặc số điện thoại đã được sử dụng!";
        }

    }

    @Override
    public Boolean isEmailAndPhoneValid(String email, String phoneNumber) {
        return !userRepository.existsByEmail(email) && !userRepository.existsByPhoneNumber(phoneNumber);
    }


    @Override
    public User updatePassword(Long id, String currentPassword, String newPassword) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User không tồn tại"));
        if (!existingUser.getPassword().equals(currentPassword)) {
            throw new RuntimeException("Mật khẩu hiện tại không đúng");
        }
        if (newPassword == null || newPassword.trim().isEmpty()) {
            throw new RuntimeException("Password không hợp lệ");
        }
        existingUser.setPassword(newPassword);
        return userRepository.save(existingUser);
    }

    @Override
    public ResponseEntity<User> setRoleAdmin(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + id));
            user.setRole(1);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException("Cập nhật vai trò admin thất bại");
        }
    }

    @Override
    public ResponseEntity<User> setRoleUser(Long id) {
        try {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy user với id: " + id));
            user.setRole(0);
            userRepository.save(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            throw new RuntimeException("Cập nhật vai trò user thất bại");
        }
    }

    @Override
    public UserDTO login(String email, String password) {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if(optionalUser.isPresent()){
            User user = optionalUser.get();
            String passwordDB = user.getPassword();
            if(passwordDB.equals(password)){
                return userMapper.convertToDTO(userRepository.getUserById(user.getId()));
            }
        } else System.out.println("Tài Khoản Không tồn tại!");

        return null;
    }

    @Override
    public User updateAccount(Long id, UserDTO userDTO) {
            User existingUser = userRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("User không tồn tại"));

            if (!existingUser.getName().equals(userDTO.getName())) {
                existingUser.setName(userDTO.getName());
            }

            if (!existingUser.getEmail().equals(userDTO.getEmail())) {
                if (userRepository.existsByEmail(userDTO.getEmail())) {
                    throw new RuntimeException("Email đã tồn tại");
                }
                existingUser.setEmail(userDTO.getEmail());
            }

            if (!existingUser.getPhoneNumber().equals(userDTO.getPhoneNumber())) {
                if (userRepository.existsByPhoneNumber(userDTO.getPhoneNumber())) {
                    throw new RuntimeException("Số điện thoại đã tồn tại");
                }
                existingUser.setPhoneNumber(userDTO.getPhoneNumber());
            }

            if (!existingUser.getAddress().equals(userDTO.getAddress())) {
                existingUser.setAddress(userDTO.getAddress());
            }
            return userRepository.save(existingUser);
        }


}
