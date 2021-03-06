package models;

import com.google.inject.ImplementedBy;

import java.util.concurrent.CompletionStage;
import java.util.stream.Stream;

/**
 * This interface provides a non-blocking API for possibly blocking operations.
 */
@ImplementedBy(JPACropRepository.class)
public interface CropRepository {

    CompletionStage<Crop> add(Crop crop);

    CompletionStage<Crop> getc(Long cid);

    CompletionStage<Stream<Crop>> listfc(Long fid);

    CompletionStage<Stream<Crop>> listc();

    CompletionStage<Stream<Crop>> listl();

    CompletionStage<Stream<Crop>> listCinL(String location);

    CompletionStage<Stream<Crop>> listct(String location);

    public CompletionStage<Crop> updateCrop(Long cid, String status);
}
