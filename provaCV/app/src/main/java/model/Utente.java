package model;

import java.util.Date;

public class Utente {
    private String nome;
    private String nickname;
    private String email;
    private Date dataDiNascita;
    private String password;
    private String salt;
    private boolean mostraNickname;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataDiNascita() {
        return dataDiNascita;
    }

    public void setDataDiNascita(Date dataDiNascita) {
        this.dataDiNascita = dataDiNascita;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public boolean isMostraNickname() {
        return mostraNickname;
    }

    public void setMostraNickname(boolean mostraNickname) {
        this.mostraNickname = mostraNickname;
    }

}
