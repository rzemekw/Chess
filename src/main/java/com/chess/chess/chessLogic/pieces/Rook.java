package com.chess.chess.chessLogic.pieces;

import com.chess.chess.chessLogic.GameUtils;
import com.chess.chess.chessLogic.Move;

import java.util.ArrayList;
import java.util.List;

public class Rook extends ColoredPiece {

    public Rook(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Move move, Piece[] pieces) {
        int xDiff = move.getPrevX() - move.getX();
        int yDiff = move.getPrevY() - move.getY();

        if (xDiff != 0 && yDiff != 0 || xDiff == 0 && yDiff == 0)
            return false;

        return getBlockingPositions(move).stream().allMatch(p -> pieces[p] == null);

    }

    @Override
    public List<Integer> getBlockingPositions(Move move) {
        var result = new ArrayList<Integer>();

        int xDiff = move.getPrevX() - move.getX();
        int yDiff = move.getPrevY() - move.getY();

        if (xDiff != 0 && yDiff != 0 || xDiff == 0 && yDiff == 0)
            return result;

        if (xDiff != 0) {
            for (int i = Math.min(move.getPrevY(), move.getY()) + 1; i < Math.max((move.getPrevY()), move.getY()); i++) {
                result.add(GameUtils.xyToPos(move.getX(), i));
            }
            return result;
        }

        for (int i = Math.min(move.getPrevX(), move.getX()) + 1; i < Math.max((move.getPrevX()), move.getX()); i++) {
            result.add(GameUtils.xyToPos(i, move.getY()));
        }

        return result;
    }

    @Override
    public boolean canMoveAnywhere(int pos, Piece[] pieces) {
        if (pos >= 8 && (pieces[pos - 8] == null || pieces[pos - 8].isWhite() != isWhite()))
            return true;
        if (pos >= 1 && (pieces[pos - 1] == null || pieces[pos - 1].isWhite() != isWhite()))
            return true;
        if (pos <= 63 - 1 && (pieces[pos + 1] == null || pieces[pos + 1].isWhite() != isWhite()))
            return true;
        if (pos <= 63 - 8 && (pieces[pos + 8] == null || pieces[pos + 8].isWhite() != isWhite()))
            return true;
        return false;
    }

}
