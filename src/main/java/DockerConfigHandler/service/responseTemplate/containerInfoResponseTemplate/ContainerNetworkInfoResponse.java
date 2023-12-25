package DockerConfigHandler.service.responseTemplate.containerInfoResponseTemplate;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown=true)
public class ContainerNetworkInfoResponse {
    private String networkID;
    private String ipAddress;
    private String macAddress;

    public String getNetworkID() {
        return networkID;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }


    public void setNetworkID(String networkID) {
        this.networkID = networkID;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }
}
