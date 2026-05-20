package com.example.persistencia_lab.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.persistencia_lab.services.AutenticacaoService;

@Controller
public class AutenticacaoController {

    @Autowired
    AutenticacaoService autenticacaoService; 

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/logar")
    public String logar(Model model, String email, String senha) {
        
        try {

            autenticacaoService.autenticar(email, senha);
            return "redirect:/professores";

        }catch (RuntimeException exception){

            model.addAttribute("erro", exception.getMessage());
            return "login";
            
        }
    }

    @GetMapping("/logout")
    public String logout(){

        autenticacaoService.logout();
        return "redirect:/login";

    }


}
