package com.example.persistencia_lab.services;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.persistencia_lab.models.Professor;
import com.example.persistencia_lab.repositories.ProfessorRepository;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Service
public class AutenticacaoService {

    @Autowired
    ProfessorRepository repository;
    
    @Autowired
    HttpServletRequest request;

    @Autowired
    HttpServletResponse response;

    @Autowired
    HttpSession session;

    public Professor autenticar(String email, String senha){

        PasswordEncoder encoder;

        Professor professor = repository.findProfessorByEmail(email);

        if (professor == null) {
            throw new RuntimeException("e-mail invalido");
        }

        encoder = new BCryptPasswordEncoder();

        if (!encoder.matches(senha, professor.getSenha())) {
            throw new RuntimeException("senha incorreta");
        }

        //1° passo
        String sessaoId = UUID.randomUUID().toString();
        session.setAttribute(sessaoId, professor);

        //2° passo
        Cookie cookie = new Cookie("APP_SESSID", sessaoId);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // inacessível por js, por exemplo

        response.addCookie(cookie);

        return professor;

    }

    public void logout(){

        session.invalidate();

        Cookie cookie = new Cookie("APP_SESSID", "");
        cookie.setPath("/");
        cookie.setMaxAge(0);

        response.addCookie(cookie);

    }

    public Professor getProfessorLogado(){

        try {

            Cookie[] cookies = request.getCookies();

            if (cookies == null) {
                return null;
            }

            for (Cookie cookie : cookies) {

                if (cookie.getName().equals("APP_SESSID")) {
                    String sessId = cookie.getValue();

                    Professor professor = (Professor) session.getAttribute(sessId);
                    return professor;
                }
            }

            return null;

        } catch(Exception exception){

            return null;

        }

    }


}
