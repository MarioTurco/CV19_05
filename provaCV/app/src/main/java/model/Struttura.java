package model;

public class Struttura {
    private String indirizzo;
    private double longitudine;
    private double latitudine;
    private String nome;
    private String descrizione;
    private String città;
    private int idStruttura;
    private double valutazioneMedia;
    private String fasciaDiPrezzo;
    private String categoria;


    /*public Struttura(String indirizzo, double longitudine, double latitudine, String nome, String descrizione, String città, int idStruttura, float valutazioneMedia, String fasciaDiPrezzo, String categoria) {
        this.indirizzo = indirizzo;
        this.longitudine = longitudine;
        this.latitudine = latitudine;
        this.nome = nome;
        this.descrizione = descrizione;
        this.città = città;
        this.idStruttura = idStruttura;
        this.valutazioneMedia = valutazioneMedia;
        this.fasciaDiPrezzo = fasciaDiPrezzo;
        this.categoria = categoria;
    }
    */
    public String getIndirizzo() {
        return indirizzo;
    }

    public void setIndirizzo(String indirizzo) {
        this.indirizzo = indirizzo;
    }

    public double getLongitudine() {
        return longitudine;
    }

    public void setLongitudine(double longitudine) {
        this.longitudine = longitudine;
    }

    public double getLatitudine() {
        return latitudine;
    }

    public void setLatitudine(double latitudine) {
        this.latitudine = latitudine;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getCittà() {
        return città;
    }

    public void setCittà(String città) {
        this.città = città;
    }

    public int getIdStruttura() {
        return idStruttura;
    }

    public void setIdStruttura(int idStruttura) {
        this.idStruttura = idStruttura;
    }

    public double getValutazioneMedia() {
        return valutazioneMedia;
    }

    public void setValutazioneMedia(double valutazioneMedia) {
        this.valutazioneMedia = valutazioneMedia;
    }

    public String getFasciaDiPrezzo() {
        return fasciaDiPrezzo;
    }

    public void setFasciaDiPrezzo(String fasciaDiPrezzo) {
        this.fasciaDiPrezzo = fasciaDiPrezzo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
}

