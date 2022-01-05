package back.main;

import java.util.Map;

public class ClientRequest {
    public String address;
    public Map<String,String> payload;

    public ClientRequest() {}

    public ClientRequest(String address, Map<String,String> payload) {
        this.address = address;
        this.payload = payload;
    }
}
