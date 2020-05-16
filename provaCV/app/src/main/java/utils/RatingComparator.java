package utils;
import java.util.*;

import model.Recensione;

public class RatingComparator implements Comparator<Recensione>{

    public int compare(Recensione r1, Recensione r2){
        if(r1.getValutazione() > r2.getValutazione())
            return 1;
        else if(r1.getValutazione() < r2.getValutazione())
            return -1;
        else
            return 0;
    }
}
