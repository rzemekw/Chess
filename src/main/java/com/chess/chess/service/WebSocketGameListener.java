package com.chess.chess.service;

import com.chess.chess.chessLogic.GameResultType;
import com.chess.chess.chessLogic.Move;
import com.chess.chess.controller.WebSocketEventListener;
import com.chess.chess.service.eventListeners.GameCreatedEventListener;
import com.chess.chess.service.eventListeners.GameOverEventListener;
import com.chess.chess.service.eventListeners.PlayerMovedEventListener;
import com.chess.chess.service.mapping.GameMappingService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class WebSocketGameListener implements GameCreatedEventListener,
        GameOverEventListener, PlayerMovedEventListener {

    private final GameManagerService gameManagerService;
    private final SimpMessageSendingOperations simpMessageSendingOperations;
    private final GameMappingService gameMappingService;

    public WebSocketGameListener(@Autowired GameManagerService gameManagerService,
                                 @Autowired SimpMessageSendingOperations simpMessageSendingOperations,
                                 @Autowired GameMappingService gameMappingService) {
        this.gameManagerService = gameManagerService;
        this.simpMessageSendingOperations = simpMessageSendingOperations;
        this.gameMappingService = gameMappingService;

        this.gameManagerService.addGameCreatedListener(this);
        this.gameManagerService.addGameOverListener(this);
        this.gameManagerService.addPlayerMovedListener(this);
    }

    @Override
    public void gameCreated(String white, String black) {
        log.info(white);
        log.info(black);
        simpMessageSendingOperations.convertAndSendToUser(white, "/queue/gameCreated", true);
        simpMessageSendingOperations.convertAndSendToUser(black, "/queue/gameCreated", false);
    }

    @Override
    public void gameOver(GameResultType gameResultType, String player1, String player2) {
        simpMessageSendingOperations.convertAndSendToUser(player1, "/queue/gameOver", gameResultType);
        simpMessageSendingOperations.convertAndSendToUser(player2, "/queue/gameOver", gameResultType);
    }

    @Override
    public void playerMoved(Move move, String player1, String player2) {
        var moveMessage = gameMappingService.mapToMoveMessage(move);
        simpMessageSendingOperations.convertAndSendToUser(player1, "/queue/move", moveMessage);
        simpMessageSendingOperations.convertAndSendToUser(player2, "/queue/move", moveMessage);
    }
}
