package DockerConfigHandler.service.responseTemplate.containerInfoResponseTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ContainerNetworkResponse {
    Map<String, ContainerNetworkInfoResponse> Networks;

    public Map<String, ContainerNetworkInfoResponse> getNetworks() {
        return Networks;
    }

    public void setNetworks(Map<String, ContainerNetworkInfoResponse> networks) {
        Networks = networks;
    }
}
