package com.example.jwttoken.service.impl;

import com.example.jwttoken.service.UserService;
import com.example.jwttoken.dto.UserDTO;
import com.example.jwttoken.entity.User;
import com.example.jwttoken.repositary.UserRepository;
import com.example.jwttoken.util.VarList;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
//08 ==data gnn thamyi use krgnn

@Transactional
@Service
public class UserServiceImpl implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), getAuthority(user));
    }
    public UserDTO loadUserDetailsByUsername(String username) throws UsernameNotFoundException{
        User user = userRepository.findByEmail(username);
        return modelMapper.map(user,UserDTO.class);
    }

    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(user.getRole()));
        return authorities;

    }

    //user name password  eken usename ek unic email ek widiyt gnne
    //entity value dto dagnn one hinda mohok idan mokktda dara tranfer krnn object dek map krgnn
    @Override
    public UserDTO searchUser(String userName) {
       if (userRepository.existsByEmail(userName)){
           User user = userRepository.findByEmail(userName);
           return modelMapper.map(user,UserDTO.class);
       }else {
           return null;
       }
    }

    @Override
    public int saveUser(UserDTO userDTO) {
       if (userRepository.existsByEmail(userDTO.getEmail())){
           return VarList.Not_Acceptable;
       }else {
           BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
           userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
           userDTO.setRole("DASH_ADMIN");
           userRepository.save(modelMapper.map(userDTO,User.class));
           return VarList.Created;
       }
    }



}
