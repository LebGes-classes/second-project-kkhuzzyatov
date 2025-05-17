package users;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private int passwordHash;

    public Person(int id, String firstName, String lastName, String phoneNumber, int passwordHash) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.passwordHash = passwordHash;
    }

    public Person() {}

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public int getPasswordHash() {
        return passwordHash;
    }
}
