package com.chess.chess.chessLogic;

import com.chess.chess.chessLogic.pieces.*;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
public class ChessGame implements Game {

    private final ArrayList<GameOverEventListener> listeners = new ArrayList<>();

    private boolean whiteMove = true;
    private final Piece[] pieces = new Piece[64];

    private int whiteKingPos = 4;
    private int blackKingPos = 60;

    public ChessGame() {
        pieces[0] = new Rook(true);
        pieces[1] = new Knight(true);
        pieces[2] = new Bishop(true);
        pieces[3] = new Queen(true);
        pieces[4] = new King(true);
        pieces[5] = new Bishop(true);
        pieces[6] = new Knight(true);
        pieces[7] = new Rook(true);
        for (int i = 8; i < 16; i++) {
            pieces[i] = new Pawn(true);
        }

        pieces[56] = new Rook(false);
        pieces[57] = new Knight(false);
        pieces[58] = new Bishop(false);
        pieces[59] = new Queen(false);
        pieces[60] = new King(false);
        pieces[61] = new Bishop(false);
        pieces[62] = new Knight(false);
        pieces[63] = new Rook(false);

        for (int i = 48; i < 56; i++) {
            pieces[i] = new Pawn(false);
        }
    }

    @Override
    public boolean move(Move move, boolean white) {
        if (white != whiteMove)
            return false;

        int from = GameUtils.xyToPos(move.getPrevX(), move.getPrevY());
        int to = GameUtils.xyToPos(move.getX(), move.getY());

        if (from < 0 || from >= 64 || to < 0 || to > 64 || from == to)
            return false;

        if (pieces[from] == null || pieces[from].isWhite() != whiteMove)
            return false;

        if (pieces[to] != null && pieces[to].isWhite() == whiteMove)
            return false;

        if (!pieces[from].canMove(move, pieces))
            return false;

        if (from == getKingPos()) {
            moveKing(to);

            if (onlyKingsLeft())
                draw();

            whiteMove = !whiteMove;
            return true;
        }

        Piece toPiece = pieces[to];
        pieces[to] = pieces[from];
        pieces[from] = null;

        if (kingChecked(whiteMove)) {
            pieces[from] = pieces[to];
            pieces[to] = toPiece;

            return false;
        }

        if (mated()) {
            if (whiteMove)
                whiteWon();
            else
                blackWon();
        }

        if (staleMated())
            draw();

        whiteMove = !whiteMove;
        return true;
    }

    @Override
    public void addGameOverEventListener(GameOverEventListener listener) {
        listeners.add(listener);
    }

    private int getKingPos() {
        if (whiteMove)
            return whiteKingPos;
        else
            return blackKingPos;
    }

    private void moveKing(int pos) {
        if (whiteMove) {
            pieces[pos] = pieces[whiteKingPos];
            pieces[whiteKingPos] = null;
            whiteKingPos = pos;
        } else {
            pieces[pos] = pieces[blackKingPos];
            pieces[blackKingPos] = null;
            blackKingPos = pos;
        }
    }

    private boolean onlyKingsLeft() {
        return Arrays.stream(pieces).filter(Objects::nonNull).count() == 2;
    }

    private void draw() {
        listeners.forEach(l -> l.gameOver(GameResultType.DRAW));
    }

    private void whiteWon() {
        listeners.forEach(l -> l.gameOver(GameResultType.WHITE_WON));
    }

    private void blackWon() {
        listeners.forEach(l -> l.gameOver(GameResultType.BLACK_WON));
    }

    private boolean kingChecked(boolean whiteKing) {
        int kingPos;
        if (whiteKing)
            kingPos = whiteKingPos;
        else
            kingPos = blackKingPos;


        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == null || pieces[i].isWhite() == whiteKing)
                continue;

            if (pieces[i].canMove(GameUtils.posToMove(i, kingPos), pieces))
                return true;
        }

        return false;
    }

    private boolean opponentKingCanMove() {
        int from;
        if (!whiteMove)
            from = whiteKingPos;
        else
            from = blackKingPos;

        return pieces[from].canMoveAnywhere(from, pieces);
    }

    private int getKingPos(boolean whiteKing) {
        if (whiteKing)
            return whiteKingPos;
        else
            return blackKingPos;
    }

    private ArrayList<Integer> getOpponentKingAttackerPos() {
        var result = new ArrayList<Integer>();

        int kingPos = getKingPos(!whiteMove);

        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == null || pieces[i].isWhite() != whiteMove)
                continue;

            if (pieces[i].canMove(GameUtils.posToMove(i, kingPos), pieces))
                result.add(i);
        }

        return result;
    }

    private boolean mated() {
        if (!kingChecked(!whiteMove)) {
            return false;
        }

        log.info("king checked");

        if (opponentKingCanMove())
            return false;

        log.info("king can move");

        var attackersPos = getOpponentKingAttackerPos();
        if (attackersPos.size() > 1)
            return true;
        log.info("one attacker");

        int oppKingPos = getKingPos(!whiteMove);
        int attackerPos = attackersPos.get(0);
        var blockingPositions = pieces[attackerPos].getBlockingPositions(
                GameUtils.posToMove(attackerPos, oppKingPos));

        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == null || pieces[i].isWhite() == whiteMove)
                continue;

            if (pieces[i].canMove(GameUtils.posToMove(i, attackerPos), pieces)) {
                log.info("can take " + i);
                return false;
            }

            if (i == oppKingPos)
                continue;

            int finalI = i;
            var canBlock = blockingPositions.stream().anyMatch(pos -> pieces[finalI].canMove(
                    GameUtils.posToMove(finalI, pos), pieces)
            );

            if (canBlock)
                return false;
        }
        return true;
    }

    private boolean staleMated() {
        for (int i = 0; i < pieces.length; i++) {
            if (pieces[i] == null || pieces[i].isWhite() == whiteMove)
                continue;
            if (pieces[i].canMoveAnywhere(i, pieces))
                return false;
        }

        return true;
    }
}
