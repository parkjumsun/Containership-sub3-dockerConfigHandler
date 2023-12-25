package DockerConfigHandler.service;

import DockerConfigHandler.model.ContainerInfo;
import DockerConfigHandler.model.DeployInfo;
import DockerConfigHandler.model.GroupInfo;
import DockerConfigHandler.model.MessageResponse;
import DockerConfigHandler.service.responseTemplate.NetworkResponseTemplate.NetworkConfigResponse;
import DockerConfigHandler.service.responseTemplate.NetworkResponseTemplate.NetworkResponse;
import DockerConfigHandler.service.responseTemplate.NetworkResponseTemplate.NetworkSubnetResponse;
import DockerConfigHandler.service.responseTemplate.containerCreationRequestTemplate.ContainerCreationInfo;
import DockerConfigHandler.service.responseTemplate.containerCreationRequestTemplate.ContainerNetworkConnectionInfo;
import DockerConfigHandler.service.responseTemplate.containerCreationRequestTemplate.HostConfig;
import DockerConfigHandler.service.responseTemplate.containerInfoResponseTemplate.ContainerInfoResponse;
import DockerConfigHandler.service.responseTemplate.containerInfoResponseTemplate.ContainerNetworkInfoResponse;
import DockerConfigHandler.service.responseTemplate.containerInfoResponseTemplate.ContainerNetworkResponse;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DockerAPISender implements APISender {
    private String ip ="192.168.219.198";
    private String port = "2375";

    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public List<ContainerInfo> getContainerInfos() {
        String url = "http://" + ip + ":" + port + "/containers/json";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
        String body = responseEntity.getBody();
        List<ContainerInfo> containerInfoList = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        try {
            ContainerInfoResponse[] containerInfoResponses = objectMapper.readValue(body, ContainerInfoResponse[].class);

            for(ContainerInfoResponse containerInfoResponse: containerInfoResponses){
                ContainerInfo containerInfo = new ContainerInfo();
                String name = containerInfoResponse.getNames().get(0);
                String removedName = name.replace("/", "");
                ContainerNetworkResponse networkSettings = containerInfoResponse.getNetworkSettings();
                Map<String, ContainerNetworkInfoResponse> networks = networkSettings.getNetworks();
                for (String networkName : networks.keySet()) {
                    ContainerNetworkInfoResponse containerNetworkInfoResponse = networks.get(networkName);
                    String networkID = containerNetworkInfoResponse.getNetworkID();
                    String removedNetworkId = networkID.substring(0, 12);
                    containerInfo.setNetworkName(networkName);
                    containerInfo.setNetworkID(removedNetworkId);
                    containerInfo.setContainerIP(containerNetworkInfoResponse.getIpAddress());
                    containerInfo.setContainerMac(containerNetworkInfoResponse.getMacAddress());
                }
                containerInfo.setContainerName(removedName);
                containerInfoList.add(containerInfo);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return containerInfoList;
    }

    @Override
    public List<GroupInfo> getNetworkInfos() {
        String url = "http://" + ip + ":" + port + "/networks";
        ResponseEntity<String> responseEntity = restTemplate.exchange(url, HttpMethod.GET, new HttpEntity(new HttpHeaders()), String.class);
        String body = responseEntity.getBody();
        ObjectMapper objectMapper = new ObjectMapper().configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true).configure(JsonGenerator.Feature.IGNORE_UNKNOWN, true);
        List<GroupInfo> groupInfoList = new ArrayList<>();
        try {
            NetworkResponse[] networkResponses = objectMapper.readValue(body, NetworkResponse[].class);
            for (NetworkResponse networkResponse : networkResponses) {
                String networkName = networkResponse.getName();
                if(networkName.equals("host") || networkName.equals("bridge") || networkName.equals("none"))
                    continue;
                String networkId = networkResponse.getId().substring(0, 12);
                NetworkConfigResponse ipam = networkResponse.getIPAM();
                String subnet = ipam.getConfig().get(0).getSubnet();
                GroupInfo groupInfo = new GroupInfo(networkName, networkId, subnet);
                groupInfoList.add(groupInfo);
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return groupInfoList;
    }

    @Override
    public String deployContainer(DeployInfo deployInfo) {
        String url = "http://" + ip + ":" + port + "/containers/create?name=" + deployInfo.getContainerName();
        ContainerCreationInfo containerCreationInfo = new ContainerCreationInfo();
        containerCreationInfo.setImage(deployInfo.getImage());
        containerCreationInfo.setTty(true);
        containerCreationInfo.setAttachStdin(true);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ContainerCreationInfo> entity1 = new HttpEntity<>(containerCreationInfo, headers);

        HostConfig hostConfig = new HostConfig();
        hostConfig.setNetworkMode(deployInfo.getNetworkID());

        ResponseEntity<String> responseEntity1 = restTemplate.exchange(url, HttpMethod.POST, entity1, String.class);

        url = "http://" + ip + ":" + port + "/containers/" + deployInfo.getContainerName() + "/start";
        HttpEntity<String> entity2 = new HttpEntity<>(headers);
        ResponseEntity<String> responseEntity2 = restTemplate.exchange(url, HttpMethod.POST, null, String.class);


        ContainerNetworkConnectionInfo connectionInfo = new ContainerNetworkConnectionInfo();

        url = "http://" + ip + ":" + port + "/networks/bridge" + "/disconnect";
        connectionInfo.setContainer(deployInfo.getContainerName());
        HttpEntity<ContainerNetworkConnectionInfo> entity3 = new HttpEntity<>(connectionInfo, headers);
        ResponseEntity<String> responseEntity3 = restTemplate.exchange(url, HttpMethod.POST, entity3, String.class);


        url = "http://" + ip + ":" + port + "/networks/" + deployInfo.getNetworkID() + "/connect";
        connectionInfo.setContainer(deployInfo.getContainerName());
        HttpEntity<ContainerNetworkConnectionInfo> entity4 = new HttpEntity<>(connectionInfo, headers);
        ResponseEntity<String> responseEntity4 = restTemplate.exchange(url, HttpMethod.POST, entity4, String.class);


        return deployInfo.getContainerName();
    }
}
