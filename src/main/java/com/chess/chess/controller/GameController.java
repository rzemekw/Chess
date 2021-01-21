package com.chess.chess.controller;

import com.chess.chess.model.MoveMessage;
import com.chess.chess.service.GameManagerService;
import com.chess.chess.service.WaitingRoomService;
import com.chess.chess.service.mapping.GameMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@Slf4j
public class GameController {

    @Autowired
    public GameController(WaitingRoomService waitingRoomService,
                          GameManagerService gameManagerService,
                          GameMappingService gameMappingService) {
        this.waitingRoomService = waitingRoomService;
        this.gameManagerService = gameManagerService;
        this.gameMappingService = gameMappingService;
    }

    private final WaitingRoomService waitingRoomService;
    private final GameManagerService gameManagerService;
    private final GameMappingService gameMappingService;

    @MessageMapping("/findGame")
    public void findGame(Principal principal) {
        this.waitingRoomService.addWaitingPlayer(principal.getName());
    }

    @MessageMapping("/move")
    public void move(@Payload final MoveMessage message, Principal principal) {
        this.gameManagerService.move(principal.getName(), gameMappingService.mapToMove(message));
    }

}
