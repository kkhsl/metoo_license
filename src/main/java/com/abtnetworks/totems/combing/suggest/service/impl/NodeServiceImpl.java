////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.abtnetworks.totems.combing.suggest.service.impl;
//
//import com.abtnetworks.totems.combing.hit.dao.mysql.PolicyDevConfigMapper;
//import com.abtnetworks.totems.combing.hit.dto.NodeDevConfigDto;
//import com.abtnetworks.totems.combing.suggest.dao.mysql.NodeMapper;
//import com.abtnetworks.totems.combing.suggest.dao.mysql.UmsUserMapper;
//import com.abtnetworks.totems.combing.suggest.entity.mysql.NodeEntity;
//import com.abtnetworks.totems.combing.suggest.service.NodeService;
//import com.abtnetworks.totems.common.utils.AliStringUtils;
//import com.abtnetworks.totems.common.utils.Collections3;
//import com.abtnetworks.totems.whale.baseapi.ro.DeviceDataRO;
//import com.abtnetworks.totems.whale.baseapi.ro.DeviceRO;
//import com.abtnetworks.totems.whale.baseapi.service.WhaleDeviceObjectClient;
//import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//import javax.annotation.Resource;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//@Service
//public class NodeServiceImpl implements NodeService {
//    @Resource
//    private NodeMapper nodeDao;
//    @Autowired
//    private WhaleDeviceObjectClient whaleDeviceObjectClient;
//    @Resource
//    private PolicyDevConfigMapper policyDevConfigDao;
//    @Resource
//    private UmsUserMapper umsUserMapper;
//
//    public NodeServiceImpl() {
//    }
//
//    public List<NodeEntity> getDevNode(String typeId, String userId) {
//        String branchLevel = "00";
//        if (!AliStringUtils.isEmpty(userId)) {
//            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
//        }
//
//        List<NodeEntity> nodeList = this.nodeDao.getDevNode(typeId, branchLevel);
//        return nodeList;
//    }
//
//    public Map<String, List<NodeEntity>> getAllDevNode(String userId) {
//        String branchLevel = "00";
//        if (!AliStringUtils.isEmpty(userId)) {
//            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
//        }
//
//        List<NodeEntity> nodeList = this.nodeDao.getAllDevNode(branchLevel);
//        Map<String, List<NodeEntity>> nodeMap = new HashMap();
//        if (!Collections3.isEmpty(nodeList)) {
//            Iterator var5 = nodeList.iterator();
//
//            while(true) {
//                NodeEntity node;
//                DeviceRO deviceRO;
//                do {
//                    do {
//                        do {
//                            do {
//                                if (!var5.hasNext()) {
//                                    return nodeMap;
//                                }
//
//                                node = (NodeEntity)var5.next();
//                            } while(AliStringUtils.isEmpty(node.getUuid()));
//
//                            deviceRO = this.whaleDeviceObjectClient.getDeviceROByUuid(node.getUuid());
//                        } while(deviceRO == null);
//                    } while(deviceRO.getData() == null);
//                } while(((DeviceDataRO)deviceRO.getData().get(0)).getIsVsys() != null && ((DeviceDataRO)deviceRO.getData().get(0)).getIsVsys());
//
//                List<NodeEntity> subNodeList = (List)nodeMap.get(node.getVendorName());
//                if (Collections3.isEmpty((Collection)subNodeList)) {
//                    subNodeList = new ArrayList();
//                }
//
//                ((List)subNodeList).add(node);
//                nodeMap.put(node.getVendorName(), subNodeList);
//            }
//        } else {
//            return nodeMap;
//        }
//    }
//
//
//    public Map<String, List<NodeEntity>> getAllDevNodeByBranchLevel(String branchLevel) {
//        List<NodeEntity> nodeList = this.nodeDao.getAllDevNode(branchLevel);
//        Map<String, List<NodeEntity>> nodeMap = new HashMap();
//        if (!Collections3.isEmpty(nodeList)) {
//            Iterator var5 = nodeList.iterator();
//
//            while(true) {
//                NodeEntity node;
//                DeviceRO deviceRO;
//                do {
//                    do {
//                        do {
//                            do {
//                                if (!var5.hasNext()) {
//                                    return nodeMap;
//                                }
//
//                                node = (NodeEntity)var5.next();
//                            } while(AliStringUtils.isEmpty(node.getUuid()));
//
//                            deviceRO = this.whaleDeviceObjectClient.getDeviceROByUuid(node.getUuid());
//                        } while(deviceRO == null);
//                    } while(deviceRO.getData() == null);
//                } while(((DeviceDataRO)deviceRO.getData().get(0)).getIsVsys() != null && ((DeviceDataRO)deviceRO.getData().get(0)).getIsVsys());
//
//                List<NodeEntity> subNodeList = (List)nodeMap.get(node.getVendorName());
//                if (Collections3.isEmpty((Collection)subNodeList)) {
//                    subNodeList = new ArrayList();
//                }
//
//                ((List)subNodeList).add(node);
//                nodeMap.put(node.getVendorName(), subNodeList);
//            }
//        } else {
//            return nodeMap;
//        }
//    }
//
//    public NodeEntity getDevNodeByUuid(String uuid) {
//        return this.nodeDao.getDevNodeByUuid(uuid);
//    }
//
//    public List<String> listVendorName(String userId) {
//        String branchLevel = "00";
//        if (!AliStringUtils.isEmpty(userId)) {
//            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
//        }
//
//        return this.nodeDao.listVendorName(branchLevel);
//    }
//
//    public PageInfo<NodeDevConfigDto> listDevNodeLogConfig(String vendorName, String name, String typeId, Integer typeStatus, String userId, Integer page, Integer limit) {
//        String branchLevel = "00";
//        if (!AliStringUtils.isEmpty(userId)) {
//            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
//        }
//
//        PageHelper.startPage(page, limit);
//        List<NodeDevConfigDto> list = this.nodeDao.listDevNodeLogConfig(vendorName, name, typeId, typeStatus, branchLevel);
//        List<String> devIds = this.policyDevConfigDao.getDevIds(typeId, 1);
//        Iterator var11 = list.iterator();
//
//        while(true) {
//            NodeDevConfigDto node;
//            List vsysNodeList;
//            do {
//                if (!var11.hasNext()) {
//                    PageInfo<NodeDevConfigDto> pageInfo = new PageInfo(list);
//                    return pageInfo;
//                }
//
//                node = (NodeDevConfigDto)var11.next();
//                vsysNodeList = this.nodeDao.listVsysNodeByIp(node.getIp(), branchLevel);
//            } while(Collections3.isEmpty(vsysNodeList));
//
//            Iterator var14 = vsysNodeList.iterator();
//
//            while(var14.hasNext()) {
//                NodeDevConfigDto vsysNode = (NodeDevConfigDto)var14.next();
//                DeviceRO deviceRO = this.whaleDeviceObjectClient.getDeviceROByUuid(vsysNode.getDevId());
//                if (deviceRO != null && deviceRO.getSuccess() && deviceRO.getData() != null && deviceRO.getData().size() > 0) {
//                    DeviceDataRO deviceDataRO = (DeviceDataRO)deviceRO.getData().get(0);
//                    vsysNode.setIsVsys(deviceDataRO.getIsVsys());
//                    String dname = deviceDataRO.getName();
//                    String rootDeviceUuid = deviceDataRO.getRootDeviceUuid();
//                    if (!AliStringUtils.isEmpty(rootDeviceUuid)) {
//                        vsysNode.setRootDeviceUuid(rootDeviceUuid);
//                        if (devIds.contains(rootDeviceUuid)) {
//                            vsysNode.setTypeStatus(true);
//                        }
//
//                        dname = deviceDataRO.getVsysName();
//                    }
//
//                    vsysNode.setvSysName(dname);
//                }
//            }
//
//            node.setVsysNodeList(vsysNodeList);
//        }
//    }
//}
