**11.4.21: Recap of the past week (Mark and André)**
=====================================
**STILL A LOT TO DO**
This past week was very intense and tiring for the group. Being a group of only two brings certain challenges with it that we have to overcome. The majority of our time is spent on the project to ensure that we meet up the expectations of the milestones. But it is not easy.

The game logic of our chess game is working and the pieces can move according to the rules. Also, the Chat GUI is running and behaving according to plan. The needed documentation and other milestone achievements are also done. Nevertheless, there are still some bugs that need to be fixed as soon as possible. It's pretty clear to us that we won't be able to achieve the same results as fast as the other groups. But we won't let that demotivate us.

Even though we made good progress, we are still worried that all our effort will be for nothing. As said this journey has not been the easiest and time is definetely not on our side.



**11.4.21: Recap and further planning (Mark and André)**
=====================================
**STRESS AND MENTAL FATIGUE**
André wasnt available much this past week due to unfortunate circumstances and with one more week til deadline there is still a lot to do. We decided that Mark will try to implement the rest of the game logic and the Chat GUI while André will start doing all the documentation and finish up fixing some remaining bugs in his code. Overall the game logic is very extensive and in an almost finished state, but some extra features which were initially planned (like castling for example) will have to be postponed. Those features, although a likely improvement to the enjoyement of the game, weren't part of the set project goals and have to be cut in order to have a chance at meeting the deadline. André will also implement the required external library. We have chosen log4j which was recommended in the exercise slot.

The Lobby is done and the chat GUI development is currently paused in order to get the multiplayer running. Mark has set the deadline for this feature to this wednesday. After that he will try to implement the chat GUI.



**9.4.21: What I've learned so far (Mark)**
=====================================
**LOBBY FINISHED**

I'm quite happy with myself after "finishing" (so far ^^) one of the bigger parts of the project, the networking capabilities. I've learned a lot about how communication via a text-based network protocol can be handled. I also could practice a lot of the other more "advanced" topics from the "Erweiterte Grundlagen" lecture like threads and exceptions. After lobby chat working the next step will be the implementation of the chat into a GUI. 

The game's logic is still not working properly and once ready there's still a full list of multiplayer features to add. It is an overwhelming amount of work but I'm trying to stay optimistic. 

So far I've done a lot of research on JavaFX and am doing an extended tutorial which will help me design a chat GUI and, hopefully, already a board to play the game on. Currently the focus on completing the milestone achievements is an absolute priority though. A lot has to be done and working with half the man power than the rest of the groups is quite challenging in itself. 


**1.4.21: Update (André)**
=====================================
**GAME LOGIC**
As it was decided in the group meeting, I am responsible to keep developing the game logic. During these 5 days of eastern, we will try to do the majority of the work, so we don't get under time pressure. Because now that we are only a duo, we will need as much time as possible to ensure that this project is successful.
Before working on the game logic, I decided to fix some issues. I started by fixing the setName function, which unfortunately didn’t work correctly during the Milestone 2 presentation. Now the function is able to check all existing names and make the changes that are needed. Then I fixed a minor issue where the name wasn’t being displayed correctly in the chat after changing it.

Now that those issues were out of my way I began by identifying all the needed attributes and classes in order for the game to work. One of the first things I made was the Chessboard and its respective Squares. Once this was done I started to give the pieces their move logic. This turned out to be more complicated than expected but the pieces can now move according to the rules. A lot of methods and functions needed to be added in order to facilitate the functioning of the classes. I also added other classes like Move, Player and Game that are necessary for our game. Because of the complexity of chess in general and the number of rules that have to be implemented and checked during every single move, this took a lot of time to make and a lot of trial and error. I wasn't able to finish everything during these 5 days because of the amount of work and its complexity and also because of private responsibilities. Nonetheless, the majority of the game logic was done and only some bugs and additional functions had to be addressed.

**31.3.21: Meeting (Mark and André)**
=====================================
**UPDATE AND EASTER REDEMPTION PLAN**

After the deadline of milestone 2 on March 29th Marton decided to leave the project, so the ChessMania team only consists of André and Mark now. We have decided to keep the project going and give our best to make it succeed. After the presentation and the discussion with the tutors we have set up a plan for the easter days to get ahead of our heavy workload.

Until Monday 5th we want to accomplish the following things:

