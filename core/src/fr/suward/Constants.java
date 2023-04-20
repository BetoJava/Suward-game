package fr.suward;

import fr.suward.game.entities.classes.*;
import fr.suward.game.entities.classes.monsters.pdcity.*;

public class Constants {

    public static String[] mapNames = {"max","pvp3","suward1","suward2","suward3","suward4","pdcity1","pdcity2","pdcity3","pdcityboss",};
    public static String[] className = {"Ouq", "Erit", "Dolma", "Bram", "Eres", "Pasdoeuf", "Maître de Chaî", "Oeuf", "Pasdstuff", "Moustache de Gégé"};

    public final static int OUQ = 0;
    public final static int ERIT = 1;
    public final static int DOLMA = 2;
    public final static int BRAM = 3;
    public final static int ERES = 4;

    public final static int PASDOEUF = 5;
    public final static int MAITRE_DE_CHAI = 6;
    public final static int OEUF = 7;
    public final static int PASDSTUFF = 8;
    public final static int MOUSTACHE_DE_GEGE = 9;

    public static ClassData classFromClassName(int className) {
        if(className == OUQ) {
            return new Ouq();
        } else if (className == BRAM) {
            return new Bram();
        } else if (className == ERIT) {
            return new Erit();
        } else if (className == DOLMA) {
            return new Dolma();
        } else if (className == ERES) {
            return new Eres();
        } else if (className == PASDOEUF) {
            return new PasdoeufClass();
        } else if (className == MAITRE_DE_CHAI) {
            return new MaitreDeChaiClass();
        } else if (className == OEUF) {
            return new OeufClass();
        } else if (className == PASDSTUFF) {
            return new PasdstuffClass();
        } else if (className == MOUSTACHE_DE_GEGE) {
            return new MoustacheDeGegeClass();
        } else {
            return null;
        }
    }




}
