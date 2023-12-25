package DockerConfigHandler.service.responseTemplate.containerInfoResponseTemplate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ContainerInfoResponse {
    private List<String> names;


    private ContainerNetworkResponse networkSettings;



    public List<String> getNames() {
        return names;
    }

    public ContainerNetworkResponse getNetworkSettings() {
        return networkSettings;
    }



    public void setNames(List<String> names) {
        this.names = names;
    }

    public void setNetworkSettings(ContainerNetworkResponse networkSettings) {
        this.networkSettings = networkSettings;
    }
}
