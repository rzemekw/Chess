package com.chess.chess.service.mapping;

import com.chess.chess.chessLogic.Move;
import com.chess.chess.model.MoveMessage;
import org.springframework.stereotype.Service;

@Service
public class GameMappingService {

    public MoveMessage mapToMoveMessage(Move move) {
        return MoveMessage.builder()
                .y(move.getY())
                .prevY(move.getPrevY())
                .prevX(move.getPrevX())
                .x(move.getX())
                .build();
    }

    public Move mapToMove(MoveMessage move) {
        return Move.builder()
                .y(move.getY())
                .prevY(move.getPrevY())
                .prevX(move.getPrevX())
                .x(move.getX())
                .build();
    }
}
