package com.example.blog.Controller;

import com.example.blog.DTO.UserDTO;
import com.example.blog.Entity.User;
import com.example.blog.Service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
        public final UserService userService;
        UserController(UserService userService){
            this.userService=userService;
        }
        @GetMapping("/{id}")
        public ResponseEntity<UserDTO> getUserById(@PathVariable Long id){
            return ResponseEntity.ok(userService.getUserById(id));
        }
        @PostMapping("/register")
        public ResponseEntity<UserDTO> createUser(@RequestBody User user){
            return ResponseEntity.status(201).body(userService.createUser(user));
        }
        @PutMapping("/{id}")
        public ResponseEntity<UserDTO> updateUser(@PathVariable long id,@RequestBody User user){
            return ResponseEntity.ok(userService.updateUser(id,user));
        }
        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable long id){
            userService.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
}
