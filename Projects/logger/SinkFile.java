package Projects.logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SinkFile implements Sink, AutoCloseable {
    private final BufferedWriter writer;

    public SinkFile(String filePath) throws IOException {
        this.writer = Files.newBufferedWriter(
            Path.of(filePath),
            StandardCharsets.UTF_8,
            StandardOpenOption.CREATE,
            StandardOpenOption.APPEND
        );
    }


    @Override
    public void write(String text) throws IOException {
        writer.write(text);
        writer.newLine();
        writer.flush();
    }
    
    @Override
    public void close() throws IOException {
        writer.close();
    }
}