package model;

public class Recensione {
    private String testo;
    private String dataRecensione;
    private String titolo;
    private int valutazione;
    private int idRecensione;
    private Struttura struttura;
    private String statoRecensione;
    private Utente autore;

    public String getTesto() {
        return testo;
    }

    public void setTesto(String testo) {
        this.testo = testo;
    }

    public String getDataRecensione() {
        return dataRecensione;
    }

    public void setDataRecensione(String dataRecensione) {
        this.dataRecensione = dataRecensione;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public int getValutazione() {
        return valutazione;
    }

    public void setValutazione(int valutazione) {
        this.valutazione = valutazione;
    }

    public int getIdRecensione() {
        return idRecensione;
    }

    public void setIdRecensione(int idRecensione) {
        this.idRecensione = idRecensione;
    }

    public Struttura getStruttura() {
        return struttura;
    }

    public void setStruttura(Struttura struttura) {
        this.struttura = struttura;
    }

    public String getStatoRecensione() {
        return statoRecensione;
    }

    public void setStatoRecensione(String statoRecensione) {
        this.statoRecensione = statoRecensione;
    }

    public Utente getAutore() {
        return autore;
    }

    public void setAutore(Utente autore) {
        this.autore = autore;
    }
}
