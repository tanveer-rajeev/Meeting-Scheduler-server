package com.assignment.booking.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Collection;
import java.util.List;

public class MyUserDetails implements UserDetails {
    private final String userName;
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public MyUserDetails(String userName , String password , List<? extends GrantedAuthority> authorities ,
                         boolean accountNonExpired , boolean accountNonLocked , boolean credentialsNonExpired ,
                         boolean enabled) {
        this.userName              = userName;
        this.authorities           = getAuthorities();
        this.password              = password;
        this.accountNonExpired     = accountNonExpired;
        this.accountNonLocked      = accountNonLocked;
        this.credentialsNonExpired = credentialsNonExpired;
        this.enabled               = enabled;
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
