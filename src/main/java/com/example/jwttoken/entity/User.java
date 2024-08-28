package com.example.jwttoken.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

//05
@Entity
@Table(name="systemuser")
@NoArgsConstructor
@AllArgsConstructor
@Data
//@Setter
//@Getter
//@ToString(exclude ="password")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID uid;
    private String email;
    private String password;
    private String name;
    private String companyName;
    private String role;

}
