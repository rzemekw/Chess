package com.chess.chess.service;


import com.chess.chess.chessLogic.Game;
import lombok.Builder;
import lombok.Getter;

@Builder
public class PlayersGame {

    @Getter
    private Game game;

    @Getter
    private String whitePlayer;

    @Getter
    private String blackPlayer;
}
