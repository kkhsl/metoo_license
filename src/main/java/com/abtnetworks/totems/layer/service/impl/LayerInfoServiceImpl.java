//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.layer.service.impl;

import com.abtnetworks.totems.layer.common.ImageHandle;
import com.abtnetworks.totems.layer.dao.mysql.LayerInfoMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerNodeInfoMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerNodeNodeRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerNodeRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerNodeSubnetRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerNodeZoneRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerSubnetInfoMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerSubnetRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerSubnetZoneRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerZoneInfoMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerZoneNodeRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerZoneRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerZoneSubnetRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.LayerZoneZoneRelationMapper;
import com.abtnetworks.totems.layer.dao.mysql.TopoNodeMapper;
import com.abtnetworks.totems.layer.dao.mysql.UmsUserMapper;
import com.abtnetworks.totems.layer.domain.LayerInfoDO;
import com.abtnetworks.totems.layer.domain.LayerNodeInfoDO;
import com.abtnetworks.totems.layer.domain.LayerNodeNodeRelationDO;
import com.abtnetworks.totems.layer.domain.LayerNodeRelationDO;
import com.abtnetworks.totems.layer.domain.LayerNodeSubnetRelationDO;
import com.abtnetworks.totems.layer.domain.LayerNodeZoneRelationDO;
import com.abtnetworks.totems.layer.domain.LayerSubnetInfoDO;
import com.abtnetworks.totems.layer.domain.LayerSubnetRelationDO;
import com.abtnetworks.totems.layer.domain.LayerSubnetZoneRelationDO;
import com.abtnetworks.totems.layer.domain.LayerZoneInfoDO;
import com.abtnetworks.totems.layer.domain.LayerZoneNodeRelationDO;
import com.abtnetworks.totems.layer.domain.LayerZoneRelationDO;
import com.abtnetworks.totems.layer.domain.LayerZoneSubnetRelationDO;
import com.abtnetworks.totems.layer.domain.LayerZoneZoneRelationDO;
import com.abtnetworks.totems.layer.domain.TopoNodeDO;
import com.abtnetworks.totems.layer.dto.LayerJsonObjectDTO;
import com.abtnetworks.totems.layer.dto.LayerLineDTO;
import com.abtnetworks.totems.layer.dto.LayerPointDTO;
import com.abtnetworks.totems.layer.service.LayerInfoService;
import com.abtnetworks.totems.layer.vo.LayerContentVO;
import com.abtnetworks.totems.layer.vo.LayerJsonObjectVO;
import com.abtnetworks.totems.layer.vo.LayerLineVO;
import com.abtnetworks.totems.layer.vo.LayerPointVO;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.util.Assert;
import org.springframework.util.FileCopyUtils;

@Service("layerInfoService")
public class LayerInfoServiceImpl implements LayerInfoService {
    private static final Logger log = LoggerFactory.getLogger(LayerInfoServiceImpl.class);
    @Resource
    LayerInfoMapper layerInfoMapper;
    @Resource
    LayerNodeInfoMapper layerNodeInfoMapper;
    @Resource
    LayerSubnetInfoMapper layerSubnetInfoMapper;
    @Resource
    LayerZoneInfoMapper layerZoneInfoMapper;
    @Resource
    LayerNodeSubnetRelationMapper layerNodeSubnetRelationMapper;
    @Resource
    LayerNodeZoneRelationMapper layerNodeZoneRelationMapper;
    @Resource
    LayerNodeRelationMapper layerNodeRelationMapper;
    @Resource
    LayerSubnetRelationMapper layerSubnetRelationMapper;
    @Resource
    LayerZoneRelationMapper layerZoneRelationMapper;
    @Resource
    LayerZoneNodeRelationMapper layerZoneNodeRelationMapper;
    @Resource
    LayerSubnetZoneRelationMapper layerSubnetZoneRelationMapper;
    @Resource
    LayerZoneSubnetRelationMapper layerZoneSubnetRelationMapper;
    @Resource
    LayerNodeNodeRelationMapper layerNodeNodeRelationMapper;
    @Resource
    LayerZoneZoneRelationMapper layerZoneZoneRelationMapper;
    @Value("${layer.upload-file}")
    String uploadFile;
    @Value("${thirdPart.oauth2.client.clientId}")
    String clientId;
    @Resource
    TopoNodeMapper topoNodeMapper;
    @Resource
    UmsUserMapper umsUserMapper;
    private static final List<String> DEVICE_TYPE_LIST = Arrays.asList("device", "firewall", "router", "switch", "virtualNode", "load_balancer", "gap", "hostComputer");
    private static final List<String> ZONE_TYPE_LIST = Arrays.asList("zone", "logicZone", "logicZone2", "firewallZone", "assetGroup");

    public LayerInfoServiceImpl() {
    }

    public List<LayerInfoDO> listLayers(LayerInfoDO layerInfoDO) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        String adminBranchLevel = "00";
        String branchLevel = layerInfoDO.getBranchLevel();
//        if (StringUtils.isBlank(branchLevel)) {
//            if (this.clientId.equals(userName)) {
//                branchLevel = null;
//            } else {
//                branchLevel = this.umsUserMapper.getBranchLevelById(userName);
//            }
//        } else if (this.clientId.equals(userName) && !branchLevel.equals(adminBranchLevel)) {
//            branchLevel = adminBranchLevel + branchLevel;
//        }

        layerInfoDO.setBranchLevel(branchLevel);
        Integer layerType = layerInfoDO.getLayerType();
        if (layerType == null) {
            layerInfoDO.setLayerType(0);
        }

        List<LayerInfoDO> layerInfoDOS = this.layerInfoMapper.listLayers(layerInfoDO);
        Iterator var8 = layerInfoDOS.iterator();

        while(var8.hasNext()) {
            LayerInfoDO infoDO = (LayerInfoDO)var8.next();
            infoDO.setLayerId((Integer)null);
        }

