package com.example.provacv;

public class Filtri {

    private String nome;
    private String citta;
    private String categoria;
    private String prezzo;
    private String valutazione;
    private String distanzaMassima;
    private boolean prossimita;

    public Filtri(String nome, String citta, String categoria, String prezzo, String valutazione, String distanzaMassima, boolean prossimita){
        this.nome = nome;
        this.citta = citta;
        this.categoria = categoria;
        this.prezzo = prezzo;
        this.valutazione = valutazione;
        this.distanzaMassima = distanzaMassima;
        this.prossimita = prossimita;
    }



    public void setDistanzaMassima(String distanzaMassima) {
        this.distanzaMassima = distanzaMassima;
    }

    public void setProssimita(boolean prossimita) {
        this.prossimita = prossimita;
    }


    public String getDistanzaMassima() {
        return distanzaMassima;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCitta(String citta) {
        this.citta = citta;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setPrezzo(String prezzo) {
        this.prezzo = prezzo;
    }

    public void setValutazione(String valutazione) {
        this.valutazione = valutazione;
    }

    public void setProssimità(boolean prossimita) {
        this.prossimita = prossimita;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getPrezzo() {
        return prezzo;
    }

    public String getValutazione() {
        return valutazione;
    }

    public boolean isProssimita() {
        return prossimita;
    }

    public String getNonNullStrings(double x, double y) {
        String returnString = "";

        if (!nome.equals(""))
            returnString += "&nome=%25" + nome + "%25";
        if (!citta.equals(""))
            returnString += "&citta=%25" + citta + "%25";
        if (!categoria.equals("Nessuno"))
            returnString += "&categoria=" + categoria;
        if (!prezzo.equals("Nessuno"))
            returnString += "&prezzo=" + prezzo;
        if (!valutazione.equals("Nessuno"))
            returnString += "&valutazione_media=" + nome;
        if (prossimita) {
            if (!distanzaMassima.equals(""))
                returnString += "&radius=" + distanzaMassima;
            else
                returnString += "&radius=0";

            returnString += "&x=" + x;
            returnString += "&y=" + y;

        }
        return returnString;
    }
}
