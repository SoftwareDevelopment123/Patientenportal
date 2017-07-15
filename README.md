# Patientenportal

**Automatische Tabellen-Generierung für Hibernate ist in der Konfig momentan deaktiviert!**  
  --> einfach wieder bei Bedarf einkommentieren!


Nicht im Development-Branch arbeiten!

- Wenn ihr mit Programmieren startet, checked euren Branch und merged dann von "Development"
- Nach dem Commit und Push in eurem eigenen Branch checked ihr Development und merged von dort dann euren eigenen Branch (da sollte möglichst alles funktionieren dann)

## Fortschrittsstand

Fertig und überprüft:
DAO	-	(bis auf die Interfaces) User, Patient, Doctor, Relative, Registration, Office, Address, Contact, Insurance, Rights, Medication, Medicine, VitalData
WS	-	

Fertig:
DAO	- InstructionalDoc und MedicalDoc (inkl. Tests) + Case-Verknüpfung
WS	- RegistrationWS, AccountWS, PatientWS, RelativeWS, DoctorWS, OfficeWS, AddressWS, ContactWS (getestet, token/rolecheck fehlt noch)

in Arbeit:
DAO	-	finale Prüfung (z.B. criteria anpassen)
WS	-	

Fehlt noch:
WS	-	restliche ws


## Dokumentation

06.06. (Stefan)
- Entities (User, Doctor, Patient, Address, Contact) angelegt, fehlende Verknüpfungen sind auskommentiert

07.06. (Jan/Stefan)
- UserCRUDTest angelegt (Kompletter Unit-Test des UserDAOs avisiert)
- Bidirektional-Test angelegt (funktioniert alles)
- RegistrationDAO angelegt für Actor-Create-Methoden
- Entities (Relative, Office, Insurance) angelegt, fehlende Verknüpfungen sind auskommentiert

- UserDAO fertig (UpdateAddress, UpdateContact hinzugefügt)
- UserCRUDTest fertig (funktioniert alles)

- Entities (Case) angelegt, fehlende Verknüpfungen sind auskommentiert
- CaseCRUDTest angelegt (Kompletter Unit-Test des CaseDAOs avisiert)
- Testing-Suite eingerichtet (Test können jetzt alle zusammen laufen)
- Ordner-Struktur geändert (vor allem bei Tests) / kleine Tests zum "Probieren" kommen jetzT in die "Spielwiese"

08.06. (Stefan)
- OfficeDAO angefangen
- DoctorOfficeCRUDTest angefangen

14.06. (Stefan/Jan)
- User/Doctor/Relative/Patient/Address/Contact DAOs bearbeitet
- Tests weiterentwickelt
- ManyToMany bei Patient-Relative gestestet (funktioniert super)

15.06. (Stefan)
- Try/Catch/Finally - Blöcke in die Tests eingebaut (noch nicht fertig)
- Struktur der DAOs leicht geändert
- Criteria impolementiert und getestet (siehe Spielwiese.smalltests)
- CheckUsername zur RegistrationDAO hinzugefügt und UserCRUDTest um checkUsername erweitert --> funktioniert alles

16.06.(Jan)
- RelativeDAO Update und Delete eingefügt
- PatientDAO Update und Delete eingefügt
- braucht man bei Contact ein create oder wird die dann in die Service schicht geschrieben da es ja über den User läuft

19.06. (Jan/Stefan)

- Bidirektionaler Zugriff optimiert (muss noch auf alle bestehenden Klassen angewendet werden)
	- Join-Column auf der "owning-side" und MappedBy - Attribut auf der Gegenseite
- Criteria (Rechte, checkusername)
- Rechte fertig, RechteTest fertig, AccessTest angelegt und fertig
- ActorDAOs und RegistrationDAO fertig (bis auf Patient)
- InsuranceDAO und Test fast fertig

20.6 (Jan)
- MDOC, InstructionDoc und Doc als Supperklasse angelegt + zugehörige DAOs für MedicalDOc und InstructionDoc
- für Dokument kein DAO da reine Entity für Vererbung 

