package at.fhtw.mtcg.app.service.transaction;

import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

public class TransactionService implements Service {

    private final TransactionController transactionController;

    public TransactionService() {
        this.transactionController = new TransactionController(new UserRepository(), new CardRepository());
    }

    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.POST) {
            return this.transactionController.buyPackage(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
