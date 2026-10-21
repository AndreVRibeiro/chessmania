# Requirement Analysis: Chess Mania!

## Spielstart:

Das Spiel wird über das Command Line Window mit den folgenden Befehlen gestartet:

java -jar ChessMania!.jar server <listenport>
java -jar ChessMania!.jar client <serverip>:<serverport>

Beim Aufruf wird man nach seinem Nickname gefragt “Enter Nickname”.
Ein Spielername wird anhand des Systemnamens vorgeschlagen (evtl. mittels popup Fenster).

Danach kommt man in das Main Menü mit den folgenden Optionen:
- New Game
- Join Game
- Rules
- Settings
- Quit

#### Rules
Öffnet ein Fenster mit den Spielregeln.

#### Settings
Einstellungen zu: Spieldauer, Spielername ändern, evtl. Musik Lautstärke regeln.

#### Quit
Schliesst das Programm.

#### New Game
Erstellt ein Spielinstanz. Man wird in die Lobby weitergeleitet. Sobald sich vier Spieler in der Lobby befinden startet das Spiel.

#### Join Game
Man wird in die Lobby weitergeleitet. Sobald sich vier Spieler in der Lobby befinden startet das Spiel.

## Spielablauf

#### Spielfenster

Das GUI besteht aus:

- Spielbrett mit Steinen in aktueller Position. Das Spielbrett ist horizontal von links nach rechts mit den Buchstaben a-n und vertikal von unten nach oben mit den Ziffern 1-14 fix als Standardposition für Spieler 1 beschriftet. Das Spielbrett wird für die weiteren Spieler so gedreht, dass sie das Brett ebenfalls von unten betrachten. Die Beschriftung der Achsen wird entsprechend angepasst, so dass die Bezeichnung der Felder für alle Spieler identisch ist.
- Vier Kästchen je mit dem Name des Spielers, Farben und eigener verbleibender Spielzeit.
- Ein Chat-Fenster mit Eingabefeld und ‘Send Button’.
- Ein Fenster 'Player Moves' für die schriftliche Ausgabe von Zügen.
- Ein ‘Exit Button’ der das Spiel beendet.
- Ein ’Settings Button’
- Ein ‘Give Up’ Knopf für die Aufgabe des Spiels.


#### Bedienung

Im ‘Player Moves’ Fenster wird der aktuelle Spieler mit “(user)’s turn” identifiziert. Die Spieleruhr des Spielers wird gestartet.

Falls man am Zug ist, klickt man mit dem Mouse Pointer auf einen Stein um ihn auszuwählen. Der gewählte Stein wird visuell hervorgehoben. Danach klickt man auf einem Spielfeld um den Stein dorthin zu bewegen. Ein erneuter Klick auf die ausgewählte Figur hebt die Auswahl auf.

Ein Versuch, einen Stein auf eine unzulässige Position zu ziehen, gibt die Meldung "Illegal Move" im 'Player Moves' zurück.

Wenn ein Zug zu einem Schach führt, wird im 'Player Moves' “Check!” ausgegeben.
Wenn ein Zug zu einem Schachmatt führt, wird im 'Player Moves' “Check mate! Team (user1) & (user2) wins!” ausgegeben.
Falls die Zeit des Spielers abläuft, wird im 'Player Moves' “Time is up! Team (user1) & (user2) wins!” ausgegeben.

Nach dem Zug wird die Bewegung bzw. das Schlagen im 'Player Moves' Fenster schriftlich zusammengefasst: “Turm d1 >>> d4, schlägt Bauer”.

Beispielausgabe mit users “Nick” & “James”

*Nick’s turn:*

*Turm d1 >>> d4, schlägt Bauer*

*Check!*

*James’s turn:*

*Bauer b4 >>> c4*

Eventuell könnten witzige Kommentare implementiert werden?
“Woaah” “Nice move <user>!” “Things are heating up!” “Ouch, that must hurt” “Another one bites the dust” “Time is ticking away” usw…

Das Chat-Fenster ist jeder Zeit verfügbar.

