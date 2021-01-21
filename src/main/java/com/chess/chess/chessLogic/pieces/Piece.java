package com.chess.chess.chessLogic.pieces;

import com.chess.chess.chessLogic.Move;

import java.util.List;

public interface Piece {
    boolean canMove(Move move, Piece[] pieces);
    boolean isWhite();

    List<Integer> getBlockingPositions(Move move);

    boolean canMoveAnywhere(int pos, Piece[] pieces);
}
