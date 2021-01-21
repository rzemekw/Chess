package com.chess.chess.service;

import com.chess.chess.chessLogic.GameResultType;
import com.chess.chess.chessLogic.Move;
import com.chess.chess.service.eventListeners.GameCreatedEventListener;
import com.chess.chess.service.eventListeners.GameOverEventListener;
import com.chess.chess.service.eventListeners.PlayerMovedEventListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class SimpleGameManagerService implements GameManagerService {

    public SimpleGameManagerService(@Autowired GameFactory gameFactory) {
        this.gameFactory = gameFactory;
    }

    private final ConcurrentHashMap<String, PlayersGame> playerGames = new ConcurrentHashMap<>();
    private final GameFactory gameFactory;

    private final List<GameCreatedEventListener> gameCreatedEventListeners = new ArrayList<>();
    private final List<GameOverEventListener> gameOverEventListeners = new ArrayList<>();
    private final List<PlayerMovedEventListener> playerMovedEventListeners = new ArrayList<>();

    @Override
    public void createGame(String player1, String player2) {
        var game = this.gameFactory.createGame();

        String white;
        String black;
        var rand = new Random();
        if(rand.nextInt(2) == 0) {
            white = player1;
            black = player2;
        }
        else {
            white = player2;
            black = player1;
        }

        var playersGame = PlayersGame.builder()
                .game(game).whitePlayer(white).blackPlayer(black).build();

        playerGames.put(player1, playersGame);
        playerGames.put(player2, playersGame);

        game.addGameOverEventListener(result -> onGameOver(result, player1, player2));

        onGameCreated(white, black);
    }

    @Override
    public void removePlayer(String player) {
        var playersGame = playerGames.getOrDefault(player, null);
        if(playersGame == null)
            return;

        playerGames.remove(playersGame.getBlackPlayer());
        playerGames.remove(playersGame.getWhitePlayer());

        if(player.equals(playersGame.getWhitePlayer()))
            onGameOver(GameResultType.BLACK_WON, playersGame.getWhitePlayer(), playersGame.getBlackPlayer());
        else
            onGameOver(GameResultType.WHITE_WON, playersGame.getWhitePlayer(), playersGame.getBlackPlayer());
    }

    @Override
    public boolean playerPlaying(String playerId) {
        return playerGames.containsKey(playerId);
    }

    @Override
    public void addGameCreatedListener(GameCreatedEventListener eventListener) {
        this.gameCreatedEventListeners.add(eventListener);
    }

    @Override
    public void addGameOverListener(GameOverEventListener eventListener) {
        this.gameOverEventListeners.add(eventListener);
    }

    @Override
    public void addPlayerMovedListener(PlayerMovedEventListener eventListener) {
        this.playerMovedEventListeners.add(eventListener);
    }

    @Override
    public void move(String player, Move move) {
        var playersGame = this.playerGames.getOrDefault(player, null);
        if(playersGame == null)
            return;

        var game = playersGame.getGame();

        log.warn(player);
        log.warn(playersGame.getWhitePlayer());

        if(game.move(move, player.equals(playersGame.getWhitePlayer())))
            onPlayerMoved(move, playersGame.getBlackPlayer(), playersGame.getWhitePlayer());
    }

    private void onGameOver(GameResultType gameResultType, String player1, String player2) {

        this.gameOverEventListeners.forEach(l -> l.gameOver(gameResultType, player1, player2));
    }

    private void onGameCreated(String player1, String player2) {
        this.gameCreatedEventListeners.forEach(l -> l.gameCreated(player1, player2));
    }

    private void onPlayerMoved(Move move, String player1, String player2) {
        this.playerMovedEventListeners.forEach(l -> l.playerMoved(move, player1, player2));
    }
}
