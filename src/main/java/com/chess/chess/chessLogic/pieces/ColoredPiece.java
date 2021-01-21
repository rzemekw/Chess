package com.chess.chess.chessLogic.pieces;

import com.chess.chess.chessLogic.Move;

import java.util.ArrayList;
import java.util.List;

public abstract class ColoredPiece implements Piece {

    private boolean isWhite;

    ColoredPiece(boolean white) {
        isWhite = white;
    }

    @Override
    public boolean isWhite() {
        return isWhite;
    }

    @Override
    public abstract boolean canMove(Move move, Piece[] pieces);

    @Override
    public List<Integer> getBlockingPositions(Move move) {
        return new ArrayList<>();
    }

    @Override
    public abstract boolean canMoveAnywhere(int pos, Piece[] pieces);
}
