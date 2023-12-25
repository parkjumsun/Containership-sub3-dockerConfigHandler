package DockerConfigHandler.controller;


import DockerConfigHandler.model.*;
import DockerConfigHandler.service.APISender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class DockerConfigHandler {
    private APISender sender;

    private List<GroupInfo> networkList = new ArrayList<>();
    private List<ContainerInfo> containerList = new ArrayList<>();


    @Autowired
    public DockerConfigHandler(APISender sender) {
        this.sender = sender;
        this.getContainerInfos();
    }


    @GetMapping("/containers")
    List<ContainerInfo> getContainerInfos() {
        this.containerList = this.sender.getContainerInfos();
        return this.containerList;
    }

    @GetMapping("/networks")
    List<GroupInfo> getNetworkInfos() {
        List<GroupInfo> networkInfos = this.sender.getNetworkInfos();
        for (GroupInfo groupInfo : networkInfos) {
            List<String> containerList = new ArrayList<>();
            for (ContainerInfo containerInfo : this.containerList) {
                if (groupInfo.getNetworkName().equals(containerInfo.getNetworkName())) {
                    containerList.add(containerInfo.getContainerName());
                }
            }
            groupInfo.setContainers(containerList);
        }
        this.networkList = networkInfos;
        return this.networkList;
    }

    @PostMapping("/containers/deploy")
    DeployedContainer deployContainer(@RequestBody DeployInfo deployInfo) {
        String newContainerName = this.sender.deployContainer(deployInfo);
        List<ContainerInfo> containerInfos = this.getContainerInfos();
        DeployedContainer deployedContainer = null;
        for(ContainerInfo containerInfo :containerInfos){
            if(containerInfo.getContainerName().equals(newContainerName)){
                deployedContainer = new DeployedContainer(containerInfo.getContainerName(), containerInfo.getContainerIP(), containerInfo.getContainerMac());
            }
        }
        return deployedContainer;
    }
}
