package at.fhtw.mtcg.app.service.user;

import at.fhtw.mtcg.app.dal.repository.UserRepository;
import at.fhtw.mtcg.http.ContentType;
import at.fhtw.mtcg.http.HttpStatus;
import at.fhtw.mtcg.http.Method;
import at.fhtw.mtcg.server.Request;
import at.fhtw.mtcg.server.Response;
import at.fhtw.mtcg.server.Service;

public class UserService implements Service {
    private final UserController userController;

    public UserService() {
        this.userController = new UserController(new UserRepository());
    }

    @Override
    public Response handleRequest(Request request) {

        if (request.getMethod() == Method.GET) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token = authorization.split(" ")[1];
            System.out.println("token " + token);
            return this.userController.getUserByUsername(request.getPathParts().get(1), token);
        } else if (request.getMethod() == Method.POST) {
            return this.userController.registerUser(request);
        } else if(request.getMethod() == Method.PUT) {
            String authorization = request.getHeaderMap().getHeader("Authorization");
            String token = authorization.split(" ")[1];
            return this.userController.updateUser(request.getPathParts().get(1), request, token);
        }

        return new Response(
                HttpStatus.BAD_REQUEST,
                ContentType.JSON,
                "[]"
        );
    }
}
