package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.entity.GameEvent;
import ch.uzh.ifi.hase.soprafs23.entity.Lobby;
import ch.uzh.ifi.hase.soprafs23.entity.LobbyEvent;
import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.Inventory;
import ch.uzh.ifi.hase.soprafs23.game.Player;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import ch.uzh.ifi.hase.soprafs23.rest.dto.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import ch.uzh.ifi.hase.soprafs23.service.GameSSE;
import ch.uzh.ifi.hase.soprafs23.service.LobbyService;
import ch.uzh.ifi.hase.soprafs23.service.LobbySSE;
import ch.uzh.ifi.hase.soprafs23.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class GameController {
    private final GameService gameService;

    private final LobbyService lobbyService;

    private final UserService userService;

    private final LobbySSE lobbySse;

    private final GameSSE gameSSE;

    GameController(LobbyService lobbyService, UserService userService, GameService gameService, GameSSE gameSSE, LobbySSE lobbySse) {
        this.lobbyService = lobbyService;
        this.userService = userService;
        this.gameService = gameService;
        this.lobbySse = lobbySse;
        this.gameSSE = gameSSE;
    }


    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createGame(@RequestBody long lobbyId, @RequestHeader(value = "token") String token, @RequestHeader(value = "username") String username) {
        UserAuthDTO userAuthDTO = DTOMapper.INSTANCE.convertVariablesToUserAuthDTO(username, token);
        userService.checkAuthentication(userAuthDTO.getUsername(), userAuthDTO.getToken());
        // gets the lobby from the lobbyService (check if lobby exists is already included)
        Lobby lobby = lobbyService.getLobby(lobbyId);

        if(lobby == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Lobby with gameId " + lobbyId + " not found!");

        // checks if the user is the host of the lobby
        if(!lobby.getHost().equals(username)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "The user is not the host of this lobby! Only the host can start the lobby.");
        }

        // checks if exactly 4 players are in the lobby
        if(lobby.getPlayerList().split(",").length != lobby.getMaxPlayers()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "The lobby doesn't have exactly 4 players!");
        }

        Game game = gameService.createGame();

        // adds the playerNames to the new game
        for(String playerName: lobby.getPlayerList().split(",")) {
            game.addPlayer(playerName);
        }

        lobbySse.send(new LobbyEvent("START," + game.getId(), lobbyId));
        // Return the ID of the newly created game
        return game.getId();
    }

    @GetMapping("/games/{gameId}/players")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<PlayerGetDTO> getPlayers(@PathVariable String gameId) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);
        if(game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Game with gameId " + gameId + " not found!");
        List<PlayerGetDTO> playerGetDTOS = new ArrayList<>();
        for(Player player: game.getPlayers()) {
            PlayerGetDTO playerGetDTO = new PlayerGetDTO(player.getPlayerName(), player.getPlayerId());
            playerGetDTOS.add(playerGetDTO);
        }
        return playerGetDTOS;
    }

    @PostMapping("/games/{gameId}/players")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String addPlayerToGame(@PathVariable String gameId, @RequestBody String playerName) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);
        if(game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Game with gameId " + gameId + " not found!");

        // Add the player to the game using the addPlayer method
        return game.addPlayer(playerName);
    }

    @GetMapping("/games/{gameId}/{username}/inventory")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BlockGetDTO> getPlayerInventory(@PathVariable String gameId, @PathVariable String username) {

        // Retrieve the player object from the game by gameId and playerId
        Game game = gameService.getGameById(gameId);
        if(game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Game with gameId " + gameId + " not found!");
        Player player = game.getPlayerByUsername(username);
        if(player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Player with username " + username + " not found!");

        // Create a new list of BlockGetDTO objects based on the player's blocks
        List<BlockGetDTO> blockGetDTOs = new ArrayList<>();
        for (Block block : player.getInventory().getBlocks()) {
            BlockGetDTO blockGetDTO = new BlockGetDTO(block.getBlockName(), block.getLength(), block.getHeight(), block.getShape());
            blockGetDTOs.add(blockGetDTO);
        }

        // Return the list of BlockGetDTO objects
        return blockGetDTOs;
    }


    @GetMapping("/games/{gameId}/status")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CellStatus[][] getGameBoard(@PathVariable String gameId) {

        // Retrieve the player object from the game by gameId and playerId
        Game game = gameService.getGameById(gameId);
        if(game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Game with gameId " + gameId + " not found!");
        GameBoard gameBoard = game.getGameBoard();

        // Create GameGetDTO
        GameBoardGetDTO gameBoardGetDTO = new GameBoardGetDTO(gameBoard);

        return gameBoardGetDTO.getGameBoard();
    }

    @PutMapping("/games/{gameId}/{username}/move")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void placeBlock(@PathVariable String gameId, @PathVariable String username, @RequestBody BlockPlaceDTO blockPlaceDTO) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);
        if(game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Game with gameId " + gameId + " not found!");

        Player player = game.getPlayerByUsername(username);
        if(player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Player with username " + username + " not found!\n" +
                "Players in game " + gameId + ": " + Arrays.stream(game.getPlayers()).map(Player::getPlayerName));

        Inventory inventory = player.getInventory();
        GameBoard gameBoard = game.getGameBoard();
        Block block = inventory.getBlockByBlockName(blockPlaceDTO.getBlockName());
        if(block == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Block with blockName " + blockPlaceDTO.getBlockName() + " not found!\n" +
                        "Possible blocks (from " + username + "'s inventory):\n"
                        + inventory.getBlocks());

        // Check whether move is valid
        if (!gameBoard.canPlacePiece(blockPlaceDTO.getRow(), blockPlaceDTO.getColumn(), block)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid move");
        }

        // Remove block from inventory and add it to gameBoard
        inventory.removeBlock(block);
        gameBoard.placeBlock(player, blockPlaceDTO.getRow(), blockPlaceDTO.getColumn(), block);
        gameSSE.send(new GameEvent("MOVE", gameId));
    }

    @PutMapping("/games/{gameId}/{username}/vertical_flip")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void flipVerticalBlock(@PathVariable String gameId, @PathVariable String username, @RequestBody BlockFlipDTO blockFlipDTO) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);
        if(game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Game with gameId " + gameId + " not found!");

        Player player = game.getPlayerByUsername(username);
        if(player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Player with username " + username + " not found!\n" +
                        "Players in game " + gameId + ": " + Arrays.stream(game.getPlayers()).map(Player::getPlayerName));

        Inventory inventory = player.getInventory();
        GameBoard gameBoard = game.getGameBoard();
        Block block = inventory.getBlockByBlockName(blockFlipDTO.getBlockName());
        if(block == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Block with blockName " + blockFlipDTO.getBlockName() + " not found!\n" +
                        "Possible blocks (from " + username + "'s inventory):\n"
                        + inventory.getBlocks());


        block.flipVertical();

    }

    @PutMapping("/games/{gameId}/{username}/horizontal_flip")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void flipHorizontalBlock(@PathVariable String gameId, @PathVariable String username, @RequestBody BlockFlipDTO blockFlipDTO) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);
        if(game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Game with gameId " + gameId + " not found!");

        Player player = game.getPlayerByUsername(username);
        if(player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Player with username " + username + " not found!\n" +
                        "Players in game " + gameId + ": " + Arrays.stream(game.getPlayers()).map(Player::getPlayerName));

        Inventory inventory = player.getInventory();
        GameBoard gameBoard = game.getGameBoard();
        Block block = inventory.getBlockByBlockName(blockFlipDTO.getBlockName());
        if(block == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Block with blockName " + blockFlipDTO.getBlockName() + " not found!\n" +
                        "Possible blocks (from " + username + "'s inventory):\n"
                        + inventory.getBlocks());


        block.flipHorizontal();

    }

    @PutMapping("/games/{gameId}/{username}/rotate")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void rotateBlock(@PathVariable String gameId, @PathVariable String username, @RequestBody BlockRotateDTO blockRotateDTO) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);
        if(game == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Game with gameId " + gameId + " not found!");

        Player player = game.getPlayerByUsername(username);
        if(player == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Player with username " + username + " not found!\n" +
                        "Players in game " + gameId + ": " + Arrays.stream(game.getPlayers()).map(Player::getPlayerName));

        Inventory inventory = player.getInventory();
        GameBoard gameBoard = game.getGameBoard();
        Block block = inventory.getBlockByBlockName(blockRotateDTO.getBlockName());
        if(block == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                "Block with blockName " + blockRotateDTO.getBlockName() + " not found!\n" +
                        "Possible blocks (from " + username + "'s inventory):\n"
                        + inventory.getBlocks());


        // Rotate block
        block.rotateClockwise();
        }

}
