package com.chess.chess.controller;

import com.chess.chess.service.GameManagerService;
import com.chess.chess.service.WaitingRoomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {

    @Autowired
    public WebSocketEventListener(GameManagerService gameManagerService,
                                  WaitingRoomService waitingRoomService) {
        this.waitingRoomService = waitingRoomService;
        this.gameManagerService = gameManagerService;
    }

    private final GameManagerService gameManagerService;
    private final WaitingRoomService waitingRoomService;

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        gameManagerService.removePlayer(event.getUser().getName());
        waitingRoomService.removeWaitingPlayer(event.getUser().getName());
    }
}
