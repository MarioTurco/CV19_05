public String getFiltriNonVuoti(double longitudine, double latitudine) {
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
        returnString += "&valutazione_media=" + valutazione;
    if (prossimita) {
        if (!distanzaMassima.equals(""))
            returnString += "&radius=" + distanzaMassima;
        else
            returnString += "&radius=0";
        returnString += "&x=" + longitudine;
        returnString += "&y=" + latitudine;
    }
    return returnString;
}