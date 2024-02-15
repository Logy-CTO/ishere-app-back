package com.example.demo.global.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Entity
@Setter
@Getter
@Table(name = "user")
public class UserEntity implements UserDetails {

    @Id
    @Column(name = "user_id")
    private Long id;

    @Column(name = "phone_number")
    private String username;

    private String password;

    private String role; // 유저에 대한 권한을 줌.

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}