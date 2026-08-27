package Projects.logger;

public class SinkConsole implements Sink {
    @Override
    public void write(String text) {
        System.out.println(text);
    }
    
}
