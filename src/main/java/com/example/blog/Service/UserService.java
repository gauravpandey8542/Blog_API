package com.example.blog.Service;

import com.example.blog.DTO.UserDTO;
import com.example.blog.Entity.User;
import com.example.blog.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder){
        this.userRepository=userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    public UserDTO createUser(User user){
        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Hashing
        userRepository.save(user);
        UserDTO dto=new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
    public UserDTO getUserById(long id){
        User user=userRepository.findById(id).orElseThrow(()-> new RuntimeException("No User with id "+id+" found"));
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setEmail(user.getEmail());
        return dto;
    }
    public UserDTO updateUser(long id, User user){
        User res=userRepository.findById(id).orElseThrow(()-> new RuntimeException("No User with id "+id+" found"));
        res.setName(user.getName());
        res.setEmail(user.getEmail());
        res.setPassword(user.getPassword());
        userRepository.save(res);

        UserDTO dto=new UserDTO();
        dto.setId(res.getId());
        dto.setName(res.getName());
        dto.setEmail(res.getEmail());
        return dto;
    }
    public void deleteUser(long id){
        User res=userRepository.findById(id).orElseThrow(()-> new RuntimeException("No User with id "+id+" found"));
        userRepository.delete(res);
    }
}
