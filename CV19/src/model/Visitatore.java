/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author checc
 */
public class Visitatore {
    private String nome;
    private String nickname;
    private String email;
    private String dataDiNascita;
    private int recensioniApprovate;
    private int recensioniRifiutate;

    public Visitatore(String nome, String nickname) {
        this.nome = nome;
        this.nickname = nickname;
    }

    public String getNome() {
        return nome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    
}
