package back.main;

public class ServerResponse {
    public String address;
    public String payload;
    public String type;

    public ServerResponse(String address, String payload, String type) {
        this.address = address;
        this.payload = payload;
        this.type = type;
    }
}
