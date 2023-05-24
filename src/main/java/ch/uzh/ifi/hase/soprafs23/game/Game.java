package ch.uzh.ifi.hase.soprafs23.game;
import java.util.*;

import ch.uzh.ifi.hase.soprafs23.entity.GameStats;
import ch.uzh.ifi.hase.soprafs23.game.blocks.Block;
import ch.uzh.ifi.hase.soprafs23.game.blocks.CellStatus;


public class Game {
    private final String gameId;
    private final GameBoard gameboard;
    private final Stopwatch stopwatch;

    private final Countdown countdown;

    private final ArrayList<Player> players = new ArrayList<>();
    private Player currentPlayer;

    private class CountdownCompleteListener implements Countdown.CountdownListener {
        @Override
        public void onCountdownComplete() {
           nextPlayersTurn();
        }
    }

    public Game() {
        this.gameId = UUID.randomUUID().toString();
        this.gameboard = new GameBoard();
        Countdown.CountdownListener listener = new CountdownCompleteListener();
        this.countdown = new Countdown(listener);
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
                countdown.resetTime();
            } while(!canPlaceBrick(currentPlayer));

        }
        else {
            endGame();
        }
    }

    public boolean canPlaceBrick(Player p) {
        if(!p.isInGame()) {
            return false;
        }
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                for (Block block : p.getInventory().getBlocks()) {
                    for (int flip = 0; flip < 1; flip++) {
                        for (int rot = 0; rot < 4; rot++) {
                            if (gameboard.canPlacePiece(i, j, block)) {
                                System.out.println("Player " + p.getPlayerName() + " can place piece " + block.getBlockName() + " at " + i + ", " + j + " (flip=" + flip + ", rot=" + rot + ")");
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
        return true;
    }

    private boolean gameOver = false;
    public boolean isGameOver() {
        return gameOver;
    }

    private Optional<Player> winner = Optional.empty();
    public Optional<Player> getWinner() {
        return winner;
    }

    private int duration = 0;
    public int getDuration() {
        return duration;
    }

    public HashMap<String, Integer> getPlacedBlocks() {
        HashMap<String, Integer> placedBlocks = new HashMap<>();
        for (Player p : players) {
            placedBlocks.put(p.getPlayerName(), p.getPlacedBlocks());
        }
        return placedBlocks;
    }

    public Map<String, GameStats> endGame(){
        stopwatch.stop();
        countdown.stop();
        duration = stopwatch.getMinutes();
        winner = Optional.of(findWinner());
        System.out.println("The winner is: " + winner.get().getPlayerName());
        ArrayList<Player> playersToUpdate = getPlayers();
        Map<String, GameStats> gameStatsMap = new HashMap<>();
        for (Player p : playersToUpdate){
            GameStats gameStats = new GameStats();
            gameStats.setGamesWon(p.getPlayerName().equals(winner.get().getPlayerName()) ? 1 : 0);
            gameStats.setMinutesPlayed(duration);
            gameStats.setBlocksPlaced(p.getPlacedBlocks());
            gameStatsMap.put(p.getPlayerName(), gameStats);
        }
        gameOver = true;
        return gameStatsMap;
    }



    private Player findWinner() {
        ArrayList<Player> players = getPlayers();
        Player winner = null;
        int minimumTiles = 90;
        for(Player p : players){
            if(p.getUnplacedTiles() <= minimumTiles){
                winner = p;
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

    public Date getStartDate(){
        return stopwatch.getStartDate();
    }

}
