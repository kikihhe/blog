package com.xiaohe.domain.entity;

import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser implements UserDetails {
    private User user;
    private List<String> permission;

    public LoginUser(User user) {
        this.user = user;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return Constants.User.SIMPLE_STATUS.equals(user.getStatus());
    }

    @Override
    public boolean isAccountNonLocked() {
        return Constants.User.SIMPLE_STATUS.equals(user.getStatus());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return Constants.User.SIMPLE_STATUS.equals(user.getStatus());
    }

    @Override
    public boolean isEnabled() {
        return Constants.User.SIMPLE_STATUS.equals(user.getStatus());
    }
}
