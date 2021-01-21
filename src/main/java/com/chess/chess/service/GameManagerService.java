package com.chess.chess.service;

import com.chess.chess.chessLogic.Move;
import com.chess.chess.service.eventListeners.GameCreatedEventListener;
import com.chess.chess.service.eventListeners.GameOverEventListener;
import com.chess.chess.service.eventListeners.PlayerMovedEventListener;

public interface GameManagerService {

    void createGame(String player1, String player2);
    void removePlayer(String player);
    boolean playerPlaying(String playerId);


    void addGameCreatedListener(GameCreatedEventListener eventListener);
    void addGameOverListener(GameOverEventListener eventListener);
    void addPlayerMovedListener(PlayerMovedEventListener eventListener);

    void move(String player, Move move);
}
