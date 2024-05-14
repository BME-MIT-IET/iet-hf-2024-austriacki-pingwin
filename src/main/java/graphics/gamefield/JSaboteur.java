package graphics.gamefield;

import model.characters.Saboteur;

import javax.swing.*;
import java.awt.*;

/**
 * Saboteurt reprezentáló grafikus osztály
 */
public class JSaboteur extends JCharacter {

    /**
     * Létrehozza magát a kapott színnel
     */
    public JSaboteur(Saboteur saboteur, ImageIcon playerIco) {
        observedCharacter=saboteur;
        playerName=playerIco.getDescription();
        graphicalImage=playerIco;
        color = Color.red;
    }
}
