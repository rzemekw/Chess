package com.chess.chess.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SimpleWaitingRoomService implements WaitingRoomService {

    SimpleWaitingRoomService(@Autowired GameManagerService gameManagerService) {
        this.gameManagerService = gameManagerService;
    }

    private String playerId = null;

    private final Object lock = new Object();

    private final GameManagerService gameManagerService;

    @Override
    public void addWaitingPlayer(String playerId) {
        synchronized (lock) {
            if(this.gameManagerService.playerPlaying(playerId)) {
                return;
            }
            if (this.playerId == null) {
                this.playerId = playerId;
                return;
            }
            if(this.playerId.equals(playerId)) {
                return;
            }
            gameManagerService.createGame(this.playerId, playerId);
            this.playerId = null;
        }
    }

    @Override
    public void removeWaitingPlayer(String playerId) {
        synchronized (lock) {
            if(this.playerId.equals(playerId))
                this.playerId = null;
        }
    }
}
