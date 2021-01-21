package com.chess.chess.chessLogic.pieces;

import com.chess.chess.chessLogic.GameUtils;
import com.chess.chess.chessLogic.Move;

import java.util.ArrayList;
import java.util.List;

public class Bishop extends ColoredPiece {

    public Bishop(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Move move, Piece[] pieces) {

        int xDiff = move.getPrevX() - move.getX();
        int yDiff = move.getPrevY() - move.getY();

        if (Math.abs(xDiff) != Math.abs(yDiff))
            return false;

        return getBlockingPositions(move).stream().allMatch(p -> pieces[p] == null);

    }

    @Override
    public List<Integer> getBlockingPositions(Move move) {
        var result = new ArrayList<Integer>();

        int xDiff = move.getPrevX() - move.getX();
        int yDiff = move.getPrevY() - move.getY();

        if (Math.abs(xDiff) != Math.abs(yDiff))
            return result;

        int iChange = xDiff > 0 ? 1 : -1;
        int jChange = yDiff > 0 ? 1 : -1;

        int i = iChange;
        int j = jChange;

        while (i != xDiff) {
            result.add(GameUtils.xyToPos(move.getX() + i, move.getY() + j));
            i += iChange;
            j += jChange;
        }

        return result;
    }

    @Override
    public boolean canMoveAnywhere(int pos, Piece[] pieces) {
        if (pos >= 9 && (pieces[pos - 9] == null || pieces[pos - 9].isWhite() != isWhite()))
            return true;
        if (pos >= 7 && (pieces[pos - 7] == null || pieces[pos - 7].isWhite() != isWhite()))
            return true;
        if (pos <= 63 - 7 && (pieces[pos + 7] == null || pieces[pos + 7].isWhite() != isWhite()))
            return true;
        if (pos <= 63 - 9 && (pieces[pos + 9] == null || pieces[pos + 9].isWhite() != isWhite()))
            return true;
        return false;
    }
}
