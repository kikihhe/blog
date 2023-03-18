package com.xiaohe.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xiaohe.constants.Constants;
import com.xiaohe.domain.entity.LoginUser;
import com.xiaohe.domain.entity.Menu;
import com.xiaohe.domain.entity.PageVo;
import com.xiaohe.domain.entity.User;
import com.xiaohe.domain.vo.*;
import com.xiaohe.service.MenuService;
import com.xiaohe.service.RoleService;
import com.xiaohe.service.UserService;
import com.xiaohe.utils.JWTUtils;
import com.xiaohe.utils.Result;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;




@RestController
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @PostMapping("/user/login")
    public Result login(@RequestBody AdminLoginUserVo user) throws JsonProcessingException {
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());

        Authentication authenticate = authenticationManager.authenticate(authentication);
        if (Objects.isNull(authenticate) || Objects.isNull(authentication.getPrincipal())) {
            return Result.error("用户名/密码错误");
        }
        ObjectMapper objectMapper = new ObjectMapper();

        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        User user1 = loginUser.getUser();
        user1.setPassword(null);
        // 根据用户信息生成token
        // token为键，json为值 存入redis
        String json = objectMapper.writeValueAsString(user1);
        String token = JWTUtils.generateToken(objectMapper.readValue(json, Map.class));
        // 存入redis，以 token-json形式存储, token存活时间为30mins
        stringRedisTemplate.opsForValue().set(Constants.User.ADMIN_LOGIN_TOKEN + token, json, 30, TimeUnit.MINUTES);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 将token返回
        Map<String, String> map = new HashMap<>();
        map.put(Constants.User.AUTHENTICATION_NAME, token);
        return Result.success(map, "登录成功");
    }

    @PostMapping("/user/logout")
    public Result logout(HttpServletRequest request) {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(Objects.isNull(principal)) {
            return Result.error("请先登录再退出登录");
        }
        // 获取token, 删除redis中的token
        String token = request.getHeader(Constants.User.AUTHENTICATION_NAME);
        Boolean delete = stringRedisTemplate.delete(Constants.User.ADMIN_LOGIN_TOKEN + token);
        if (!delete) {
            return Result.error("退出失败!");
        }
        return Result.success("成功退出");
    }


    @GetMapping("getInfo")
    public Result getInfo() {
        // 从SecurityContext中取出当前登录的用户的信息
        User user = ((LoginUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUser();
        Long id = user.getId();
        // 根据用户id查询权限信息
        List<String> permissions = menuService.getAccessPathByUserId(id);

        // 根据用户id查询他的roleKey
        List<String> roleKey = roleService.getRoleKeyByUserId(id);


        UserInfoVo userInfoVo = new UserInfoVo();
        BeanUtils.copyProperties(user, userInfoVo);
        return Result.success(new AdminUserInfoVo(permissions, roleKey, userInfoVo));

    }

    @GetMapping("getRouters")
    public Result getRouters() {
        User user = ((LoginUser) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getUser();
        Long id = user.getId();
        List<Menu> routers = menuService.getRouterByUserId(id);
        return Result.success(new RoutersVo(routers));
    }


    @Autowired
    private UserService userService;


    @GetMapping("/system/user/list")
    public Result getAllUser(Integer pageNum, Integer pageSize, String userName, String phonenumber) {
        PageVo pageVo = userService.getAllUser(pageNum, pageSize, userName, phonenumber);
        return Result.success(pageVo);
    }

    @PostMapping("/system/user")
    public Result addUser(@RequestBody AddUserVo addUserVo) {
        // 将密码加密处理
        addUserVo.setPassword(passwordEncoder.encode(addUserVo.getPassword()));
        boolean b = userService.addUser(addUserVo);
        if (b) {
            return Result.success("新增成功");
        } else {
            return Result.error("新增过程出现错误");
        }
    }

    @DeleteMapping("/system/user/{id}")
    public Result deleteUser(@PathVariable("id") List<Long> ids) {
        boolean b = userService.removeUser(ids);
        if (b) {
            return Result.error("删除成功");
        } else {
            return Result.success("删除失败");
        }
    }

    @GetMapping("/system/user/{id}")
    public Result getUser(@PathVariable("id") Long id) {
        UserVo userVo = userService.getUserBeforeUpdate(id);
        if (Objects.isNull(userVo)) {
            return Result.error("该用户不存在");
        }
        return Result.success(userVo);
    }

    @PutMapping("/system/user")
    public Result updateUser(@RequestBody AddUserVo user) {
        boolean b = userService.updateUser(user);
        if (b) {
            return Result.success("更新成功");
        } else {
            return Result.error("更新失败");
        }
    }
}
