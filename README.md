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

To-Do
- ActorDAOs, RegistrationDAO und OfficeDAO vervollständigen (siehe DAO Klassendiagramm und UserDAO) -- Jan

- ManytoMany-Verknüpfung ausprobieren

- Unit-Test in einzelne Methoden aufteilen (?)
	- geht teilweise schlecht weil die Tests nicht der Reihe nach, sondern zufällig ausgeführt werden


Anmerkungen
- Case-Status bei Anzeige der Fälle filtern (Schon in den Rechten oder später?) --> in der Service-Darstellung geändert (noch nicht sicher)
- Idee: SaveOrUpdate-Funktion (ausprobieren, testen)
- versch. Methodennamen für die getUserx-Methoden werden nicht mehr gebraucht, da bidirektionaler Zugriff funktioniert

- EAGER für OnetoOne, Lazy für große Abfragen !!!
	- FEHLER IST BEHOBEN, NACH STUNDENLANGER SUCHE, FUCK YEAH
	- Die Lösung ist in der CaseDAO zu bewundern
	
- Flush-Error bei OneToMany und ManyToOne ist behoben
	- eine bestimmte Reihenfolge muss eingehalten werden, damit alles funktioniert
	- wie das aussehen kann/muss, könnt ihr euch im DoctorOfficeCRUDTest ansehen
	
- Die if (xxx =! null) - Logik in der Persistenzschicht kann weg, wenn wir immer das Prinzip nutzen, was den Flush-Error vermeidet
	- die if-Logik kann dann in die Service-Schicht, wo sie ja eigentlich "hingehört"