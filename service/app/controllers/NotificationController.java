package controllers;


import models.Notification;
import models.NotificationRepository;
import play.data.FormFactory;
import play.libs.Json;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import com.fasterxml.jackson.databind.JsonNode;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;
import static play.libs.Json.fromJson;
import static play.libs.Json.toJson;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link HttpExecutionContext} to provide access to the
 * {@link play.mvc.Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class NotificationController extends Controller {

    private final NotificationRepository notificationRepository;
    private final HttpExecutionContext ec;
    private final FormFactory formFactory;

    @Inject
    public NotificationController(FormFactory formFactory, NotificationRepository notificationRepository, HttpExecutionContext ec) {
        this.notificationRepository = notificationRepository;
        this.ec = ec;
        this.formFactory = formFactory;
    }

    public CompletionStage<Result> addNotification(Long userId, String notification) {
        JsonNode js = Json.parse("{" +
                "\"userId\" : \""+userId+"\","+
                "\"notification\" : \""+notification+"\","+
                "\"status\" : \"unread\""+
                "}");
        Notification notification1 = fromJson(js, Notification.class);
        return notificationRepository.add(notification1).thenApplyAsync(p -> {
            return ok("Created notification");
        }, ec.current());
    }

}

