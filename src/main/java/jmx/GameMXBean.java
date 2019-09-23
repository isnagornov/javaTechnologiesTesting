package jmx;

public interface GameMXBean {

    void play(String gameName);

    String getPlayerName();

    void setPlayerName(String playerName);

}
