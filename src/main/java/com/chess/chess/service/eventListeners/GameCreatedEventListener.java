package com.chess.chess.service.eventListeners;

import com.chess.chess.chessLogic.GameResultType;

public interface GameCreatedEventListener {
    void gameCreated(String white, String black);
}
