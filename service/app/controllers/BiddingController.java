package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.Bidding;
import models.BiddingRepository;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.stream.Collectors;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import static play.libs.Json.toJson;
import static play.libs.Json.fromJson;
import play.data.FormFactory;


public class BiddingController extends Controller {

    private final BiddingRepository biddingRepository;
    private final HttpExecutionContext ec;


    @Inject
    public BiddingController(BiddingRepository biddingRepository, HttpExecutionContext ec) {
        this.biddingRepository = biddingRepository;
        this.ec = ec;
    }

    public CompletionStage<Result> add() {
        JsonNode js = request().body().asJson();
        Bidding bidding = fromJson(js, Bidding.class);
        return biddingRepository.add(bidding).thenApplyAsync(p -> {
            return ok("Created.");
        }, ec.current());
    }
}