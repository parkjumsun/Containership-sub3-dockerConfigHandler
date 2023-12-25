package DockerConfigHandler.service.responseTemplate.NetworkResponseTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NetworkResponse {
    private String name;
    private String id;
    private NetworkConfigResponse IPAM;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public NetworkConfigResponse getIPAM() {
        return IPAM;
    }

    public void setIPAM(NetworkConfigResponse IPAM) {
        this.IPAM = IPAM;
    }
}
