package com.chess.chess.chessLogic;

import lombok.Builder;
import lombok.Getter;

@Builder
public class Move {
    @Getter
    private int prevX;

    @Getter
    private int prevY;

    @Getter
    private int x;

    @Getter
    private int y;

}
