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


To-Do
- ActorDAOs und OfficeDAO anlegen, RegistrationDAO vervollständigen (siehe DAO Klassendiagramm und UserDAO) -- Jan

- ManytoMany-Verknüpfung ausprobieren

- Unit-Test in einzelne Methoden aufteilen (?)




Anmerkungen
- Case-Status bei Anzeige der Fälle filtern (Schon in den Rechten oder später?) --> in der Service-Darstellung geändert (noch nicht sicher)
- Idee: SaveOrUpdate-Funktion (ausprobieren, testen)
- versch. Methodennamen für die getUserx-Methoden werden nicht mehr gebraucht, da bidirektionaler Zugriff funktioniert
- EAGER für OnetoOne, Lazy für große Abfragen !!!
	- FEHLER IST BEHOBEN, NACH STUNDENLANGE SUCHE, FUCK YEAH
	- Die Lösung ist in der CaseDAO zu bewundern