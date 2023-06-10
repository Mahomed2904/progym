/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utils;

/**
 *
 * @author mahomed
 */
public class Pesquisa {
    private String nome;
    private String atividade;
    private String matricula;
    private String mensalidade;

    public Pesquisa(String nome, String atividade, String matricula, String mensalidade) {
        this.nome = nome;
        this.atividade = atividade;
        this.matricula = matricula;
        this.mensalidade = mensalidade;
    }

    public String getNome() {
        return nome;
    }

    public String getAtividade() {
        return atividade;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getMensalidade() {
        return mensalidade;
    }
}
