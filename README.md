# Patientenportal

**Automatische Tabellen-Generierung f�r Hibernate ist in der Konfig momentan deaktiviert!**  
  --> einfach wieder bei Bedarf einkommentieren!


Nicht im Development-Branch arbeiten!

- Wenn ihr mit Programmieren startet, checked euren Branch und merged dann von "Development"
- Nach dem Commit und Push in eurem eigenen Branch checked ihr Development und merged von dort dann euren eigenen Branch (da sollte m�glichst alles funktionieren dann)

## Fortschrittsstand

Fertig und �berpr�ft:
DAO	-	(bis auf die Interfaces) User, Patient, Doctor, Relative, Registration, Office, Address, Contact, Insurance, Rights, Medication, Medicine, VitalData
WS	-	

Fertig:
DAO	- InstructionalDoc und MedicalDoc (inkl. Tests) + Case-Verkn�pfung
WS	- RegistrationWS, AccountWS, PatientWS, RelativeWS, DoctorWS, OfficeWS, AddressWS, ContactWS (getestet, token/rolecheck fehlt noch)

in Arbeit:
DAO	-	finale Pr�fung (z.B. criteria anpassen)
WS	-	

Fehlt noch:
WS	-	restliche ws


## Dokumentation

06.06. (Stefan)
- Entities (User, Doctor, Patient, Address, Contact) angelegt, fehlende Verkn�pfungen sind auskommentiert

07.06. (Jan/Stefan)
- UserCRUDTest angelegt (Kompletter Unit-Test des UserDAOs avisiert)
- Bidirektional-Test angelegt (funktioniert alles)
- RegistrationDAO angelegt f�r Actor-Create-Methoden
- Entities (Relative, Office, Insurance) angelegt, fehlende Verkn�pfungen sind auskommentiert

- UserDAO fertig (UpdateAddress, UpdateContact hinzugef�gt)
- UserCRUDTest fertig (funktioniert alles)

- Entities (Case) angelegt, fehlende Verkn�pfungen sind auskommentiert
- CaseCRUDTest angelegt (Kompletter Unit-Test des CaseDAOs avisiert)
- Testing-Suite eingerichtet (Test k�nnen jetzt alle zusammen laufen)
- Ordner-Struktur ge�ndert (vor allem bei Tests) / kleine Tests zum "Probieren" kommen jetzT in die "Spielwiese"

08.06. (Stefan)
- OfficeDAO angefangen
- DoctorOfficeCRUDTest angefangen

14.06. (Stefan/Jan)
- User/Doctor/Relative/Patient/Address/Contact DAOs bearbeitet
- Tests weiterentwickelt
- ManyToMany bei Patient-Relative gestestet (funktioniert super)

15.06. (Stefan)
- Try/Catch/Finally - Bl�cke in die Tests eingebaut (noch nicht fertig)
- Struktur der DAOs leicht ge�ndert
- Criteria impolementiert und getestet (siehe Spielwiese.smalltests)
- CheckUsername zur RegistrationDAO hinzugef�gt und UserCRUDTest um checkUsername erweitert --> funktioniert alles

16.06.(Jan)
- RelativeDAO Update und Delete eingef�gt
- PatientDAO Update und Delete eingef�gt
- braucht man bei Contact ein create oder wird die dann in die Service schicht geschrieben da es ja �ber den User l�uft

19.06. (Jan/Stefan)

- Bidirektionaler Zugriff optimiert (muss noch auf alle bestehenden Klassen angewendet werden)
	- Join-Column auf der "owning-side" und MappedBy - Attribut auf der Gegenseite
- Criteria (Rechte, checkusername)
- Rechte fertig, RechteTest fertig, AccessTest angelegt und fertig
- ActorDAOs und RegistrationDAO fertig (bis auf Patient)
- InsuranceDAO und Test fast fertig

