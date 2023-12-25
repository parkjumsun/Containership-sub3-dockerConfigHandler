package DockerConfigHandler.service.responseTemplate.NetworkResponseTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class NetworkConfigResponse {
    List<NetworkSubnetResponse> config;

    public List<NetworkSubnetResponse> getConfig() {
        return config;
    }

    public void setConfig(List<NetworkSubnetResponse> config) {
        this.config = config;
    }
}
