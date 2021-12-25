package front.client;


public class ServerRequest {
    public String address;
    public ClientPayload payload;

    public ServerRequest(String address, front.client.ClientPayload payload) {
        this.address = address;
        this.payload = payload;
    }
}
