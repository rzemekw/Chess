package com.chess.chess.service.eventListeners;


import com.chess.chess.chessLogic.Move;

public interface PlayerMovedEventListener {
    void playerMoved(Move move, String player1, String player2);
}
