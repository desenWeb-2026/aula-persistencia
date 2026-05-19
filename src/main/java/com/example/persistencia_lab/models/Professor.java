package com.example.persistencia_lab.models;

import java.time.LocalDate;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class Professor {

    private Integer id;
    private String nome;
    private String email;
    private String senha;


    private LocalDate dataNascimento;
    private Double salarioBase;
    private Curso curso = null;

    public Professor() {
    }

    public Professor(Integer id, String nome, String email, String senha, LocalDate dataNascimento, Double salarioBase) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.salarioBase = salarioBase;

        this.setSenha(senha);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Double getSalarioBase() {
        return salarioBase;
    }

    public void setSalarioBase(Double salarioBase) {
        this.salarioBase = salarioBase;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public void setSenha(String senha) {

        PasswordEncoder encoder = new BCryptPasswordEncoder();
        this.senha = encoder.encode(senha);

    }

    public void setSenhaHashed(String senhaHashed){

        this.senha = senhaHashed;

    }

    public String getSenha(){
        return this.senha;
    }

    @Override
    public String toString() {
        return "Professor [id=" + id + ", nome=" + nome + ", email=" + email + ", senha=" + senha + ", dataNascimento="
                + dataNascimento + ", salarioBase=" + salarioBase + ", curso=" + curso + "]";
    }

    

}
