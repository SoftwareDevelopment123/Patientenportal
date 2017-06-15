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

To-Do
- ActorDAOs, RegistrationDAO und OfficeDAO vervollständigen (siehe DAO Klassendiagramm und UserDAO) -- Jan

- Unit-Tests aktualisieren (neue Update-Mechanik)

- Herausfinden, warum die doctor_office JoinTable angelegt wird

- Login-Logik implementieren (bzw mal dieses System recherchieren, dass der Kollege uns bei der zwischenpräsentation genannt hat
- Criteria-Abfragen testen ("Suche" nach username, bzw. Abfragen wie "zeige mir alle Fälle, bei denen ich Rechte habe")

Anmerkungen
- Case-Status bei Anzeige der Fälle filtern (Schon in den Rechten oder später?) --> in der Service-Darstellung geändert (noch nicht sicher)
- Idee: SaveOrUpdate-Funktion (ausprobieren, testen)
- versch. Methodennamen für die getUserx-Methoden werden nicht mehr gebraucht, da bidirektionaler Zugriff funktioniert
	- gleiches gilt für die getDoctorsByx, etc. Methoden, das geht durch Eager/Init - Zugriff (macht eigentlich mehr Sinn)

- EAGER für OnetoOne, Lazy für große Abfragen !!!
	- FEHLER IST BEHOBEN, NACH STUNDENLANGER SUCHE, FUCK YEAH
	- Die Lösung ist in der CaseDAO zu bewundern
	
- Flush-Error bei OneToMany und ManyToOne ist behoben
	- eine bestimmte Reihenfolge muss eingehalten werden, damit alles funktioniert
	- wie das aussehen kann/muss, könnt ihr euch im DoctorOfficeCRUDTest ansehen
	
- Die if (xxx =! null) - Logik in der Persistenzschicht kann weg, wenn wir immer das Prinzip nutzen, was den Flush-Error vermeidet
	- die if-Logik kann dann in die Service-Schicht, wo sie ja eigentlich "hingehört"
	
- Das gleiche Prinzip kann man vllt auch bei dem Schreiben von Adreessen über das Office angewendet werden (funktioniert noch nicht)
	- Dadurch sparen wir uns die getrennten Address- und ContactDAOs
	- stattdessen nehmen wir das bestehende Objekt aus der Abfrage und ändern nur den gewünschten Wert
	- z.B. office.getContact().setMail("neue Mail");
	
	
Logik für die Service-Schicht
- Verknüpfungen löschen bei delete