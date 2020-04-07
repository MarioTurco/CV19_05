/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cv19;

import javafx.beans.property.SimpleStringProperty;

public class Recensione {

    private SimpleStringProperty nome;
    private SimpleStringProperty struttura;
    private SimpleStringProperty data;

    
    public Recensione(String nome, String struttura, String data) {
        this.nome = new SimpleStringProperty(nome);
        this.struttura = new SimpleStringProperty(struttura);
        this.data = new SimpleStringProperty(data);
    }
    
    public String getNome() {
        return nome.get();
    }
    
    public String getStruttura() {
        return struttura.get();
    }
    
    public String getData() {
        return data.get();
    }
    
    public void setNome(String nome) {
        this.nome.set(nome);
    }
    
    public void setStruttura(String struttura) {
        this.struttura.set(struttura);
    }
    
    public void setData(String data) {
        this.data.set(data);
    }

}
