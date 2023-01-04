package at.fhtw.mtcg.app.dal.repository;

import at.fhtw.mtcg.app.dal.UnitOfWork;
import at.fhtw.mtcg.app.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardRepository {

    public static List<Card> showAllCards(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        List<Card> cardArrayList = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT card_id, name, type, element, damage FROM stack WHERE username=?
                             """)) {
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            unitOfWork.commitTransaction();

            String cardId;
            String name;
            String specialName;
            String type;
            String element;
            Float damage;

            while (resultSet.next()) {
                cardId = resultSet.getString("card_id");
                name = resultSet.getString("name");
                type = resultSet.getString("type");
                element = resultSet.getString("element");
                damage = resultSet.getFloat("damage");

                Card card = new Card(cardId, CardNameEnum.valueOf(name), CardTypeEnum.valueOf(type), CardElementEnum.valueOf(element), damage);
                cardArrayList.add(card);
            }
            preparedStatement.close();
            resultSet.close();
            unitOfWork.finishWork();

            return cardArrayList;

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();

        return null;
    }

    public boolean tradeOfferExists(String tradeData) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM tradings WHERE trading_id = ?
                             """)) {

            preparedStatement.setString(1, tradeData);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                preparedStatement.close();
                resultSet.close();
                unitOfWork.finishWork();
                return true;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return false;
    }

    public boolean tradeWithOneself(String tradeData, String username) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM tradings WHERE trading_id = ? AND username = ?
                             """)) {

            preparedStatement.setString(1, tradeData);
            preparedStatement.setString(2, username);

//            preparedStatement.setString(2, user.getPassword());

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                preparedStatement.close();
                resultSet.close();
                unitOfWork.finishWork();
                return true;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return false;
    }

    public boolean cardInDeck(String card_id, String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM stack WHERE card_id = ? AND username = ? AND in_deck=true
                             """)) {
            preparedStatement.setString(1, card_id);
            preparedStatement.setString(2, username);

            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                System.out.println("if " + card_id + " not in deck and belongs to user " + username);
                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return true;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        System.out.println("if " + card_id + " in deck and not belongs to user " + username);

        return false;
    }

    public void insertIntoTradings(String[] tradeData, String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 INSERT INTO tradings VALUES(?,?,?,?,?)
                             """)) {
            preparedStatement.setString(1, tradeData[0]);
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, tradeData[1]);
            preparedStatement.setString(4, tradeData[2]);
            preparedStatement.setFloat(5, Float.parseFloat(tradeData[3]));

            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
            unitOfWork.finishWork();
        }
        unitOfWork.finishWork();
        return;
    }

    public List<Trade> checkTradingDeals() {
        UnitOfWork unitOfWork = new UnitOfWork();

        List<Trade> tradeArrayList = new ArrayList<>();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT trading_id, card_id, card_type, min_damage FROM tradings
                             """)) {

            ResultSet resultSet = preparedStatement.executeQuery();
            unitOfWork.commitTransaction();

            String tradingId;
            String cardId;
            String cardType;
            Integer minDamage;

            while (resultSet.next()) {
                tradingId = resultSet.getString("trading_id");
                cardId = resultSet.getString("card_id");
                cardType = resultSet.getString("card_type");
                minDamage = resultSet.getInt("min_damage");

                Trade trade = new Trade(tradingId, cardId, cardType, minDamage);
                tradeArrayList.add(trade);
            }
            preparedStatement.close();
            resultSet.close();
            unitOfWork.finishWork();

            return tradeArrayList;

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();

        return null;
    }

    public boolean dealNotUsers(String username, String tradeId) {


        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM tradings WHERE trading_id = ? AND username = ?
                             """)) {

            preparedStatement.setString(1, tradeId);
            preparedStatement.setString(2, username);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                preparedStatement.close();
                resultSet.close();
                unitOfWork.finishWork();
                return false;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return true;
    }

    public void deleteTradeDeal(String tradeId) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                  DELETE FROM tradings WHERE trading_id = ?
                             """)) {

            preparedStatement.setString(1, tradeId);

            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.finishWork();
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
    }

    public Card viewOfferedCard(String username, String cardToTrade) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM stack WHERE username = ? AND card_id = ? AND in_deck = FALSE
                             """)) {

            preparedStatement.setString(1, username);
            preparedStatement.setString(2, cardToTrade);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();
            String card_id;
            String name;
            String type;
            String element;
            Float damage;

            if (resultSet.next()) {
                card_id = resultSet.getString("card_id");
                name = resultSet.getString("name");
                type = resultSet.getString("type");
                element = resultSet.getString("element");
                damage = resultSet.getFloat("damage");

                Card card = new Card(card_id, CardNameEnum.valueOf(name), CardTypeEnum.valueOf(type), CardElementEnum.valueOf(element), damage);

                preparedStatement.close();
                resultSet.close();
                unitOfWork.finishWork();
                return card;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return null;
    }

    public boolean checkOfferedCard(String tradeId, Card offeredCard) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT card_type, min_damage FROM tradings WHERE trading_id = ?
                             """)) {

            preparedStatement.setString(1, tradeId);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            String type;
            Float damage;

            if (resultSet.next()) {
                type = resultSet.getString("card_type");
                damage = resultSet.getFloat("min_damage");

                preparedStatement.close();
                resultSet.close();
                unitOfWork.finishWork();

                if (type.equals(offeredCard.getType().name()) && damage <= offeredCard.getDamage()) {
                    return true;
                }
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.finishWork();
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return false;
    }

    public void insertIntoPlayerStack(String username, Card card) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 INSERT INTO stack(card_id, name, type, element, damage, username) VALUES(?,?,?,?,?,?)
                             """)) {
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

    public String getTradeUsername(String tradeId) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT username FROM tradings WHERE trading_id = ?
                             """)) {

            preparedStatement.setString(1, tradeId);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            String username;
            if (resultSet.next()) {
                username = resultSet.getString(1);
                preparedStatement.close();
                resultSet.close();
                unitOfWork.finishWork();
                return username;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.finishWork();
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return "";
    }

    public void deleteFromPlayerStack(String tradeDealUserUsername, String offeredCard) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                  DELETE FROM stack WHERE username = ? AND card_id = ?
                             """)) {

            preparedStatement.setString(1, tradeDealUserUsername);
            preparedStatement.setString(2, offeredCard);


            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.finishWork();
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
    }

    public String getTradeCard(String tradeId) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT card_id FROM tradings WHERE trading_id = ?
                             """)) {

            preparedStatement.setString(1, tradeId);

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            String card;
            if (resultSet.next()) {
                card = resultSet.getString(1);
                preparedStatement.close();
                resultSet.close();
                unitOfWork.finishWork();
                return card;
            }


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.finishWork();
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
        return "";
    }

    public List<Card> showDeck(String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        List<Card> cardsInStack = new ArrayList<Card>();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM stack WHERE in_deck = TRUE AND username = ?
                             """)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            String card_id;
            String name;
            String type;
            String element;
            Float damage;

            while (resultSet.next()) {
                card_id = resultSet.getString("card_id");
                name = resultSet.getString("name");
                type = resultSet.getString("type");
                element = resultSet.getString("element");
                damage = resultSet.getFloat("damage");

                Card card = new Card(card_id, CardNameEnum.valueOf(name), CardTypeEnum.valueOf(type), CardElementEnum.valueOf(element), damage);
                cardsInStack.add(card);
            }

            resultSet.close();
            preparedStatement.close();
            unitOfWork.finishWork();
            return cardsInStack;

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return null;
    }

    public void insertCardsToDeck(String username, String card) {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 UPDATE stack SET in_deck=TRUE WHERE card_id=? AND username=?
                             """)) {

            preparedStatement.setString(1, card);
            preparedStatement.setString(2, username);

            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();
    }

    public boolean cardBelongsToUser(String cardData, String username) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM stack WHERE username = ? AND card_id = ?
                             """)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, cardData);

            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                unitOfWork.finishWork();
                return true;
            }
            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }

        unitOfWork.finishWork();

        return false;
    }

    public boolean isDeckEmpty(String player) {
        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;
        boolean isEmpty = true;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM stack WHERE in_deck = TRUE AND username = ?
                             """)) {
            preparedStatement.setString(1, player);
            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                isEmpty = false;
            }

            resultSet.close();
            preparedStatement.close();
            unitOfWork.finishWork();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return isEmpty;
    }

    public static void addCardToPackage(String card) {
        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 INSERT INTO packages(cards) VALUES(?)
                             """)) {
            preparedStatement.setString(1, card);

            preparedStatement.execute();
            unitOfWork.commitTransaction();


        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
    }

    public static String getSpecificPackage(String cards) {
        UnitOfWork unitOfWork = new UnitOfWork();

        String cardsInPackage = null;
        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                                 SELECT * FROM packages WHERE cards = ?
                             """)) {
            preparedStatement.setString(1, cards);
            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            if (resultSet.next()) {
                cardsInPackage = resultSet.getString("cards");
            }

            resultSet.close();
            preparedStatement.close();
            unitOfWork.finishWork();

        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return cardsInPackage;
    }

    public boolean packageEmpty() {
        UnitOfWork unitOfWork = new UnitOfWork();

        ResultSet resultSet = null;

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM packages
                """))
        {
            preparedStatement.executeQuery();
            unitOfWork.commitTransaction();
            resultSet = preparedStatement.getResultSet();

            if(resultSet.next()) {

                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return false;
            }
        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return true;
    }

    public String extractPackage() {

        UnitOfWork unitOfWork = new UnitOfWork();

        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                    SELECT * FROM packages ORDER BY package_id ASC LIMIT 1
                """))
        {

            preparedStatement.execute();
            unitOfWork.commitTransaction();
            ResultSet resultSet = preparedStatement.getResultSet();


            if (resultSet.next()) {
                String cardPackage = resultSet.getString(2);
                resultSet.close();
                preparedStatement.close();
                unitOfWork.finishWork();
                return cardPackage;
            }
        } catch (SQLException e) {
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
        return "";
    }

    public static void assignToPackage(String username, Card card) {
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
        unitOfWork.finishWork();
    }

    public void deletePackage(String cardPackage) {
        UnitOfWork unitOfWork = new UnitOfWork();
        try (PreparedStatement preparedStatement =
                     unitOfWork.prepareStatement("""
                     DELETE FROM packages WHERE cards = ?
                """))
        {
            preparedStatement.setString(1, cardPackage);
            preparedStatement.execute();
            unitOfWork.commitTransaction();

        } catch (SQLException e) {
            unitOfWork.finishWork();
            System.out.println(e);
            unitOfWork.rollbackTransaction();
        }
        unitOfWork.finishWork();
    }
}
