package ch.uzh.ifi.hase.soprafs23.game;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import ch.uzh.ifi.hase.soprafs23.entity.GameStats;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;


public class Game {
    private final String gameId;
    private final GameBoard gameboard;
    private final Stopwatch stopwatch;
    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;

    public Game() {
        this.gameId = UUID.randomUUID().toString();
        this.gameboard = new GameBoard();

        this.stopwatch = new Stopwatch();
        stopwatch.start();

        players.add(new Player(CellStatus.PLAYER1, null));
        players.add(new Player(CellStatus.PLAYER2, null));
        players.add(new Player(CellStatus.PLAYER3, null));
        players.add(new Player(CellStatus.PLAYER4, null));
        this.currentPlayer = players.get(0);

    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public boolean checkPlayersTurn(Player player) {
        return currentPlayer == player;
    }

    public void nextPlayersTurn() {
        if (!checkGameOver()) {
            do {
                currentPlayer = players.get((players.indexOf(currentPlayer) + 1) % players.size());
            } while(!canPlaceBrick(currentPlayer));
        }
        else {
            endGame();
        }
    }

    public boolean canPlaceBrick(Player p) {
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                for (Block block : p.getInventory().getBlocks()) {
                    for (int flip = 0; flip < 1; flip++) {
                        for (int rot = 0; rot < 4; rot++) {
                            if (gameboard.canPlacePiece(i, j, block)) {
                                return true;
                            }
                            block.rotateClockwise();
                        }
                        block.rotateClockwise();
                        block.flipVertical();
                    }
                }
            }
        }
        return false;
    }

    public boolean checkGameOver() {
        for(Player p : players) {
            if (canPlaceBrick(p)) {
                return false;
            }
        }
        stopwatch.stop();
        return true;
    }

    public Map<String, GameStats> endGame(){
        int minutesPlayed = stopwatch.getMinutes();
        String winner = findWinner();
        ArrayList<Player> playersToUpdate = getPlayers();
        Map<String, GameStats> gameStatsMap = new HashMap<>();
        for (Player p : playersToUpdate){
            GameStats gameStats = new GameStats();
            if (p.getPlayerName().equals(winner)){
                gameStats.setGamesWon(1);
            }
            else {
                gameStats.setGamesWon(0);
            }
            gameStats.setMinutesPlayed(minutesPlayed);
            gameStats.setBlocksPlaced(p.getPlacedBlocks());
            gameStatsMap.put(p.getPlayerName(), gameStats);
        }
        return gameStatsMap;
    }

    private String findWinner() {
        ArrayList<Player> players = getPlayers();
        String winner = "";
        int minimumTiles = 90;
        for(Player p : players){
            if(p.getUnplacedTiles() <= minimumTiles){
                winner = p.getPlayerName();
            }
        }
        return winner;
    }

    public GameBoard getGameBoard() {
        return gameboard;
    }

    public String addPlayer(String playerName) {

        // Find the next free slot in the players array
        int nextSlot = -1;
        for (int i = 0; i < players.size(); i++) {
            if (players.get(i) == null) {
                nextSlot = i;
                break;
            } else if (players.get(i).getPlayerName() == null) {
                nextSlot = i;
                break;
            }
        }

        // Throw an exception if there are no free slots
        if (nextSlot == -1) {
            throw new RuntimeException("No free slots in the players array");
        } else {
            players.get(nextSlot).setPlayerName(playerName);
        }

        return players.get(nextSlot).getPlayerId();
    }

    public String getId() {
        return gameId;
    }

    public Player getPlayerById(String playerId) {
        if (playerId == null) throw new IllegalArgumentException("playerId cannot be null");

        for (Player player : players) {
            if (player != null && player.getPlayerId().equals(playerId)) {
                return player;
            }
        }
        return null; // return null if no player with the specified playerId is found
    }

    public Player getPlayerByUsername(String username) {
        if(username == null) throw new IllegalArgumentException("username cannot be null");

        for (Player player : players) {
            if (player.getPlayerName().equals(username)) {
                return player;
            }
        }
        return null; // return null if no player with the specified username is found
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public long getRunningTime(){
        return stopwatch.getRunningTime();
    }


}
