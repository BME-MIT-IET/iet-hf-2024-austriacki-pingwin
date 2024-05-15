package proto;

import model.Game;
import model.characters.Character;
import model.characters.Plumber;
import model.characters.Saboteur;
import model.fields.Cistern;
import model.fields.Field;
import model.fields.Pipe;
import org.apache.logging.log4j.Logger;
import test.Tester;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Consumer;

public class CommandHelper {
    private static final Logger LOGGER = LogHelper.getLogger();

    private final HashMap<String, Consumer<String[]>> methodMap;  //Parancsok tárolása

    /**
     * Parancsok és a hozzájuk tartozó metódsok beállítása a HashMap-ben
     */
    public CommandHelper() {
        methodMap = new HashMap<>();
        methodMap.put("logloc", this::logloc);
        methodMap.put("loadgreat", this::loadgreat);
        methodMap.put("loadgcmd", this::loadgcmd);
        methodMap.put("move", this::move);
        methodMap.put("fix", this::fix);
        methodMap.put("damage", this::damage);
        methodMap.put("nextplayer", this::nextplayer);
        methodMap.put("cancel", this::cancel);
        methodMap.put("stop", this::cancel);
        methodMap.put("start", this::start);
        methodMap.put("pickupPump", this::pickupPump);
        methodMap.put("pickupPipe", this::pickupPipe);
        methodMap.put("placedownPipe", this::placedownPipe);
        methodMap.put("placedownPump", this::placedownPump);
        methodMap.put("change", this::change);
        methodMap.put("slippery", this::slippery);
        methodMap.put("sticky", this::sticky);
        methodMap.put("state", this::state);
        methodMap.put("help", this::help);
    }

    /**
     * A logfájl helyét lehet vele beállítani. Az elérési utat szóközzel kell megadni a parancs után.
     *
     * @param str argumentumok: [elérési út]
     */
    public void logloc(String[] str) {
        // metódus kódja
        if (str != null && str.length > 0) {
            LOGGER.debug("Kapott logfájlnév: \"" + str[0] + "\"");
            LogHelper.setTestLogging(str[0]);
        } else System.out.println("Adjon meg a logloc-nak argumentumot! pl: \"logloc test/logs/testName1\"");
    }

    /**
     * A tesztelőnyelvvel megadott parancsokat lehet vele betölteni. Az elérési utat szóközzel kell megadni a parancs után.
     *
     * @param str argumentumok: [elérési út]
     */
    public void loadgreat(String[] str) {
        if (str == null || str.length == 0) {
            System.out.println("Adjon meg a loadgreat-nek argumentumot! pl: \"loadgreat test/test1\"");
            return;
        }


        //Futtassunk minden tesztet a mappában
        if (str[0].contains("*")) {
            String filepath = str[0];
            try {
                String folderPath;
                if (filepath.equals("*")) { //Ha csak egy * akkor megegyező mappában vagyunk
                    folderPath = System.getProperty("user.dir");
                } else {
                    folderPath = filepath.substring(0, filepath.lastIndexOf("*")); // a "folder/subfolder/" rész kell
                }
                File folder = new File(folderPath);

                System.out.println("Mappában lévő tesztek futtatása...");
                //Fájlok listázása és betöltése

                File[] listOfFiles = folder.listFiles();
                assert listOfFiles != null;
                for (File file : listOfFiles) {
                    if (file.isFile() && file.getName().contains(".greatest")) {  //greatest fájlok futtatása
                        //Teszter létrehozása és indítása
                        Tester tester = new Tester(this);
                        try {
                            System.out.println(file.getPath());
                            LOGGER.info("A [{}] teszt futtatása...", file.getName());
                            Game.getNullGame();
                            tester.runTest(file.getPath());
                            System.out.println(file.getPath());
                        } catch (Exception e) {
                            LOGGER.error("Hiba a [{}] test futtatása közben!", file.getName(), e);
                        }
                    }
                }
            } catch (NullPointerException e) {
                System.err.println("Nem létező mappa vagy fájl lett megadva!");
                LOGGER.warn("Nem létező mappa vagy fájl lett megadva!", e);
            }
        } else {
            for (String filepath : str) {

                //Elérési út átírása
                LOGGER.debug("Tesztfájl megadott elérési útja: {}", filepath);
                if (!filepath.contains(".greatest")) filepath += ".greatest";
                LOGGER.debug("Tesztfájl átalakított elérési útja: {}", filepath);

                if (filepath.contains(".greatest")) {//Teszter létrehozása és indítása
                    Tester tester = new Tester(this);
                    try {
                        LOGGER.info("Teszt futtatása...");
                        Game.getNullGame();
                        tester.runTest(filepath);
                    } catch (Exception e) {
                        LOGGER.error("Hiba a test futtatása közben!", e);
                    }
                }
            }
        }
    }

