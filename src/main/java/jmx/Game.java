package jmx;

public class Game implements GameMXBean {

    private String playerName;

    @Override
    public void play(String gameName) {
        System.out.println(playerName + " plays " + gameName);
    }

    @Override
    public String getPlayerName() {
        System.out.println("getPlayerName - " + playerName);

        return playerName;
    }

    @Override
    public void setPlayerName(String playerName) {
        System.out.println("setPlayerName - " + playerName);

        this.playerName = playerName;
    }
}
