package Projects.pubsub_drivers;

public class ValidatorText implements Validator {
    int maxLength;

    public ValidatorText(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public boolean isValid(Message message) {
        if (message.getText().length() >= maxLength) {
            System.out.println("[Error] Message w/ Text must be less than " + maxLength + " characters!");
            return false;
        }

        return true;
    }
    
}
