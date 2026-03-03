package com.example.websiteauto.controllers;

import com.example.websiteauto.dto.request.UserRegistrationRequest;
import com.example.websiteauto.service.AuthService;
import com.example.websiteauto.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;
    private final AuthService authService;

    @GetMapping
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new UserRegistrationRequest());
        return "register";
    }

    @PostMapping
    public String processRegistration(@ModelAttribute("user") @Valid UserRegistrationRequest request,
                                      BindingResult result) {
        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.registerUser(request);
        } catch (com.example.websiteauto.exception.UsernameExistsException e) {
            result.rejectValue("username", "error.user", "Этот логин уже занят");
            return "register";
        } catch (com.example.websiteauto.exception.EmailExistsException e) {
            result.rejectValue("email", "error.user", "Этот email уже зарегистрирован");
            return "register";
        } catch (Exception e) {
            result.reject(null, "Произошла системная ошибка при регистрации");
            return "register";
        }

        authService.autoLogin(request.getUsername(), request.getPassword());
        return "redirect:/ads";
    }
}
