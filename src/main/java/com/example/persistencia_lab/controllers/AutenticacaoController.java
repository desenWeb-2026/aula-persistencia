package com.example.persistencia_lab.controllers;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.persistencia_lab.models.Professor;
import com.example.persistencia_lab.repositories.ProfessorRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Controller
public class AutenticacaoController {

    @Autowired
    private HttpSession session;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private ProfessorRepository repository;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logar")
    public String logar(String email, String senha) {

        Professor professor = repository.findProfessorByEmail(email);

        if (professor == null) {
            throw new RuntimeException("e-mail invalido");
        }

        PasswordEncoder encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(senha, professor.getSenha())) {
            throw new RuntimeException("senha incorreta");
        }

        String sessaoId = UUID.randomUUID().toString();
        session.setAttribute(sessaoId, professor);

        Cookie cookie = new Cookie("APP_SESSID", sessaoId);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // inacessível por js, por exemplo

        response.addCookie(cookie);

        return "redirect:/professores";
    }

}
