package com.chess.chess.chessLogic.pieces;

import com.chess.chess.chessLogic.GameUtils;
import com.chess.chess.chessLogic.Move;

public class King extends ColoredPiece {

    public King(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Move move, Piece[] pieces) {
        int pos = GameUtils.xyToPos(move.getX(), move.getY());

        int xDiff = move.getX() - move.getPrevX();
        int yDiff = move.getY() - move.getPrevY();

        if(xDiff == 0 && yDiff == 0)
            return false;

        if(Math.abs(xDiff) > 1 || Math.abs((yDiff)) > 1)
            return false;

        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == null || pieces[i].isWhite() == isWhite())
                continue;
            if (pieces[i].canMove(GameUtils.posToMove(i, pos), pieces))
                return false;
        }

        return true;
    }

    @Override
    public boolean canMoveAnywhere(int pos, Piece[] pieces) {
        if (pos >= 9 && (pieces[pos - 9] == null || pieces[pos - 9].isWhite() != isWhite()) &&
                canMove(GameUtils.posToMove(pos, pos - 9), pieces))
            return true;
        if (pos >= 8 && (pieces[pos - 9] == null || pieces[pos - 9].isWhite() != isWhite()) &&
                canMove(GameUtils.posToMove(pos, pos - 8), pieces))
            return true;
        if (pos >= 7 && (pieces[pos - 7] == null || pieces[pos - 7].isWhite() != isWhite()) &&
                canMove(GameUtils.posToMove(pos, pos - 7), pieces))
            return true;
        if (pos >= 1 && (pieces[pos - 1] == null || pieces[pos - 1].isWhite() != isWhite()) &&
                canMove(GameUtils.posToMove(pos, pos - 1), pieces))
            return true;
        if (pos < 63 && (pieces[pos + 1] == null || pieces[pos + 1].isWhite() != isWhite()) &&
                canMove(GameUtils.posToMove(pos, pos + 1), pieces))
            return true;
        if (pos < 64 - 7 && (pieces[pos + 7] == null || pieces[pos + 7].isWhite() != isWhite()) &&
                canMove(GameUtils.posToMove(pos, pos + 7), pieces))
            return true;
        if (pos < 64 - 8 && (pieces[pos + 8] == null || pieces[pos + 8].isWhite() != isWhite()) &&
                canMove(GameUtils.posToMove(pos, pos + 8), pieces))
            return true;
        if (pos < 64 - 9 && (pieces[pos + 9] == null || pieces[pos + 9].isWhite() != isWhite()) &&
                canMove(GameUtils.posToMove(pos, pos + 9), pieces))
            return true;
        return false;
    }
}
