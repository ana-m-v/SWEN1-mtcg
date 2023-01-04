package at.fhtw.mtcg.app.dal.repository;

import at.fhtw.mtcg.app.dal.UnitOfWork;
import at.fhtw.mtcg.app.model.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    public static void registerUser(User user) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatementRegisterUser =
                     unitOfWork.prepareStatement("""
                    INSERT INTO users (username, password) VALUES(?,?)
                """))
        {
            preparedStatementRegisterUser.setString(1, user.getUsername());
            preparedStatementRegisterUser.setString(2, user.getPassword());

            preparedStatementRegisterUser.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
    }

    public static String loginUser(String username, String password) {

        UnitOfWork unitOfWork = new UnitOfWork();

        String token = username + "-mtcgToken";

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    INSERT INTO logincredentials VALUES(?,?,?)
                """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, token);


            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return token;
    }

    public static boolean isLoggedIn(String username, String password) {
        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;
        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM logincredentials WHERE username = ? AND password=?
                """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.execute();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            if(resultSet.next()) {
                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return false;
    }

    public static boolean isLoggedInToken(String token) {

        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM logincredentials WHERE token = ?
                """))
        {
            preparedStatement.setString(1, token);
            preparedStatement.execute();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            if(resultSet.next()) {
                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return false;
    }

    public static String tokenToUsername(String token) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM logincredentials WHERE token = ?
                """))
        {

            preparedStatement.setString(1, token);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {

                String username = resultSet.getString(1);   // packagename

                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return username;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return "";
    }

    public static Integer usernameToId(String username) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM users WHERE username = ?
                """))
        {

            preparedStatement.setString(1, username);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {

                int userId = resultSet.getInt(1);
                System.out.println("trade user to userid " + userId);
                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return userId;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return null;
    }
    public boolean userExists(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM users WHERE username = ?
                """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.execute();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            if(resultSet.next()) {
                unitOfWork.finishWork();
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
//        ??
        unitOfWork.finishWork();
        return false;
    }

    public User getUserById(Integer ID) {
        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM users WHERE user_id = ?
                """))
        {
            preparedStatement.setInt(1, ID);
            preparedStatement.executeQuery();

            unitOfWork.commitTransaction();

            resultSet = preparedStatement.getResultSet();
            String username = null;
            String fullname = null;
            int coins = 0;
            String password;
            String bio;
            String image;
            String token;
            Float elo;
            Integer wins;
            Integer losses;

            if(resultSet.next()) {
                ID = resultSet.getInt("user_id");
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                fullname = resultSet.getString("name");
                coins = resultSet.getInt("coins");
                bio = resultSet.getString("bio");
                image = resultSet.getString("image");
                token = resultSet.getString("token");
                elo = resultSet.getFloat("elo");
                wins = resultSet.getInt("wins");
                losses = resultSet.getInt("losses");

                User user = new User(ID, username, fullname, password, coins, bio, image, token, elo, wins, losses);

                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return user;
            }

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();

        }
        unitOfWork.finishWork();
        return null;
    }

    public static User getUserByUsername(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM public.users WHERE username = ?
                """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            Integer ID = 0;
            String fullname = null;
            int coins = 0;
            String password;
            String bio;
            String image;
            String token;
            Float elo;
            Integer wins;
            Integer losses;

            if(resultSet.next()) {
                ID = resultSet.getInt("user_id");
                username = resultSet.getString("username");
                password = resultSet.getString("password");
                fullname = resultSet.getString("name");
                coins = resultSet.getInt("coins");
                bio = resultSet.getString("bio");
                image = resultSet.getString("image");
                token = resultSet.getString("token");
                elo = resultSet.getFloat("elo");
                wins = resultSet.getInt("wins");
                losses = resultSet.getInt("losses");


                User returnUser = new User(ID, username, fullname, password, coins, bio, image, token, elo, wins, losses);

                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return returnUser;
            }

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();

        return null;
    }

    public boolean checkCredentials(String username, String password) {

        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM public.users WHERE username=? AND password=?
                """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            if(resultSet.next()) {
                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return true;
            }

        } catch (Exception e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return false;
    }

    public void updateUser(User user, String username) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    UPDATE users SET name=?, bio=?, image=? WHERE username = ?
                """))
        {
            preparedStatement.setString(1, user.getFullName());
            preparedStatement.setString(2, user.getBio());
            preparedStatement.setString(3, user.getImage());
            preparedStatement.setString(4, username);

            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
    }

    public String getStats(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT elo, wins, losses FROM public.users WHERE username = ?
                """))
        {
//            preparedStatement = conn.prepareStatement("SELECT * FROM public.users WHERE user_id = ?");
            preparedStatement.setString(1, username);
            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            Float elo;
            Integer wins;
            Integer losses;
//            resultSet.next();

            if(resultSet.next()) {
                elo = resultSet.getFloat("elo");
                wins = resultSet.getInt("wins");
                losses = resultSet.getInt("losses");

                String stats = "User: " + username + "\n" + "Elo: " + elo + "\n" + "Wins: " + wins + "\n" + "Losses: " + losses + "\n";
                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return stats;
            }

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();

        return "";
    }

    public void updateStats(String username, Boolean hasWon) {

        UnitOfWork unitOfWork = new UnitOfWork();

        if(hasWon) {
            try (PreparedStatement preparedStatement =
                         unitOfWork.prepareStatement("""
                    UPDATE users SET elo=elo+?, wins=wins+?, losses=losses+? WHERE username = ?
                """))
            {
                preparedStatement.setInt(1, 3);
                preparedStatement.setInt(2, 1);
                preparedStatement.setInt(3, 0);
                preparedStatement.setString(4, username);

                preparedStatement.execute();
                unitOfWork.commitTransaction();

            } catch (SQLException e) {
                System.out.println(e);
                unitOfWork.rollbackTransaction();
            }
        }
        else {
            try (PreparedStatement preparedStatement =
                         unitOfWork.prepareStatement("""
                    UPDATE users SET elo=elo+?, wins=wins+?, losses=losses+? WHERE username = ?
                """))
            {
                preparedStatement.setInt(1, -5);
                preparedStatement.setInt(2, 0);
                preparedStatement.setInt(3, 1);
                preparedStatement.setString(4, username);

                preparedStatement.execute();
                unitOfWork.commitTransaction();

            } catch (SQLException e) {
                System.out.println(e);
                unitOfWork.rollbackTransaction();
            }
        }


        unitOfWork.finishWork();
    }

    public Integer checkCoins(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM users WHERE username = ?
                """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            int coins;

            if(resultSet.next()) {
                coins = resultSet.getInt("coins");

                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return coins;
            }
        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return null;
    }
    public void decreaseCoins(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    UPDATE users SET coins = coins - 5 WHERE username=?
                """))
        {
            preparedStatement.setString(1, username);

            preparedStatement.execute();
            unitOfWork.commitTransaction();


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
    }
}
