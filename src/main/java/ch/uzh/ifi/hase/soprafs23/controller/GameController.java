package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.game.Player;
import ch.uzh.ifi.hase.soprafs23.rest.dto.GameGetDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import org.springframework.http.HttpStatus;
import ch.uzh.ifi.hase.soprafs23.service.GameService;


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
    public void addPlayerToGame(@PathVariable String gameId, @RequestBody String playerName) {
        // Retrieve the game with the given ID from the GameService
        Game game = gameService.getGameById(gameId);

        // Add the player to the game using the addPlayer method
        game.addPlayer(playerName);
    }

}
