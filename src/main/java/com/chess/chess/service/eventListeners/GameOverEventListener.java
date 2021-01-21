package com.chess.chess.service.eventListeners;

import com.chess.chess.chessLogic.GameResultType;

public interface GameOverEventListener {
    void gameOver(GameResultType gameResultType, String player1, String player2);
}
