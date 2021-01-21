package com.chess.chess.chessLogic;

public class GameUtils {
    public static int xyToPos(int x, int y) {
        return y * 8 + x;
    }

    public static Move posToMove(int prevPos, int nextPos) {
        int prevX = prevPos % 8;
        int prevY = (prevPos - prevX) / 8;

        int x = nextPos % 8;
        int y = (nextPos - x) / 8;

        return Move.builder().prevX(prevX).prevY(prevY).x(x).y(y).build();
    }
}
