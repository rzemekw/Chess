package com.chess.chess.chessLogic.pieces;

import com.chess.chess.chessLogic.GameUtils;
import com.chess.chess.chessLogic.Move;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Pawn extends ColoredPiece {

    public Pawn(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Move move, Piece[] pieces) {

        int pos = GameUtils.xyToPos(move.getX(), move.getY());
        int xDiff = move.getX() - move.getPrevX();
        int yDiff = move.getY() - move.getPrevY();

        if (isWhite()) {
            if (yDiff != 1) {
                if(yDiff != 2 || xDiff != 0 || pieces[pos] !=null || pieces[pos - 8] != null) {
                    return  false;
                }
            }
        } else {
            if (yDiff != -1) {
                if(yDiff != -2 || xDiff != 0 || pieces[pos] !=null || pieces[pos + 8] != null) {
                    return  false;
                }
            }
        }

        if (xDiff == 0 && pieces[pos] == null) {
            return true;
        }
        if (xDiff == -1 || xDiff == 1) {
            if (pieces[pos] != null)
                return true;
        }
        return false;
    }

    @Override
    public boolean canMoveAnywhere(int pos, Piece[] pieces) {
        if (isWhite()) {
            if (pieces[pos + 8] == null)
                return true;
            if (pieces[pos + 7] != null && pieces[pos + 7].isWhite() != isWhite())
                return true;
            if (pos < 64 - 9 && pieces[pos + 9] != null && pieces[pos + 9].isWhite() != isWhite())
                return true;
        } else {
            if (pieces[pos - 8] == null)
                return true;
            if (pieces[pos - 7] != null && pieces[pos + 7].isWhite() != isWhite())
                return true;
            if (pos >= 9 && pieces[pos - 9] != null && pieces[pos - 9].isWhite() != isWhite())
                return true;
        }
        return false;
    }
}
