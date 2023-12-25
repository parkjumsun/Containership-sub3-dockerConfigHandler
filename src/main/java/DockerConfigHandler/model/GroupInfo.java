package DockerConfigHandler.model;

import java.util.List;

public class GroupInfo {
    private String networkName;
    private String networkID;
    private String subnet;

    private List<String> containers;

    public GroupInfo() {
    }

    public GroupInfo(String networkName, String networkID, String subnet) {
        this.networkName = networkName;
        this.networkID = networkID;
        this.subnet = subnet;
    }

    public String getNetworkName() {
        return networkName;
    }

    public String getNetworkID() {
        return networkID;
    }

    public String getSubnet() {
        return subnet;
    }

    public List<String> getContainers() {
        return containers;
    }

    public void setContainers(List<String> containers) {
        this.containers = containers;
    }
}
