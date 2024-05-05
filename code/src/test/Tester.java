package test;

import model.Game;
import model.Steppable;
import model.characters.Character;
import model.fields.Pipe;
import proto.CommandHelper;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;


public class Tester {
    private final Hashtable<String, Object> localVariables = new Hashtable<>();
    private final Hashtable<String, Object> globalVariables = new Hashtable<>();
    private boolean globalScope = true;
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    final ArrayList<Boolean> moduleTestResults = new ArrayList<>();
    final ArrayList<Boolean> testTestResults = new ArrayList<>();
    File outputFile = null;
    PrintStream fileStream = null;
    final CommandHelper commandHelper;

    public Tester(CommandHelper ch) {
        commandHelper = ch;
    }

    public void runTest(String path) throws FileNotFoundException {

        File file = new File(path);
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
            while (true) {
                String line;
                line = br.readLine();
                if (line == null)
                    break;
                String[] cmd = line.split(" ");

                switch (cmd[0]) {
                    case "#MODULE":
                        module(line);
                        break;
                    case "#TEST":
                        test(line);
                        break;
                    case "#END":
                        end();
                        break;
                    case "*":
                        System.out.println("\t" + line.substring(2));
                        break;
                    case "number":
                        number(cmd);
                        break;
                    case "text":
                        text(cmd);
                        break;
                    case "choice":
                        choice(cmd);
                        break;
                    case "?<":
                        querySmaller(cmd);
                        break;
                    case "?>":
                        queryBigger(cmd);
                        break;
                    case "?=":
                        queryEquals(cmd);
                        break;
                    case "!=":
                        queryNotEquals(cmd);
                        break;
                    case "!null":
                        queryNotNull(cmd);
                        break;
                    case "?true":
                        queryTrue(cmd);
                        break;
                    case "?log":
                        queryLog(cmd);
                        break;
                    case "?lines":
                        queryLines(cmd);
                        break;
                    case "?occur":
                        queryOccur(cmd);
                        break;
                    case "printoccur":
                        printOccur(line, cmd);
                        break;
                    case "loadlog":
                        loadlog(cmd);
                        break;
                    case "loadexp":
                        loadexp(cmd);
                        break;
                    case "gmcd":
                        gmcd(cmd);
                        break;
                    case "//": //kommentelés, igazából felesleges de ha valamit kezdeni akarnánk vele akkor itt van
                        break;
                    case "output":
                        output(cmd);
                        break;
                    case "add":
                        add(cmd);
                        break;
                    case "sub":
                        sub(cmd);
                        break;
                    case "mult":
                        mult(cmd);
                        break;
                    case "div":
                        div(cmd);
                        break;
                    case "cpy":
                        cpy(cmd);
                        break;
                    case "crt":
                        crt(cmd);
                        break;
                    case "call":
                        call(cmd);
                        break;
                    case "callsv":
                        callsv(cmd);
                    case "addplayer":
                        addPlayer(cmd);
                        break;
                    case "actplayer":
                        setActPlayer(cmd);
                        break;
                    case "addstep":
                        addSteppable(cmd);
                        break;
                    case "addpipe":
                        addPipe(cmd);
                        break;
                    case "getppo":
                        getPpo(cmd);
                        break;
                    case "getspo":
                        getSpo(cmd);
                        break;
                    case "testhelper":
                        testHelper(cmd);
                        break;
                    default:
                        break;
                }

            }
            br.close();
            if (outputFile != null)
                fileStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | InstantiationException | NoSuchMethodException |
                 IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * a Game osztály addPipe metódusa hívható meg vele
     * @param cmd a bemeneti parancsok tömbje
     */
    void addPipe(String[] cmd){
        Pipe p;
        if (globalScope)
            p = (Pipe) globalVariables.get(cmd[1]);
        else
            p = (Pipe) localVariables.get(cmd[1]);
        Game.getInstance().addPipe(p);
    }

    /**
     * a Game osztály testHelper metódusa hívható meg vele
     * @param cmd a bemeneti parancsok tömbje
     */
    void testHelper(String[] cmd){
        boolean b;
        if (globalScope)
            b = (boolean) globalVariables.get(cmd[1]);
        else
            b = (boolean) localVariables.get(cmd[1]);
        Game.getInstance().testHelper(b);
    }

    /**
     * a Game osztály getPpo metódusa hívható meg vele
     * @param cmd a bemeneti parancsok tömbje
     */
    void getPpo(String[] cmd){
        Object observer = Game.getInstance().getPpo();
        if(globalScope){
            globalVariables.put(cmd[1], observer);
        }else {
            localVariables.put(cmd[1], observer);
        }
    }

    /**
     * a Game osztály geSpo metódusa hívható meg vele
     * @param cmd a bemeneti parancsok tömbje
     */
    void getSpo(String[] cmd){
        Object observer = Game.getInstance().getPpo();
        if(globalScope){
            globalVariables.put(cmd[1], observer);
        }else {
            localVariables.put(cmd[1], observer);
        }
    }

    /**
     * Hozzáad egy léptethető objektumot a Game osztályoz
     *
     * @param cmd a bemeneti parancsok tömbje
     */
    private void addSteppable(String[] cmd) {
        Steppable steppable;
        if (globalScope)
            steppable = (Steppable) globalVariables.get(cmd[1]);
        else
            steppable = (Steppable) localVariables.get(cmd[1]);
        Game.getInstance().addSteppable(steppable);
    }

    /***
     * Beállítja az aktuális játékost a Game osztályban
     * @param cmd a bemeneti parancsok tömbje
     */
    private void setActPlayer(String[] cmd) {
        Character character;
        if (globalScope)
            character = (Character) globalVariables.get(cmd[1]);
        else
            character = (Character) localVariables.get(cmd[1]);
        Game.getInstance().setActCharacter(character);
    }

    /**
     * Hozzáad egy játékost (karaktert) a Game osztályoz
     *
     * @param cmd a bemeneti parancsok tömbje
     */
    private void addPlayer(String[] cmd) {
        Character character;
        if (globalScope)
            character = (Character) globalVariables.get(cmd[1]);

        else
            character = (Character) localVariables.get(cmd[1]);

        Game.getInstance().addCharacter(character);
    }

    /**
     * Beaállítja a kimenetét a teszternek
     * @param cmd a bemeneti parancsok tömbje
     * @throws FileNotFoundException ha nem található a fájl
     */
    private void output(String[] cmd) throws FileNotFoundException {
        outputFile = new File(cmd[1]);
        fileStream = new PrintStream(outputFile);
    }

    /**
     * egy játékbeli parancsot dolgoz fel
     * @param cmd  a bemeneti parancsok tömbje
     */
    private void gmcd(String[] cmd) {
        String[] args = new String[cmd.length - 2];
        System.arraycopy(cmd, 2, args, 0, cmd.length - 2);

        commandHelper.runMethod(cmd[1], args);
    }

    /***
     * egy program beli függvényt tud meghívni egy objektumon és lementeni annak bemenetét
     * @param cmd a bemeneti parancsok tömbje
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void callsv(String[] cmd) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object myObject;
        ArrayList<Object> myParams = new ArrayList<>();
        if (globalScope) {
            myObject = globalVariables.get(cmd[1]);
            for (int i = 3; i < cmd.length; i++) {
                myParams.add(globalVariables.get(cmd[i]));
            }
        } else {
            myObject = localVariables.get(cmd[1]);
            for (int i = 3; i < cmd.length; i++) {
                myParams.add(localVariables.get(cmd[i]));
            }
        }

        Class<?> myClass = myObject.getClass();
        Class<?>[] parameterTypes = getParameterTypes(myParams);
        Method myMethod = null;
        if (parameterTypes.length > 0) {
            while (parameterTypes[0] != null) {
                try {
                    myMethod = myClass.getDeclaredMethod(cmd[2], parameterTypes);
                    break;
                } catch (NoSuchMethodException e) {
                    parameterTypes[0] = parameterTypes[0].getSuperclass();
                }
            }
        } else {
            myMethod = myClass.getDeclaredMethod(cmd[2]);
        }

        if (myMethod == null) {
            while (myClass != null) {
                try {
                    myMethod = myClass.getDeclaredMethod(cmd[2], getParameterTypes(myParams));
                    break;
                } catch (NoSuchMethodException e) {
                    myClass = myClass.getSuperclass();
                }
            }
        }

        Object saved = null;
        if (myMethod != null) {
           saved = myMethod.invoke(myObject, myParams.toArray());
        } else {
            System.err.println("Nem létező metódus!");
            // ha nem található a keresett metódus az adott objektum és annak ősosztályai között
            // itt kezelhetjük le a hibát, például egy kivétel dobásával vagy egy üzenet kiírásával
        }
        if (globalScope)
            globalVariables.put(cmd[1], saved);
        else
            localVariables.put(cmd[1], saved);
    }

    /**
     * egy program beli függvényt tud meghívni egy objektumon, tud paramétereket is
     * @param cmd a bemeneti parancsok tömbje
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     */
    private void call(String[] cmd) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object myObject;
        ArrayList<Object> myParams = new ArrayList<>();
        if (globalScope) {
            myObject = globalVariables.get(cmd[1]);
            for (int i = 3; i < cmd.length; i++) {
                myParams.add(globalVariables.get(cmd[i]));
            }
        } else {
            myObject = localVariables.get(cmd[1]);
            for (int i = 3; i < cmd.length; i++) {
                myParams.add(localVariables.get(cmd[i]));
            }
        }

        Class<?> myClass = myObject.getClass();
        Class<?>[] parameterTypes = getParameterTypes(myParams);
        Method myMethod = null;
        if (parameterTypes.length > 0) {
            while (parameterTypes[0] != null) {
                try {
                    myMethod = myClass.getDeclaredMethod(cmd[2], parameterTypes);
                    break;
                } catch (NoSuchMethodException e) {
                    parameterTypes[0] = parameterTypes[0].getSuperclass();
                }
            }
        } else {
            myMethod = myClass.getDeclaredMethod(cmd[2]);
        }

        if (myMethod == null) {
            while (myClass != null) {
                try {
                    myMethod = myClass.getDeclaredMethod(cmd[2], getParameterTypes(myParams));
                    break;
                } catch (NoSuchMethodException e) {
                    myClass = myClass.getSuperclass();
                }
            }
        }

        if (myMethod != null) {
            myMethod.invoke(myObject, myParams.toArray());
        } else {
            System.err.println("Nem létező metódus!");
            // ha nem található a keresett metódus az adott objektum és annak ősosztályai között
            // itt kezelhetjük le a hibát, például egy kivétel dobásával vagy egy üzenet kiírásával
        }


    }

