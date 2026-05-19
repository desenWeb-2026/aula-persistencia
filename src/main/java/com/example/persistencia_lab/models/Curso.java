package com.example.persistencia_lab.models;

public class Curso {

    private Integer curso_id;
    private String nome;

    public Curso(){}

    public Curso(Integer id, String nome){
        this.curso_id = id;
        this.nome = nome;
    }
    
    public Integer getCurso_id() {
        return curso_id;
    }
    public void setCurso_id(Integer curso_id) {
        this.curso_id = curso_id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    @Override
    public String toString() {
        return "Curso [curso_id=" + curso_id + ", nome=" + nome + "]";
    }
}