Mark has set up the first crucial classes of our game already and thought out how the logic will work. It's possible to create a new board and initialize all the pieces so we get the board status of how a new game should be set up when started. You can print out a simple version of the set up board on the console. What needs to be done is to implement the drafted basic move logic of the pieces and then additionally the special cases for the game. André has decided to take over and implement all those move rules. Also he's gonna finish up the bugged change name method from milestone 2. Mark will figure out how to implement the necessary Pinging functionality from milestone 2 as well and research and work on the demanded lobby and server functionality for the game. First GUI attempts should be checked out individually.


**27.03.21: Marton**
===========================
**MILESTONE 2 DEADINE APPROACHING FAST**

We have been working hard these last three days to try to cover as much ground as possible. Knowing that getting the server / client up and running is still
only step 0 to completing the achievements, it was extremely frustrating that we continually ran into issues with it. It took many hours of troubleshooting
and some help from Nikodem to finally get our system to work. In the meantime we decided it's best to split our efforts and finish the tasks that were not
directly connected with the server (QA Concept, Gitignore, Diary Update, Network Protokoll Definition & Documentation). We even started to look at programming
our chessboard & chess pieces when finally the server connection was functional enough that we could start implementing the Milestones. The encoding / decoding
process took some time to wrap our heads around but finally we believe to have a good system in place.


**25.03.21: Meeting (All)**
===========================
**DISCUSSING SPECIFIC PLAN TO MEET MILESTONE 2 REQUIREMENTS**

Unfortunately we started this meeting for Milestone 2 already quite behind schedule. Marton and André both had a challenging week leading up to today so
overall the Team wasn't as prepared as we should have been.

Today we received the additional bad news that Mirjam has decided to leave the project. This news took us all by surprise but we understood her decision.

The difficulty of our situation came also from the fact, that by now the complexity of the project has grown exponentially and if the lecture materials are
not fully understood it becomes increasingly difficult to maintain an overview.

After some discussion we agreed to dedicate the remaining days to meeting the milestone requirements - a straightforward choice given our situation. Mark
created a Trello board that gave us much needed transparency - we could see exactly what the teammates were working on.

Seeing that Milestone 2 is very focused on the Networking aspect of the game, we decided to prioritize getting our server - client connection up and running.


**15.03.21: Meeting (All)**
===========================
**BESPRECHUNG ENTWICKLUNGSSTAND MILESTONE 1**

Gibt es Probleme, braucht irgendjemand Hilfe?

- Besprechung Projektplan und Zuständigkeiten
  - Client-Side: Andre, Marton (Benutzeroberfläche, Interaktion)
  - Server-Side: Mirjam, Mark (Spielregeln, Status)
- Ausarbeitung Präsentationsstruktur
  - Einführung und Beschreibung des Ziels des Spieles und der zugehörigen Spielregeln (Mirjam)
  - Präsentation der Anforderungen an die zu entwickelnde Software, funktionale Anforderungen (Andre)
  - Festlegung der Schnittstellen zwischen Client und Server (Mark)
  - Projektplan und Zuständigkeiten (Mark)
  

**13.03.21: Meeting (All)**
===========================
**BESPRECHUNG KONKRETE ERWARTUNGEN FÜR MILESTONE 1**

**Erwartungen für Repo**
- Milestone 1: http://p9.dmi.unibas.ch/cs108/2021/
- Detaillierter Projektplan
- Analyseergebnis in textueller Form und in Präsentation
  Definition der Spielregeln
  Definition des Problems/Fragestellung
  Definition der Anforderungen: funktional und ggfs. nicht-funktional
  
  Festlegung der Schnittstellen zwischen Client und Server


**Erwartet für Präsentation:**

- Präsentation von 10min
- In der Präsentation werden folgende Details erwartet:
  - Beschreibung des Ziels des Spieles und der zugehörigen Spielregeln
  - Präsentation der Anforderungen an die zu entwickelnde Software
  - Festlegung der Funktionalität von Client und Server (welche Server-Funktionalität kann der Client aufrufen),noch ohne Beschreibung des Protokolls.
- Projektorganisation: Arbeitsplan/Balkendiagramm, Zuständigkeiten


**AUFGABEN HEUTE:**

- Statusupdate bei allen, Abklärung W1 Aufgaben bei allen ohne Probleme?
- Kommunikation: Kanäle festlegen
- Spielnamen Vorschläge: Chess Mania!
- kompletten Projektplan erstellen (Mark)
- Mockups/Bilder erstellen (Andre)
- Powerpoint Präsentation (Mirjam)
- Diary (Mark)
- Spiel Erklärung, Definition Spielregeln und Anforderungen (Spielernamen, game menu, chat, timer) (Marton)
- .gitignore file (Mark)
- Client Server Anforderungen (alle, MVC Architektur Link)
