package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.GameBoard;
import ch.uzh.ifi.hase.soprafs23.game.Inventory;
import ch.uzh.ifi.hase.soprafs23.game.Player;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;
import ch.uzh.ifi.hase.soprafs23.rest.dto.BlockGetDTO;
import ch.uzh.ifi.hase.soprafs23.rest.dto.GameBoardGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import ch.uzh.ifi.hase.soprafs23.service.GameService;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;


@RestController
public class GameController {

    @Autowired
    private GameService gameService;

    /*
    @GetMapping("/{gameId)}")
    @ResponseStatus(HttpStatus.OK)
    public GameGetDTO getGameById(@PathVariable Long gameId) {
        Game game = GameService.getGameById(gameId);
        if (game == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Game not found");
        }
        return DTOMapper.INSTANCE.convertEntityToGameGetDTO(game);
    }
     */

    @PostMapping("/games")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String createGame() {
        // Create a new game using the GameService
        Game game = gameService.createGame();

        // Return the ID of the newly created game
        return game.getId();
    }

    @PostMapping("/games/{gameId}/players")
    @ResponseStatus(HttpStatus.CREATED)
    @ResponseBody
    public String addPlayerToGame(@PathVariable String gameId, @RequestBody String playerName) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);

        // Add the player to the game using the addPlayer method
        return game.addPlayer(playerName);
    }

    @GetMapping("/games/{gameId}/{playerId}/inventory")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public List<BlockGetDTO> getPlayerInventory(@PathVariable String gameId, @PathVariable String playerId) {

        // Retrieve the player object from the game by gameId and playerId
        Game game = gameService.getGameById(gameId);
        Player player = game.getPlayerById(playerId);

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
        GameBoard gameBoard = game.getGameBoard();

        // Create GameGetDTO
        GameBoardGetDTO gameBoardGetDTO = new GameBoardGetDTO(gameBoard);

        return gameBoardGetDTO.getGameBoard();
    }

    @PutMapping("/games/{gameId}/{playerId}/move")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public void placeBlock(@PathVariable String gameId, @PathVariable String playerId, String blockName, int row, int column) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);
        Player player = game.getPlayerById(playerId);
        Inventory inventory = player.getInventory();
        GameBoard gameBoard = game.getGameBoard();
        Block block = inventory.getBlockByBlockName(blockName);

        // Check whether move is valid
        if (!gameBoard.canPlacePiece(row, column, block)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid move");
        }

        // Remove block from inventory and add it to gameBoard
        inventory.removeBlock(block);
        gameBoard.placeBlock(player, row, column, block);
    }

    // TO DO: Flip Block

    // TO DO: Rotate Block

}
