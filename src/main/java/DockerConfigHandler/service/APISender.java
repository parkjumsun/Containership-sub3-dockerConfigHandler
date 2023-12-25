package DockerConfigHandler.service;

import DockerConfigHandler.model.ContainerInfo;
import DockerConfigHandler.model.DeployInfo;
import DockerConfigHandler.model.GroupInfo;
import DockerConfigHandler.model.MessageResponse;

import java.util.List;

public interface APISender {
    List<ContainerInfo> getContainerInfos();
    List<GroupInfo> getNetworkInfos();

    String deployContainer(DeployInfo deployInfo);
}
