package com.chess.chess.service;

import com.chess.chess.chessLogic.ChessGame;
import com.chess.chess.chessLogic.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChessGameFactory implements GameFactory {
    @Override
    public Game createGame() {
        return new ChessGame();
    }
}
