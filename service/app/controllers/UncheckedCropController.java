package controllers;

import com.fasterxml.jackson.databind.JsonNode;
import models.*;
import play.libs.concurrent.HttpExecutionContext;
import play.mvc.Controller;
import play.mvc.Result;
import java.util.stream.Collectors;
import javax.inject.Inject;
import java.util.concurrent.CompletionStage;
import static play.libs.Json.toJson;
import static play.libs.Json.fromJson;
import play.data.FormFactory;
import utils.EmailUtil;

/**
 * The controller keeps all database operations behind the repository, and uses
 * {@link HttpExecutionContext} to provide access to the
 * {@link play.mvc.Http.Context} methods like {@code request()} and {@code flash()}.
 */
public class UncheckedCropController extends Controller {

    private final UncheckedCropRepository uncheckedCropRepository;
    private final CropRepository cropRepository;
    private final HttpExecutionContext ec;
    private final CropController cropController;
    private final FormFactory formFactory;
    private final NotificationRepository notificationRepository;
    private final AdminController adminController;
    EmailUtil emailUtil;


    @Inject
    public UncheckedCropController(AdminController adminController,
                                   EmailUtil emailUtil,
                                   FormFactory formFactory,
                                   NotificationRepository notificationRepository,
                                   UncheckedCropRepository uncheckedCropRepository,
                                   CropRepository cropRepository,
                                   HttpExecutionContext ec,
                                   CropController cropController) {
        this.uncheckedCropRepository = uncheckedCropRepository;
        this.cropRepository  = cropRepository;
        this.ec = ec;
        this.cropController =cropController;
        this.notificationRepository = notificationRepository;
        this.adminController = adminController;
        this.formFactory = formFactory;
        this.emailUtil = emailUtil;
    }

    public CompletionStage<Result> addUncheckedCrop() {
        JsonNode js = request().body().asJson();
        UncheckedCrop uncheckedcrop = fromJson(js, UncheckedCrop.class);
        return uncheckedCropRepository.add(uncheckedcrop).thenApplyAsync(p -> {
            return ok("Created");
        }, ec.current());
    }

    public CompletionStage<Result> getUncheckedCrops() {
        return uncheckedCropRepository.listc().thenApplyAsync(cropStream -> {
            return ok(toJson(cropStream.collect(Collectors.toList())));
        }, ec.current());
    }

    public CompletionStage<Result> getUncheckedCrop(Long cid) {
        return uncheckedCropRepository.getuc(cid).thenApplyAsync(crop -> {
            return ok(toJson(crop));
        }, ec.current());
    }

    public CompletionStage<Result> approveCrop(Long cid) {
        UncheckedCrop uncheckedCrop = uncheckedCropRepository.approveCrop(cid);
        JsonNode js = toJson(uncheckedCrop);
        Crop crop = fromJson(js, Crop.class);

        CompletionStage<UncheckedCrop> cs = uncheckedCropRepository.deleteCrop(cid);

        Register register = uncheckedCropRepository.getFarmer(crop.id);
        String message = "Your "+crop.name+" crop in "+crop.location+" had been approved.";
        NotificationController notificationController=new NotificationController(formFactory, notificationRepository, ec);
        CompletionStage<Result> n = notificationController.addNotification(register.id, message);

        AdminController adminController = new AdminController(ec, emailUtil);
        adminController.sendEmail(register.email, "Crop Approved", message);

        CropController cropController=new CropController(formFactory, cropRepository, ec);
        return cropController.addChecked(crop).thenApplyAsync(p -> {
            return ok("Approved.");                                                                                       ///////added for approval
        }, ec.current());
    }

    public CompletionStage<Result> deleteCrop(Long cid) {
        UncheckedCrop uncheckedCrop = uncheckedCropRepository.getUCrop(cid);

        Register register = uncheckedCropRepository.getFarmer(uncheckedCrop.id);
        String message = "Your "+uncheckedCrop.name+" crop in "+uncheckedCrop.location+" had been rejected.";
        NotificationController notificationController=new NotificationController(formFactory, notificationRepository, ec);
        CompletionStage<Result> n = notificationController.addNotification(register.id, message);

        AdminController adminController = new AdminController(ec, emailUtil);
        adminController.sendEmail(register.email, "Crop Rejected", message);

        return uncheckedCropRepository.deleteCrop(cid).thenApplyAsync(crop -> {
            return ok("Rejected. Deleted.");
        }, ec.current());
    }

}
