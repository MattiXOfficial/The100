package me.mattix.the100;

import java.util.HashMap;
import java.util.Map;

public class QuestPlayer {

    public static Map<GamePlayer, QuestPlayer> questPlayers = new HashMap<>();

    public QuestPlayer(GamePlayer gamePlayer) {
        questPlayers.put(gamePlayer, this);
    }
}