20.6 (Jan)
- MDOC, InstructionDoc und Doc als Supperklasse angelegt + zugeh�rige DAOs f�r MedicalDOc und InstructionDoc
- f�r Dokument kein DAO da reine Entity f�r Vererbung 

22.06. (Stefan)
- mapped bei bei den user-actor-verkn�pfungen eingef�hrt, au�erdem bei doctor-office
	- war auch n�tig, da der WS sonst fehler wirft
- Patient-Case eingef�gt
- MedicalDocDAO vervollst�ndigt (beim update ggf. noch Patient hinzuf�gen, try-Bl�cke fehlen noch)
- Mdoc zu case und patient eingef�gt
- MDoc-Test angelegt ( bis auf die Case-Verkn�pfung klappt alles)

23.06 (Stefan / nur auf Tablet eingef�gt, noch nicht getestet)
- case-doctor-Verkn�pfung angelegt (noch kein Test geschrieben)
- vitaldata-case-Verkn�pfung ge�ndert (Timestamp noch anschauen!), caseTest amge�asst

26.06.
- QM bei allen bestehenden DAOs (try-catch-Bl�che eingef�gt und Logik �berpr�ft)
- Entities Medication und Medicine angelegt, Verkn�pfung zum Case (DAOs und Test fehlen noch)

30.06-1.07 Jan 
- MedicationDAo mit Try Catch Bl�cken eingef�gt 
- MDoc DAO erg�nzt um TryCatch Bl�cke
- Insurance Test funktioniert
- Vitaldata Test ist bisher in Case enthalten so kann es einzeln gepr�ft werden...k�nnen wir aber auch rausnehemen, m�ssen wir mal dr�ber sprechen
- L�sung gesucht f�r die Hibernate Superklasse nennt sich union subclass bei hibernate aber kein beispiel gefunden bzw. nicht ganz verstanden aber die L�sung ist die richtige

01.07. (alle)
- Medication/Medicine inkl. Tests fertig und gepr�ft
- Alle Tests angepasst, sodass die ID unabh�ngig verwendbar ist (Suite funktioniert wieder)
- Dokumenten-Test
- WS angefangen

05.07 Jan
- Verbindung zu FTTP Server (Filezilla)
- InDoc und MedicalDoc Entity erg�nzt
- die DAOs entsprechend erg�nzt
- FTP methoden allgmein geschrieben dann extra Klassen gemacht f�r die Methoden bez�glich der FtpMethoden f�r InstructionDocs und MDocs
- Tests zu beiden geschrieben
- Tests laufen durch (testsuite funktioniert auch noch)
- Problem mit Download gel�st 
- durch das Attribut FileType k�nnen nur verschiedene Dateien abgespeichert werden

06.07 Jascha
- AuthenticationWS l�uft - jedoch immer erst richtig nach dem 2. Mal - kp. warum
 hierf�r wurde u.a. UserDao und hibernate.cfg angepasst
- Libary Ordner entfernt --> werden nur noch �ber Maven gemanaged!
- alles erfolgreich gemerged

07.07. Jascha
* Alles erfolgreich gemerged, Gender-Enum eingef�gt und alles entsprechend angepasst

bis 09.07. (alle)
- DAOs und Case-Verkn�pfungen weitgehend Fertig
	- noch testen und dann abschlie�en
- WS bis zu den Actors fertig
- Token / Authentication / Authorization / Login / Logout funktioniert
- DateiServerZugriff funktioniert (bei Jan, hier vllt noch eine Anleitung einf�gen)

## To-Do
- Deprecated Criterias anpassen (UserDAO, WebSessionDAO, ...?)

- Superklasse f�r die Dokumente wieder einf�gen?
- Superklasse f�r die ListResponse-Entities

- Token- und Role-Check (soweit n�tig) in die Methoden einf�ge
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
	- L�uft darauf hinaus, dass wir alle (!) Case-Verkn�pfungen individuell anlegen und bei Case myppedBy angeben
	- noch nicht alles angelegt

