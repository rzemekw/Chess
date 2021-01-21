package com.chess.chess.service;

public interface WaitingRoomService {

    void addWaitingPlayer(String playerId);

    void removeWaitingPlayer(String playerId);
}
