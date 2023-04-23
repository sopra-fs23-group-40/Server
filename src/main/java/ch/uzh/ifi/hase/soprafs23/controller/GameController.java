package ch.uzh.ifi.hase.soprafs23.controller;

import ch.uzh.ifi.hase.soprafs23.game.Game;
import ch.uzh.ifi.hase.soprafs23.rest.dto.GameGetDTO;
import org.springframework.web.bind.annotation.*;
import ch.uzh.ifi.hase.soprafs23.rest.mapper.DTOMapper;
import org.springframework.http.HttpStatus;
import ch.uzh.ifi.hase.soprafs23.service.GameService;


@RestController
public class GameController {

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
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ResponseBody
    public void createGame() {
        Game game = new Game();
    }
}
