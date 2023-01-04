package at.fhtw.mtcg.app.dal.repository;

import at.fhtw.mtcg.app.dal.UnitOfWork;
import at.fhtw.mtcg.app.model.Card;
import at.fhtw.mtcg.app.model.CardElementEnum;
import at.fhtw.mtcg.app.model.CardNameEnum;
import at.fhtw.mtcg.app.model.CardTypeEnum;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BattleRepository {
    public void enterBattlePlayer1(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        System.out.println("player 1 username " + username);
        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    INSERT INTO battle(player1) VALUES(?)
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

    public void enterBattlePlayer2(String username, Integer battle_id) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    UPDATE battle SET player2=? WHERE battle_id = ?
                """))
        {
            preparedStatement.setString(1, username);

            preparedStatement.setInt(2, battle_id);

            preparedStatement.execute();
            unitOfWork.commitTransaction();


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
    }

    public Integer getBattleId(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();
        Integer battleId = null;
        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT battle_id FROM battle WHERE player1 = ?
                """))
        {
            preparedStatement.setString(1, username);

            ResultSet resultSet = preparedStatement.executeQuery();
            unitOfWork.commitTransaction();

            if(resultSet.next()) {
                battleId = resultSet.getInt("battle_id");
            }
            else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }


        unitOfWork.finishWork();
        return battleId;
    }

    public String getPlayer1() {
        UnitOfWork unitOfWork = new UnitOfWork();
        String player = null;
        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT player1 FROM battle
                """))
        {

            ResultSet resultSet = preparedStatement.executeQuery();
            unitOfWork.commitTransaction();

            if(resultSet.next()) {
                player = resultSet.getString("player1");
            }

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        System.out.println("in battle table player 1 = " + player);
        unitOfWork.finishWork();
        return player;
    }

    public String getPlayer2() {
        UnitOfWork unitOfWork = new UnitOfWork();
        String player = null;
        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT player2 FROM battle
                """))
        {

            ResultSet resultSet = preparedStatement.executeQuery();
            unitOfWork.commitTransaction();

            if(resultSet.next()) {
                player = resultSet.getString("player2");
            }

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        System.out.println("in battle table player 2 = " + player);
        unitOfWork.finishWork();
        return player;
    }

    public Card chooseCardToFight(String player) {
        UnitOfWork unitOfWork = new UnitOfWork();
        Card cardPlayer = null;
        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM stack WHERE in_deck=TRUE AND username = ? ORDER BY RANDOM() LIMIT 1
                             """)) {
            preparedStatement.setString(1, player);
            ResultSet resultSet = preparedStatement.executeQuery();
            unitOfWork.commitTransaction();

            String cardId;
            String name;
            String type;
            String element;
            float damage;

            if (resultSet.next()) {
                cardId = resultSet.getString("card_id");
                name = resultSet.getString("name");
                type = resultSet.getString("type");
                element = resultSet.getString("element");
                damage = resultSet.getFloat("damage");

                cardPlayer = new Card(cardId, CardNameEnum.valueOf(name), CardTypeEnum.valueOf(type), CardElementEnum.valueOf(element), damage);
            } else {
                unitOfWork.rollbackTransaction();
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
            unitOfWork.finishWork();
        }

        unitOfWork.finishWork();
        return cardPlayer;
    }

    public void deleteFromPlayerStack(String username, String card) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                     DELETE FROM stack WHERE username = ? AND card_id = ?
                """))
        {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, card);


            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.finishWork();
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
    }

    public static Card extractWonCard(String username, String card) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT card_id, name, type, element, damage FROM stack WHERE username=? AND card_id=?
                """))
        {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, card);

            ResultSet resultSet = preparedStatement.executeQuery();
            unitOfWork.commitTransaction();

            String cardId = null;
            String name = null;
            String type= null;
            String element = null;
            Float damage = null;

            if(resultSet.next()) {
                cardId = resultSet.getString("card_id");
                name = resultSet.getString("name");
                type = resultSet.getString("type");
                element = resultSet.getString("element");
                damage = resultSet.getFloat("damage");
            }
            Card cardWon = new Card(cardId, CardNameEnum.valueOf(name), CardTypeEnum.valueOf(type), CardElementEnum.valueOf(element), damage);

            preparedStatement.close();
            resultSet.close();
            unitOfWork.finishWork();

            return cardWon;

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();

        return null;
    }

    public void insertIntoPlayerStack(String username, Card card) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    INSERT INTO stack(card_id, name, type, element, damage, username) VALUES(?,?,?,?,?,?)
                """))
        {
            preparedStatement.setString(1, card.getCardId());
            preparedStatement.setString(2, card.getName().name());
            preparedStatement.setString(3, card.getType().name());
            preparedStatement.setString(4, card.getElement().name());
            preparedStatement.setFloat(5, card.getDamage());
            preparedStatement.setString(6, username);
            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
    }

    public void insertIntoBattleLog(Integer battleID, String log) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    INSERT INTO battlelog(battle_id, battle_log) VALUES(?,?)
                """))
        {
            preparedStatement.setInt(1, battleID);
            preparedStatement.setString(2, log);
            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
    }
}