22.06. (Stefan)
- mapped bei bei den user-actor-verknüpfungen eingeführt, außerdem bei doctor-office
	- war auch nötig, da der WS sonst fehler wirft
- Patient-Case eingefügt
- MedicalDocDAO vervollständigt (beim update ggf. noch Patient hinzufügen, try-Blöcke fehlen noch)
- Mdoc zu case und patient eingefügt
- MDoc-Test angelegt ( bis auf die Case-Verknüpfung klappt alles)

23.06 (Stefan / nur auf Tablet eingefügt, noch nicht getestet)
- case-doctor-Verknüpfung angelegt (noch kein Test geschrieben)
- vitaldata-case-Verknüpfung geändert (Timestamp noch anschauen!), caseTest amgeüasst

26.06.
- QM bei allen bestehenden DAOs (try-catch-Blöche eingefügt und Logik überprüft)
- Entities Medication und Medicine angelegt, Verknüpfung zum Case (DAOs und Test fehlen noch)

30.06-1.07 Jan 
- MedicationDAo mit Try Catch Blöcken eingefügt 
- MDoc DAO ergänzt um TryCatch Blöcke
- Insurance Test funktioniert
- Vitaldata Test ist bisher in Case enthalten so kann es einzeln geprüft werden...können wir aber auch rausnehemen, müssen wir mal drüber sprechen
- Lösung gesucht für die Hibernate Superklasse nennt sich union subclass bei hibernate aber kein beispiel gefunden bzw. nicht ganz verstanden aber die Lösung ist die richtige

01.07. (alle)
- Medication/Medicine inkl. Tests fertig und geprüft
- Alle Tests angepasst, sodass die ID unabhängig verwendbar ist (Suite funktioniert wieder)
- Dokumenten-Test
- WS angefangen

05.07 Jan
- Verbindung zu FTTP Server (Filezilla)
- InDoc und MedicalDoc Entity ergänzt
- die DAOs entsprechend ergänzt
- FTP methoden allgmein geschrieben dann extra Klassen gemacht für die Methoden bezüglich der FtpMethoden für InstructionDocs und MDocs
- Tests zu beiden geschrieben
- Tests laufen durch (testsuite funktioniert auch noch)
- Problem mit Download gelöst 
- durch das Attribut FileType können nur verschiedene Dateien abgespeichert werden

06.07 Jascha
- AuthenticationWS läuft - jedoch immer erst richtig nach dem 2. Mal - kp. warum
 hierfür wurde u.a. UserDao und hibernate.cfg angepasst
- Libary Ordner entfernt --> werden nur noch über Maven gemanaged!
- alles erfolgreich gemerged

07.07. Jascha
* Alles erfolgreich gemerged, Gender-Enum eingefügt und alles entsprechend angepasst

bis 09.07. (alle)
- DAOs und Case-Verknüpfungen weitgehend Fertig
	- noch testen und dann abschließen
- WS bis zu den Actors fertig
- Token / Authentication / Authorization / Login / Logout funktioniert
- DateiServerZugriff funktioniert (bei Jan, hier vllt noch eine Anleitung einfügen)

## To-Do
- Deprecated Criterias anpassen (UserDAO, WebSessionDAO, ...?)

- Superklasse für die Dokumente wieder einfügen?
- Superklasse für die ListResponse-Entities

- Token- und Role-Check (soweit nötig) in die Methoden einfüge
	- Tests anpassen, sodass das Token in den Accessor mitgegeben wir
	- Tests (min. 1-2 Mal) auf die Verschiedenen Outputs (InvalidToken, NoToken, ...) per AssertEquals testen
	
- restliche WS anlegen

- javadoc

- API in der wsdl?

- Websession anpassen

- Timestamp bei den Dokumenten/VitalData und Datumsformat bei Birthdate

- Suchfunktionen

