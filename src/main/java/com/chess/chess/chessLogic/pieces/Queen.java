package com.chess.chess.chessLogic.pieces;

import com.chess.chess.chessLogic.Move;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Queen extends ColoredPiece {

    private final Bishop bishop;
    private final Rook rook;


    public Queen(boolean white) {
        super(white);
        bishop = new Bishop(white);
        rook = new Rook(white);
    }

    @Override
    public boolean canMove(Move move, Piece[] pieces) {
        return bishop.canMove(move, pieces) || rook.canMove(move, pieces);
    }

    @Override
    public boolean canMoveAnywhere(int pos, Piece[] pieces) {
        return bishop.canMoveAnywhere(pos, pieces) || rook.canMoveAnywhere(pos, pieces);
    }

    @Override
    public List<Integer> getBlockingPositions(Move move) {
        return Stream.concat(bishop.getBlockingPositions(move).stream(), rook.getBlockingPositions(move).stream()).
                collect(Collectors.toList());
    }
}
