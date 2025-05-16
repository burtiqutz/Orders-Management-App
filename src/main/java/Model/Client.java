package Model;
/**
 * Represents a client with an id, name, address, and email.
 */
public class Client {
    private int id;
    private String name;
    private String address;
    private String email;
    /**
     * Default constructor.
     */
    public Client() {
    }
    /**
     * Constructs a Client without an ID.
     *
     * @param name client's name
     * @param address client's address
     * @param email client's email
     */
    public Client(String name, String address, String email) {
        this.name = name;
        this.address = address;
        this.email = email;
    }
    /**
     * Constructs a Client with an ID.
     *
     * @param id client's id
     * @param name client's name
     * @param address client's address
     * @param email client's email
     */
    public Client(int id, String name, String address, String email) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.email = email;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "Client [id=" + id + ", name=" + name + ", address=" + address + ", email=" + email;
    }
}
