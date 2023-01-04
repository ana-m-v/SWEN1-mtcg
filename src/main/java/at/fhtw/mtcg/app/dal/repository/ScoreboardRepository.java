package at.fhtw.mtcg.app.dal.repository;

import at.fhtw.mtcg.app.dal.UnitOfWork;
import at.fhtw.mtcg.app.model.Scoreboard;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ScoreboardRepository {
    public List<Scoreboard> getScoreboard() {
        UnitOfWork unitOfWork = new UnitOfWork();

        List<Scoreboard> scoreboardList = new ArrayList<>();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT username, elo, wins, losses FROM public.users ORDER BY elo DESC
                """))
        {

            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            String username;
            Float elo;
            Integer wins;
            Integer losses;

            while(resultSet.next()) {
                username = resultSet.getString("username");
                elo = resultSet.getFloat("elo");
                wins = resultSet.getInt("wins");
                losses = resultSet.getInt("losses");

                Scoreboard scoreboard = new Scoreboard(username, elo, wins, losses);
                scoreboardList.add(scoreboard);
            }
            resultSet.close();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();

        return scoreboardList;
    }
}

