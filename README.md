**Design**

Three main components of the project are the HTTP server, application package and the database.
The server has been provided by the course lecturer and contains all the code that deals with HTTP requests and responses.
The application package was developed to deal with the overall game logic and database access (queries).
Following “service” packages were taken into consideration:

- User
- Transaction
- Statistics
- Session
- Scoreboard
- Package
- Deck
- Card
- Battle

Based on the following “models”:

- Card
- Card Element (enum)
- Card Name (enum)
- Card Type (enum)
- Round
- Scoreboard
- Trade
- User


Each of the service packages contain a service file (that implements request handling with regards to the HTTP method and URL content) and the controller (that handles responses).

For the purpose of database access and modification, there is a separate Data Access Layer package with following repositories:

- User repository
- Card repository
- Scoreboard repository
- Battle repository

The “scoreboard” was developed separately from the “user” and the “stats” because it is a mix of both and pertains to all users.

The development was generally straight-forward, with an exception of the “battle”.
Since the battle is to begin automatically once the second player “enters”, it was interpreted as such: a player enters the “battle lobby”, checks if there is any other player in the lobby ready to battle. If not, first player is inserted as “player 1” and sends a response back that there is someone waiting. Second player enters the “lobby” and the battle is automatically started.
For those purposes a separate class, “Round” was developed, and it handles the cards of the players, taking the “specialties” into account when assigning damage.

Since two POST requests are sent together, i.e. the second POST request waits for the first one to send a response, the “battle” method was set to be “synchronized” – when not, threads can access the method simultaneously and that results in many “player 1”s in the lobby.

The database used is PostgreSQL and is integrated (directly installed) via IntelliJ IDE.

**Lessons learned**

The project was initially set up with separate Data Access Layer files for each service used in the project, which was later altered into more general Repositories, to keep it from being pure service oriented, as per suggestion from code review/presentation.

The work on the project started rather late, due to the workload of other courses which was, on one hand, stressful due to the approaching deadline but on the other hand, 90% of the time allocated for school related work was tightly packed into working on the project, meaning the concentration and commitment was not dispersed.

Code reviews (presentations) were gold-worth because the students have the chance to receive feedback and concrete help. The only issue was that the progress was very individual, so there was little of that in the first two months of the semester.

Unit testing

Tests for verifying the building requests and responses were omitted because that part of the code was already made available – not self-developed.

Other were developed mainly to test the models and repositories (which was self-developed).

User Tests - Instantiating a new user
Card Tests – Instantiating new cards with respect to their type/name
Round Tests – Instantiating new rounds with respect to card specialties
Scoreboard Tests – Instantiating new scoreboard with respect to the order of display (first users with higher ELO etc.)
Trades Tests – Instantiating a new trade with respect to trade requirements
User Repository Tests – verifying database access and modification with regards to user data
Scoreboard Repository Tests - verifying database access and modification with regards to statistical data


**Unique feature**

When two cards that are set against each other have the same damage, there is a 30% chance that either one or the other wins instantly. This only applies when the two cards are not drawn as last in either of the users’ decks, because the battle has 100 rounds; meaning the chance of that happening is then much higher, and the battle would nearly never end in a tie.

**Time spent (approximation)**

Initial setup = 4h
Database = 1h
User component = 7h
Sessions component = 5h
Packages component = 7h
Cards component = 3h
Unit of Work = 1h
Battle component = 21h
DAL to Repository migration = 6h
Debugging = 20h

Total = 75h (approx.)


**Link to GIT**

https://github.com/ana-m-v/SWEN1-mtcg
