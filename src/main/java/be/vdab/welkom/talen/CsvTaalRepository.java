package be.vdab.welkom.talen;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
@Qualifier("CSV")
public class CsvTaalRepository implements TaalRepository {
    @Override
    public List<Taal> findAll() {
        try (var stream = Files.lines(Path.of("/data/talen.csv"))) {
            return stream
                    .map(regel -> regel.split(","))
                    .map(regelOnderdelen ->
                            new Taal(regelOnderdelen[0], regelOnderdelen[1]))
                    .toList();
        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException ex) {
            throw new IllegalArgumentException("Talenbestand bevat verkeerde data", ex);
        }
    }
}