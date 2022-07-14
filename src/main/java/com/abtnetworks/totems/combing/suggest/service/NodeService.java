//package com.abtnetworks.totems.combing.suggest.service;
//
//import com.abtnetworks.totems.combing.hit.dto.NodeDevConfigDto;
//import com.abtnetworks.totems.combing.suggest.entity.mysql.NodeEntity;
//import com.github.pagehelper.PageInfo;
//import java.util.List;
//import java.util.Map;
//
//public interface NodeService {
//    List<NodeEntity> getDevNode(String typeId, String userId);
//
//    Map<String, List<NodeEntity>> getAllDevNode(String userId);
//
//    Map<String, List<NodeEntity>> getAllDevNodeByBranchLevel(String branchLevel);
//
//    NodeEntity getDevNodeByUuid(String uuid);
//
//    List<String> listVendorName(String userId);
//
//    PageInfo<NodeDevConfigDto> listDevNodeLogConfig(String vendorName, String name, String typeId, Integer typeStatus, String userId, Integer page, Integer limit);
//}
