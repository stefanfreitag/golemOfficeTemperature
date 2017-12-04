[![Download](https://api.bintray.com/packages/stefanfreitag/maven/officeTemperature/images/download.svg) ](https://bintray.com/stefanfreitag/maven/officeTemperature/_latestVersion)
[![Build Status](https://travis-ci.org/stefanfreitag/officeTemperature.svg?branch=master)](https://travis-ci.org/stefanfreitag/officeTemperature)
[![Coverage Status](https://coveralls.io/repos/github/stefanfreitag/officeTemperature/badge.svg?branch=master)](https://coveralls.io/github/stefanfreitag/officeTemperature?branch=master)

# Motivation
[Golem.de](http://www.golem.de/) wollte im April 2016 wissen, bei welcher Temperatur 
sich Mitarbeiter am produktivsten fühlen. Als Besitzer eines [Raspberry Pi](https://de.wikipedia.org/wiki/Raspberry_Pi) 
und eines [Raspi-SHT21](http://emsystech.de/raspi-sht21/) möchte ich an dem 
Mitmachprojekt teilnehmen und habe eine Java-basierte Anwendung hierfür geschrieben.

Die Datenerfassung soll sich übrigens von April 2016 bis Juli 2016 erstrecken. Es ist also
noch etwas Zeit ebenso mitzumachen!

# Die Anwendung
Die Anwendung kann über obigen Link als Binär-Distribution bezogen werden. Im [zugehörigen 
Repository](https://bintray.com/stefanfreitag/maven/officeTemperature/view#) finden sich auch
Quellcode und JavaDoc wieder.

Nach dem Herunterladen kann das Archiv extrahiert werden, zum Beispiel

    unzip de.freitag.stefan.golem.office-1.0.2.zip 

In dem entstandenen Unterverzeichnis liegt ein bin-Verzeichnis mit Startskripten für
Linux und Microsoft Windows. Der Aufruf des Skripte ohne weitere Angaben gibt eine
erste Hilfestellung hinsichtlich der erwarteten Parameter:

    cd de.freitag.stefan.golem.office-1.0.2/bin/ 
    ./de.freitag.stefan.golem.office
    Option "-debug" is required
     -city VAL                           : Stadt-/Gemeindename
     -country VAL                        : Zweistelliger Landescode
     -debug N                            : Debug Mode (1 enabled, 0 disabled)
     -interval N                         : Sendeintervall (Minuten)
     -latitude N                         : -90 to 90
     -longitude N                        : -180 to 180
     -token VAL                          : Eindeutiger Token zur Geraeteerkennung
     -type [sbc | ard | wifimuc | other] : Hardware type
     -zip VAL                            : Postleitzahl

Die Parameter lehnen sich an die [hier](http://www.golem.de/projekte/ot/doku.php)
auf Golem.de veröffentlichten an:

* Basis-URL: 
    http://www.golem.de/projekte/ot/temp.php?

* Erforderliche Parameter
    * dbg - Test (dbg=1) oder Auswertung (dbg=0)
* Optionale Parameter  
    * token - Eindeutiger Token zur Geräteerkennung, siehe Token-Generierung
    * type - Zur Messung benutzte Plattform (sbc, ard, wifimuc, other)
    * country - Land der Messung, zweistelliger Landescode (z.B.: country=DE)
    * city - Stadt-/Gemeindename
    * zip - Postleitzahl
    * lat - GPS-Angabe, Breitengrad
    * long - GPS-Angabe, Längengrad
    
 Der Temperaturwert wird im Gegensatz zur Beschreibung auf Golem.de direkt
 aus den Messungen des Raspi-SHT21 ermittelt und übertragen. Des Weiteren
 gibt es den erforderlichen Parameter __interval__. Dieser bestimmt den Intervall
 zwischen zwei Datenübertragungen.
 
## Einsatz hinter einem Proxy
 
 Ich bin vermutlich nicht der einzige, dessen Büro über einen Proxy mit dem hiesigen
 Internet in Kontakt steht. Deswegen sei hier kurz erwähnt, dass man im Startskript
 in den  __DEFAULT_JVM_OPTS__ den Proxy angeben kann. Die fraglichen Schalter lauten
 *-Dhttp.proxyHost* und *-Dhttp.proxyPort*.

 