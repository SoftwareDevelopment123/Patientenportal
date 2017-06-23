# Patientenportal

Nicht im Master-Branch arbeiten!

- Wenn ihr mit Programmieren startet, checked euren Branch und merged dann von "Development"
- Nach dem Commit und Push in eurem eigenen Branch checked ihr Development und merged von dort dann euren eigenen Branch (da sollte möglichst alles funktionieren dann)

- Hier unten können wir nochmal kurz notieren, das wir zuletzt gemacht haben und was noch fehlt


Fortschrittsstand:

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
-MDOC, InstructionDoc und Doc als Supperklasse angelegt + zugehörige DAOs für MedicalDOc und InstructionDoc
-für Dokument kein DAO da reine Entity für Vererbung 

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


To-Do

- Dokumente fertig anlegen und mit Case Verknüpfen
	- bei den Dokumenten fehlt noch das create!? (bei MDoc eingefügt - Stefan)
	- Spalten aus der Superklasse werden (noch) nicht in die Datenbank übernommen, da müssen wir noch was machen
	- Entities: Case-Verknüpfung von Dokumenten in die Superklasse?
	- InstructiondocDAO MDOC Test achte dabei ob vererbte Sachen geändert werden können und wie es angelegt wird --Jan

- InsuranceTest vervollständigen -- Jan

- Bidirektionale OneToMany anpassen (soweit abgeschlossen, siehe Unteraufgabe)
	- Läuft darauf hinaus, dass wir alle (!) Case-Verknüpfungen individuell anlegen und bei Case myppedBy angeben
	- noch nicht alles angelegt

- Login-Logik implementieren (bzw mal dieses System recherchieren, dass der Betreuer uns bei der zwischenpräsentation genannt hat)
	- RBAM (role based access model)!

- Funktion: Einfügen von Dokumenten in die DB prüfen

- Tests funktionieren individuell, aber nicht in der Suite
	- liegt daran, dass z.B. mehrere Tests einen Case anlegen und damit arbeiten (beim 2. Test passt die ID dann nicht mehr)
	- entweder Lösung in der Unit-Logik finden, Tests zusammenfassen oder einfach ohne Suite arbeiten (wäre schade)

- DAOs zu Interfaces "ummodeln"

- WS-Logik!!!

Anmerkungen
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
	- die if-Logik kann dann in die Service-Schicht, wo sie ja eigentlich "hingehört"
	
- Das gleiche Prinzip kann man vllt auch bei dem Schreiben von Adreessen über das Office angewendet werden (funktioniert noch nicht)
	- Dadurch sparen wir uns die getrennten Address- und ContactDAOs
	- stattdessen nehmen wir das bestehende Objekt aus der Abfrage und ändern nur den gewünschten Wert
	- z.B. office.getContact().setMail("neue Mail");
	
- MAPPEDBY für Bidirektionale Beziehungen benutzen!!!!!! Siehe Patient-Insurance
	
Logik für die Service-Schicht
- Verknüpfungen löschen bei delete
- Keine eigene DAO-Abfrage für Case-Status, lieber "aussortieren" in der WS-Logik?
- Bei Create-Case auch dem erstellenden Doktor Lese-/Schreibrechte mitgeben