        return layerInfoDOS;
    }

    public LayerInfoDO getLatestLayer() throws Exception {
        return this.layerInfoMapper.getLatestLayer();
    }

    public LayerJsonObjectVO getLayerById(String layerUuid) throws Exception {
        LayerContentVO layerContentVO = new LayerContentVO();
        List<LayerLineVO> links = new ArrayList();
        log.info("节点与子网的连线信息");
        LayerInfoDO layerNodeSubnetRelation = this.layerInfoMapper.listLayerNodeSubnetRelation(layerUuid);
        List<LayerNodeSubnetRelationDO> layerNodeSubnetRelationDOS = layerNodeSubnetRelation.getLayerNodeSubnetRelationDOS();

        LayerLineVO layerLineVO;
        for(Iterator var6 = layerNodeSubnetRelationDOS.iterator(); var6.hasNext(); links.add(layerLineVO)) {
            LayerNodeSubnetRelationDO layerNodeSubnetRelationDO = (LayerNodeSubnetRelationDO)var6.next();
            layerLineVO = new LayerLineVO();
            BeanUtils.copyProperties(layerNodeSubnetRelationDO, layerLineVO);
            if ("subnetToNode".equals(layerNodeSubnetRelationDO.getLineType())) {
                layerLineVO.setFrom(layerNodeSubnetRelationDO.getLayerSubnetInfoDO().getSubnetUuid());
                layerLineVO.setTo(layerNodeSubnetRelationDO.getLayerNodeInfoDO().getNodeUuid());
            } else {
                layerLineVO.setFrom(layerNodeSubnetRelationDO.getLayerNodeInfoDO().getNodeUuid());
                layerLineVO.setTo(layerNodeSubnetRelationDO.getLayerSubnetInfoDO().getSubnetUuid());
            }
        }

        log.info("节点与域的连线信息");
        LayerInfoDO layerNodeZoneRelation = this.layerInfoMapper.listLayerNodeZoneRelation(layerUuid);
        List<LayerNodeZoneRelationDO> layerNodeZoneRelationDOS = layerNodeZoneRelation.getLayerNodeZoneRelationDOS();


        for(Iterator var34 = layerNodeZoneRelationDOS.iterator(); var34.hasNext(); links.add(layerLineVO)) {
            LayerNodeZoneRelationDO layerNodeZoneRelationDO = (LayerNodeZoneRelationDO)var34.next();
            layerLineVO = new LayerLineVO();
            BeanUtils.copyProperties(layerNodeZoneRelationDO, layerLineVO);
            if ("zoneToSubnet".equals(layerNodeZoneRelationDO.getLineType())) {
                layerLineVO.setFrom(layerNodeZoneRelationDO.getLayerZoneInfoDO().getZoneUuid());
                layerLineVO.setTo(layerNodeZoneRelationDO.getLayerNodeInfoDO().getNodeUuid());
            } else {
                layerLineVO.setFrom(layerNodeZoneRelationDO.getLayerNodeInfoDO().getNodeUuid());
                layerLineVO.setTo(layerNodeZoneRelationDO.getLayerZoneInfoDO().getZoneUuid());
            }
        }

        log.info("子网与域的连线信息");
        LayerInfoDO layerSubnetZoneRelation = this.layerInfoMapper.listLayerSubnetZoneRelation(layerUuid);
        List<LayerSubnetZoneRelationDO> layerSubnetZoneRelationDOS = layerSubnetZoneRelation.getLayerSubnetZoneRelationDOS();

        for(Iterator var37 = layerSubnetZoneRelationDOS.iterator(); var37.hasNext(); links.add(layerLineVO)) {
            LayerSubnetZoneRelationDO layerSubnetZoneRelationDO = (LayerSubnetZoneRelationDO)var37.next();
            layerLineVO = new LayerLineVO();
            BeanUtils.copyProperties(layerSubnetZoneRelationDO, layerLineVO);
            if ("zoneToSubnet".equals(layerSubnetZoneRelationDO.getLineType())) {
                layerLineVO.setFrom(layerSubnetZoneRelationDO.getLayerZoneInfoDO().getZoneUuid());
                layerLineVO.setTo(layerSubnetZoneRelationDO.getLayerSubnetInfoDO().getSubnetUuid());
            } else {
                layerLineVO.setFrom(layerSubnetZoneRelationDO.getLayerSubnetInfoDO().getSubnetUuid());
                layerLineVO.setTo(layerSubnetZoneRelationDO.getLayerZoneInfoDO().getZoneUuid());
            }
        }

        log.info("节点与节点的连线信息");
        LayerInfoDO layerNodeNodeRelation = this.layerInfoMapper.listLayerNodeNodeRelation(layerUuid);
        List<LayerNodeNodeRelationDO> layerNodeNodeRelationDOS = layerNodeNodeRelation.getLayerNodeNodeRelationDOS();
        Iterator var40 = layerNodeNodeRelationDOS.iterator();

        while(var40.hasNext()) {
            LayerNodeNodeRelationDO layerNodeNodeRelationDO = (LayerNodeNodeRelationDO)var40.next();
            layerLineVO = new LayerLineVO();
            BeanUtils.copyProperties(layerNodeNodeRelationDO, layerLineVO);
            layerLineVO.setFrom(layerNodeNodeRelationDO.getLayerNodeInfoFrom().getNodeUuid());
            layerLineVO.setTo(layerNodeNodeRelationDO.getLayerNodeInfoTo().getNodeUuid());
            links.add(layerLineVO);
        }

        log.info("域与域的连线信息");
        LayerInfoDO layerZoneZoneRelation = this.layerInfoMapper.listLayerZoneZoneRelation(layerUuid);
        List<LayerZoneZoneRelationDO> layerZoneZoneRelationDOS = layerZoneZoneRelation.getLayerZoneZoneRelationDOS();
        Iterator var43 = layerZoneZoneRelationDOS.iterator();

        while(var43.hasNext()) {
            LayerZoneZoneRelationDO layerZoneZoneRelationDO = (LayerZoneZoneRelationDO)var43.next();
            layerLineVO = new LayerLineVO();
            BeanUtils.copyProperties(layerZoneZoneRelationDO, layerLineVO);
            layerLineVO.setFrom(layerZoneZoneRelationDO.getLayerZoneInfoFrom().getZoneUuid());
            layerLineVO.setTo(layerZoneZoneRelationDO.getLayerZoneInfoTo().getZoneUuid());
            links.add(layerLineVO);
        }

        layerContentVO.setLinks(links);
        Map<String, LayerPointVO> layoutMap = new LinkedHashMap();
        log.info("域的信息");
        LayerInfoDO layerZoneRelation = this.layerInfoMapper.listLayerZoneRelation(layerUuid);
        List<LayerZoneRelationDO> layerZoneRelationDOS = layerZoneRelation.getLayerZoneRelationDOS();

        LayerZoneRelationDO layerZoneRelationDO;
        LayerPointVO layerPointVO;
        for(Iterator var17 = layerZoneRelationDOS.iterator(); var17.hasNext(); layoutMap.put(layerZoneRelationDO.getLayerZoneInfoDO().getZoneUuid(), layerPointVO)) {
            layerZoneRelationDO = (LayerZoneRelationDO)var17.next();
            layerPointVO = new LayerPointVO();
            BeanUtils.copyProperties(layerZoneRelationDO, layerPointVO);
            layerPointVO.setKey(layerZoneRelationDO.getLayerZoneInfoDO().getZoneUuid());
            layerPointVO.setNodeType(layerZoneRelationDO.getLayerZoneInfoDO().getZoneType());
            layerPointVO.setNodeName(layerZoneRelationDO.getLayerZoneInfoDO().getZoneName());
            layerPointVO.setNodeMessage(layerZoneRelationDO.getLayerZoneInfoDO().getZoneMessage());
            layerPointVO.setNodeState(layerZoneRelationDO.getLayerZoneInfoDO().getZoneState());
            if (layerZoneRelationDO.getZoneParentIdFk() != null) {
                String zoneParentUuid = this.layerZoneInfoMapper.selectByZoneId(layerZoneRelationDO.getZoneParentIdFk()).getZoneUuid();
                layerPointVO.setGroup(zoneParentUuid);
            }
        }

        log.info("节点的信息");
        List<TopoNodeDO> topoNodeDOList = this.topoNodeMapper.listTopoNode((String)null);
        Map<String, Boolean> nodeUuidSkipMap = (Map)topoNodeDOList.stream().filter((topoNodeDO) -> {
            return topoNodeDO.getUuid() != null;
        }).collect(Collectors.toMap(TopoNodeDO::getUuid, (topoNodeDO) -> {
            return topoNodeDO.getSkipAnalysis() != null && Boolean.parseBoolean(topoNodeDO.getSkipAnalysis());
        }));
        LayerInfoDO layerNodeRelation = this.layerInfoMapper.listLayerNodeRelation(layerUuid);
        List<LayerNodeRelationDO> layerNodeRelationDOS = layerNodeRelation.getLayerNodeRelationDOS();

        LayerNodeInfoDO layerNodeInfoDO;
        for(Iterator var21 = layerNodeRelationDOS.iterator(); var21.hasNext(); layoutMap.put(layerNodeInfoDO.getNodeUuid(), layerPointVO)) {
            LayerNodeRelationDO layerNodeRelationDO = (LayerNodeRelationDO)var21.next();
            layerPointVO = new LayerPointVO();
            BeanUtils.copyProperties(layerNodeRelationDO, layerPointVO);
            layerNodeInfoDO = layerNodeRelationDO.getLayerNodeInfoDO();
            layerPointVO.setKey(layerNodeInfoDO.getNodeUuid());
            layerPointVO.setNodeType(layerNodeInfoDO.getNodeType());
            layerPointVO.setNodeName(layerNodeInfoDO.getNodeName());
            layerPointVO.setNodeMessage(layerNodeInfoDO.getNodeMessage());
            layerPointVO.setNodeState(layerNodeInfoDO.getNodeState());
            log.debug("查询设备是否跳过路径分析");
            if (StringUtils.equalsAny(layerNodeInfoDO.getNodeType(), new CharSequence[]{"firewall", "router", "switch", "load_balancer", "gap"}) && nodeUuidSkipMap.containsKey(layerNodeInfoDO.getNodeUuid())) {
                layerPointVO.setSkipAnalysis((Boolean)nodeUuidSkipMap.get(layerNodeInfoDO.getNodeUuid()));
            }
        }

        log.info("节点所属域");
        LayerInfoDO layerZoneNodeRelation = this.layerInfoMapper.listLayerZoneNodeRelation(layerUuid);
        List<LayerZoneNodeRelationDO> layerZoneNodeRelationDOS = layerZoneNodeRelation.getLayerZoneNodeRelationDOS();
        Iterator var53 = layerZoneNodeRelationDOS.iterator();

        while(var53.hasNext()) {
            LayerZoneNodeRelationDO layerZoneNodeRelationDO = (LayerZoneNodeRelationDO)var53.next();
            String nodeUuid = layerZoneNodeRelationDO.getLayerNodeInfoDO().getNodeUuid();
            if (layoutMap.containsKey(nodeUuid)) {
                String zoneUuid = layerZoneNodeRelationDO.getLayerZoneInfoDO().getZoneUuid();
                layerPointVO = (LayerPointVO)layoutMap.get(nodeUuid);
                layerPointVO.setGroup(zoneUuid);
                layoutMap.put(nodeUuid, layerPointVO);
            }
        }

        log.info("子网的信息");
        LayerInfoDO layerSubnetRelation = this.layerInfoMapper.listLayerSubnetRelation(layerUuid);
        List<LayerSubnetRelationDO> layerSubnetRelationDOS = layerSubnetRelation.getLayerSubnetRelationDOS();
        Iterator var57 = layerSubnetRelationDOS.iterator();

        while(var57.hasNext()) {
            LayerSubnetRelationDO layerSubnetRelationDO = (LayerSubnetRelationDO)var57.next();
            layerPointVO = new LayerPointVO();
            BeanUtils.copyProperties(layerSubnetRelationDO, layerPointVO);
            layerPointVO.setKey(layerSubnetRelationDO.getLayerSubnetInfoDO().getSubnetUuid());
            layerPointVO.setNodeType("subnet");
            layerPointVO.setNodeName(layerSubnetRelationDO.getLayerSubnetInfoDO().getSubnetName());
            layerPointVO.setNodeMessage(layerSubnetRelationDO.getLayerSubnetInfoDO().getSubnetMessage());
            layerPointVO.setNodeState(layerSubnetRelationDO.getLayerSubnetInfoDO().getSubnetState());
            layoutMap.put(layerSubnetRelationDO.getLayerSubnetInfoDO().getSubnetUuid(), layerPointVO);
        }

        log.info("子网所属域");
        LayerInfoDO layerZoneSubnetRelation = this.layerInfoMapper.listLayerZoneSubnetRelation(layerUuid);
        List<LayerZoneSubnetRelationDO> layerZoneSubnetRelationDOS = layerZoneSubnetRelation.getLayerZoneSubnetRelationDOS();
        Iterator var61 = layerZoneSubnetRelationDOS.iterator();

        while(var61.hasNext()) {
            LayerZoneSubnetRelationDO layerZoneSubnetRelationDO = (LayerZoneSubnetRelationDO)var61.next();
            String subnetUuid = layerZoneSubnetRelationDO.getLayerSubnetInfoDO().getSubnetUuid();
            if (layoutMap.containsKey(subnetUuid)) {
                String zoneUuid = layerZoneSubnetRelationDO.getLayerZoneInfoDO().getZoneUuid();
                layerPointVO = (LayerPointVO)layoutMap.get(subnetUuid);
                layerPointVO.setGroup(zoneUuid);
                layerPointVO.setZoneNodeStatus(layerZoneSubnetRelationDO.getZoneNodeState());
                layoutMap.put(subnetUuid, layerPointVO);
            }
        }

        layerContentVO.setLayout(layoutMap);
        log.info("图层信息");
        LayerJsonObjectVO layerJsonObjectVO = new LayerJsonObjectVO();
        layerJsonObjectVO.setName(layerZoneRelation.getLayerName());
        layerJsonObjectVO.setDesc(layerZoneRelation.getLayerDesc());
        layerJsonObjectVO.setLayerUuid(layerUuid);
        layerJsonObjectVO.setContent(layerContentVO);
        return layerJsonObjectVO;
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public int saveLayer(LayerJsonObjectDTO layerJsonObjectDTO) {
        try {
            log.debug("判断是添加还是更新");
            boolean isAdd = true;
            String layerUuid = layerJsonObjectDTO.getLayerUuid();
            if (layerUuid != null && layerUuid.length() > 0) {
                isAdd = false;
            }

            LayerInfoDO layerInfoDO = new LayerInfoDO();
            int layerId;
            String userName;
            if (isAdd) {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                userName = authentication.getName();
                String branchLevel = layerJsonObjectDTO.getBranchLevel();
                if (StringUtils.isBlank(branchLevel)) {
                    if (!this.clientId.equals(userName)) {
                        branchLevel = this.umsUserMapper.getBranchLevelById(userName);
                    } else {
                        branchLevel = "00";
                    }
                }

                layerInfoDO.setBranchLevel(branchLevel);
                layerInfoDO.setLayerName(layerJsonObjectDTO.getName());
                layerInfoDO.setLayerDesc(layerJsonObjectDTO.getDesc());
                layerUuid = UUID.randomUUID().toString().replaceAll("-", "");
                layerInfoDO.setLayerUuid(layerUuid);
                layerInfoDO.setCreatedTime(new Date());
                layerInfoDO.setModifiedTime(new Date());
                Integer countLayers = this.layerInfoMapper.countLayers();
                layerInfoDO.setIsDefault(countLayers > 0 ? "false" : "true");
                layerInfoDO.setLayerType(layerJsonObjectDTO.getLayerType() == null ? 0 : layerJsonObjectDTO.getLayerType());
                Integer countBranchLayers = this.layerInfoMapper.countBranchLayers(branchLevel);
                layerInfoDO.setIsBranchDefault(countBranchLayers > 0 ? "false" : "true");
                this.layerInfoMapper.insertLayer(layerInfoDO);
                layerId = layerInfoDO.getLayerId();
            } else {
                layerInfoDO.setLayerUuid(layerUuid);
                layerInfoDO.setModifiedTime(new Date());
                this.layerInfoMapper.updateLayer(layerInfoDO);
                layerId = this.layerInfoMapper.selectByLayerUuid(layerUuid).getLayerId();
                this.layerNodeRelationMapper.delete(layerId);
                this.layerSubnetRelationMapper.delete(layerId);
                this.layerZoneRelationMapper.delete(layerId);
                this.layerNodeSubnetRelationMapper.delete(layerId);
                this.layerNodeZoneRelationMapper.delete(layerId);
                this.layerZoneNodeRelationMapper.delete(layerId);
                this.layerZoneSubnetRelationMapper.delete(layerId);
                this.layerSubnetZoneRelationMapper.delete(layerId);
                this.layerNodeNodeRelationMapper.delete(layerId);
                this.layerZoneZoneRelationMapper.delete(layerId);
            }

            log.info("处理缩略图");
            String baseUrl = layerJsonObjectDTO.getBaseUrl();
            if (baseUrl != null) {
                userName = this.uploadFile + layerUuid + ".png";
                boolean generateImage = ImageHandle.generateImage(baseUrl, userName);
                if (!generateImage) {
                    throw new Exception("缩略图上传失败");
                }
            }

            log.info("新增图层节点信息");
            Map<String, Integer> uuidIdMap = new HashMap(16);
            Map<String, String> uuidNodeType = new HashMap(16);
            List<LayerPointDTO> layerPointDTOS = layerJsonObjectDTO.getContent().getLayout();

            LayerPointDTO layerPointDTO;
            String nodeType;
            String from;
            String to;
            String lineType;
            int keyId;
            Iterator var33;
            for(var33 = layerPointDTOS.iterator(); var33.hasNext(); uuidIdMap.put(from, keyId)) {
                layerPointDTO = (LayerPointDTO)var33.next();
                nodeType = layerPointDTO.getNodeType();
                from = layerPointDTO.getKey();
                to = layerPointDTO.getNodeName();
                lineType = layerPointDTO.getNodeMessage();
                if (DEVICE_TYPE_LIST.contains(nodeType)) {
                    uuidNodeType.put(from, "device");
                    LayerNodeInfoDO layerNodeInfoDO = this.layerNodeInfoMapper.selectByNodeUuid(from);
                    if (layerNodeInfoDO != null) {
                        keyId = layerNodeInfoDO.getNodeId();
                        layerNodeInfoDO.setNodeMessage(lineType);
                        layerNodeInfoDO.setNodeName(to);
                        this.layerNodeInfoMapper.updateByNodeUuid(layerNodeInfoDO);
                    } else {
                        layerNodeInfoDO = new LayerNodeInfoDO();
                        layerNodeInfoDO.setNodeUuid(from);
                        layerNodeInfoDO.setNodeType(nodeType);
                        layerNodeInfoDO.setNodeName(to);
                        layerNodeInfoDO.setNodeMessage(lineType);
                        layerNodeInfoDO.setNodeState("existence");
                        this.layerNodeInfoMapper.insert(layerNodeInfoDO);
                        keyId = layerNodeInfoDO.getNodeId();
                    }
                } else if ("subnet".equals(nodeType)) {
                    uuidNodeType.put(from, "subnet");
                    LayerSubnetInfoDO layerSubnetInfoDO = this.layerSubnetInfoMapper.selectBySubnetUuid(from);
                    if (layerSubnetInfoDO != null) {
                        keyId = layerSubnetInfoDO.getSubnetId();
                        layerSubnetInfoDO.setSubnetName(to);
                        layerSubnetInfoDO.setSubnetMessage(lineType);
                        this.layerSubnetInfoMapper.updateBySubnetUuid(layerSubnetInfoDO);
                    } else {
                        layerSubnetInfoDO = new LayerSubnetInfoDO();
                        layerSubnetInfoDO.setSubnetUuid(from);
                        layerSubnetInfoDO.setSubnetName(to);
                        layerSubnetInfoDO.setSubnetMessage(lineType);
                        layerSubnetInfoDO.setSubnetState("existence");
                        this.layerSubnetInfoMapper.insert(layerSubnetInfoDO);
                        keyId = layerSubnetInfoDO.getSubnetId();
                    }
                } else {
                    if (!ZONE_TYPE_LIST.contains(nodeType)) {
                        throw new Exception("节点类型错误：" + nodeType);
                    }

                    uuidNodeType.put(from, "zone");
                    LayerZoneInfoDO layerZoneInfoDO = this.layerZoneInfoMapper.selectByZoneUuid(from);
                    if (layerZoneInfoDO != null) {
                        keyId = layerZoneInfoDO.getZoneId();
                        layerZoneInfoDO.setZoneName(to);
                        layerZoneInfoDO.setZoneMessage(lineType);
                        this.layerZoneInfoMapper.updateByZoneUuid(layerZoneInfoDO);
                    } else {
                        layerZoneInfoDO = new LayerZoneInfoDO();
                        layerZoneInfoDO.setZoneUuid(from);
                        layerZoneInfoDO.setZoneName(to);
                        layerZoneInfoDO.setZoneType(nodeType);
                        layerZoneInfoDO.setZoneMessage(lineType);
                        layerZoneInfoDO.setZoneState("existence");
                        this.layerZoneInfoMapper.insert(layerZoneInfoDO);
                        keyId = layerZoneInfoDO.getZoneId();
                    }
                }
            }

            log.info("新增图层关联信息");
            var33 = layerPointDTOS.iterator();

            while(var33.hasNext()) {
                layerPointDTO = (LayerPointDTO)var33.next();
                nodeType = layerPointDTO.getNodeType();
                from = layerPointDTO.getKey();
                to = layerPointDTO.getGroup();
                if (to != null && !uuidIdMap.containsKey(to)) {
                    log.error("数据异常：不存在uuid为group:{}", to);
                    to = null;
                }

                if (DEVICE_TYPE_LIST.contains(nodeType)) {
                    LayerNodeRelationDO layerNodeRelationDO = new LayerNodeRelationDO();
                    layerNodeRelationDO.setLayerIdFk(layerId);
                    layerNodeRelationDO.setNodeIdFk((Integer)uuidIdMap.get(from));
                    BeanUtils.copyProperties(layerPointDTO, layerNodeRelationDO);
                    this.layerNodeRelationMapper.insert(layerNodeRelationDO);
                    if (to != null) {
                        LayerZoneNodeRelationDO layerZoneNodeRelationDO = new LayerZoneNodeRelationDO();
                        layerZoneNodeRelationDO.setLayerIdFk(layerId);
                        layerZoneNodeRelationDO.setZoneIdFk((Integer)uuidIdMap.get(to));
                        layerZoneNodeRelationDO.setNodeIdFk((Integer)uuidIdMap.get(from));
                        this.layerZoneNodeRelationMapper.insert(layerZoneNodeRelationDO);
                    }
                } else if ("subnet".equals(nodeType)) {
                    LayerSubnetRelationDO layerSubnetRelationDO = new LayerSubnetRelationDO();
                    layerSubnetRelationDO.setLayerIdFk(layerId);
                    layerSubnetRelationDO.setSubnetIdFk((Integer)uuidIdMap.get(from));
                    BeanUtils.copyProperties(layerPointDTO, layerSubnetRelationDO);
                    this.layerSubnetRelationMapper.insert(layerSubnetRelationDO);
                    if (to != null) {
                        LayerZoneSubnetRelationDO layerZoneSubnetRelationDO = new LayerZoneSubnetRelationDO();
                        layerZoneSubnetRelationDO.setLayerIdFk(layerId);
                        layerZoneSubnetRelationDO.setZoneIdFk((Integer)uuidIdMap.get(to));
                        layerZoneSubnetRelationDO.setSubnetIdFk((Integer)uuidIdMap.get(from));
                        layerZoneSubnetRelationDO.setZoneNodeState(layerPointDTO.getZoneNodeStatus() == null ? "existence" : layerPointDTO.getZoneNodeStatus());
                        this.layerZoneSubnetRelationMapper.insert(layerZoneSubnetRelationDO);
                    }
                } else {
                    if (!ZONE_TYPE_LIST.contains(nodeType)) {
                        throw new Exception("节点类型错误：" + nodeType);
                    }

                    LayerZoneRelationDO layerZoneRelationDO = new LayerZoneRelationDO();
                    layerZoneRelationDO.setLayerIdFk(layerId);
                    layerZoneRelationDO.setZoneIdFk((Integer)uuidIdMap.get(from));
                    if (to != null) {
                        layerZoneRelationDO.setZoneParentIdFk((Integer)uuidIdMap.get(to));
                    }

                    BeanUtils.copyProperties(layerPointDTO, layerZoneRelationDO);
                    this.layerZoneRelationMapper.insert(layerZoneRelationDO);
                }
            }

            log.info("新增图层连线信息");
            List<LayerLineDTO> layerLineDTOS = layerJsonObjectDTO.getContent().getLinks();
            Iterator var35 = layerLineDTOS.iterator();

            while(true) {
                while(true) {
                    while(var35.hasNext()) {
                        LayerLineDTO layerLineDTO = (LayerLineDTO)var35.next();
                        from = layerLineDTO.getFrom();
                        if (from != null && uuidIdMap.containsKey(from)) {
                            to = layerLineDTO.getTo();
                            if (to != null && uuidIdMap.containsKey(to)) {
                                log.info("计算线条类型");
                                lineType = null;
                                String nodeTypeFrom = (String)uuidNodeType.get(from);
                                Assert.isTrue(StringUtils.equalsAny(nodeTypeFrom, new CharSequence[]{"device", "subnet", "zone"}), "from key[" + from + "] 类型未知");
                                String nodeTypeTo = (String)uuidNodeType.get(to);
                                Assert.isTrue(StringUtils.equalsAny(nodeTypeTo, new CharSequence[]{"device", "subnet", "zone"}), "to key[" + to + "] 类型未知");
                                log.debug("是否需要反转from to的标志");
                                byte var19 = -1;
                                switch(nodeTypeFrom.hashCode()) {
                                    case -1335157162:
                                        if (nodeTypeFrom.equals("device")) {
                                            var19 = 0;
                                        }
                                        break;
                                    case -891534499:
                                        if (nodeTypeFrom.equals("subnet")) {
                                            var19 = 1;
                                        }
                                        break;
                                    case 3744684:
                                        if (nodeTypeFrom.equals("zone")) {
                                            var19 = 2;
                                        }
                                }

                                byte var21;
                                label202:
                                switch(var19) {
                                    case 0:
                                        var21 = -1;
                                        switch(nodeTypeTo.hashCode()) {
                                            case -1335157162:
                                                if (nodeTypeTo.equals("device")) {
                                                    var21 = 0;
                                                }
                                                break;
                                            case -891534499:
                                                if (nodeTypeTo.equals("subnet")) {
                                                    var21 = 1;
                                                }
                                                break;
                                            case 3744684:
                                                if (nodeTypeTo.equals("zone")) {
                                                    var21 = 2;
                                                }
                                        }

                                        switch(var21) {
                                            case 0:
                                                lineType = "nodeToNode";
                                                break label202;
                                            case 1:
                                                lineType = "nodeToSubnet";
                                                break label202;
                                            case 2:
                                                lineType = "nodeToZone";
                                            default:
                                                break label202;
                                        }
                                    case 1:
                                        var21 = -1;
                                        switch(nodeTypeTo.hashCode()) {
                                            case -1335157162:
                                                if (nodeTypeTo.equals("device")) {
                                                    var21 = 0;
                                                }
                                                break;
                                            case -891534499:
                                                if (nodeTypeTo.equals("subnet")) {
                                                    var21 = 1;
                                                }
                                                break;
                                            case 3744684:
                                                if (nodeTypeTo.equals("zone")) {
                                                    var21 = 2;
                                                }
                                        }

                                        switch(var21) {
                                            case 0:
                                                lineType = "subnetToNode";
                                                break label202;
                                            case 1:
                                                throw new Exception("不支持子网到子网的连线");
                                            case 2:
                                                lineType = "subnetToZone";
                                            default:
                                                break label202;
                                        }
                                    case 2:
                                        var21 = -1;
                                        switch(nodeTypeTo.hashCode()) {
                                            case -1335157162:
                                                if (nodeTypeTo.equals("device")) {
                                                    var21 = 0;
                                                }
                                                break;
                                            case -891534499:
                                                if (nodeTypeTo.equals("subnet")) {
                                                    var21 = 1;
                                                }
                                                break;
                                            case 3744684:
                                                if (nodeTypeTo.equals("zone")) {
                                                    var21 = 2;
                                                }
                                        }

                                        switch(var21) {
                                            case 0:
                                                lineType = "zoneToNode";
                                                break;
                                            case 1:
                                                lineType = "zoneToSubnet";
                                                break;
                                            case 2:
                                                lineType = "zoneToZone";
                                        }
                                }

                                layerLineDTO.setLineType(lineType);
                                Integer fromId = (Integer)uuidIdMap.get(from);
                                Integer toId = (Integer)uuidIdMap.get(to);
                                if (StringUtils.isEmpty(layerLineDTO.getLinkState())) {
                                    layerLineDTO.setLinkState("existence");
                                }

                                String var20 = (String)Objects.requireNonNull(lineType);
                                var21 = -1;
                                switch(var20.hashCode()) {
                                    case -874833431:
                                        if (var20.equals("zoneToNode")) {
                                            var21 = 3;
                                        }
                                        break;
                                    case -874475629:
                                        if (var20.equals("zoneToZone")) {
                                            var21 = 7;
                                        }
                                        break;
                                    case 1150939354:
                                        if (var20.equals("subnetToNode")) {
                                            var21 = 1;
                                        }
                                        break;
                                    case 1151297156:
                                        if (var20.equals("subnetToZone")) {
                                            var21 = 4;
                                        }
                                        break;
                                    case 1235094463:
                                        if (var20.equals("nodeToNode")) {
                                            var21 = 6;
                                        }
                                        break;
                                    case 1235452265:
                                        if (var20.equals("nodeToZone")) {
                                            var21 = 2;
                                        }
                                        break;
                                    case 1247302020:
                                        if (var20.equals("zoneToSubnet")) {
                                            var21 = 5;
                                        }
                                        break;
                                    case 1663444442:
                                        if (var20.equals("nodeToSubnet")) {
                                            var21 = 0;
                                        }
                                }

                                switch(var21) {
                                    case 0:
                                    case 1:
                                        LayerNodeSubnetRelationDO layerNodeSubnetRelationDO = new LayerNodeSubnetRelationDO();
                                        BeanUtils.copyProperties(layerLineDTO, layerNodeSubnetRelationDO);
                                        layerNodeSubnetRelationDO.setLayerIdFk(layerId);
                                        if ("subnetToNode".equals(lineType)) {
                                            layerNodeSubnetRelationDO.setNodeIdFk(toId);
                                            layerNodeSubnetRelationDO.setSubnetIdFk(fromId);
                                        } else {
                                            layerNodeSubnetRelationDO.setNodeIdFk(fromId);
                                            layerNodeSubnetRelationDO.setSubnetIdFk(toId);
                                        }

                                        this.layerNodeSubnetRelationMapper.insert(layerNodeSubnetRelationDO);
                                        break;
                                    case 2:
                                    case 3:
                                        LayerNodeZoneRelationDO layerNodeZoneRelationDO = new LayerNodeZoneRelationDO();
                                        BeanUtils.copyProperties(layerLineDTO, layerNodeZoneRelationDO);
                                        layerNodeZoneRelationDO.setLayerIdFk(layerId);
                                        if ("zoneToNode".equals(lineType)) {
                                            layerNodeZoneRelationDO.setNodeIdFk(toId);
                                            layerNodeZoneRelationDO.setZoneIdFk(fromId);
                                        } else {
                                            layerNodeZoneRelationDO.setNodeIdFk(fromId);
                                            layerNodeZoneRelationDO.setZoneIdFk(toId);
                                        }

                                        this.layerNodeZoneRelationMapper.insert(layerNodeZoneRelationDO);
                                        break;
                                    case 4:
                                    case 5:
                                        LayerSubnetZoneRelationDO layerSubnetZoneRelationDO = new LayerSubnetZoneRelationDO();
                                        BeanUtils.copyProperties(layerLineDTO, layerSubnetZoneRelationDO);
                                        layerSubnetZoneRelationDO.setLayerIdFk(layerId);
                                        if ("zoneToSubnet".equals(lineType)) {
                                            layerSubnetZoneRelationDO.setSubnetIdFk(toId);
                                            layerSubnetZoneRelationDO.setZoneIdFk(fromId);
                                        } else {
                                            layerSubnetZoneRelationDO.setSubnetIdFk(fromId);
                                            layerSubnetZoneRelationDO.setZoneIdFk(toId);
                                        }

                                        this.layerSubnetZoneRelationMapper.insert(layerSubnetZoneRelationDO);
                                        break;
                                    case 6:
                                        LayerNodeNodeRelationDO layerNodeNodeRelationDO = new LayerNodeNodeRelationDO();
                                        BeanUtils.copyProperties(layerLineDTO, layerNodeNodeRelationDO);
                                        layerNodeNodeRelationDO.setLayerIdFk(layerId);
                                        layerNodeNodeRelationDO.setNodeIdFromFk(fromId);
                                        layerNodeNodeRelationDO.setNodeIdToFk(toId);
                                        this.layerNodeNodeRelationMapper.insert(layerNodeNodeRelationDO);
                                        break;
                                    case 7:
                                        LayerZoneZoneRelationDO layerZoneZoneRelationDO = new LayerZoneZoneRelationDO();
                                        BeanUtils.copyProperties(layerLineDTO, layerZoneZoneRelationDO);
                                        layerZoneZoneRelationDO.setLayerIdFk(layerId);
                                        layerZoneZoneRelationDO.setZoneIdFromFk(fromId);
                                        layerZoneZoneRelationDO.setZoneIdToFk(toId);
                                        this.layerZoneZoneRelationMapper.insert(layerZoneZoneRelationDO);
                                        break;
                                    default:
                                        throw new Exception("连线类型：" + lineType + " 不存在");
                                }
                            } else {
                                log.error("连线信息参数存在异常：to:{}", to);
                            }
                        } else {
                            log.error("连线信息参数存在异常：from:{}", from);
                        }
                    }

                    return 0;
                }
            }
        } catch (Exception var27) {
            log.error("图层添加异常", var27);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public int editLayer(String layerUuid, String layerName) {
        try {
            LayerInfoDO layerInfoDO = new LayerInfoDO();
            layerInfoDO.setLayerUuid(layerUuid);
            layerInfoDO.setLayerName(layerName);
            layerInfoDO.setModifiedTime(new Date());
            int result = this.layerInfoMapper.updateLayer(layerInfoDO);
            return result > 0 ? 0 : 2;
        } catch (Exception var5) {
            log.error("图层名字修改错误", var5);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return var5.getMessage().contains("Data too long for column 'layer_name'") ? 1 : 3;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public int editLayerBranch(String layerUuids, String branchLevel) throws Exception {
        try {
            String[] layerUuidArray = StringUtils.split(layerUuids, ",");
            int result = this.layerInfoMapper.updateLayerBranch(layerUuidArray, branchLevel);
            return result > 0 ? 0 : 2;
        } catch (Exception var5) {
            log.error("图层组织机构修改错误", var5);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public int deleteLayer(String layerUuids) {
        try {
            String[] layerUuidArray = StringUtils.split(layerUuids, ",");
            int result = this.layerInfoMapper.deleteLayer(layerUuidArray);
            log.info("删除缩略图");
            if (result <= 0) {
                return 1;
            } else {
                String[] var4 = layerUuidArray;
                int var5 = layerUuidArray.length;

                for(int var6 = 0; var6 < var5; ++var6) {
                    String layerUuid = var4[var6];
                    FileUtils.forceDeleteOnExit(new File(this.uploadFile + layerUuid + ".png"));
                }

                return 0;
            }
        } catch (Exception var8) {
            log.error("图层删除失败", var8);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public int setDefaultLayer(String layerUuid) {
        try {
            this.layerInfoMapper.updateDefaultLayerToFalse();
            this.layerInfoMapper.updateDefaultLayerToTrue(layerUuid);
            return 0;
        } catch (Exception var3) {
            log.error("默认图层设置失败", var3);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public int setBranchDefaultLayer(String layerUuid, String branchLevel) throws Exception {
        try {
            this.layerInfoMapper.updateBranchDefaultLayerToFalse(branchLevel);
            this.layerInfoMapper.updateBranchDefaultLayerToTrue(branchLevel, layerUuid);
            return 0;
        } catch (Exception var4) {
            log.error("默认组织机构图层设置失败", var4);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
    }

    public LayerJsonObjectVO getDefaultLayer(String layerUuid) throws Exception {
        if (StringUtils.isBlank(layerUuid)) {
            LayerInfoDO layerInfoDO = new LayerInfoDO();
            layerInfoDO.setIsDefault("true");
            List<LayerInfoDO> layerInfoDOS = this.layerInfoMapper.listLayers(layerInfoDO);
            if (layerInfoDOS.size() != 1) {
                throw new Exception("没有设置默认图层");
            }

            layerUuid = ((LayerInfoDO)layerInfoDOS.get(0)).getLayerUuid();
        }

        return this.getLayerById(layerUuid);
    }

    public List<String> listNodeByLayerAndSubnet(String layerUuid, String subnetUuid) throws Exception {
        return this.layerNodeSubnetRelationMapper.listNodeByLayerAndSubnet(layerUuid, subnetUuid);
    }

    public List<String> listSubnetByLayerAndNode(String layerUuid, String nodeUuid) throws Exception {
        return this.layerNodeSubnetRelationMapper.listSubnetByLayerAndNode(layerUuid, nodeUuid);
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public int copyLayer(String layerUuid, String layerName, String branchLevel) {
        try {
            LayerInfoDO selectByLayerUuid = this.layerInfoMapper.selectByLayerUuid(layerUuid);
            LayerInfoDO layerInfoDO = new LayerInfoDO();
            if (StringUtils.isEmpty(layerName)) {
                layerInfoDO.setLayerName(selectByLayerUuid.getLayerName() + "-副本");
            } else {
                layerInfoDO.setLayerName(layerName);
            }

            layerInfoDO.setLayerDesc(selectByLayerUuid.getLayerDesc());
            String copyLayerUuid = UUID.randomUUID().toString().replaceAll("-", "");
            layerInfoDO.setLayerUuid(copyLayerUuid);
            layerInfoDO.setCreatedTime(new Date());
            layerInfoDO.setModifiedTime(new Date());
            layerInfoDO.setIsDefault("false");
            layerInfoDO.setBranchLevel(branchLevel);
            layerInfoDO.setLayerType(selectByLayerUuid.getLayerType());
            layerInfoDO.setIsBranchDefault("false");
            this.layerInfoMapper.insertLayer(layerInfoDO);
            log.info("copy缩略图");
            FileCopyUtils.copy(new File(this.uploadFile + layerUuid + ".png"), new File(this.uploadFile + copyLayerUuid + ".png"));
            int layerIdNew = layerInfoDO.getLayerId();
            int layerIdOld = selectByLayerUuid.getLayerId();
            log.info("copy所有的关联关系");
            this.layerNodeRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerNodeSubnetRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerNodeZoneRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerSubnetRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerSubnetZoneRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerZoneNodeRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerZoneRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerZoneSubnetRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerNodeNodeRelationMapper.copyLayer(layerIdOld, layerIdNew);
            this.layerZoneZoneRelationMapper.copyLayer(layerIdOld, layerIdNew);
            log.info("物理拓扑图复制时生成新的虚拟域");
            LayerInfoDO layerZoneRelation = this.layerInfoMapper.listLayerZoneRelation(copyLayerUuid);
            List<LayerZoneRelationDO> layerZoneRelationDOS = layerZoneRelation.getLayerZoneRelationDOS();
            Map<Integer, Integer> oldNewZoneIdMap = new HashMap();
            Iterator var12 = layerZoneRelationDOS.iterator();

            while(var12.hasNext()) {
                LayerZoneRelationDO layerZoneRelationDO = (LayerZoneRelationDO)var12.next();
                LayerZoneInfoDO layerZoneInfoDO = layerZoneRelationDO.getLayerZoneInfoDO();
                if (layerZoneInfoDO.getZoneType().equals("zone")) {
                    int zoneIdOld = layerZoneInfoDO.getZoneId();
                    String copyZoneUuid = UUID.randomUUID().toString().replaceAll("-", "");
                    LayerZoneInfoDO copyZoneInfo = new LayerZoneInfoDO();
                    BeanUtils.copyProperties(layerZoneInfoDO, copyZoneInfo);
                    copyZoneInfo.setZoneId((Integer)null);
                    copyZoneInfo.setZoneUuid(copyZoneUuid);
                    this.layerZoneInfoMapper.insert(copyZoneInfo);
                    int zoneIdNew = copyZoneInfo.getZoneId();
                    this.layerNodeZoneRelationMapper.updateZoneId(layerIdNew, zoneIdOld, zoneIdNew);
                    this.layerSubnetZoneRelationMapper.updateZoneId(layerIdNew, zoneIdOld, zoneIdNew);
                    this.layerZoneNodeRelationMapper.updateZoneId(layerIdNew, zoneIdOld, zoneIdNew);
                    this.layerZoneRelationMapper.updateZoneId(layerIdNew, zoneIdOld, zoneIdNew);
                    this.layerZoneSubnetRelationMapper.updateZoneId(layerIdNew, zoneIdOld, zoneIdNew);
                    this.layerZoneZoneRelationMapper.updateZoneFromId(layerIdNew, zoneIdOld, zoneIdNew);
                    this.layerZoneZoneRelationMapper.updateZoneToId(layerIdNew, zoneIdOld, zoneIdNew);
                    oldNewZoneIdMap.put(zoneIdOld, zoneIdNew);
                }
            }

            oldNewZoneIdMap.forEach((zoneIdOldx, zoneIdNewx) -> {
                this.layerZoneRelationMapper.updateZoneParentId(layerIdNew, zoneIdOldx, zoneIdNewx);
            });
            return 0;
        } catch (Exception var19) {
            log.error("copyLayer异常", var19);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
    }

    @Transactional(
            rollbackFor = {Exception.class}
    )
    public int updateLayerStatus(String layerUuid) {
        try {
            this.layerNodeRelationMapper.updateLayerStatus(layerUuid);
            this.layerNodeSubnetRelationMapper.updateLayerStatus(layerUuid);
            this.layerNodeZoneRelationMapper.updateLayerStatus(layerUuid);
            this.layerSubnetRelationMapper.updateLayerStatus(layerUuid);
            this.layerSubnetZoneRelationMapper.updateLayerStatus(layerUuid);
            this.layerZoneRelationMapper.updateLayerStatus(layerUuid);
            this.layerZoneNodeRelationMapper.updateLayerStatus(layerUuid);
            this.layerZoneSubnetRelationMapper.updateLayerStatus(layerUuid);
            this.layerNodeNodeRelationMapper.updateLayerStatus(layerUuid);
            this.layerZoneZoneRelationMapper.updateLayerStatus(layerUuid);
            return 0;
        } catch (Exception var3) {
            log.error("updateLayerStatus异常", var3);
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return 1;
        }
    }

    public List<LayerZoneInfoDO> listZone(String layerUuid) throws Exception {
        LayerInfoDO layerZoneRelation = this.layerInfoMapper.listLayerZoneRelation(layerUuid);
        List<LayerZoneInfoDO> layerZoneInfoDOList = new ArrayList();
        if (layerZoneRelation != null && layerZoneRelation.getLayerZoneRelationDOS() != null) {
            layerZoneRelation.getLayerZoneRelationDOS().forEach((layerZoneRelationDO) -> {
                layerZoneInfoDOList.add(layerZoneRelationDO.getLayerZoneInfoDO());
            });
        }

        return layerZoneInfoDOList;
    }
}
