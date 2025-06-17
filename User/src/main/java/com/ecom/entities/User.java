package com.ecom.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Integer id;

 private String username;
 private String email;
 private String password;
 private int age;
 private String gender;
 private String phoneNumber;
 private String imageurl;

 // Many users can have one role
 @ManyToOne(fetch = FetchType.EAGER)
 @JoinColumn(name = "role_id")
 private Role role;
}

//   @Id
//   @GeneratedValue(strategy = GenerationType.IDENTITY)
//
//    private int id;
//
//    private String Username;
//
//    private String email;
//
//    private String password;
//
//    private int age;
//
//    private String token;
//
//    private String gender;
//
//    private String PhoneNumber;
//    //@OneToMany(mappedBy = "user",cascade =CascadeType.ALL,fetch = FetchType.LAZY)
//    //private List<Posts> posts=new ArrayList<>();
// @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
// @JoinTable(
//  name="user_role",
//         joinColumns=@JoinColumn(name = "user",referencedColumnName = "id"),
//         inverseJoinColumns=@JoinColumn(name="role",referencedColumnName ="id")
// )
//    private Set<Role> roles=new HashSet<>();
//
// @Override
// public Collection<? extends GrantedAuthority> getAuthorities() {
//  return List.of();
// }
//
// @Override
// public String getUsername() {
//  return this.email;
// }
//
// @Override
// public boolean isAccountNonExpired() {
//  return true;
// }
//
// @Override
// public boolean isAccountNonLocked() {
//  return true;
// }
//
// @Override
// public boolean isCredentialsNonExpired() {
//  return true;
// }
//
// @Override
// public boolean isEnabled() {
//  return true;
// }}
