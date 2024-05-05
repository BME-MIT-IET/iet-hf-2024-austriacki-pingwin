package proto;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configurator;

/**
 * Segédosztály a logoláshoz.
 */
public class LogHelper {
    private static Logger LOGGER = null;    //Singleton Logger objektum.

    /**
     * Logger lekérése.
     *
     * @return Singleton Logger osztály
     */
    public static synchronized Logger getLogger() {
        if (LOGGER == null) {
            LOGGER = LogManager.getLogger("root");
        }
        return LOGGER;
    }

    /**
     * Beállítja a tesztelés szintű logolást a statikus LOGGER-re.
     *
     * @param logFilePath logfájl neve, amit a logs/testLogs mappában érünk el.
     */
    public static void setTestLogging(String logFilePath) {
        if (logFilePath.isEmpty()) {
            LOGGER.warn("Nincs megadva logfájlnév!");
            return;
        }


        //Ha nem tartalmazna megfelelő formátumot, akkor adjunk hozzá
        if (!logFilePath.contains(".log")) {
            logFilePath += ".log";
            LOGGER.debug("Log elérési útja javítva: \"" + logFilePath + "\"");
        }
        //Ha nem tartalmazna mappahivatkozást adjuk meg neki
        if (!logFilePath.contains("/") || !logFilePath.contains("\\")) logFilePath = "./" + logFilePath;

        if (System.getProperty("logFilePath") != null && System.getProperty("logFilePath").equals(logFilePath)) {
            LOGGER.warn("A log már be van állítva erre a fájlra!");
        } else {
            System.setProperty("logFilePath", logFilePath); // a fájlnév beállítása rendszer tulajdonságként

            //Logger kérése és újratöltése
            LoggerContext context = Configurator.initialize(null, "log4j2.xml"); // konfigurációs fájl betöltése
            context.reconfigure(); // konfiguráció újratöltése
            LOGGER = LogManager.getLogger("testLogger");

            LOGGER.info("Logolás beállítva teszt szinten!");
        }
    }


}
