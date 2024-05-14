package graphics;

import javax.swing.*;

public class View {
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel"); //swing stílus beállítása
        } catch (Exception e) {
            e.printStackTrace();
        }
        new MainFrame();
    }
}
