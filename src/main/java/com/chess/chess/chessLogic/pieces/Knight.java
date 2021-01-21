package com.chess.chess.chessLogic.pieces;

import com.chess.chess.chessLogic.GameUtils;
import com.chess.chess.chessLogic.Move;

public class Knight extends ColoredPiece {

    public Knight(boolean white) {
        super(white);
    }

    @Override
    public boolean canMove(Move move, Piece[] pieces) {
        if(Math.abs(move.getPrevX() - move.getX()) == 1)
            return Math.abs((move.getPrevY()) - move.getY()) == 2;

        if(Math.abs(move.getPrevX() - move.getX()) == 2)
            return Math.abs((move.getPrevY()) - move.getY()) == 1;

        return false;
    }

    @Override
    public boolean canMoveAnywhere(int pos, Piece[] pieces) {
        if(pos >= 17 && pieces[pos - 17] == null)
            return true;
        if(pos >= 15 && pieces[pos - 15] == null)
            return true;
        if(pos >= 10 && pieces[pos - 10] == null)
            return true;
        if(pos >= 6 && pieces[pos - 6] == null)
            return true;
        if(pos < 64 - 6 && pieces[pos + 6] == null)
            return true;
        if(pos < 64 - 10 && pieces[pos + 10] == null)
            return true;
        if(pos < 64 - 15 && pieces[pos + 15] == null)
            return true;
        if(pos < 64 - 17 && pieces[pos + 17] == null)
            return true;
        return false;
    }
}
