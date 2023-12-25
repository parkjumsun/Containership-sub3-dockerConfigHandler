package DockerConfigHandler.service.responseTemplate.containerCreationRequestTemplate;

public class ContainerCreationInfo {
    private String image;

    private Boolean tty;

    private Boolean attachStdin;

    private HostConfig hostConfig;


    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getTty() {
        return tty;
    }

    public void setTty(Boolean tty) {
        this.tty = tty;
    }

    public Boolean getAttachStdin() {
        return attachStdin;
    }

    public void setAttachStdin(Boolean attachStdin) {
        this.attachStdin = attachStdin;
    }
}
