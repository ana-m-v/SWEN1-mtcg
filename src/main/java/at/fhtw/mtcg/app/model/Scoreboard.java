package at.fhtw.mtcg.app.model;


import com.fasterxml.jackson.annotation.JsonAlias;

public class Scoreboard {
    @JsonAlias({"username"})
    private String username;

    @JsonAlias({"elo"})
    private Float elo;

    @JsonAlias({"wins"})
    private Integer wins;

    @JsonAlias({"losses"})
    private Integer losses;

    public Scoreboard() {};
    public Scoreboard(String username, Float elo, Integer wins, Integer losses) {
        this.username = username;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Float getElo() {
        return elo;
    }

    public void setElo(Float elo) {
        this.elo = elo;
    }

    public Integer getWins() {
        return wins;
    }

    public void setWins(Integer wins) {
        this.wins = wins;
    }

    public Integer getLosses() {
        return losses;
    }

    public void setLosses(Integer losses) {
        this.losses = losses;
    }
}

