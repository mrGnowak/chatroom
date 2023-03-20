package com.chat.chatroom.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.chat.chatroom.dto.AppUserDto;
import com.chat.chatroom.dto.LoginUserDto;
import com.chat.chatroom.model.AppUser;
import com.chat.chatroom.security.SecurityUser;
import com.chat.chatroom.service.UserService;

import io.micrometer.common.lang.NonNull;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationManager authManager;

    private HttpSession getSession() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        var session = attr.getRequest().getSession(true); // true == allow create
        return session;
    }

    @PostMapping(value = "/login", consumes = { "*/*" })
    public Long login(@RequestBody LoginUserDto loginUser) {
        try {
            var credentials = new UsernamePasswordAuthenticationToken(loginUser.getUserName(),
                    loginUser.getPassword());
            var authenticate = authManager.authenticate(credentials);
            var principial = (SecurityUser) authenticate.getPrincipal();
            var securityContext = SecurityContextHolder.getContext();
            securityContext.setAuthentication(authenticate);
            getSession().setAttribute(
                    HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                    securityContext);
            return principial.getId();
        } catch (Exception ex) {
            return null;
        }
    }

    @GetMapping("/getUser")
    public AppUserDto getUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var principial = authentication.getPrincipal();
        if (!(principial instanceof SecurityUser)) {
            return null;
        }
        var securityUser = (SecurityUser) principial;
        return new AppUserDto() {
            {
                setId(securityUser.getId());
                setUserName(securityUser.getUsername());
            }
        };
    }

    @PostMapping(value = "/register", consumes = { "*/*" })
    public String register(@NonNull @NotBlank @RequestBody AppUser appUser) {
        return userService.saveNewUser(appUser);
    }

    @PostMapping(value = "/logout")
    public void logout() {
        getSession().removeAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY);

    }

    @PutMapping(value = "/changepass")
    public String changePass(@NonNull @NotBlank @RequestBody AppUser appUser) {
        userService.updateUser(appUser);
        return "Changed!";
    }
}
