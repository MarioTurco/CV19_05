/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv19;

/**
 *
 * @author checc
 */
public class Visitatore {
    private String nome;
    private String nickname;

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
