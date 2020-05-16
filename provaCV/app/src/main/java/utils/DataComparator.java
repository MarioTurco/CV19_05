package utils;

import java.util.Comparator;

import model.Recensione;

public class DataComparator implements Comparator<Recensione> {
    public int compare(Recensione r1, Recensione r2){
        return r1.getDataInFormatoData().compareTo(r2.getDataInFormatoData());
    }
}
