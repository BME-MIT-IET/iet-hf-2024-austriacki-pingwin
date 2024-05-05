 # IET-HF
 
[HF leírása](https://edu.vik.bme.hu/mod/page/view.php?id=127170)

## Feladatok

 ### IET-HF.md fájlban egy-két bekezdésben foglalja össze a csapat a saját szavaival, hogy
 - mi a projekt célja, 
 - és nagyobb projekt esetén válasszon ki a funkcióknak egy részhalmazát, amivel később dolgozni fog majd.

### Technológia fókusz
- Build keretrendszer beüzemelése, ha még nincs (**Maven**, Gradle...) + CI beüzemelése, ha még nincs (GitHub Actions, AppVeyor, Azure Pipelines...)
- Manuális kód átvizsgálás elvégzése az alkalmazás egy részére (GitHub, Gerrit...) + Statikus analízis eszköz futtatása és jelzett hibák átnézése (**SonarCloud**, SpotBugs, VS Code Analyzer, Codacy, Coverity Scan...).
    - Mivel az eszközök rengeteg hibát és figyelmeztetést találhatnak, ezért elég azok egy részét megvizsgálni és ha a csapat minden tagja egyetért vele, akkor javítani. Törekedjetek arra, hogy különböző típusú, és lehetőleg nem triviális hibajelzéseket vizsgáljatok meg.
- ~~Deployment segítése (Docker, Vagrant, felhő szolgáltatásba telepítés, ha értelmes az adott alkalmazás esetén...)~~
- ~~Egységtesztek készítése/kiegészítése (xUnit...) + tesztek kódlefedettségének mérése és ez alapján tesztkészlet bővítése (JaCoCo, OpenCover, Coveralls, Codecov.io...)~~
- ~~API tesztelés, ha van számítógéppel feldolgozható API leírás (pl. OpenAPI), valamilyen keretrendszer segítségével (REST-assured, Postman, kontraktus-alapú tesztelés Pact segítségével)~~

### Termék/felhasználó fókusz
- Nem-funkcionális jellemzők (teljesítmény, stresszteszt, biztonság, használhatóság...) vizsgálata (pl. JMeter, k6...)
- ~~UI tesztek készítése (Selenium, Tosca, Appium...)~~
- BDD tesztek készítése (**Cucumber**, Specflow...)
- Manuális tesztek megtervezése, végrehajtása és dokumentálása vagy exploratory testing

---

>Mindkét csoportból **legalább 2-2 feladat** elvégzése kötelező. A csapat eldöntheti, hogy melyik feladatokat akarja elvégezni (a projekt adottságait és az egyéni érdeklődéseket figyelembe véve). Javasolt, hogy egy-egy feladaton többen is dolgozzanak. A feladat elvégzése után kötelező, hogy legalább egy másik, a feladaton nem dolgozó csapattag ellenőrizze a munkát (pull request review keretében).

>**FONTOS** A házi feladatra csapattagonként kb. 15 órát kell fordítani. Ezt az időt a csapat jól ossza be, és koncentráljatok azokra a feladatokra, amik az adott projekt szempontjából hasznosak, és amivel a legtöbbet tudtok meg az adott szoftver minőségéről vagy nagy eséllyel tudtok javítani rajta.

## Feladatok dokumentálása
- Minden egyes kiválasztott feladathoz egy-egy **issue**-t kell felvenni, amiben nyomon lehet majd követni a feladat megoldásának alakulását. Az issue-ban egyeztessék a csapattagok, hogy **ki mit csinál** majd, és legyenek **belinkelve a kapcsolódó főbb commit-ok** vagy **pull request-ek**, és látszódjon, hogy **ki ellenőrizte** a végén az adott feladatot.
- Az elvégzett munka **rövid dokumentáció**ját a saját GitHub tárhely **doc** könyvtárába rakott fájlba kell írni. A fájlokhoz a **Markdown** vagy ~~AsciiDoc~~ formátumot használjuk. Minden egyes kiválasztott feladatról legyen egy-egy külön fájl: **egy-két bekezdés az elvégzett munkáról, képernyőkép (ha releváns), eredmények és tanulságok összefoglalása.**


## Értékelés
- A házi feladatot a félév végén be kell majd mutatni. (Ennek pontos beosztását majd a 14. héten intézzük.)
- Az értékelés nem mennyiségi, hanem minőségi alapon történik. Nem az a lényeg, hogy 15 vagy 20 teszt készült el, hanem, hogy mit tanult a feladatból a csapat, és sikerült-e a kiválasztott projekt minőségén javítani valamit. Hasonlóan figyeljetek arra, hogy hiába készít a csapat 20 egységtesztet, ha azok mind az egyszerű getter és setter metódusok működését ellenőrzik, akkor túl sok hasznuk nincs.
- Törekedjen mindenki arra, hogy jó minőségű munkát végezzen (szép kód, értelmes commit üzenetek, rendezett repository, áttekinthető dokumentáció, követhető issue-k...). Tehát nem csak az elkészült kód, hanem minden egyéb termék része az értékelésnek.

## Kérdések
- Technikai jellegű kérdéseket a Teams csoport HF csatornájában lehet feltenni (így más is tud segíteni, és a válaszokat más is csapatok is láthatják).
- Egyéb, személyes kérdésekkel Micskei Zoltánt keressétek emailben.

## Határidők
- 2024.05.22. feladatok elvégzése és dokumentálása
- 2024.05.29. pótlás határideje

## Bemutatás
- A munkát a félév végén a csapatnak be kell mutatni (az erre való jelentkezést Teams üzenetben publikáljuk):

- Ismertetni kell az elvégzett munkát, az egyes csapattagok hozzájárulását, az elért eredményeket, a kiválasztott projekt értékelését (mit tudtunk meg a végrehajtott ellenőrzési feladatok során a projekt állapotáról és minőségéről) és az elvégzett munka értékelését (mi sikerült, mit tanultak belőle, mi hiányzik még, mivel érdemes később bővíteni...).
- A bemutatóra nem szükséges fóliasorozatot készíteni, saját laptopon kell az egyes részfelatatokat bemutatni (tehát a tesztek lefuttathatók, statikus ellenőrző eszköz eredményei megnézhetők...). Kérjük, hogy minden csapat készítse elő a laptopot a megfelelő weboldalak és eszközök megnyitásával.
- A bemutató jellegében olyasmi, mintha a csapat a főnökének vagy megrendelőjének mutatná be a munkáját, tehát készüljetek fel rá adatokkal, összesítésekkel és érvekkel, hogy kellő információt tudjatok adni kérdések esetén, és meg tudjátok győzni az elvégzett munka minőségéről.
- Alapesetben elvárt, hogy az összes csapattag részt vegyen. Ha bemutatási alkalom valakinek órarendi elfoglaltsággal ütközik, akkor neki nem kötelező megjelenni, de ezt is figyelembe véve a csapatnak olyan időpontot kell választani, amin legalább 3 csapattag részt vesz.
- Egy-egy csapatra kb. 10 perc fog jutni.
- A bemutatásra készíteni kell egy legfeljebb 2 oldalas dokumentumot, ami a végleges, részletes munkamegosztást tartalmazza (melyik csapattag pontosan milyen részfeladatokat végzett el, kb. mennyi időben). Figyelem, ennek konzisztensnek kell lennie a csapat GitHub tárhelyén lévő commit történettel! A dokumentum ezen kívül tartalmazza a tárgy nevét, a csapat nevét és a csapattagok nevét, NEPTUN-kódját és GitHub azonosítóját. Használható ez a sablon. A dokumentumot fel kell tölteni a Moodle-be, valamint kinyomtatva is kell hozni.