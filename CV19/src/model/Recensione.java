/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleStringProperty;

public class Recensione {

    private SimpleStringProperty testo;
    private SimpleStringProperty titolo;
    private SimpleStringProperty data;
    private int valutazione;
    
    public Recensione(String testo, String titolo, String data, int valutazione) {
        this.testo = new SimpleStringProperty(testo);
        this.titolo = new SimpleStringProperty(titolo);
        this.data = new SimpleStringProperty(data);
        this.valutazione = valutazione;
    }

    @Override
    public String toString() {
        return "Recensione{" + "testo=" + testo.get() + ", titolo=" + titolo.get() + ", data=" + data.get() + ", valutazione=" + valutazione + '}';
    }
    
    public String getNome() {
        return testo.get();
    }
    
    public String getStruttura() {
        return titolo.get();
    }
    
    public String getData() {
        return data.get();
    }
    
    public void setNome(String nome) {
        this.testo.set(nome);
    }
    
    public void setStruttura(String struttura) {
        this.titolo.set(struttura);
    }
    
    public void setData(String data) {
        this.data.set(data);
    }

}