- getCases (patientID) in der DAO vs. PatientDAO.getCases(); + Rechteabgleich in der Service-Schicht?!

- Case-Status bei Anzeige der F�lle filtern (Schon in den Rechten oder sp�ter?) --> in der Service-Darstellung ge�ndert (noch nicht sicher)
- Idee: SaveOrUpdate-Funktion (ausprobieren, testen)

- versch. Methodennamen f�r die getUserx-Methoden werden nicht mehr gebraucht, da bidirektionaler Zugriff funktioniert
	- gleiches gilt f�r die getDoctorsByx, etc. Methoden, das geht durch Eager/Init - Zugriff (macht eigentlich mehr Sinn)

- EAGER f�r OnetoOne, Lazy f�r gro�e Abfragen !!!
	- FEHLER IST BEHOBEN, NACH STUNDENLANGER SUCHE, FUCK YEAH
	- Die L�sung ist in der CaseDAO zu bewundern
	- im Prinzip k�nnen wir die ganzen EAGER-Verbindungen auch lazy machen und mit Hibernate-Initialize arbeiten (insofern sinnvoll)
	
- Flush-Error bei OneToMany und ManyToOne ist behoben
	- eine bestimmte Reihenfolge muss eingehalten werden, damit alles funktioniert
	- wie das aussehen kann/muss, k�nnt ihr euch im DoctorOfficeCRUDTest ansehen
	- Grundlegend: Referenzierte Entities erst anlegen und dann verkn�pfen (au�er cascadetype.all und und owning-Klassen)
	
- Die if (xxx =! null) - Logik in der Persistenzschicht kann weg, wenn wir immer das Prinzip nutzen, was den Flush-Error vermeidet
	- d.h. wir rufen ein (vollst�ndiges) Objekt aus der DB ab, �ndern Werte und geben dann das dieses Objekt wieder zur�ck
	- die if-Logik kann dann in die Service-Schicht, wo sie ja eigentlich "hingeh�rt"
	
- Das gleiche Prinzip kann man vllt auch bei dem Schreiben von Adreessen �ber das Office angewendet werden (funktioniert noch nicht)
	- Dadurch sparen wir uns die getrennten Address- und ContactDAOs
	- stattdessen nehmen wir das bestehende Objekt aus der Abfrage und �ndern nur den gew�nschten Wert
	- z.B. office.getContact().setMail("neue Mail");

- WS-Logik!!
	- Listen werden nicht von JAXB verstande
	- L�sung 1: @SOAPBinding(style = Style.RPC) zu Style.Document �ndern, dann m�ssen wir aber auch ein entsprechendes Doc erstelle
	- L�sung 2: @SOAPBinding auskommentieren und die @Xml... mappings benutzen --> das wirds wahrscheinlich, muss ich noch teste
- gel�st durch @XmlTransient-Annotation bei unn�tigen Verkn�pfungen (Data-Input bleibt durch entsprechende Methoden erhalten

- MAPPEDBY f�r Bidirektionale Beziehungen benutzen!!!!!! Siehe Patient-Insurance

- Tests funktionierten nur individuell, aber nicht in der Suite (gel�st)
	- liegt daran, dass z.B. mehrere Tests einen Case anlegen und damit arbeiten (beim 2. Test passt die ID dann nicht mehr)
	- entweder L�sung in der Unit-Logik finden, Tests zusammenfassen oder einfach ohne Suite arbeiten (w�re schade)
	- beheben wir durch den ID-Abruf aus dem Objekt vom Anlegen

## Logik f�r die Service-Schicht
- Verkn�pfungen l�schen bei delete
- Keine eigene DAO-Abfrage f�r Case-Status, lieber "aussortieren" in der WS-Logik?
- Bei Create-Case auch dem erstellenden Doktor Lese-/Schreibrechte mitgeben

## Ausblick

- DAO - Interfaces und non-static DAO
- Token-Authentication �ber HTTP Heade
- Response-Entities verbessern

