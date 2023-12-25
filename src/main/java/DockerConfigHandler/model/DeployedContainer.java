package DockerConfigHandler.model;

public class DeployedContainer {
    private String containerName;
    private String containerIP;
    private String containerMac;

    public DeployedContainer() {
    }

    public DeployedContainer(String containerName, String containerIP, String containerMac) {
        this.containerName = containerName;
        this.containerIP = containerIP;
        this.containerMac = containerMac;
    }

    public String getContainerName() {
        return containerName;
    }

    public void setContainerName(String containerName) {
        this.containerName = containerName;
    }

    public String getContainerIP() {
        return containerIP;
    }

    public void setContainerIP(String containerIP) {
        this.containerIP = containerIP;
    }

    public String getContainerMac() {
        return containerMac;
    }

    public void setContainerMac(String containerMac) {
        this.containerMac = containerMac;
    }
}
