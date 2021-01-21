package com.chess.chess.model;

import lombok.Builder;
import lombok.Getter;

@Builder
public class MoveMessage {
    @Getter
    private int prevX;

    @Getter
    private int prevY;

    @Getter
    private int x;

    @Getter
    private int y;

}
