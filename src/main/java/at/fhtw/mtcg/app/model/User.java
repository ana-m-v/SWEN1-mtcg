package at.fhtw.mtcg.app.model;

import com.fasterxml.jackson.annotation.JsonAlias;

public class User {
    @JsonAlias({"user_id"})
    private Integer userId;
    @JsonAlias({"Username"})
    private String username;
    @JsonAlias({"Name"})
    private String fullName;

    @JsonAlias({"Password"})
    private String password;

    @JsonAlias({"coins"})
    private Integer coins;

    @JsonAlias({"Bio"})
    private String bio;

    @JsonAlias({"Image"})
    private String image;

    @JsonAlias({"token"})
    private String token;

    @JsonAlias({"Elo"})
    private Float elo;

    @JsonAlias({"wins"})
    private Integer wins;

    @JsonAlias({"losses"})
    private Integer losses;

    public User() {}


    public User(Integer id, String username, String fullName, String password, Integer coins, String bio, String image, String token, Float elo, Integer wins, Integer losses) {
        this.userId = id;
        this.username = username;
        this.fullName = fullName;
        this.password = password;
        this.coins = coins;
        this.bio = bio;
        this.image = image;
        this.token = token;
        this.elo = elo;
        this.wins = wins;
        this.losses = losses;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = this.getUsername() + "-mtcg";
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
