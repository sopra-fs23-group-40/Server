
## Week 1

### Karoline Siarsky

Are small tasks: https://github.com/sopra-fs23-group-40/Server/issues/31, https://github.com/sopra-fs23-group-40/Client/issues/4, https://github.com/sopra-fs23-group-40/Client/issues/7

https://github.com/sopra-fs23-group-40/Server/issues/32

https://github.com/sopra-fs23-group-40/Server/issues/33

https://github.com/sopra-fs23-group-40/Client/issues/18

### Thomas Stoller 

https://github.com/sopra-fs23-group-40/Server/issues/39

https://github.com/sopra-fs23-group-40/Client/issues/8

### Paul Grünenwald

Create all 21 subclasses of the Block Superclass [#40](https://github.com/sopra-fs23-group-40/Server/issues/40)

Implement the function to rotate/flip a block [#35](https://github.com/sopra-fs23-group-40/Server/issues/35)

### Thomas Joos

Added menu button on the overview view and closed 3 issues:

https://github.com/sopra-fs23-group-40/Client/issues/14

https://github.com/sopra-fs23-group-40/Client/issues/15

https://github.com/sopra-fs23-group-40/Client/issues/16

### Justin Verhoek

https://github.com/sopra-fs23-group-40/Server/issues/37

https://github.com/sopra-fs23-group-40/Server/issues/36

https://github.com/sopra-fs23-group-40/Server/issues/41

## Week 2

### Karoline Siarsky
https://github.com/sopra-fs23-group-40/Server/issues/53

https://github.com/sopra-fs23-group-40/Server/issues/54

https://github.com/sopra-fs23-group-40/Client/issues/24

### Thomas Stoller 

https://github.com/sopra-fs23-group-40/Server/issues/2

which includes : https://github.com/sopra-fs23-group-40/Client/issues/20
https://github.com/sopra-fs23-group-40/Client/issues/21
https://github.com/sopra-fs23-group-40/Client/issues/22
https://github.com/sopra-fs23-group-40/Client/issues/23

### Paul Grünenwald

In the Player class generate all blocks and give them the status corresponding with the player [#44](https://github.com/sopra-fs23-group-40/Server/issues/44)

Implement Player Class [#42](https://github.com/sopra-fs23-group-40/Server/issues/42)

When placing a block remove the block from the inventory [#46](https://github.com/sopra-fs23-group-40/Server/issues/46)



### Thomas Joos

Added logout button in the overview menu:
https://github.com/sopra-fs23-group-40/Client/issues/17

Closed the User Story for the Menu in the top right corner as all subtasks are now completed:
https://github.com/sopra-fs23-group-40/Server/issues/34

Added the Rules view to display the Game's Rules as they are in the Mockups:
https://github.com/sopra-fs23-group-40/Client/issues/19 <br>
_(As in the Mockups, we have a separate page for the Rules instead of a box)_

Implemented the createLobby REST Endpoint, closing issue 38:
https://github.com/sopra-fs23-group-40/Server/issues/38

Added functionality to list all waiting lobbies in the client (but it is not yet clickable, thus not completed):
https://github.com/sopra-fs23-group-40/Client/issues/3

### Justin Verhoek

https://github.com/sopra-fs23-group-40/Server/issues/41

https://github.com/sopra-fs23-group-40/Server/issues/45

## Week 3

### Karoline Siarsky

Added PlayerGetDTO and implemented getPlayers() endpoint to get all players in a game by given gameID: https://github.com/sopra-fs23-group-40/Server/issues/63

Implemented /joinLobby endpoint and added joinLobby method in LobbyService: https://github.com/sopra-fs23-group-40/Server/issues/57

Added LobbyPutDTO: [f5c8253](https://github.com/sopra-fs23-group-40/Server/commit/f5c82536b41045e451c21a9e9129ef6f85d5a211)

Closed issue adding Locks to Overview with: [a3c363d](https://github.com/sopra-fs23-group-40/Client/commit/a3c363df6bca36b661b8d5785bdab97b6826ebcd)

Added JoinPrivateLobby View: [53c3885](https://github.com/sopra-fs23-group-40/Client/commit/53c3885b9cf776743e1be8dc134cd73cc5a794ca) and Closed with https://github.com/sopra-fs23-group-40/Server/issues/30

Added joinPublicLobby method in Overview and get redirected directly to the public lobby: [738423c](https://github.com/sopra-fs23-group-40/Client/commit/738423c877c627072d0793ea3a5144961aba0b22)

Added startGame() function in Lobby.js and get redirected to game: [76112f5](https://github.com/sopra-fs23-group-40/Client/commit/76112f5d28fb7c901393139648bd0c105a7e55ef)

Used the external API for the Avatars in Lobby and on the Profile Page: https://github.com/sopra-fs23-group-40/Client/issues/30

Closed createGame() in GameController method with: [14688f0](https://github.com/sopra-fs23-group-40/Server/commit/14688f046fd056ad9e17acd13d6586d3b23a06b5)

### Thomas Stoller 

https://github.com/sopra-fs23-group-40/Server/issues/67

which includes Tasks: https://github.com/sopra-fs23-group-40/Client/issues/27, https://github.com/sopra-fs23-group-40/Client/issues/28, https://github.com/sopra-fs23-group-40/Server/issues/70, https://github.com/sopra-fs23-group-40/Server/issues/71, https://github.com/sopra-fs23-group-40/Server/issues/72

Server:
Added findByLobbyId in LobbyRepository
Added delete lobby and check host Rest points in LobbyController and the corresponding services in LobbyService
[c7c6201](https://github.com/sopra-fs23-group-40/Server/commit/c7c6201d07e3c1c5914c243c78e1124487fb8993)

Added playerlist to Lobby Entity & LobbyGetDTO.
Added getLobby restpoint & service.
[8f9cc0b](https://github.com/sopra-fs23-group-40/Server/commit/8f9cc0b8f4b62b079072b8f134fa73c22e0d3921)

Added in build.gradle: implementation 'org.springframework.boot:spring-boot-starter-webflux' & testImplementation 'org.springframework.boot:spring-boot-starter-test'
Added SSE to LobbyController and to leave and join methods.
Added LobbyHandler for the SSE
Added SSE for the SSE functions
[ca346b2](https://github.com/sopra-fs23-group-40/Server/commit/ca346b28f18b60d7d9e77cf01672c2bbb1a54518)

Client:
Added leavelobby/deletelobby in Lobby.js
[b58388b](https://github.com/sopra-fs23-group-40/Client/commit/b58388b9365f971ea3396084ee38a02007809107)

Added Eventsource
[55041a9](https://github.com/sopra-fs23-group-40/Client/commit/55041a9a45701c8e54883b098798a28faace40f3)

Changed eventsource URL (added baseURL to also make it work on GoogleCloud
made token and username 'global' since we use it a lot.
Added 2 different reactions to Events.
[fa1c455](https://github.com/sopra-fs23-group-40/Client/commit/fa1c45525d22bba8291684135c30eaa69a6eed65)




### Paul Grünenwald

Implement POST mapping to create a game [#55](https://github.com/sopra-fs23-group-40/Server/issues/55) 

Implement POST mapping to add player to a game  [#56](https://github.com/sopra-fs23-group-40/Server/issues/56) 

Implement GET mapping for a player's inventory  [#59](https://github.com/sopra-fs23-group-40/Server/issues/59) 

Implement PUT mapping to place a block  [#60](https://github.com/sopra-fs23-group-40/Server/issues/60) 




### Thomas Joos

Added random tips in the Lobby View:
https://github.com/sopra-fs23-group-40/Server/issues/48, https://github.com/sopra-fs23-group-40/Server/issues/3

Added refresh button to the Lobby Overview:
https://github.com/sopra-fs23-group-40/Client/issues/26

Added an inventory which shows the Player's blocks: 
https://github.com/sopra-fs23-group-40/Client/issues/25

Added the functionality to pick up a block from the inventory: 
https://github.com/sopra-fs23-group-40/Client/issues/9
(functionality added, not graphically)

Created the functionality to place a block (in the client):
https://github.com/sopra-fs23-group-40/Client/issues/13

Removing a block from the inventory once it has been placed:
https://github.com/sopra-fs23-group-40/Client/issues/29

Updated some of the Server's REST Endpoints and functionality to make it work together with the client (for tasks above in the client), commits [de4f441](https://github.com/sopra-fs23-group-40/Server/commit/de4f4419fb6f2844574518325a7422eb5cdaa745), [63bef12](https://github.com/sopra-fs23-group-40/Server/commit/63bef120b4b160fec6111a0388db724102f5c2f9), [30347f2](https://github.com/sopra-fs23-group-40/Server/commit/30347f21bbb5c08f1a871a7d5694e47405106fe4), [53490d7](https://github.com/sopra-fs23-group-40/Server/commit/53490d7d38a7f4d41fb9a9f80cd76bc1eaad6f7b).

Updated some of the Client's layout for the LobbyView, commits [1da46f3](https://github.com/sopra-fs23-group-40/Client/commit/1da46f3a0571911c5ff8a26a65e0277679e4e094), [fe56fd4](https://github.com/sopra-fs23-group-40/Client/commit/fe56fd4dc30d53cce1c9907fcad0884369aaa902), [bf77a09](https://github.com/sopra-fs23-group-40/Client/commit/bf77a09c9c0bf5c3ca1cb80147b0ccd5208be40a).

### Justin Verhoek

created the gameboard in the front end https://github.com/sopra-fs23-group-40/Server/issues/58

created test for the LobbyController https://github.com/sopra-fs23-group-40/Server/issues/64

## Week 4

### Karoline Siarsky
https://github.com/sopra-fs23-group-40/Server/issues/83

https://github.com/sopra-fs23-group-40/Client/issues/35

Added public/ private switch in lobby: https://github.com/sopra-fs23-group-40/Client/commit/6f140f3b54a3dc1047cf83a627a78e703a456687


### Thomas Stoller 

Added currentPlayers to lobby entity/repository
Updated LobbyGetDTO & DTOMapper
Added necessary commands in create, join and leave lobby to maintain the amount of players.
https://github.com/sopra-fs23-group-40/Server/issues/86
https://github.com/sopra-fs23-group-40/Server/issues/89

Display number of players in the lobby right now. (on overview screen)
https://github.com/sopra-fs23-group-40/Client/issues/34

Implemented a counter and display for the clock on the game screen. (not synchronized yet)
https://github.com/sopra-fs23-group-40/Client/issues/36
https://github.com/sopra-fs23-group-40/Client/issues/37

Implemented Stopwatch class to track the time of a game
Added creating a stopwatch in game & starting/stopping it.
https://github.com/sopra-fs23-group-40/Server/issues/94


### Paul Grünenwald

Add endpoint for rotating a block [#87](https://github.com/sopra-fs23-group-40/Server/issues/87)

Add endpoint for flipping a block [#88](https://github.com/sopra-fs23-group-40/Server/issues/88)

Added buttons to flip/rotate a block [#11](https://github.com/sopra-fs23-group-40/Client/issues/11)



### Thomas Joos
The inventory loads freshly from the server when a block is placed or the page is refreshed: https://github.com/sopra-fs23-group-40/Client/issues/31

The inventory's colors match the player's color: https://github.com/sopra-fs23-group-40/Client/issues/32

Once a player makes his move, the next player can play - nobody else. Double moves are not possible anymore. https://github.com/sopra-fs23-group-40/Server/issues/81

The client displays now what Player's turn it is: https://github.com/sopra-fs23-group-40/Server/issues/82

### Justin Verhoek

created tests for all LobbyController endpoints https://github.com/sopra-fs23-group-40/Server/issues/78

created all tests for GameService https://github.com/sopra-fs23-group-40/Server/issues/75

## Week 5

### Karoline Siarsky

https://github.com/sopra-fs23-group-40/Client/issues/42

https://github.com/sopra-fs23-group-40/Client/issues/43

https://github.com/sopra-fs23-group-40/Client/issues/44

### Thomas Stoller

Implemented endpoint to synchronize time of game with client.
https://github.com/sopra-fs23-group-40/Server/issues/95
Implemented the Game.endGame method to give back statistics about the game and updating them in GameController.
https://github.com/sopra-fs23-group-40/Server/issues/96
Implemented a Entity to give back statistics to the Gamecontroller.
https://github.com/sopra-fs23-group-40/Server/issues/102

Synchronized counter for every client and getting it from the endpoint on the server.
https://github.com/sopra-fs23-group-40/Client/issues/38


### Paul Grünenwald
Create tests for the Inventory Class [#98](https://github.com/sopra-fs23-group-40/Server/issues/98)

Create tests for the Block Class (rotating, flipping) [#100](https://github.com/sopra-fs23-group-40/Server/issues/100)
### Thomas Joos
When picking up a block, it is fixed to the cursor until the block is being placed or the player cancells the move: https://github.com/sopra-fs23-group-40/Client/issues/12

Added a background music ingame, in the lobby and sound effects for successful or unsuccessful block placement: https://github.com/sopra-fs23-group-40/Client/issues/40

Created a README.md for the Server and the Client, respectively: https://github.com/sopra-fs23-group-40/Server/issues/99

### Justin Verhoek
Created all tests for the LobbyService https://github.com/sopra-fs23-group-40/Server/issues/79

Started working on the tests for the GameController endpoints https://github.com/sopra-fs23-group-40/Server/issues/101

## Week 6

### Karoline Siarsky

Sadly, spent way too much time trying to fix bugs: 
https://github.com/sopra-fs23-group-40/Client/commit/50fdefe6724693e10eb24556c2f51d52e3abb164

https://github.com/sopra-fs23-group-40/Client/commit/1edb4c845b4cbd4c916aa21b851e0b41f500e864

Made it possible to leave a game: https://github.com/orgs/sopra-fs23-group-40/projects/1/views/1?pane=issue&itemId=28594495

### Thomas Stoller 

Got rid of everything SSE related => eventsource, setting up eventsource and using it. => Introduced polling in Overview, Lobby & Game.
https://github.com/sopra-fs23-group-40/Client/issues/51
https://github.com/sopra-fs23-group-40/Client/issues/52
https://github.com/sopra-fs23-group-40/Client/issues/53

Redirect players to overview screen if lobby is deleted.
https://github.com/sopra-fs23-group-40/Client/issues/54

Redirect players to game screen if the lobby/game is started.
https://github.com/sopra-fs23-group-40/Client/issues/55

Updated Gameguard & Lobbyguard to check if the player is in the lobby or not. If not redirected to overview screen.
https://github.com/sopra-fs23-group-40/Client/issues/56
https://github.com/sopra-fs23-group-40/Client/issues/57

### Paul Grünenwald

Display a pop-up upon entering the game where it is explained how to place/flip/rotate a block [#49](https://github.com/sopra-fs23-group-40/Client/issues/49)

Show spinner when game is loading [#50](https://github.com/sopra-fs23-group-40/Client/issues/50)

Implement a countdown that switches to the next player after 30 secs of not placing any block [#104](https://github.com/sopra-fs23-group-40/Server/issues/104)


### Thomas Joos
Closed https://github.com/sopra-fs23-group-40/Client/issues/41

Closed https://github.com/sopra-fs23-group-40/Server/issues/80

Worked together with Karo on https://github.com/sopra-fs23-group-40/Client/issues/11

### Justin Verhoek
small bug fix and creation of test for canPlacePiece method https://github.com/sopra-fs23-group-40/Server/issues/41

Created an effect to change the button-colors to one of the 4 colors when hovering them https://github.com/sopra-fs23-group-40/Client/issues/47

Created the tests for the GameController endpoints https://github.com/sopra-fs23-group-40/Server/issues/101


## Week 7

### Karoline Siarsky
Bug fixes:

[#60](/https://github.com/sopra-fs23-group-40/Client/issues/60) (Bug Fix regarding disappearing inventory)

[Fixed bug in GameController and GameBoard](https://github.com/sopra-fs23-group-40/Server/commit/b56b14b14c2a930a7a79d7c81c18e1b9a7c9331e)

[Changed placeBlock method (passing shape along and not rotated/flipped anymore)](https://github.com/sopra-fs23-group-40/Server/commit/7f0ad6383144b556abbaad02208c859cf8efa2d1)

[Fixed bug regarding Interval (Interval didn't get cleared after leaving the game)](https://github.com/sopra-fs23-group-40/Client/commit/921fc8ba067e6d4f2b0adffc6506575abd00196e)

[Added Alert if Move invalid](https://github.com/sopra-fs23-group-40/Client/commit/03ded237c37b7aa174efa53cb0d97f5c9b981cfb)

[Changed Rules View](https://github.com/sopra-fs23-group-40/Client/commit/09446f135430376909c2e96280b544b14d824fad)

[Added "Resign" and "Rules" PopUp In-Game](https://github.com/sopra-fs23-group-40/Client/commit/74222748efd48771e8ad2e802f707fa083e12609)

### Thomas Stoller 
Remove unused Endpoints & Corresponding Tests in Gamecontroller(-Test).
https://github.com/sopra-fs23-group-40/Server/issues/119

Delete Game if every player left it.
https://github.com/sopra-fs23-group-40/Server/issues/120

Taking gametime after player leaves to update statistics then.
https://github.com/sopra-fs23-group-40/Server/issues/121

Only let players place their first block in their corresponding corner.
https://github.com/sopra-fs23-group-40/Server/issues/122

Removed host after game starts => if host leaves, can create a new lobby.
https://github.com/sopra-fs23-group-40/Server/issues/125

### Paul Grünenwald

Design update [#46](https://github.com/sopra-fs23-group-40/Client/issues/46)

Debugging (way too much time for little) [#46](https://github.com/sopra-fs23-group-40/Client/issues/46) [#104](https://github.com/sopra-fs23-group-40/Server/issues/104)

### Thomas Joos

Fixed a bug in the inventory (client): https://github.com/sopra-fs23-group-40/Client/issues/62

Fixed a bug in the lobby overview: https://github.com/sopra-fs23-group-40/Client/issues/61

Updated the game over screen to show the avatars from the external API and the remaining tiles (not blocks): https://github.com/sopra-fs23-group-40/Server/issues/124

### Justin Verhoek

Fixed an issue where a long username created a mess in the lobby overview https://github.com/sopra-fs23-group-40/Client/issues/59

Created tests for the UserService https://github.com/sopra-fs23-group-40/Server/issues/114

Created tests for the UserController enpoints https://github.com/sopra-fs23-group-40/Server/issues/113