    /**
     * létrehoz egy programbeli objektumot, tud konstruktort is
     * @param cmd a bemeneti parancsok tömbje
     * @throws InstantiationException
     * @throws IllegalAccessException
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     */
    private void crt(String[] cmd) throws  InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        // Get the class object for the class you want to instantiate
        Class<?> targetClass = null;
        int idx = 0;
        boolean cantFind = false;
        while (targetClass == null && !cantFind) {
            try {
                switch (idx) {
                    case 0:
                        targetClass = Class.forName(cmd[1]);
                    case 1:
                        targetClass = Class.forName("model.fields." + cmd[1]);
                    case 2:
                        targetClass = Class.forName("model.characters." + cmd[1]);
                    case 3:
                        targetClass = Class.forName("model." + cmd[1]);
                    case 4:
                        targetClass = Class.forName("proto." + cmd[1]);
                    default:
                        cantFind = true;
                }
            } catch (Exception e) {
                idx++;
            }
        }

        ArrayList<Object> myParams = new ArrayList<>();
        if (globalScope) {
            for (int i = 3; i < cmd.length; i++) {
                myParams.add(globalVariables.get(cmd[i]));
            }
        } else {
            for (int i = 3; i < cmd.length; i++) {
                myParams.add(localVariables.get(cmd[i]));
            }
        }

