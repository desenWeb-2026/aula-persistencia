package com.example.persistencia_lab.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.persistencia_lab.models.Professor;
import com.example.persistencia_lab.repositories.ProfessorRepository;

@Controller
public class ProfessorController {

    @Autowired
    ProfessorRepository repository;

    @GetMapping("/professores")
    public String getProfessores(
        Model model,
        @RequestParam(required = false, defaultValue = "1") Integer page,
        @RequestParam(required = false, defaultValue = "") String nome
    ){
        
        List<Professor> professores =  repository.getProfessores(5, page, nome);

        model.addAttribute("professores", professores);

        return "professores";

    }
}
