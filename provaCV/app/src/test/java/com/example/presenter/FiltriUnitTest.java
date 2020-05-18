package com.example.presenter;

import org.junit.Before;
import org.junit.Test;

import model.Filtri;

import static junit.framework.TestCase.assertEquals;

public class FiltriUnitTest {
    private static Filtri filtri;

    @Test
    public void getFiltriNonVuoti_BranchCoverage_2_3_5_7_9_11_13_21(){
        filtri = new Filtri("", "", "Nessuno", "Nessuno", "Nessuno","", false);
        assertEquals("", filtri.getFiltriNonVuoti(0,0));
    }
    @Test
    public void getFiltriNonVuoti_BranchCoverage_2_a_15_18_19_21(){
        filtri = new Filtri("a", "a", "a", "a", "1","1", true);
        String returnString = "&nome=%25a%25&citta=%25a%25&categoria=a&prezzo=a&valutazione_media=a&radius=1&x=0.0&y=0.0";
        assertEquals(returnString, filtri.getFiltriNonVuoti(0.0,0.0));
    }
    @Test
    public void getFiltriNonVuoti_BranchCoverage_2_a_14_17_18_19_21(){
        filtri = new Filtri("a", "a", "a", "a", "1","", true);
        String returnString = "&nome=%25a%25&citta=%25a%25&categoria=a&prezzo=a&valutazione_media=a&radius=0&x=0.0&y=0.0";
        assertEquals(returnString, filtri.getFiltriNonVuoti(0.0,0.0));
    }
}
