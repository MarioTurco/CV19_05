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
public class Utente {
    private String nome;
    private String nickname;
    private String email;
    private String dataDiNascita;
    private int recensioniApprovate;
    private int recensioniRifiutate;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setDataDiNascita(String dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public void setRecensioniApprovate(int recensioniApprovate) {
        this.recensioniApprovate = recensioniApprovate;
    }

    public void setRecensioniRifiutate(int recensioniRifiutate) {
        this.recensioniRifiutate = recensioniRifiutate;
    }

    public String getEmail() {
        return email;
    }

    public String getDataDiNascita() {
        return dataDiNascita;
    }

    public int getRecensioniApprovate() {
        return recensioniApprovate;
    }

    public int getRecensioniRifiutate() {
        return recensioniRifiutate;
    }

    public Utente(String nome, String nickname, String email, String dataDiNascita, int recensioniApprovate, int recensioniRifiutate) {
        this.nome = nome;
        this.nickname = nickname;
        this.email = email;
        this.dataDiNascita = dataDiNascita;
        this.recensioniApprovate = recensioniApprovate;
        this.recensioniRifiutate = recensioniRifiutate;
    }
    
    public Utente(){
        
    }
    public Utente(String nome, String nickname) {
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

    @Override
    public String toString() {
        return "Visitatore{" + "nome=" + nome + ", nickname=" + nickname + ", email=" + email + ", dataDiNascita=" + dataDiNascita + ", recensioniApprovate=" + recensioniApprovate + ", recensioniRifiutate=" + recensioniRifiutate + '}';
    }
    
}
