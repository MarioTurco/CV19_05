package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

public class Recensione {
    private String testo;
    private String dataRecensione;
    private String titolo;
    private int valutazione;
    private int idRecensione;
    private Struttura struttura;
    private String statoRecensione;
    private Utente autore;

    public static final Comparator<Recensione> comparatorByData = new Comparator<Recensione>(){
        @Override
        public int compare(Recensione r1, Recensione r2) {
            return r1.getDataInFormatoData().compareTo(r2.getDataInFormatoData());
        }
    };

    public static final Comparator<Recensione> comparatorByRating = new Comparator<Recensione>() {
        @Override
        public int compare(Recensione r1, Recensione r2) {
            if(r1.getValutazione() > r2.getValutazione())
                return 1;
            else if(r1.getValutazione() < r2.getValutazione())
                return -1;
            else
                return 0;
        }
    };

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

    public Date getDataInFormatoData() {
        Date dataRitorno = null;
        try {
            dataRitorno = new SimpleDateFormat("yyyy-MM-dd").parse(dataRecensione);
        } catch (ParseException e) {
            System.out.println(dataRecensione);
            System.out.println("Errore parse data");
        }
        return dataRitorno;
    }
}
