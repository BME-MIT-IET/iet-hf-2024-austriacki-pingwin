package proto;
//
//
//  @ Project : Szoftver projekt laboratórium: Sivatagi vízhálózat
//  @ File Name : Proto.Skeleton.java
//  @ Date : 2023. 03. 28.
//  @ Author : aD4B team
//
//

import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;


public class Proto {
    private static final Logger LOGGER = LogHelper.getLogger();
    private static final CommandHelper ch = new CommandHelper();


    public static void main(String[] args) {
        LOGGER.info("start");

        boolean run = true;
        try {
            //Program argumentumainak kezelése
            if (args.length > 0) {
                try {
                    String[] commands = new String[args.length];
                    for (int i = 0; i < args.length; i++) {
                        if (!args[i].startsWith("-"))       //Ellenőrizzük, hogy "-"-el kezdődik-e
                            throw new IllegalArgumentException("A program parancsat \"-\"-el kell kezdeni!");
                        commands[i] = args[i].substring(1);
                    }
                    ch.runMethod(commands[0], Arrays.copyOfRange(commands, 1, commands.length));   //Futtassuk a metódust
                } catch (IllegalArgumentException e) {
                    System.err.println(e.getMessage());
                }
            }

            while (run) {
                String line = new BufferedReader(new InputStreamReader(System.in)).readLine();
                line = line.trim();
                if (!line.isEmpty()) {
                    LOGGER.trace("Input from user: {}", line);
                    String[] parts = line.split(" ");
                    String command = parts[0];
                    String[] options = Arrays.copyOfRange(parts, 1, parts.length);

                    if (command.equalsIgnoreCase("exit")) run = false;
                    else ch.runMethod(command, options);
                }
            }
        } catch (IOException e) {
            LOGGER.error("Hiba lépett fel a beolvasás során!", e);
        } catch (Exception e) {
            LOGGER.fatal("Váratlan hiba!", e);
        }

        LOGGER.info("finish");
    }


}
