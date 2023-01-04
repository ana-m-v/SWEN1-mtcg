package at.fhtw.mtcg.app.service.packagecard;

import at.fhtw.mtcg.app.dal.repository.CardRepository;
import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

public class PackageCardService implements Service {

    private final PackageCardController packageCardController;

    public PackageCardService() {
        this.packageCardController = new PackageCardController(new UserRepository(), new CardRepository());
    }

    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.POST) {
            return this.packageCardController.addCardToPackage(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}