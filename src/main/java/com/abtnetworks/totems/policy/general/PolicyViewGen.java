//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.policy.general;

import com.abtnetworks.totems.common.enums.DeviceObjectTypeEnum;
import com.abtnetworks.totems.common.enums.NodeTypeEnum;
import com.abtnetworks.totems.common.enums.PolicyCheckTypeEnum;
import com.abtnetworks.totems.common.enums.PolicyTypeEnum;
import com.abtnetworks.totems.common.enums.VendorEnum;
import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.common.utils.DateUtil;
import com.abtnetworks.totems.policy.common.utils.ExportPolicyExcelUtils;
import com.abtnetworks.totems.policy.domain.Node;
import com.abtnetworks.totems.policy.service.PolicyCheckReportService;
import com.abtnetworks.totems.policy.service.PolicyService;
import com.abtnetworks.totems.policy.vo.PolicyViewVO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceDataRO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceFilterlistRO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceRO;
import com.abtnetworks.totems.whale.baseapi.ro.ImportedRoutingTableRO;
import com.abtnetworks.totems.whale.baseapi.ro.RoutingtableRO;
import com.abtnetworks.totems.whale.baseapi.service.WhaleDeviceObjectClient;
import com.abtnetworks.totems.whale.baseapi.service.WhaleDevicePolicyClient;
import com.abtnetworks.totems.whale.policyoptimize.ro.RuleCheckResultDataRO;
import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PolicyViewGen {
    private static final Logger log = LoggerFactory.getLogger(PolicyViewGen.class);
    @Autowired
    private WhaleDeviceObjectClient whaleDeviceObjectClient;
    @Autowired
    private PolicyCheckReportService policyCheckReportService;
    @Autowired
    private WhaleDevicePolicyClient whaleDevicePolicyClient;
    @Autowired
    private PolicyService policyService;

    public PolicyViewGen() {
    }

    public List<PolicyViewVO> getData(List<Node> nodeList) throws Exception {
        log.info("策略概览查询开始");
        long start = System.currentTimeMillis();
        final List<PolicyViewVO> list = new ArrayList();
        if (nodeList != null && nodeList.size() != 0) {
            List<Thread> threadList = Lists.newArrayList();
            Iterator var6 = nodeList.iterator();

            while(var6.hasNext()) {
                final Node node = (Node)var6.next();
                threadList.add(new Thread(new Runnable() {
                    public void run() {
                        try {
                            PolicyViewVO vo = new PolicyViewVO();
                            String strType = NodeTypeEnum.getNameByCode(node.getType());
                            vo.setTypeCode(node.getType() != null ? Integer.valueOf(node.getType()) : 0);
                            vo.setType(strType);
                            vo.setState(node.getState());
                            vo.setIp(node.getIp());
                            vo.setDeviceName(node.getDeviceName());
                            vo.setVendorName(node.getVendorName());
                            vo.setVendorId(node.getVendorId());
                            vo.setCreatedTime(DateUtil.dateToString(node.getCreatedTime(), "yyyy-MM-dd HH:mm:ss"));
                            vo.setVersion(node.getVersion());
                            String deviceUuid = node.getUuid();
                            DeviceRO deviceRO = PolicyViewGen.this.whaleDeviceObjectClient.getDeviceROByUuid(deviceUuid);
                            DeviceDataRO deviceObj = new DeviceDataRO();
                            if (deviceRO != null && deviceRO.getData() != null && deviceRO.getData().size() > 0) {
                                deviceObj = (DeviceDataRO)deviceRO.getData().get(0);
                            }

                            if (deviceObj.getIsVsys() != null && deviceObj.getIsVsys()) {
                                vo.setDeviceName(deviceObj.getName());
                            }

                            ResultRO<List<DeviceFilterlistRO>> deviceFilterlistResultRO = PolicyViewGen.this.policyService.getDeviceFilterlist(deviceUuid);
                            int safeTotal = 0;
                            int natTotal = 0;
                            int aclTotal = 0;
                            int policyRoutTotal = 0;
                            if (deviceFilterlistResultRO != null && deviceFilterlistResultRO.getData() != null && ((List)deviceFilterlistResultRO.getData()).size() > 0) {
                                Iterator var11 = ((List)deviceFilterlistResultRO.getData()).iterator();

                                label174:
                                while(true) {
                                    while(true) {
                                        if (!var11.hasNext()) {
                                            break label174;
                                        }

                                        DeviceFilterlistRO r = (DeviceFilterlistRO)var11.next();
                                        if (!r.getRuleListType().equalsIgnoreCase(PolicyTypeEnum.SYSTEM__POLICY_1.getRuleListType()) && !r.getRuleListType().equalsIgnoreCase(PolicyTypeEnum.SYSTEM__POLICY_2.getRuleListType())) {
                                            if (r.getRuleListType().equalsIgnoreCase(PolicyTypeEnum.SYSTEM__NAT_LIST.getRuleListType())) {
                                                natTotal += Integer.parseInt(r.getRuleTotal());
                                            } else if (PolicyTypeEnum.SYSTEM__GENERIC_ACL.getRuleListType().indexOf(r.getRuleListType()) != -1) {
                                                aclTotal += Integer.parseInt(r.getRuleTotal());
                                            } else if (r.getRuleListType().equalsIgnoreCase(PolicyTypeEnum.SYSTEM__POLICY_ROUTING.getRuleListType())) {
                                                policyRoutTotal += Integer.parseInt(r.getRuleTotal());
                                            }
                                        } else {
                                            safeTotal += Integer.parseInt(r.getRuleTotal());
                                        }
                                    }
                                }
                            }

                            int staticRoutTotal = 0;
                            ResultRO<List<RoutingtableRO>> routingTable = PolicyViewGen.this.whaleDevicePolicyClient.getRoutingTable(deviceUuid);
                            if (routingTable != null && routingTable.getData() != null) {
                                Iterator var13 = ((List)routingTable.getData()).iterator();

                                while(var13.hasNext()) {
                                    RoutingtableRO tablesRO = (RoutingtableRO)var13.next();
                                    if (tablesRO.getRoutingEntriesTotal() != null) {
                                        staticRoutTotal += tablesRO.getRoutingEntriesTotal();
                                    }
                                }
                            }

                            int routingTableTotal = 0;
                            ResultRO<List<ImportedRoutingTableRO>> importRoutTable = PolicyViewGen.this.whaleDevicePolicyClient.getImportRoutTable(deviceUuid);
                            if (importRoutTable != null && importRoutTable.getData() != null) {
                                Iterator var15 = ((List)importRoutTable.getData()).iterator();

                                label146:
                                while(true) {
                                    ImportedRoutingTableRO rtRO;
                                    do {
                                        do {
                                            if (!var15.hasNext()) {
                                                break label146;
                                            }

                                            rtRO = (ImportedRoutingTableRO)var15.next();
                                        } while(rtRO.getBaseRoutingTables() == null);
                                    } while(rtRO.getBaseRoutingTables().isEmpty());

                                    Iterator var17 = rtRO.getBaseRoutingTables().iterator();

                                    while(var17.hasNext()) {
                                        RoutingtableRO tablesROx = (RoutingtableRO)var17.next();
                                        if (tablesROx.getRoutingEntriesTotal() != null) {
                                            routingTableTotal += tablesROx.getRoutingEntriesTotal();
                                        }
                                    }
                                }
                            }

                            vo.setSafeTotal(safeTotal);
                            vo.setAclTotal(aclTotal);
                            vo.setNatTotal(natTotal);
                            vo.setPolicyRoutTotal(policyRoutTotal);
                            vo.setRoutTableTotal(routingTableTotal);
                            vo.setStaticRoutTotal(staticRoutTotal);
                            int policyTotal = 0;
                            int policyTotalx;
                            if (NodeTypeEnum.FIREWALL.getCode().equals(node.getType()) && VendorEnum.CISCO.getCode().equalsIgnoreCase(node.getVendorId())) {
                                policyTotalx = policyTotal + aclTotal + natTotal + policyRoutTotal;
                            } else {
                                policyTotalx = aclTotal + natTotal + policyRoutTotal + safeTotal + routingTableTotal + staticRoutTotal;
                            }

                            vo.setPolicyTotal(policyTotalx);
                            int servieGroupTotal = 0;
                            if (deviceObj.getServiceGroups() != null) {
                                servieGroupTotal = deviceObj.getServiceGroups().size();
                            }

                            int serviceTotal = 0;
                            if (deviceObj.getServices() != null) {
                                serviceTotal = deviceObj.getServices().size();
                            }

                            int netWorkGroupTotal = 0;
                            if (deviceObj.getIpGroups() != null) {
                                netWorkGroupTotal = deviceObj.getIpGroups().size();
                            }

                            int netWorkTotal = 0;
                            if (deviceObj.getIpItems() != null) {
                                netWorkTotal = deviceObj.getIpItems().size();
                            }

                            int timeTotal = 0;
                            if (deviceObj.getTimeGroups() != null) {
                                timeTotal = deviceObj.getTimeGroups().size();
                            }

                            vo.setTimeTotal(timeTotal);
                            vo.setNetWorkTotal(netWorkTotal);
                            vo.setNetWorkGroupTotal(netWorkGroupTotal);
                            vo.setServiceTotal(serviceTotal);
                            vo.setServieGroupTotal(servieGroupTotal);
                            int objectTotal = timeTotal + netWorkTotal + netWorkGroupTotal + serviceTotal + servieGroupTotal;
                            vo.setObjectTotal(objectTotal);
                            int hiddenCheckTotal = 0;
                            ResultRO<List<RuleCheckResultDataRO>> hideCheckResultRO = PolicyViewGen.this.policyCheckReportService.getCheckList(1, 1, deviceUuid, PolicyCheckTypeEnum.HIDDEN, (Map)null, (Map)null, (Map)null);
                            if (hideCheckResultRO != null) {
                                hiddenCheckTotal = hideCheckResultRO.getTotal();
                            }

                            int mergeCheckTotal = 0;
                            ResultRO<List<RuleCheckResultDataRO>> mergeCheckResultRO = PolicyViewGen.this.policyCheckReportService.getCheckList(1, 1, deviceUuid, PolicyCheckTypeEnum.MERGE, (Map)null, (Map)null, (Map)null);
                            if (mergeCheckResultRO != null) {
                                mergeCheckTotal = mergeCheckResultRO.getTotal();
                            }

                            int redundancyCheckTotal = 0;
                            ResultRO<List<RuleCheckResultDataRO>> redundancyCheckResultRO = PolicyViewGen.this.policyCheckReportService.getCheckList(1, 1, deviceUuid, PolicyCheckTypeEnum.REDUNDANCY, (Map)null, (Map)null, (Map)null);
                            if (redundancyCheckResultRO != null) {
                                redundancyCheckTotal = redundancyCheckResultRO.getTotal();
                            }

                            int expiredCheckTotal = 0;
                            ResultRO<List<RuleCheckResultDataRO>> expiredCheckResultRO = PolicyViewGen.this.policyCheckReportService.getCheckList(1, 1, deviceUuid, PolicyCheckTypeEnum.EXPIRED, (Map)null, (Map)null, (Map)null);
                            if (expiredCheckResultRO != null) {
                                expiredCheckTotal = expiredCheckResultRO.getTotal();
                            }

                            int expiringCheckTotal = 0;
                            ResultRO<List<RuleCheckResultDataRO>> expiringCheckResultRO = PolicyViewGen.this.policyCheckReportService.getCheckList(1, 1, deviceUuid, PolicyCheckTypeEnum.NEAR_EXPIRED, (Map)null, (Map)null, (Map)null);
                            if (expiringCheckResultRO != null) {
                                expiringCheckTotal = expiringCheckResultRO.getTotal();
                            }

                            int outZoneCheckTotal = 0;
                            ResultRO<List<RuleCheckResultDataRO>> outZoneCheckResultRO = PolicyViewGen.this.policyCheckReportService.getCheckList(1, 1, deviceUuid, PolicyCheckTypeEnum.RC_ZONE_OUT_RULE, (Map)null, (Map)null, (Map)null);
                            if (outZoneCheckResultRO != null) {
                                outZoneCheckTotal = outZoneCheckResultRO.getTotal();
                            }

                            int aclNoUseCheckTotal = 0;
                            ResultRO<List<RuleCheckResultDataRO>> aclNoUseCheckResultRO = PolicyViewGen.this.policyCheckReportService.getCheckList(1, 1, deviceUuid, PolicyCheckTypeEnum.RC_UNREF_ACL, (Map)null, (Map)null, (Map)null);
                            if (aclNoUseCheckResultRO != null) {
                                aclNoUseCheckTotal = aclNoUseCheckResultRO.getTotal();
                            }

                            int emptyCheckTotal = 0;
                            ResultRO<List<RuleCheckResultDataRO>> emptyCheckResultRO = PolicyViewGen.this.policyCheckReportService.getCheckList(1, 1, deviceUuid, PolicyCheckTypeEnum.EMPTY, (Map)null, (Map)null, (Map)null);
                            if (emptyCheckResultRO != null) {
                                emptyCheckTotal = emptyCheckResultRO.getTotal();
                            }

                            vo.setHiddenCheckTotal(hiddenCheckTotal);
                            vo.setMergeCheckTotal(mergeCheckTotal);
                            vo.setRedundancyCheckTotal(redundancyCheckTotal);
                            vo.setExpiredCheckTotal(expiredCheckTotal);
                            vo.setEmptyCheckTotal(emptyCheckTotal);
                            vo.setWillExpiringCheckTotal(expiringCheckTotal);
                            vo.setOutZoneCheckTotal(outZoneCheckTotal);
                            vo.setAclNoUseCheckTotal(aclNoUseCheckTotal);
                            int policyCheckTotal = hiddenCheckTotal + mergeCheckTotal + redundancyCheckTotal + expiredCheckTotal + emptyCheckTotal + expiringCheckTotal + outZoneCheckTotal + aclNoUseCheckTotal;
                            vo.setPolicyCheckTotal(policyCheckTotal);
                            int netWorkCheckTotal = PolicyViewGen.this.policyCheckReportService.getTotalObjectCheck(deviceUuid, DeviceObjectTypeEnum.NETWORK_OBJECT);
                            int netWorkGroupCheckTotal = PolicyViewGen.this.policyCheckReportService.getTotalObjectCheck(deviceUuid, DeviceObjectTypeEnum.NETWORK_GROUP_OBJECT);
                            int serviceCheckTotal = PolicyViewGen.this.policyCheckReportService.getTotalObjectCheck(deviceUuid, DeviceObjectTypeEnum.SERVICE_OBJECT);
                            int serviceGroupCheckTotal = PolicyViewGen.this.policyCheckReportService.getTotalObjectCheck(deviceUuid, DeviceObjectTypeEnum.SERVICE_GROUP_OBJECT);
                            int timeCheckTotal = PolicyViewGen.this.policyCheckReportService.getTotalObjectCheck(deviceUuid, DeviceObjectTypeEnum.TIME_OBJECT);
                            vo.setNetWorkCheckTotal(netWorkCheckTotal);
                            vo.setNetWorkGroupCheckTotal(netWorkGroupCheckTotal);
                            vo.setServiceCheckTotal(serviceCheckTotal);
                            vo.setServieGroupCheckTotal(serviceGroupCheckTotal);
                            vo.setTimeCheckTotal(timeCheckTotal);
                            int objectCheckTotal = netWorkCheckTotal + netWorkGroupCheckTotal + serviceCheckTotal + serviceGroupCheckTotal + timeCheckTotal;
                            vo.setObjectCheckTotal(objectCheckTotal);
                            list.add(vo);
                        } catch (Exception var45) {
                            PolicyViewGen.log.error("策略概览异常", var45);
                        }

                    }
                }));
            }

            var6 = threadList.iterator();

            Thread t;
            while(var6.hasNext()) {
                t = (Thread)var6.next();
                t.start();
            }

            var6 = threadList.iterator();

            while(var6.hasNext()) {
                t = (Thread)var6.next();
                t.join();
            }

            var6 = list.iterator();

            while(var6.hasNext()) {
                PolicyViewVO vo = (PolicyViewVO)var6.next();
                List<Map<String, Integer>> policyTotalDetailList = new ArrayList();
                Map<String, Integer> policyTotalDetailMap = new HashMap();
                policyTotalDetailMap.put("safeTotal", vo.getSafeTotal());
                policyTotalDetailMap.put("natTotal", vo.getNatTotal());
                policyTotalDetailMap.put("aclTotal", vo.getAclTotal());
                policyTotalDetailMap.put("policyRoutTotal", vo.getPolicyRoutTotal());
                policyTotalDetailMap.put("staticRoutTotal", vo.getStaticRoutTotal());
                policyTotalDetailMap.put("routTableTotal", vo.getRoutTableTotal());
                policyTotalDetailList.add(policyTotalDetailMap);
                vo.setPolicyTotalDetail(policyTotalDetailList);
                List<Map<String, Integer>> policyCheckTotalDetailList = new ArrayList();
                Map<String, Integer> policyCheckTotalDetailMap = new HashMap();
                policyCheckTotalDetailMap.put("hiddenCheckTotal", vo.getHiddenCheckTotal());
                policyCheckTotalDetailMap.put("mergeCheckTotal", vo.getMergeCheckTotal());
                policyCheckTotalDetailMap.put("emptyCheckTotal", vo.getEmptyCheckTotal());
                policyCheckTotalDetailMap.put("expiredCheckTotal", vo.getExpiredCheckTotal());
                policyCheckTotalDetailMap.put("redundancyCheckTotal", vo.getRedundancyCheckTotal());
                policyCheckTotalDetailMap.put("expiringCheckTotal", vo.getWillExpiringCheckTotal());
                policyCheckTotalDetailMap.put("outZoneCheckTotal", vo.getOutZoneCheckTotal());
                policyCheckTotalDetailMap.put("aclNoUseCheckTotal", vo.getAclNoUseCheckTotal());
                policyCheckTotalDetailList.add(policyCheckTotalDetailMap);
                vo.setPolicyCheckTotalDetail(policyCheckTotalDetailList);
                List<Map<String, Integer>> objectTotalDetailList = new ArrayList();
                Map<String, Integer> objectTotalDetailMap = new HashMap();
                objectTotalDetailMap.put("netWorkTotal", vo.getNetWorkTotal());
                objectTotalDetailMap.put("netWorkGroupTotal", vo.getNetWorkGroupTotal());
                objectTotalDetailMap.put("serviceTotal", vo.getServiceTotal());
                objectTotalDetailMap.put("servieGroupTotal", vo.getServieGroupTotal());
                objectTotalDetailMap.put("timeTotal", vo.getTimeTotal());
                objectTotalDetailList.add(objectTotalDetailMap);
                vo.setObjectTotalDetail(objectTotalDetailList);
                List<Map<String, Integer>> objectCheckTotalDetailList = new ArrayList();
                Map<String, Integer> objectCheckTotalDetailMap = new HashMap();
                objectCheckTotalDetailMap.put("netWorkCheckTotal", vo.getNetWorkCheckTotal());
                objectCheckTotalDetailMap.put("netWorkGroupCheckTotal", vo.getNetWorkGroupCheckTotal());
                objectCheckTotalDetailMap.put("serviceCheckTotal", vo.getServiceCheckTotal());
                objectCheckTotalDetailMap.put("servieGroupCheckTotal", vo.getServieGroupCheckTotal());
                objectCheckTotalDetailMap.put("timeCheckTotal", vo.getTimeCheckTotal());
                objectCheckTotalDetailList.add(objectCheckTotalDetailMap);
                vo.setObjectCheckTotalDetail(objectCheckTotalDetailList);
            }

            Long time = System.currentTimeMillis() - start;
            log.info("策略概览查询结束,耗时time:{}ms", time);
            Collections.sort(list, new PolicyViewVO());
            return list;
        } else {
            return list;
        }
    }

    public void exportDataToExcel(List<PolicyViewVO> list, String filePath) throws Exception {
        FileOutputStream out = null;

        try {
            out = new FileOutputStream(filePath);
            List<List<String>> data = new ArrayList();
            int index = 1;

            for(Iterator var6 = list.iterator(); var6.hasNext(); ++index) {
                PolicyViewVO vo = (PolicyViewVO)var6.next();
                List<String> rowData = new ArrayList();
                rowData.add(String.valueOf(index));
                rowData.add(vo.getType());
                rowData.add(vo.getVendorName());
                rowData.add(vo.getCreatedTime());
                rowData.add(vo.getIp());
                rowData.add(vo.getDeviceName());
                rowData.add(this.getCellShowValue(vo.getSafeTotal()));
                rowData.add(this.getCellShowValue(vo.getNatTotal()));
                rowData.add(this.getCellShowValue(vo.getAclTotal()));
                rowData.add(this.getCellShowValue(vo.getPolicyRoutTotal()));
                rowData.add(this.getCellShowValue(vo.getStaticRoutTotal()));
                rowData.add(this.getCellShowValue(vo.getRoutTableTotal()));
                rowData.add(this.getCellShowValue(vo.getPolicyTotal()));
                rowData.add(this.getCellShowValue(vo.getNetWorkTotal()));
                rowData.add(this.getCellShowValue(vo.getNetWorkGroupTotal()));
                rowData.add(this.getCellShowValue(vo.getServiceTotal()));
                rowData.add(this.getCellShowValue(vo.getServieGroupTotal()));
                rowData.add(this.getCellShowValue(vo.getTimeTotal()));
                rowData.add(this.getCellShowValue(vo.getObjectTotal()));
                rowData.add(this.getCellShowValue(vo.getNetWorkCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getNetWorkGroupCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getServiceCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getServieGroupCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getTimeCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getObjectCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getHiddenCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getRedundancyCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getEmptyCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getExpiredCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getWillExpiringCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getMergeCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getOutZoneCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getAclNoUseCheckTotal()));
                rowData.add(this.getCellShowValue(vo.getPolicyCheckTotal()));
                data.add(rowData);
            }

            String[] headers = new String[]{"序号", "设备类型", "品牌", "采集时间", "设备管理IP", "设备名称", "安全策略", "NAT策略", "ACL策略", "策略路由", "静态路由", "路由表", "策略合计", "地址对象", "地址组对象", "服务对象", "服务组对象", "时间对象", "对象合计", "地址对象检查", "地址组对象检查", "服务对象检查", "服务组对象检查", "时间对象检查", "对象检查总数", "隐藏策略", "冗余策略", "空策略", "过期策略", "临过期策略", "合并策略", "域外策略", "acl未调用", "检查统计"};
            ExportPolicyExcelUtils eeu = new ExportPolicyExcelUtils();
            XSSFWorkbook workbook = new XSSFWorkbook();
            eeu.exportPolicyOverViewData(workbook, 0, "策略概览统计", headers, data, out);
            workbook.write(out);
        } catch (Exception var12) {
            if (out != null) {
                out.close();
            }

            File file = new File(filePath);
            if (file.exists()) {
                file.delete();
            }

            log.error("策略概览写入excel异常", var12);
        } finally {
            if (out != null) {
                out.close();
            }

        }

    }

    private String getCellShowValue(int total) {
        String value = "";
        if (total > 0) {
            value = String.valueOf(total);
        }

        return value;
    }
}
