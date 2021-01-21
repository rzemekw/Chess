package com.chess.chess.service;

import com.chess.chess.chessLogic.Game;
import org.springframework.stereotype.Service;

public interface GameFactory {
    Game createGame();
}
