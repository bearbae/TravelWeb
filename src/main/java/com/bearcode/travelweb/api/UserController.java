package com.bearcode.travelweb.api;

import com.bearcode.travelweb.DTO.UserDTO;
import com.bearcode.travelweb.models.ErrorResponse;
import com.bearcode.travelweb.models.User;
import com.bearcode.travelweb.repositories.UserRepository;
import com.bearcode.travelweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
        @Autowired
        private UserService userService;
        @Autowired
        private UserRepository userRepository;
        @PostMapping("/register")
        public ResponseEntity<String> register(@RequestBody UserDTO userDTO){
            String response = userService.createAccount(userDTO);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        }

        @PostMapping("/login")
        public ResponseEntity<UserDTO> login(@RequestParam("email") String email,
                                             @RequestParam("password") String password){
            UserDTO response = userService.login(email, password);
            if(response == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            else return ResponseEntity.ok(response);
        }

        @PutMapping("/update/{id}")
        public ResponseEntity<User> update(@PathVariable Long id,@RequestBody UserDTO userDTO){
            User response = userService.updateAccount(id,userDTO);
            return ResponseEntity.ok(response);
        }
        @PutMapping("/updatePassword/{id}")
        public ResponseEntity<User> updatePassword(@PathVariable Long id,@RequestParam("currentPassword") String currentPassword,
                                                    @RequestParam("newPassword") String newPassword){
            User response = userService.updatePassword(id,currentPassword,newPassword);
            return ResponseEntity.ok(response);
        }

        @GetMapping("/{id}")
        public User getUserById(@PathVariable Long id){
            return userRepository.getUserById(id);
        }
        @GetMapping("/all")
        public List<User> getAll(){return userRepository.findAll() ; }
        @ExceptionHandler(RuntimeException.class)
        public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
        @PutMapping("/roleAdmin/{id}")
        public ResponseEntity<User> setRoleAdmin(@PathVariable Long id) {
            return userService.setRoleAdmin(id);
        }
        @PutMapping("/roleUser/{id}")
        public ResponseEntity<User> setRoleUser(@PathVariable Long id) {
            return userService.setRoleUser(id);
        }

}