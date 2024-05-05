package graphics.gamefield;

import model.characters.Plumber;

import javax.swing.*;
import java.awt.*;

/**
 * Plumbert reprezentáló grafikus osztály
 */
public class JPlumber extends JCharacter {

    /**
     * Létrehozza magát a kapott színnel
     */
    public JPlumber(Plumber plumber, ImageIcon playerIco) {
        observedCharacter=plumber;
        playerName=playerIco.getDescription();
        graphicalImage=playerIco;
        color = Color.green;
    }
}
