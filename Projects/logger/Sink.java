package Projects.logger;

public interface Sink {
    public void write(String text) throws Exception;
}
