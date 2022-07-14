////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package com.abtnetworks.data.totems.topology.basic.node.service;
//
//import com.abtnetworks.data.totems.topology.alarm.schedule.task.TopologyQuartzUtil;
//import com.abtnetworks.data.totems.topology.basic.node.dao.CycleDAO;
//import com.abtnetworks.data.totems.topology.basic.node.dao.NodeDAO;
//import com.abtnetworks.data.totems.topology.basic.node.domain.Cycle;
//import com.abtnetworks.data.totems.topology.basic.node.domain.Node;
//import com.abtnetworks.data.totems.topology.basic.node.dto.UpdateCycleDTO;
//import com.abtnetworks.data.totems.topology.userms.data.domain.Branch;
//import com.abtnetworks.data.totems.topology.userms.data.domain.User;
//import com.abtnetworks.data.totems.topology.userms.service.BranchService;
//import com.abtnetworks.data.totems.topology.userms.service.UMSUserService;
//import com.alibaba.fastjson.JSONArray;
//import com.alibaba.fastjson.JSONObject;
//import java.util.ArrayList;
//import java.util.Iterator;
//import java.util.List;
//
//import org.apache.commons.collections.CollectionUtils;
//import org.apache.commons.lang3.StringUtils;
//import org.quartz.SchedulerException;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//public class CycleService {
//    private static final Logger logger = LoggerFactory.getLogger(CycleService.class);
//    @Autowired
//    CycleDAO cycleDAO;
//    @Autowired
//    NodeDAO nodeDAO;
//    @Autowired
//    private TopologyQuartzUtil topologyQuartzUtil;
//    @Autowired
//    BranchService branchService;
//    @Autowired
//    UMSUserService umsUserService;
//
//    public CycleService() {
//    }
//
//    public JSONObject saveCycle(Cycle cycle) {
//        JSONObject result = new JSONObject();
//        List<Cycle> cycleTmp = this.cycleDAO.getCycleByName(cycle.getCycleName());
//        Iterator var4 = cycleTmp.iterator();
//
//        while(var4.hasNext()) {
//            Cycle cycle1 = (Cycle)var4.next();
//            if (cycle1 != null) {
//                result.put("result", false);
//                result.put("msg", "名称已存在");
//                return result;
//            }
//        }
//
//        try {
//            this.cycleDAO.saveCycle(cycle);
//            Integer flog = Integer.parseInt(cycle.getId());
//            if (flog != null) {
//                List<Cycle> cycleList = new ArrayList();
//                cycle.setId(flog + "");
//                cycleList.add(cycle);
//                this.topologyQuartzUtil.startOrUpdateGatherJob(cycleList);
//                result.put("result", true);
//                result.put("msg", "添加成功");
//                return result;
//            }
//        } catch (Exception var6) {
//            logger.error(var6.getMessage(), var6);
//            result.put("result", false);
//            result.put("msg", var6.getMessage());
//            return result;
//        }
//
//        result.put("result", false);
//        result.put("msg", "添加失败");
//        return result;
//    }
//
//    public JSONObject getCyclePage(Integer start, Integer size, String userName, String branchLevel) {
//        JSONObject result = new JSONObject();
//
//        try {
//            if (StringUtils.isNotEmpty(branchLevel)) {
//                branchLevel = branchLevel + "%";
//            } else {
//                branchLevel = this.branchService.likeBranch(userName);
//            }
//
//            List<Cycle> cycleList = this.cycleDAO.getCyclePage(start, size, branchLevel);
//            Integer total = this.cycleDAO.getTotal(branchLevel);
//            new JSONObject();
//            new ArrayList();
//            cycleList.forEach((cycle) -> {
//                List<Node> nodeList = this.nodeDAO.queryNodeByCycleId(cycle.getId());
//                if (nodeList != null) {
//                    cycle.setNlist(nodeList);
//                }
//
//                List<Branch> branchList = this.branchService.getBranchListByLevel(cycle.getBranchLevel());
//                if (CollectionUtils.isNotEmpty(branchList)) {
//                    cycle.setBranchName(((Branch)branchList.get(0)).getBranchName());
//                }
//
//            });
//            result.put("total", total);
//            result.put("data", cycleList);
//        } catch (Exception var10) {
//            logger.error(var10.getMessage(), var10);
//        }
//
//        return result;
//    }
//
//    public JSONObject updateCycle(Cycle cycle) {
//        JSONObject result = new JSONObject();
//
//        try {
//            if (cycle == null || cycle.getId() == null) {
//                result.put("result", false);
//                result.put("msg", "参数错误");
//                return result;
//            }
//
//            Cycle cycleTmp = this.cycleDAO.getCycleById(cycle.getId());
//            if (cycleTmp == null) {
//                result.put("result", false);
//                result.put("msg", "id不存在");
//                return result;
//            }
//
//            List<Cycle> cycleByName = this.cycleDAO.getCycleByName(cycle.getCycleName());
//            Iterator var5 = cycleByName.iterator();
//
//            while(var5.hasNext()) {
//                Cycle cycle1 = (Cycle)var5.next();
//                if (cycle1 != null && !cycle1.getId().equals(cycle.getId())) {
//                    result.put("result", false);
//                    result.put("msg", "名称已存在");
//                    return result;
//                }
//            }
//
//            this.cycleDAO.updateCycle(cycle);
//            List<Cycle> cycleList = new ArrayList();
//            cycleList.add(cycle);
//            this.topologyQuartzUtil.startOrUpdateGatherJob(cycleList);
//            result.put("result", true);
//            result.put("msg", "修改成功");
//        } catch (Exception var7) {
//            logger.error(var7.getMessage(), var7);
//            result.put("result", false);
//            result.put("msg", var7.getMessage());
//        }
//
//        return result;
//    }
//
//    public JSONObject deleteCycle(String[] ids) {
//        JSONObject result = new JSONObject();
//
//        try {
//            result.put("result", true);
//            if (ids != null && ids.length > 0) {
//                JSONArray tmpArray = new JSONArray();
//
//                for(int i = 0; i < ids.length; ++i) {
//                    if (ids[i] != null && !ids[i].equals("")) {
//                        Cycle cycle = this.cycleDAO.getCycleById(ids[i]);
//                        JSONObject tmpJson = new JSONObject();
//                        List<Node> nodes = this.nodeDAO.getTheNodeByGatherCycle(ids[i]);
//                        if (nodes != null && nodes.size() > 0) {
//                            tmpJson.put("result", false);
//                            tmpJson.put("msg", "周期(" + cycle.getCycleName() + ")已被引用，不可删除。");
//                            tmpArray.add(tmpJson);
//                        } else {
//                            this.cycleDAO.deleteCycle(Integer.parseInt(ids[i]));
//                            tmpJson.put("result", true);
//                            tmpJson.put("msg", "周期(" + cycle.getCycleName() + ")删除成功");
//
//                            try {
//                                this.topologyQuartzUtil.deletejobByKey(ids[i], "TopologyGatherThread");
//                            } catch (SchedulerException var9) {
//                                logger.error(var9.getMessage(), var9);
//                            }
//
//                            tmpArray.add(tmpJson);
//                        }
//                    }
//                }
//
//                result.put("allDate", tmpArray);
//            }
//        } catch (NumberFormatException var10) {
//            logger.error(var10.getMessage(), var10);
//        }
//
//        return result;
//    }
//
//    public JSONObject getCycleById(String id) {
//        JSONObject result = new JSONObject();
//
//        try {
//            Cycle cycle = this.cycleDAO.getCycleById(id);
//            result.put("result", true);
//            result.put("data", cycle);
//        } catch (Exception var4) {
//            logger.error(var4.getMessage(), var4);
//        }
//
//        return result;
//    }
//
//    @Transactional(
//            rollbackFor = {Exception.class}
//    )
//    public int batchModify(List<UpdateCycleDTO> updateCycleDTO, Authentication authentication) throws IllegalAccessException {
//        User userInfoDTO = this.umsUserService.findOne(authentication.getName());
//        if (Boolean.TRUE) {
//            this.cycleDAO.modifyBatchBranch(updateCycleDTO);
//            return 1;
//        } else {
//            throw new IllegalAccessException("当前用户非总部用户，不允许对采集周期进行分配组");
//        }
//    }
//}
