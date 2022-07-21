package com.example.sample.oauth.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ResolvableType;
import org.springframework.security.core.parameters.P;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class BaseController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping
    public String user() {
        return "user";
    }

    private static final String authorizationRequestBaseUri = "oauth2/authorization";
    Map<String, String> oauth2AuthenticationUrls = new HashMap<>();
    private final ClientRegistrationRepository clientRegistrationRepository;

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        ((InMemoryClientRegistrationRepository) clientRegistrationRepository).forEach(
                registration -> {
            oauth2AuthenticationUrls.put(registration.getClientName(),
                    authorizationRequestBaseUri + "/" + registration.getClientId());
            model.addAttribute("urls", oauth2AuthenticationUrls);
        });
        return "login";
    }

    @GetMapping("/login/{oauth2}")
    public String loginGoogle(@PathVariable String oauth2, HttpServletResponse httpServletResponse){
        return "redirect:/oauth2/authorization/" + oauth2;
    }

    @PostMapping("/accessDenied")
    public String accessDenied() {return "accessDenied";}

}
