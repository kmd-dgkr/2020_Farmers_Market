package controllers;


import models.Register;
import models.RegisterRepository;
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
public class RegisterController extends Controller {

    private final RegisterRepository registerRepository;
    private final HttpExecutionContext ec;
    private final FormFactory formFactory;

    @Inject
    public RegisterController(FormFactory formFactory, RegisterRepository registerRepository, HttpExecutionContext ec) {
        this.formFactory = formFactory;
        this.registerRepository = registerRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> addRegister() {
        JsonNode js = request().body().asJson();
        Register register = fromJson(js, Register.class);
        return registerRepository.add(register).thenApplyAsync(p -> {
            return ok("Created");
        }, ec.current());
    }

    public Result login(){
        JsonNode j = request().body().asJson();
        String email = j.get("email").asText();
        String password = j.get("password").asText();
        Register ps = registerRepository.login(email,password);
        if(ps.email== null || ps.password==null){
            return null;
        }
        else{
            String msg = "{\"role\" : \""+ps.role+"\",\"name\":\""+ps.name+"\",\"id\":\""+ps.id+"\"}";
            //return ok("You are a valid user. "+ps.role);  //for postman
            return ok(Json.parse(msg));
        }
    }

    public CompletionStage<Result> getFarmer(Long fid) {
        return registerRepository.getFarmer(fid).thenApplyAsync(farmer -> {
            return ok(toJson(farmer));
        }, ec.current());
    }

    public CompletionStage<Result> updateRegister(Long id){
        JsonNode js = request().body().asJson();
        String name = js.get("name").asText();
        String email = js.get("email").asText();
        String password = js.get("password").asText();
        String mobile = js.get("mobile").asText();
        return registerRepository.update(id, name, email, password, mobile).thenApplyAsync(p->{
            return ok("update successful");
        },ec.current());

    }

}

