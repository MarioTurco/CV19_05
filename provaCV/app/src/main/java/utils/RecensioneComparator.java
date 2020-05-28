package utils;

import java.util.Comparator;

import model.Recensione;

public class RecensioneComparator {

    public static final Comparator<Recensione> comparatorByData = new Comparator<Recensione>(){
        public int compare(Recensione r1, Recensione r2){
            return r1.getDataInFormatoData().compareTo(r2.getDataInFormatoData());
        }
    };

    public static final Comparator<Recensione> comparatorByRating = new Comparator<Recensione>(){
        public int compare(Recensione r1, Recensione r2){
            if(r1.getValutazione() > r2.getValutazione())
                return 1;
            else if(r1.getValutazione() < r2.getValutazione())
                return -1;
            else
                return 0;
        }
    };
}
