//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Szoftver projekt laboratórium: Sivatagi vízhálózat
//  @ File Name : Model.Field.java
//  @ Date : 2023. 03. 28.
//  @ Author : aD4B team
//
//
package model.fields;

import model.characters.Character;
import org.apache.logging.log4j.Logger;
import proto.LogHelper;

import java.util.ArrayList;

/**
 * Pálya építőelemeit reprezentáló absztrakt osztály. Ismeri a rajta álló játékosokat.
 */
public abstract class Field {
    private static final Logger LOGGER = LogHelper.getLogger();
    protected boolean isDamaged = false;                    //Az elem épségét jelzi, ha TRUE, akkor hibás az elem.
    protected int maxCharacters = -1;                    //Az elemen tartózkodó karakterek maximális száma. (-1 esetén végtelen)
    protected final ArrayList<Character> characters = new ArrayList<>();        // Az elemen tartózkodó karakterek.
    protected final int maxNeighbours = 5; //Az elemhez csatlakoztatható más elemek maximális számát jelzi


    /**
     * Leszármazottakban override-olt függvény, alapértelmezetten nem csinál semmit.
     *
     * @return null
     */
    public Pump givePump() {
        System.out.println("Erről a mezőről nem lehet pumpát felvenni!");
        return null;
    }

    /**
     * Leszármazottakban override-olt függvény, alapértelmezetten nem csinál semmit.
     *
     * @param input  Bemeneti cső
     * @param output Kimeneti cső
     * @return false
     */
    public boolean settedPump(Pipe input, Pipe output) {
        System.out.println("Ezen a mezőn nem lehet pumpát állítani!");
        return false;
    }

    /**
     * Az adott mező isDamaged értékét FALSE-ra állítja és TRUE-val
     * tér vissza, ha alapból is FALSE-volt, akkor FALSE-al tér vissza.
     *
     * @return Átállítás sikeressége
     */
    public boolean fixed() {
        boolean oldIsDamaged = isDamaged;
        isDamaged = false;
        return oldIsDamaged;
    }

    /**
     * Leszármazottakban override-olt függvény, alapértelmezetten nem csinál semmit.
     *
     * @param pump Lehelyezni kívánt pumpa
     * @return false
     */
    public boolean placeDownPump(ActiveField pump) {
        System.out.println("Erre a mezőre nem lehet pumpát lehelyezni!");
        return false;
    }

    /**
     * Leszármazottakban override-olt függvény, alapértelmezetten nem csinál semmit.
     *
     * @return false
     */
    public boolean damagedPipe() {
        System.out.println("Ezt a mezőt nem lehet megrongálni!");
        return false;
    }

    /**
     * Játékost a mezőre helyezi, amennyiben van hely a mezőn.
     * Sikeres művelet esetén visszatérési értéke önmaga, különben null.
     *
     * @param ch Lehelyezni kívánt játékos
     * @return mező, amelyre a játékos került
     */
    public Field acceptCharacter(Character ch) {
        //Ha végtelen számú karakter lehet VAGY nincs még tele a mező
        if (maxCharacters < 0 || characters.size() < maxCharacters) {
            characters.add(ch);
            LOGGER.info("SIKERES karakterléptetés");
            return this;
        } else {
            LOGGER.info("SIKERTELEN karakterléptetés, nincs hely a mezőn");
            return null;
        }
    }

    /**
     * Eltávolít egy karaktert a mezőről.
     *
     * @param ch Eltávolítandó karakter
     */
    public void removeCharacter(Character ch) {
        LOGGER.info("SIKERES karaktereltávolítás");
        characters.remove(ch);
    }

    /**
     * Absztrakt függvény szomszédos elemek lekérdezésére.
     *
     * @return Szomszédos mezők
     */
    public abstract ArrayList<? extends Field> getNeighbours();

    /**
     * Absztrakt metódus a víz átadására, leszármazottak valósítják meg.
     *
     * @param water     Vízmennyiség
     * @param fromField Az a mező, amelytől a víz érkezik
     * @return Maradék vízmennyiség
     */
    public abstract int acceptWater(int water, Field fromField);

    /**
     * Leszármazottakban override-olt függvény, alapértelmezetten nem csinál semmit.
     *
     * @param pipe Felvenni kívánt cső
     */
    public void pickUpPipe(Pipe pipe) {
        System.out.println("Erről a mezőről nem lehet csövet felvenni!");
    }


    /**
     * Leszármazottakban override-olt függvény, alapértelmezetten nem csinál semmit.
     *
     * @param pipe Lehelyezni kívánt cső
     * @return false;
     */
    public boolean placeDownPipe(Pipe pipe) {
        System.out.println("Erre a mezőre nem lehet csövet lehelyezni!");
        return false;
    }

    /**
     * Leszármazottakban override-olt függvény, alapértelmezetten nem csinál semmit.
     *
     * @return false
     */
    public boolean makeSlippery() {
        System.out.println("Ezt a mezőt nem lehet csúszóssá tenni!");
        return false;
    }

    /**
     * Leszármazottakban override-olt függvény, alapértelmezetten nem csinál semmit.
     *
     * @return false
     */
    public boolean makeSticky() {
        System.out.println("Ezt a mezőt nem lehet ragadóssá tenni!");
        return false;
    }

    /**
     * Field állapota
     *
     * @return állapotleírás
     */
    public abstract String getStat();

    /**
     * Visszaadja a mezőn alkalmazható műveletetek
     * @return commandok
     */
    public abstract ArrayList<String> getCommands();

    /**
     * Visszaadja, hogy az adott mező sérült-e
     * @return true esetén sérült a mező
     */
    public boolean isDamaged() {
        return isDamaged;
    }

    /**
     * Visszaadja, hogy az adott mező csúszós-e
     * @return false, felüldefiniált
     */
    public boolean isSlippery() {return false;}

    /**
     * Visszaadja, hogy az adott mező ragadós-e
     * @return false, felüldefiniált
     */
    public boolean isSticky() {return false;}

    /**
     * Visszaadja, hogy a mező elérte-e a maximum szomszédjainak a számát
     * @return false, felüldefiniált
     */
    public boolean hasMaxNeighbours(){return false;}

    /**
     * Visszaadja, hogy a mező elérte-e a maximum szomszédjainak a számát
     * @return false, felüldefiniált
     */
    public boolean isPickedUp(){return false;}
}