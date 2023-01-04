package com.xiaohe.domain.vo;

import com.xiaohe.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.Objects;

/**
 * @author : 小何
 * @Description :
 * @date : 2023-01-04 17:56
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BlogLoginVo implements UserDetails {
    @NotNull(message = "用户名不能为空")
    private String userName;
    @NotNull(message = "密码不能为空")
    private String password;

    private User user;

    public BlogLoginVo(User user) {
        this.user = user;
        userName = user.getUserName();
        password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return Objects.isNull(userName) ? user.getUserName() : userName;
    }

    @Override
    public String getPassword() {
        return Objects.isNull(password) ? user.getPassword() : password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }



    public void setUserName(String userName) {
        this.userName = userName;
    }



    public void setPassword(String password) {
        this.password = password;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlogLoginVo that = (BlogLoginVo) o;
        return Objects.equals(userName, that.userName) && Objects.equals(password, that.password) && Objects.equals(user, that.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, password, user);
    }

    public User getUser() {
        return user;
    }

    public String getUserName() {
        return userName;
    }
}
