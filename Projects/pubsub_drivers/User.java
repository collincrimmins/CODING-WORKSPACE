package Projects.pubsub_drivers;

public class User {
    String id;
    String phoneNumber;
    String email;
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public User(String id, String phoneNumber, String email) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }


}