## Anmerkungen
- Bidirektionale OneToMany anpassen (soweit abgeschlossen, siehe Unteraufgabe)
	- Läuft darauf hinaus, dass wir alle (!) Case-Verknüpfungen individuell anlegen und bei Case myppedBy angeben
	- noch nicht alles angelegt

- getCases (patientID) in der DAO vs. PatientDAO.getCases(); + Rechteabgleich in der Service-Schicht?!

- Case-Status bei Anzeige der Fälle filtern (Schon in den Rechten oder später?) --> in der Service-Darstellung geändert (noch nicht sicher)
- Idee: SaveOrUpdate-Funktion (ausprobieren, testen)

- versch. Methodennamen für die getUserx-Methoden werden nicht mehr gebraucht, da bidirektionaler Zugriff funktioniert
	- gleiches gilt für die getDoctorsByx, etc. Methoden, das geht durch Eager/Init - Zugriff (macht eigentlich mehr Sinn)

- EAGER für OnetoOne, Lazy für große Abfragen !!!
	- FEHLER IST BEHOBEN, NACH STUNDENLANGER SUCHE, FUCK YEAH
	- Die Lösung ist in der CaseDAO zu bewundern
	- im Prinzip können wir die ganzen EAGER-Verbindungen auch lazy machen und mit Hibernate-Initialize arbeiten (insofern sinnvoll)
	
- Flush-Error bei OneToMany und ManyToOne ist behoben
	- eine bestimmte Reihenfolge muss eingehalten werden, damit alles funktioniert
	- wie das aussehen kann/muss, könnt ihr euch im DoctorOfficeCRUDTest ansehen
	- Grundlegend: Referenzierte Entities erst anlegen und dann verknüpfen (außer cascadetype.all und und owning-Klassen)
	
- Die if (xxx =! null) - Logik in der Persistenzschicht kann weg, wenn wir immer das Prinzip nutzen, was den Flush-Error vermeidet
	- d.h. wir rufen ein (vollständiges) Objekt aus der DB ab, ändern Werte und geben dann das dieses Objekt wieder zurück
	- die if-Logik kann dann in die Service-Schicht, wo sie ja eigentlich "hingehört"
	
- Das gleiche Prinzip kann man vllt auch bei dem Schreiben von Adreessen über das Office angewendet werden (funktioniert noch nicht)
	- Dadurch sparen wir uns die getrennten Address- und ContactDAOs
	- stattdessen nehmen wir das bestehende Objekt aus der Abfrage und ändern nur den gewünschten Wert
	- z.B. office.getContact().setMail("neue Mail");

- WS-Logik!!
	- Listen werden nicht von JAXB verstande
	- Lösung 1: @SOAPBinding(style = Style.RPC) zu Style.Document ändern, dann müssen wir aber auch ein entsprechendes Doc erstelle
	- Lösung 2: @SOAPBinding auskommentieren und die @Xml... mappings benutzen --> das wirds wahrscheinlich, muss ich noch teste
- gelöst durch @XmlTransient-Annotation bei unnötigen Verknüpfungen (Data-Input bleibt durch entsprechende Methoden erhalten

- MAPPEDBY für Bidirektionale Beziehungen benutzen!!!!!! Siehe Patient-Insurance

- Tests funktionierten nur individuell, aber nicht in der Suite (gelöst)
	- liegt daran, dass z.B. mehrere Tests einen Case anlegen und damit arbeiten (beim 2. Test passt die ID dann nicht mehr)
	- entweder Lösung in der Unit-Logik finden, Tests zusammenfassen oder einfach ohne Suite arbeiten (wäre schade)
	- beheben wir durch den ID-Abruf aus dem Objekt vom Anlegen

## Logik für die Service-Schicht
- Verknüpfungen löschen bei delete
- Keine eigene DAO-Abfrage für Case-Status, lieber "aussortieren" in der WS-Logik?
- Bei Create-Case auch dem erstellenden Doktor Lese-/Schreibrechte mitgeben

## Ausblick

- DAO - Interfaces und non-static DAO
- Token-Authentication über HTTP Heade
- Response-Entities verbessern

