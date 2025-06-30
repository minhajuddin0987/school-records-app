package Quixo.Model;

import java.util.*;

public class InMemoryStorage {
    public static Map<String, Player> players = new HashMap<>();
    public  static List<GameRecord> games = new ArrayList<>();
    public static List<MoveRecord> moves = new ArrayList<>();

    public static void clearAll() {
        players.clear();
        games.clear();
        moves.clear();
    }

    public static class GameRecord {
        public int gameId;
        public String playerId;
        public Date startTime;
        public Date endTime;
        public Boolean winner;

        public GameRecord(int gameId, String playerId) {
            this.gameId = gameId;
            this.playerId = playerId;
            this.startTime = new Date();
        }
    }
    public static class MoveRecord {
        public int gameId;
        public int moveNumber;
        public String playerId;
        public char symbol;
        public double duration;

        public MoveRecord(int gameId, int moveNumber, String playerId, char symbol, double direction) {
            this.gameId = gameId;
            this.moveNumber = moveNumber;
            this.playerId = playerId;
            this.symbol = symbol;
            this.duration = direction;
        }
    }
}