    /**
     * Játék parancsokat lehet vele betölteni, amiket végrehajt a program. Az elérési utat szóközzel kell megadni a parancs után.
     *
     * @param str argumentumok: [elérési út]
     */
    public void loadgcmd(String[] str) {
        if (str == null || str.length == 0) {
            System.out.println("Adjon meg a loadgcmd-nek argumentumot! pl: \"loadgcmd cmds/inp.txt\"");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(str[0]))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");   //Bemeneti sor felbontása, majd
                String command = parts[0];
                String[] options = Arrays.copyOfRange(parts, 1, parts.length);
                runMethod(command, options);
            }
        } catch (IOException e) {
            LOGGER.error("Hiba a help fájl beolvasása közben!");
        }
    }


    /**
     * Mozog az épp soron lévő játékos.
     * Szóközzel a parancstól elválasztva kell megadni annak az elemnek a sorszámát, amelyre lépni szeretnénk.
     * Először meghívja a state parancsot, hogy megjelenítse a játék jelenlegi állapotát.
     *
     * @param str argumentumok: [sorszám]
     */
    public void move(String[] str) {
        ArrayList<? extends Field> neighbours = Game.getInstance().getActCharacter().getActField().getNeighbours(); //szomszédos mezők lekérdezése
        if (str == null || str.length == 0) { //ha konzolból hívják
            LOGGER.debug("move paraméter nélküli hívása");
            System.out.println("Írja be a parancsot a kivánt mező sorszámának paraméterével! (pl: \"move 1\")");
            for (int i = 0; i < neighbours.size(); i++) {
                System.out.println("Sorszám: " + i + "\t" + neighbours.get(i));
                LOGGER.debug("Sorszám: " + i + "\t" + neighbours.get(i));
            }
        } else {
            try {
                int idx = Integer.parseInt(str[0]);
                if (idx < 0 || neighbours.size() <= idx)
                    throw new NumberFormatException("Nem létezik " + idx + " indexű szomszéd!"); //ha nincs az intervallumban az index hibát dobunk
                if (Game.getInstance().getActCharacter().move(neighbours.get(idx))) {
                    System.out.println("Sikeres lépés!"); //ha sikeres a lépés
                } else {
                    System.out.println("A lépés nem sikerült!");
                }
            } catch (NumberFormatException e) {
                System.out.println("HIBA: Nem megfelelő paraméter!");
                LOGGER.warn("hibás paraméter: " + e.getMessage());
            }
        }
    }

    /**
     * Pumpa vagy cső javítása. Ha az aktuális játékos szerelő, különben nem engedi a program.
     *
     * @param str argumentumok
     */
    public void fix(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter();
        boolean canFixed = false;

        if (actCharacter instanceof Plumber) canFixed = ((Plumber) actCharacter).fix();

        if (canFixed)
            System.out.println("Elem sikeresen megjavítva!");
        else
            System.out.println("Nem tudsz javítani!");
    }

    /**
     * Cső lyukasztása, amin a játékos áll.
     *
     * @param str argumentumok
     */
    public void damage(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter();

        if (actCharacter.damagePipe())
            System.out.println("Cső sikeresen kilyukasztva!");
        else
            System.out.println("Sikertelen lyukasztás!");
    }

    /**
     * Jelenlegi játékos befejezi a körét. Következő játékos lép.
     *
     * @param str argumentumok
     */
    public void nextplayer(String[] str) {
        //if (Game.getInstance().actPlayerDone())
        Game.getInstance().nextCharacter();

        System.out.println(Game.getInstance().getActCharacter() + " következik!");

        if (Game.getInstance().isGameOver()) {
            System.out.println("JÁTÉK VÉGE!");
            int cistWater = Game.getInstance().getPpo().getCisternWater();
            int desWater = Game.getInstance().getSpo().getDesertWater();

            state(null);
            if (cistWater == desWater) System.out.println("DÖNTETLEN!");
            if (cistWater > desWater) System.out.println("VÍZSZERELÓK NYERTEK!");
            if (cistWater < desWater) System.out.println("SZABOTŐRÖK NYERTEK!");
            LOGGER.info("A játék véget ért!");
            LOGGER.debug("cisternWater={}; desertWater={}", cistWater, desWater);
        }
    }

    /**
     * Azonnali kilépés az aktuális játékból.
     *
     * @param str argumentumok
     */
    public void cancel(String[] str) {
        LOGGER.info("Kilépés...");
        System.exit(0);
    }

    /**
     * Új játék indítása. Megadható, hogy hány szabotőr és szerelő játszik. Egymástól és a parancstól is szóközzel elválasztva.
     * Alapértelmezetten mindkét csapatban kettő játékos van, ha ennél kevesebbet akarnak a felhasználók megadni
     * azt a program nem engedi és jelzi nekik a hibájukat.
     *
     * @param str argumentumok: [szerelők száma] [szabotőrök száma]
     */

    public void start(String[] str) {
        int numOfPlumbers = 2;
        int numOfSaboteurs = 2;

        if (str.length >= 2) {
            try {
                numOfPlumbers = Integer.parseInt(str[0]);
                numOfSaboteurs = Integer.parseInt(str[1]);
            } catch (NumberFormatException e) {
                LOGGER.warn("Hibás érték lett megadva: " + e.getMessage());
            }

            if (numOfPlumbers <= 1) {
                numOfPlumbers = 2;
                System.out.println("Legalább 2 szerelőnek kell lennie!");
            }
            if (numOfSaboteurs <= 1) {
                numOfSaboteurs = 2;
                System.out.println("Legalább 2 szabotőrnek kell lennie!");
            }
        }
        System.out.println("Új játék létrehozása: Szerelők száma=" + numOfPlumbers + ", Szabotőrök száma=" + numOfSaboteurs);
        LOGGER.debug("Játék létrehozása: Szerelők száma=" + numOfPlumbers + ", Szabotőrök száma=" + numOfSaboteurs);

        Game.initGame(numOfPlumbers, numOfSaboteurs);       //Játék inicializálása a megadott paraméterekkel.
    }

    /**
     * Pumpa felvétele. Csak ha ciszternánál van az adott játékos. Ha van már nála pumpa akkor nem vehet fel újat.
     *
     * @param str argumentumok
     */

    public void pickupPump(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter();

        boolean canPickup = false;
        if (actCharacter instanceof Plumber) {
            if (actCharacter.getActField() instanceof Cistern) canPickup = ((Plumber) actCharacter).pickPump();

            if (canPickup)
                System.out.println("Sikeres pumpafelvétel!");
            else
                System.out.println("Sikertelen pumpafelvétel!");
        } else System.out.println("Csak szerelő végezhet ilyen akciót!");
    }

    /**
     * Cső felvétele. Ha van már nála cső akkor nem vehet fel újat.
     * Ha van szabadvégű szomszédja a csőnek azt kapja meg a szerelő, ha nincs azt kapja, amin áll.
     * Ha az aktuális játékos szerelő, különben nem engedi a program.
     * Ha nincs mit felvenni akkor sem engedi a program.
     *
     * @param str argumentumok
     */

    public void pickupPipe(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter();
        ArrayList<Field> neighbours = new ArrayList<>(actCharacter.getActField().getNeighbours());

        if (actCharacter instanceof Plumber) {
            if (str.length == 0) {
                for (Field p : neighbours) {
                    System.out.println(p.toString());
                }
            } else {
                try {
                    int idx = Integer.parseInt(str[0]);
                    if (idx < 0 || neighbours.size() <= idx)
                        throw new NumberFormatException("Nem létezik " + idx + " indexű szomszéd!"); //ha nincs az intervallumban az index hibát dobunk
                    if (((Plumber) actCharacter).pickPipe(neighbours.get(idx))) {
                        System.out.println("Sikeres csőfelvétel!");
                    } else {
                        System.out.println("Sikertelen csőfelvétel!");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("HIBA: Nem megfelelő paraméter!");
                    LOGGER.warn("Hibás paraméter: " + e.getMessage());
                }
            }

        } else System.out.println("Nem aktív elemen áll!");
    }

    /**
     * Cső lehelyezése. Amin áll arra csatlakoztatja, ha van szabad helye, ha nincs neki hely akkor nem lehet lerakni.
     * Ha az aktuális játékos szerelő, különben nem engedi a program.
     * Ha nincs mit lerakni akkor sem engedi a program.
     *
     * @param str argumentumok
     */
    public void placedownPipe(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter();
        boolean canPlaceDown;
        if(actCharacter instanceof Plumber){
            canPlaceDown = ((Plumber) actCharacter).placePipe();
            if (canPlaceDown) System.out.println("Sikeres csőelhelyezés!");
            else System.out.println("Az akció sikertelen volt!");
        } else System.out.println("Csak szerelő végezhet ilyen akciót");
    }

    /**
     * Pumpát helyez az aktuális csőre. Ha az aktuális játékos szerelő, különben nem engedi a program. Ha nincs mit lerakni akkor sem engedi a program.
     *
     * @param str argumentumok
     */
    public void placedownPump(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter();
        boolean canPlaceDown;
        if(actCharacter instanceof Plumber){
            canPlaceDown = ((Plumber) actCharacter).placePump();
            if (canPlaceDown) System.out.println("Sikeres pumpaelhelyezés!");
            else System.out.println("Az akció sikertelen volt!");
        } else System.out.println("Csak szerelő végezhet ilyen akciót");
    }

    /**
     * Egy pumpa be és kimenetét lehet vele beállítani. Szóközzel a parancs után kell megadni az input ás output csövek sorszámát.
     *
     * @param str argumentumok: [input] [output]
     */
    public void change(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter(); //aktuális karakter lekérdezése
        ArrayList<? extends Field> neighbours = Game.getInstance().getActCharacter().getActField().getNeighbours(); //szomszédos mezők lekérdezése
        //ha paraméter nélkül kerül hívásra, kírja a lehetőségeket
        if (str == null || str.length == 0) {
            LOGGER.debug("change paraméter nélküli hívása");
            System.out.println("Írja be a parancsot a kivánt input és output mezők sorszámának paraméterével! (pl: \"change 0 1\")");
            //szomszédok kiírása
            for (int i = 0; i < neighbours.size(); i++) {
                System.out.print("Sorszám: " + i + "\t" + neighbours.get(i));
                LOGGER.debug("Sorszám: " + i + "\t" + neighbours.get(i));

                //szomszédok szomszédjának kiírása
                ArrayList<Field> secondNeighbours = new ArrayList<>(neighbours.get(i).getNeighbours());
                Field secondNeighbour = null;
                for (Field neighbour : secondNeighbours) {
                    if (!neighbour.equals(neighbours.get(i))) { //ha a másik vége az állítsa be
                        secondNeighbour = neighbour;
                        break;
                    }
                }
                if (secondNeighbour == null) System.out.println("A cső másik vége szabad");
                else System.out.println("\t -> " + secondNeighbour); //a cső másik végének típusa pl -> Cistern
            }
        } else {
            try {
                //az új beállítások indexei
                int newInput = Integer.parseInt(str[0]);
                int newOutput = Integer.parseInt(str[1]);
                //indexek intervallum ellenőrzése
                if (newInput >= neighbours.size() || newInput < 0)
                    throw new NumberFormatException("nem megfelelő index"); //ha túl nagy vagy kicsi lenne az index hibát dob
                if (newOutput >= neighbours.size() || newOutput < 0)
                    throw new NumberFormatException("nem megfelelő index"); //ha túl nagy vagy kicsi lenne az index hibát dob

                //a parancs kiadása
                if (actCharacter.setPump(((Pipe) neighbours.get(newInput)), ((Pipe)neighbours.get(newOutput)))) {
                    System.out.println("Sikeres pumpa átállítás!");
                } else {
                    System.out.println("Nem sikerült az akció, mert nem pumpán áll, vagy rosszul választott be-, és kimeneti csövet!");
                    LOGGER.warn("Nem sikerült az akció, mert nem pumpán áll, vagy ugyanaz a be és kimeneti cső!");
                }

            } catch (NumberFormatException e) {
                System.out.println("Rossz bemenet, így nem sikerült lépni!");
                LOGGER.warn("Rossz bemenet, így nem sikerült lépni!");
            }
        }

    }

    /**
     * Csúszóssá teszi a csövet, amin a játékos áll. Ha az aktuális játékos egy szabotőr különben nem engedi a program.
     *
     * @param str argumentumok
     */

    public void slippery(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter();
        boolean canSlippery;

        if (actCharacter instanceof Saboteur) {
            canSlippery = ((Saboteur) actCharacter).slipPipe();
            if (canSlippery)
                System.out.println("Cső sikeresen csúszóssá!");
            else
                System.out.println("Az akció sikertelen volt!");
        } else {
            System.out.println("Csak szabotőr teheti csúszóssá a csövet!");
        }

    }

    /**
     * Ragadóssá teszi a csövet, amin a játékos éppen áll. Ha az aktuális játékos szerelő, különben nem engedi a program.
     *
     * @param str argumentumok
     */
    public void sticky(String[] str) {
        Character actCharacter = Game.getInstance().getActCharacter();

        if (actCharacter.stickPipe())
            System.out.println("Cső sikeresen ragadóssá téve!");
        else
            System.out.println("Az akció sikertelen volt!");
    }

    /**
     * Kiírja a különböző pályaelemeket és azok állapotát (csúszós, ki-be menet egy pumpán, stb.), szomszédjait, valamint a csapatok pontszámát.
     * Összes kommand és melyiket milyen pályaelemen lehet alkalmazni.
     *
     * @param str argumentumok
     */
    public void state(String[] str) {
        LOGGER.info("Statisztikák lekérdezése...");
        //karakter információnak kiírása
        try {
            Character actCharacter = Game.getInstance().getActCharacter();
            System.out.println(actCharacter.getActField().toString() + "\nMezőn használható parancsok:");
            for (String s : actCharacter.getActField().getCommands()) {
                System.out.print(s + "\t");
            }
            System.out.println("\n");
            LOGGER.debug("mező parancsai visszaadva");

            System.out.println(actCharacter + "\nKarakterrel használható parancsok:");
            for (String s : actCharacter.getCommands()) {
                System.out.print(s + "\t");
            }
            System.out.println("\n");
            LOGGER.debug("karakter parancsai visszaadva");
        } catch (NullPointerException e) {
            LOGGER.warn("nincs karakter a játékban");
        }
        int cistWater = Game.getInstance().getPpo().getCisternWater();
        int desWater = Game.getInstance().getSpo().getDesertWater();
        LOGGER.debug(desWater);

        System.out.println("Ciszternába folyt vízmennyiség: " + cistWater);
        System.out.println("Sivatagba folyt vízmennyiség:   " + desWater);
    }

    /**
     * Kiírja a program által elfogadott parancsokat.
     *
     * @param str argumentumok
     */
    public void help(String[] str) {
        try (BufferedReader br = new BufferedReader(new FileReader("./res/help.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            LOGGER.error("Hiba a help fájl beolvasása közben!", e);
        }
    }

    /**
     * Elindítja a parancshoz tartozó metódust a megadott argumentumokkal
     *
     * @param methodName parancs
     * @param str        argumentumok
     */
    public void runMethod(String methodName, String[] str) {
        LOGGER.debug("Kapott parancs: [" + methodName + "]");
        LOGGER.debug("Kapott argumentumok: " + Arrays.toString(str));
        if (methodMap.containsKey(methodName)) {
            LOGGER.info("A " + methodName + " parancs futtatása...");
            methodMap.get(methodName).accept(str);
        } else {
            LOGGER.info("Nem létező parancsnév!");
            System.out.println("Hibás parancsnév!\nA parancsok listázásához írja be a \"help\" parancsot!");
        }
    }
}