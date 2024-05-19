## Projekt célja
A projektnek elsősorban azt a célt tűztük ki, hogy minél több eszközzel, módszerrel megismerkedhessünk és olyan ismeretekre tegyünk szert ezen a téren, amik a későbbiekben hasznosak lesznek számunkra. Ennek megfelelően, illetve a kódot figyelembe véve választottuk ki a számunkra szimpatikus feladatokat, illetve eszközöket, amiket használni szeretnénk ezek elvégzésére.

Elsősorban úgy véljük, hogy elengedhetetlen a Maven integrálása, hiszen több alkalommal is tapasztalhattuk már korábban tanulmányaink során, hogy a build keretrendszerek kiértékelése mennyire hasznos és értékes visszajelzést tud nyujtani a megelőzhető hibák kivédésére és a kód futtathatóságának biztosítására.

A kód tesztelésére és átvizsgálására szeretnénk másodsorban a legnagyobb hangsúlyt fektetni. A kiválasztott funkcionalitások teszteléséhez kiválasztottuk a BDD tesztek készítését Cucumber segítségével, illetve a számunkra hagyományosabbnak tekinthető manuális tesztek készítését és végrehajtását, hiszen úgy gondoljuk így tudjuk a lehető legjobban összehasonlítani a különböző tesztelési módszereket. Emellett, hogy a tipikusan 1-1 funkcionalitást vizsgáló tesztektől elrugaszkodjunk exploratory teszteket is készítünk, hogy komplex játékhelyzetek is vizsgálat alá kerüljenek.

A kód átvizsgálására pedig a SonarCloud analizáló eszközt választjuk, hiszen a laboron már megismerkedhettünk vele, illetve mindannyiunk egy logikus és jól átlátható felületnek tartja. Ezzel célünk megbízhatóbbá és fenntarthatóbbá tenni a kódot.

## Ellenőrzött funkcionalitások
-BDD tesztek: Építési funkciók, játékos mozgása

-Manuális tesztek: Saboteur osztály működése

-Exploratory tesztek: Általános játékfunkciók (aktív elemekkel kapcsolatos akciók, játékos akciók/mozgása)

-Manuális kódátvizsgálás: teljes kód analizálása, majd biztonsági, kód fenntarthatósági és komplexitási problémák javítása