        Constructor<?> constructor = null;
        Class<?> currentClass = targetClass;
        Class<?>[] parameterTypes = getParameterTypes(myParams);
        if (parameterTypes.length > 0) {
            while (parameterTypes[0] != null) {
                try {
                    constructor = currentClass.getDeclaredConstructor(parameterTypes);
                    break;
                } catch (NoSuchMethodException e) {
                    parameterTypes[0] = parameterTypes[0].getSuperclass();
                }
            }
        } else {
            constructor = currentClass.getDeclaredConstructor();
        }


        if (constructor != null) {
            // Create an instance by invoking the constructor and passing the argument
            Object instance;
            if (myParams.size() > 0)
                instance = constructor.newInstance(myParams.toArray());
            else
                instance = constructor.newInstance();

            if (globalScope) {
                globalVariables.put(cmd[2], instance);
            } else {
                localVariables.put(cmd[2], instance);
            }
        } else {
            System.err.println("Nem létező konstruktor!");
        }
    }

    /**
     * egy változó értékét átmásolja egy másikba
     * @param cmd a bemeneti parancsok tömbje
     */
    private void cpy(String[] cmd) {
        if (globalScope) {
            var copiedObject = globalVariables.get(cmd[1]);
            globalVariables.put(cmd[2], copiedObject);
        } else {
            var copiedObject = localVariables.get(cmd[1]);
            localVariables.put(cmd[2], copiedObject);
        }
    }

    /**
     * egy számot eloszt egy másikkal
     * @param cmd a bemeneti parancsok tömbje
     */
    private void div(String[] cmd) {
        if (globalScope) {
            int d1 = (int) globalVariables.get(cmd[1]);
            int d2 = (int) globalVariables.get(cmd[2]);
            globalVariables.put(cmd[1], d1 / d2);
        } else {
            int d1 = (int) localVariables.get(cmd[1]);
            int d2 = (int) localVariables.get(cmd[2]);
            localVariables.put(cmd[1], d1 / d2);
        }
    }

    /**
     * egy számot megszoroz egy másikkal
     * @param cmd a bemeneti parancsok tömbje
     */
    private void mult(String[] cmd) {
        if (globalScope) {
            int m1 = (int) globalVariables.get(cmd[1]);
            int m2 = (int) globalVariables.get(cmd[2]);
            globalVariables.put(cmd[1], m1 * m2);
        } else {
            int m1 = (int) localVariables.get(cmd[1]);
            int m2 = (int) localVariables.get(cmd[2]);
            localVariables.put(cmd[1], m1 * m2);
        }
    }

    /**
     * egy számot kivon egy másikból
     * @param cmd a bemeneti parancsok tömbje
     */
    private void sub(String[] cmd) {
        if (globalScope) {
            int s1 = (int) globalVariables.get(cmd[1]);
            int s2 = (int) globalVariables.get(cmd[2]);
            globalVariables.put(cmd[1], s1 - s2);
        } else {
            int s1 = (int) localVariables.get(cmd[1]);
            int s2 = (int) localVariables.get(cmd[2]);
            localVariables.put(cmd[1], s1 - s2);

        }
    }

    /**
     * egy számot hozzáad egy másikhoz
     * @param cmd a bemeneti parancsok tömbje
     */
    private void add(String[] cmd) {
        if (globalScope) {
            int a1 = (int) globalVariables.get(cmd[1]);
            int a2 = (int) globalVariables.get(cmd[2]);
            globalVariables.put(cmd[1], a1 + a2);
        } else {
            int a1 = (int) localVariables.get(cmd[1]);
            int a2 = (int) localVariables.get(cmd[2]);
            localVariables.put(cmd[1], a1 + a2);
        }
    }

    /**
     * betölt egy elvárt kimeneti fájlt
     * @param cmd a bemeneti parancsok tömbje
     * @throws FileNotFoundException
     */
    private void loadexp(String[] cmd) throws FileNotFoundException {
        if (globalScope) {
            globalVariables.put(cmd[1], new BufferedReader(new FileReader(cmd[2])));
        } else {
            localVariables.put(cmd[1], new BufferedReader(new FileReader(cmd[2])));
        }
    }

    /**
     * betölt egy logfájlt
     * @param cmd a bemeneti parancsok tömbje
     * @throws FileNotFoundException
     */
    private void loadlog(String[] cmd) throws IOException {
        if (globalScope) {
            BufferedReader logBr = new BufferedReader(new FileReader(cmd[2]));
            logBr.mark(100000);
            globalVariables.put(cmd[1], logBr);
        } else {
            BufferedReader logBr = new BufferedReader(new FileReader(cmd[2]));
            logBr.mark(100000);
            localVariables.put(cmd[1], logBr);
        }
    }

    /**
     * kiírja egy keresett szöveg előfordulásainak sorait egy logfájlból
     * @param line
     * @param cmd a bemeneti parancsok tömbje
     * @throws IOException
     */
    private void printOccur(String line, String[] cmd) throws IOException {
        BufferedReader logBr;
        String logLine;
        String occured;
        if (globalScope) {

            logBr = (BufferedReader) globalVariables.get(cmd[1]);
            occured = (String) globalVariables.get(cmd[2]);
        } else {
            logBr = (BufferedReader) localVariables.get(cmd[1]);
            occured = (String) localVariables.get(cmd[2]);

        }
        while (logBr.readLine() != null) {
            if (line.contains(occured)) {
                System.out.println(line);
            }
        }
    }

    /**
     * megszámolja, hogy hűnyszor fordul elő egy kifejezés egy logfájlban és ellenőrzi, hogy a szám egyenlő e egy elvárt értékkel
     * @param cmd a bemeneti parancsok tömbje
     * @throws IOException
     */
    private void queryOccur(String[] cmd) throws IOException {
        String logLine;
        BufferedReader logBr;
        String occured;
        int numOfSearchOccured = 0;
        int expectedCountOfOccurence;
        if (globalScope) {
            logBr = (BufferedReader) globalVariables.get(cmd[1]);
            occured = (String) globalVariables.get(cmd[2]);
            expectedCountOfOccurence = (int) globalVariables.get(cmd[2]);
        } else {
            logBr = (BufferedReader) localVariables.get(cmd[1]);
            occured = (String) localVariables.get(cmd[2]);
            expectedCountOfOccurence = (int) localVariables.get(cmd[3]);
        }
        logBr.reset();
        while ((logLine = logBr.readLine()) != null) {


            int index = 0;


            while ((index = logLine.indexOf(occured, index)) != -1) {
                numOfSearchOccured++;
                index += occured.length();
            }



        }
        Boolean currentComparationResult = numOfSearchOccured == expectedCountOfOccurence;
        testTestResults.add(currentComparationResult);
        if (cmd.length <= 4) {
            printCompareResult("Sikeres", "Sikertelen", currentComparationResult);
        } else {
            printCompareResult(cmd[3], cmd[4], currentComparationResult);
        }
    }

    /**
     * egy bizonyos sort keres adott fájlban, azt nézi, hogy előfordul e a kívánt számban a sor
     * @param cmd a bemeneti parancsok tömbje
     * @throws IOException
     */
    private void queryLines(String[] cmd) throws IOException {
        BufferedReader logBr;
        int numOfLinesCounted = 0;
        int expectedCountOfLines;
        if (globalScope) {
            logBr = (BufferedReader) globalVariables.get(cmd[1]);
            expectedCountOfLines = (int) globalVariables.get(cmd[2]);
        } else {
            logBr = (BufferedReader) localVariables.get(cmd[1]);
            expectedCountOfLines = (int) localVariables.get(cmd[2]);
        }
        while (logBr.readLine() != null) {
            numOfLinesCounted++;
        }

        Boolean currentComparationResult = numOfLinesCounted == expectedCountOfLines;
        testTestResults.add(currentComparationResult);
        if (cmd.length < 4) {
            printCompareResult("Sikeres", "Sikertelen", currentComparationResult);
        } else {
            printCompareResult(cmd[3], cmd[4], currentComparationResult);
        }
    }

    /**
     * logfájlt hasonlít össze egy elvárt logfájlal
     * @param cmd a bemeneti parancsok tömbje
     * @throws IOException
     */
    private void queryLog(String[] cmd) throws IOException {
        BufferedReader expBr;
        BufferedReader logBr;
        if (globalScope) {
            logBr = (BufferedReader) globalVariables.get(cmd[1]);
            expBr = (BufferedReader) globalVariables.get(cmd[2]);
        } else {
            logBr = (BufferedReader) localVariables.get(cmd[1]);
            expBr = (BufferedReader) localVariables.get(cmd[2]);
        }
        String line1, line2;
        boolean problemHappened = false;
        while ((line1 = logBr.readLine()) != null && (line2 = expBr.readLine()) != null) {
            if (!line1.equals(line2)) {
                System.out.println("\t" + ANSI_RED + line1 + ANSI_RESET);
                problemHappened = true;
            }
        }
        Boolean currentComparationResult = problemHappened;
        testTestResults.add(currentComparationResult);
        if (cmd.length < 4) {
            printCompareResult("Sikeres", "Sikertelen", currentComparationResult);
        } else {
            printCompareResult(cmd[3], cmd[4], currentComparationResult);
        }
    }

    /**
     * egy boolean igaz e
     * @param cmd a bemeneti parancsok tömbje
     */
    private void queryTrue(String[] cmd) {
        Object querydBool;
        if (globalScope) {
            querydBool = globalVariables.get(cmd[1]);
        } else {
            querydBool = localVariables.get(cmd[1]);
        }
        Boolean currentComparationResult = querydBool.equals(true);
        testTestResults.add(currentComparationResult);
        if (cmd.length < 4) {
            printCompareResult("Sikeres", "Sikertelen", currentComparationResult);
        } else {
            printCompareResult(cmd[2], cmd[3], currentComparationResult);
        }
    }

    /**
     * egy vizsgált változó nem null e
     * @param cmd a bemeneti parancsok tömbje
     */
    private void queryNotNull(String[] cmd) {
        Object querydNotNullObject;
        if (globalScope) {
            querydNotNullObject = globalVariables.get(cmd[1]);
        } else {
            querydNotNullObject = localVariables.get(cmd[1]);
        }
        Boolean currentComparationResult = querydNotNullObject.equals(null);
        testTestResults.add(!currentComparationResult);
        if (cmd.length < 4) {
            printCompareResult("Sikeres", "Sikertelen", !currentComparationResult);
        } else {
            printCompareResult(cmd[3], cmd[4], !currentComparationResult);
        }
    }

    /**
     * két vizsgált változó nem egyenlőek e
     * @param cmd a bemeneti parancsok tömbje
     */
    private void queryNotEquals(String[] cmd) {
        Object queryObjectForNotEqual1, queryObjectForNotEqual2;
        if (globalScope) {
            queryObjectForNotEqual1 = globalVariables.get(cmd[1]);
            queryObjectForNotEqual2 = globalVariables.get(cmd[2]);
        } else {
            queryObjectForNotEqual1 = localVariables.get(cmd[1]);
            queryObjectForNotEqual2 = localVariables.get(cmd[2]);
        }
        Boolean currentComparationResult = queryObjectForNotEqual1.equals(queryObjectForNotEqual2);
        testTestResults.add(!currentComparationResult);
        printCompareResult(cmd[3], cmd[4], !currentComparationResult);
        if (cmd.length < 4) {
            printCompareResult("Sikeres", "Sikertelen", !currentComparationResult);
        } else {
            printCompareResult(cmd[3], cmd[4], !currentComparationResult);
        }
    }

    /**
     * két vizsgált változó egyenlőek e
     * @param cmd a bemeneti parancsok tömbje
     */
    private void queryEquals(String[] cmd) {
        Object objectQueryForEqual1, objectQueryForEqual2;
        if (globalScope) {
            objectQueryForEqual2 = globalVariables.get(cmd[1]);
            objectQueryForEqual1 = globalVariables.get(cmd[2]);
        } else {
            objectQueryForEqual2 = localVariables.get(cmd[1]);
            objectQueryForEqual1 = localVariables.get(cmd[2]);
        }
        Boolean currentComparationResult = objectQueryForEqual1.equals(objectQueryForEqual2);
        testTestResults.add(currentComparationResult);
        if (cmd.length < 4) {
            printCompareResult("Sikeres", "Sikertelen", currentComparationResult);
        } else {
            printCompareResult(cmd[3], cmd[4], currentComparationResult);
        }
    }

    /**
     * megnézi ha egy szám nagyobb e a másiknál
     * @param cmd a bemeneti parancsok tömbje
     */
    private void queryBigger(String[] cmd) {
        int param1, param2;
        if (globalScope) {
            param1 = (int) globalVariables.get(cmd[1]);
            param2 = (int) globalVariables.get(cmd[2]);
        } else {
            param1 = (int) localVariables.get(cmd[1]);
            param2 = (int) localVariables.get(cmd[2]);
        }
        Boolean currentComparationResult = param1 > param2;
        testTestResults.add(currentComparationResult);
        if (cmd.length < 4) {
            printCompareResult("Sikeres", "Sikertelen", currentComparationResult);
        } else {
            printCompareResult(cmd[3], cmd[4], currentComparationResult);
        }
    }

    /**
     * megnézi ha egy szám kisebb e a másiknál
     * @param cmd a bemeneti parancsok tömbje
     */
    private void querySmaller(String[] cmd) {
        int param1, param2;
        if (globalScope) {
            param1 = (int) globalVariables.get(cmd[1]);
            param2 = (int) globalVariables.get(cmd[2]);
        } else {
            param1 = (int) localVariables.get(cmd[1]);
            param2 = (int) localVariables.get(cmd[2]);
        }
        Boolean currentComparationResult = param1 < param2;
        testTestResults.add(currentComparationResult);
        if (cmd.length < 4) {
            printCompareResult("Sikeres", "Sikertelen", currentComparationResult);
        } else {
            printCompareResult(cmd[3], cmd[4], currentComparationResult);
        }
    }

    /**
     * egy boolean változót hoz létre
     * @param cmd a bemeneti parancsok tömbje
     */
    private void choice(String[] cmd) {
        if (globalScope) {
            globalVariables.put(cmd[1], cmd[2].equals("true"));
        } else {
            localVariables.put(cmd[1], cmd[2].equals("true"));
        }
    }

    /**
     * egy sztring változót hoz létre
     * @param cmd a bemeneti parancsok tömbje
     */
    private void text(String[] cmd) {
        StringBuilder value = new StringBuilder();
        for(int i = 2; i<cmd.length; i++){
            if(i == 2)
                value.append(cmd[i]);
            else
                value.append(" ").append(cmd[i]);
        }

        if (globalScope) {
            globalVariables.put(cmd[1], value.toString());
        } else {
            localVariables.put(cmd[1], value.toString());
        }
    }

    /**
     * egy int változót hoz létre
     * @param cmd a bemeneti parancsok tömbje
     */
    private void number(String[] cmd) {
        int n = Integer.parseInt(cmd[2]);
        if (globalScope) {
            globalVariables.put(cmd[1], n);
        } else {
            localVariables.put(cmd[1], n);
        }
    }

    /**
     * fájl végét feldolgozó függvény
     */
    private void end() {
        int testsNum;
        int successfulTestNum = 0;
        if (globalScope) {
            testsNum = moduleTestResults.size();
            for (Boolean i : moduleTestResults) {
                if (i)
                    successfulTestNum++;
            }

        } else {
            testsNum = testTestResults.size();
            for (Boolean i : testTestResults) {
                if (i)
                    successfulTestNum++;
            }

            globalScope = true;
            localVariables.clear();
        }
        if (testsNum == successfulTestNum) {
            moduleTestResults.add(true);
            System.out.println(ANSI_GREEN + "SIKERES" + " (" + successfulTestNum + "/" + testsNum + ")" + ANSI_RESET);
        } else {
            moduleTestResults.add(false);
            System.out.println(ANSI_RED + "SIKERTELEN" + " (" + (testsNum - successfulTestNum) + "/" + testsNum + ")" + ANSI_RESET);
        }
    }

    /**
     * test scopa lépő és kezelő függvény, kezeli a tesztszámlálását
     * @param line
     */
    private void test(String line) {
        System.out.println(ANSI_YELLOW + line.substring(6) + ANSI_RESET); //Azért,hogy a TEST szó ne írodjon ki kell a substring
        globalScope = false;
        localVariables.putAll(globalVariables);
    }


    /**
     * modul scope-ot kezelő függvény, számolja a modul sikeres teszteseteit
     * @param line beolvasott sor
     */
    private void module(String line) {
        System.out.println(ANSI_BLUE + line.substring(8) + ANSI_RESET); //Azért,hogy a MODULE szó ne írodjon ki kell a substring
        globalScope = true;
    }

    /**
     * segéd függvény a query eredmények kiírásához
     * @param first első üzenet
     * @param second második üzenet
     * @param comparation összehasonlítás eredménye
     */
    private static void printCompareResult(String first, String second, Boolean comparation) {
        System.out.println(comparation ? "\t" + ANSI_GREEN + first.toUpperCase() + ANSI_RESET : "\t" + ANSI_RED + second.toUpperCase() + ANSI_RESET);
    }

    /**
     * segédfüggvény egy függvény amely a paraméterként kapott listában lévő objektumok típusait állapítja meg
     * @param params
     * @return osztályokat tartalmazó tömb
     */
    private static Class<?>[] getParameterTypes(List<Object> params) {
        Class<?>[] paramTypes = new Class<?>[params.size()];

        for (int i = 0; i < params.size(); i++) {
            if (isInt(params.get(i).getClass()))
                paramTypes[i] = Integer.TYPE;
            else if (isBool(params.get(i).getClass()))
                paramTypes[i] = Boolean.TYPE;
            else
                paramTypes[i] = params.get(i).getClass();
        }
        return paramTypes;
    }

    /**
     * a paraméterként átt vett osztály Integer-e?
     * @param clazz
     * @return
     */
    private static boolean isInt(Class<?> clazz) {
        return clazz.isAssignableFrom(Integer.class);
    }

    /**
     * a paraméterként átt vett osztály Boolean-e?
     * @param clazz
     * @return
     */
    private static boolean isBool(Class<?> clazz) {
        return clazz.isAssignableFrom(Boolean.class);
    }


}
