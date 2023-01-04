package at.fhtw.mtcg.app.service.session;

import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

public class SessionService implements Service {

    private final SessionController sessionController;

    public SessionService() {
        this.sessionController = new SessionController(new UserRepository());
    }

    @Override
    public Response handleRequest(Request request) {
        if (request.getMethod() == Method.POST) {
            return this.sessionController.loginUser(request);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}