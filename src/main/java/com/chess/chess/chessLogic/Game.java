package com.chess.chess.chessLogic;

public interface Game {

    boolean move(Move move, boolean white);

    void addGameOverEventListener(GameOverEventListener listener);
}
