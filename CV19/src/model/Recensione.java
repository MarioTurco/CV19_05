/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Recensione {
    
    private SimpleIntegerProperty idRecensione;
    private SimpleStringProperty testo;
    private SimpleStringProperty titolo;
    private SimpleStringProperty data;
    private SimpleIntegerProperty valutazione;
    private SimpleStringProperty struttura; //TODO DA CAMBIARE 
    private SimpleStringProperty nomeAutore; //TODO DA CAMBIARE
    private SimpleStringProperty nickNameAutore;

    public String getNickNameAutore() {
        return nickNameAutore.get();
    }

    public void setNickNameAutore(String nickNameAutore) {
        this.nickNameAutore.set(nickNameAutore);
    }
    
    public Recensione(){
        this.testo=new SimpleStringProperty();
        this.nomeAutore=new SimpleStringProperty();
        this.struttura=new SimpleStringProperty();
        this.valutazione=new SimpleIntegerProperty();
        this.data=new SimpleStringProperty();
        this.titolo=new SimpleStringProperty();
        this.idRecensione=new SimpleIntegerProperty();
        this.nickNameAutore= new SimpleStringProperty();
    }

    public int getIdRecensione() {
        return idRecensione.get();
    }

    public void setIdRecensione(int idRecensione) {
        this.idRecensione.set(idRecensione);
    }
    
    
    @Override
    public String toString() {
        return "Recensione{id="+ idRecensione + "testo=" + testo + ", titolo=" + titolo + ", data=" + data + ", valutazione=" + valutazione + ", struttura=" + struttura + ", autore=" + nomeAutore + '}';
    }


    public String getTesto() {
        return testo.get();
    }

    public void setTesto(String testo) {
        this.testo.set(testo);
    }

    public String getTitolo() {
        return titolo.get();
    }

    public void setTitolo(String titolo) {
        this.titolo.set(titolo);
    }

    public String getData() {
        return data.get();
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public int getValutazione() {
        return valutazione.get();
    }

    public void setValutazione(int valutazione) {
        this.valutazione.set(valutazione);
    }

    public String getStruttura() {
        return struttura.get();
    }

    public void setStruttura(String struttura) {
        this.struttura.set(struttura);
    }

    public String getNomeAutore() {
        return nomeAutore.get();
    }

    public void setNomeAutore(String autore) {
        this.nomeAutore.set(autore);
    }
    


}
