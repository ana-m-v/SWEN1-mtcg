package at.fhtw;

import at.fhtw.mtcg.app.service.battle.BattleService;
import at.fhtw.mtcg.app.service.card.CardService;
import at.fhtw.mtcg.app.service.deck.DeckService;
import at.fhtw.mtcg.app.service.packagecard.PackageCardService;
import at.fhtw.mtcg.app.service.scoreboard.ScoreboardService;
import at.fhtw.mtcg.app.service.session.SessionService;
import at.fhtw.mtcg.app.service.stats.StatsService;
import at.fhtw.mtcg.app.service.transaction.TransactionService;
import at.fhtw.mtcg.app.service.user.UserService;
import at.fhtw.mtcg.server.Server;
import at.fhtw.mtcg.utils.Router;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        Server server = new Server(10001, configureRouter());
        try {
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Router configureRouter()
    {
        Router router = new Router();
        router.addService("/users", new UserService());
        router.addService("/sessions", new SessionService());
        router.addService("/packages", new PackageCardService());
        router.addService("/transactions", new TransactionService());
        router.addService("/cards", new CardService());
        router.addService("/deck", new DeckService());
        router.addService("/stats", new StatsService());
        router.addService("/score", new ScoreboardService());
        router.addService("/battles", new BattleService());
        router.addService("/tradings", new CardService());
        return router;
    }
}