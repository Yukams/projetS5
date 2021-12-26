package front.client;


import java.util.HashMap;
import java.util.Map;

public class ServerRequest {
    public String address;
    public Map<String, String> payload;

    public ServerRequest(String address, Map<String, String> payload) {
        this.address = address;
        this.payload = payload;
    }
}
