package com.example.persistencia_lab.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.persistencia_lab.models.Curso;
import com.example.persistencia_lab.models.Professor;
import com.example.persistencia_lab.repositories.ProfessorRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@Controller
@RequestMapping("/professores")
public class ProfessorController {

    @Autowired
    ProfessorRepository repository;

    @GetMapping
    public String getProfessores(
        Model model,
        @RequestParam(required = false, defaultValue = "1") Integer pagina,
        @RequestParam(required = false, defaultValue = "") String nome
    ){
        
        List<Professor> professores =  repository.getProfessores(5, pagina, nome);

        model.addAttribute("professores", professores);
        model.addAttribute("pagina", pagina);
        model.addAttribute("nome", nome);

        return "professores";

    }

    @GetMapping("/criar")
    public String criar(Model model, Professor professor) {

        //para uma repository cursos

        model.addAttribute("cursos", this.getCursos());
        
        return "professores_criar";

    }

    @PostMapping("/salvar")
    public String salvar(Professor professor, Integer cursoId) {

        Curso cursoSelecionado = getCursos().stream()
        .filter(c -> c.getCurso_id().equals(cursoId) )
        .findFirst()
        .orElse(new Curso(cursoId, "Curso Inventado"));

        professor.setCurso(cursoSelecionado);

        repository.inserir(professor);
        
        return "redirect:/professores";
    }
    

    private List<Curso> getCursos(){
        
        List<Curso> cursos = new ArrayList<>();

        cursos.add(new Curso(1, "Dados"));
        cursos.add(new Curso(2, "Lógica"));
        cursos.add(new Curso(3, "Engenharia"));

        return cursos;
        
    }

}
