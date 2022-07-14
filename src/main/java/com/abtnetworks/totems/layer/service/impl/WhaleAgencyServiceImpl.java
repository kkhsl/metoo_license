//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.layer.service.impl;

import com.abtnetworks.totems.common.io.TotemsFileUtils;
import com.abtnetworks.totems.common.lang.TotemsDateUtils;
import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.common.utils.IPUtil;
import com.abtnetworks.totems.common.utils.excel.TotemsExcelExport;
import com.abtnetworks.totems.layer.common.config.ProtocolMapConfig;
import com.abtnetworks.totems.layer.dao.mysql.TopoNodeMapper;
import com.abtnetworks.totems.layer.dao.mysql.UmsUserMapper;
import com.abtnetworks.totems.layer.domain.TopoNodeDO;
import com.abtnetworks.totems.layer.dto.AccessQueryTrafficItemExportDTO;
import com.abtnetworks.totems.layer.dto.LayerElementRO;
import com.abtnetworks.totems.layer.dto.LayerIfMatchGroupRO;
import com.abtnetworks.totems.layer.dto.LayerSplitSubnetDataRO;
import com.abtnetworks.totems.layer.dto.LayerSplitSubnetRO;
import com.abtnetworks.totems.layer.dto.ListSubnetsDTO;
import com.abtnetworks.totems.layer.service.WhaleAgencyService;
import com.abtnetworks.totems.layer.vo.DeviceDataVO;
import com.abtnetworks.totems.layer.vo.InterfaceVO;
import com.abtnetworks.totems.layer.vo.MemoryPagination;
import com.abtnetworks.totems.layer.vo.PathAnalyzeDataModifyVO;
import com.abtnetworks.totems.layer.vo.PathAnalyzeModifyVO;
import com.abtnetworks.totems.layer.vo.PathDetailModifyVO;
import com.abtnetworks.totems.layer.vo.PathFlowVO;
import com.abtnetworks.totems.layer.vo.PathInfoModifyVO;
import com.abtnetworks.totems.layer.vo.RealSubnetDataModifyVO;
import com.abtnetworks.totems.layer.vo.RealSubnetModifyVO;
import com.abtnetworks.totems.layer.vo.SubnetListVO;
import com.abtnetworks.totems.layer.vo.SubnetVO;
import com.abtnetworks.totems.layer.vo.TrafficFileVO;
import com.abtnetworks.totems.layer.vo.ZoneDataVO;
import com.abtnetworks.totems.layer.vo.ZoneVO;
import com.abtnetworks.totems.whale.baseapi.dto.DetailPathSubnetDTO;
import com.abtnetworks.totems.whale.baseapi.dto.SubnetDTO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceDataRO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceRO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceSummaryDataRO;
import com.abtnetworks.totems.whale.baseapi.ro.DeviceSummaryRO;
import com.abtnetworks.totems.whale.baseapi.ro.InterfaceExtendDataRO;
import com.abtnetworks.totems.whale.baseapi.ro.InterfaceExtendRO;
import com.abtnetworks.totems.whale.baseapi.ro.InterfacesRO;
import com.abtnetworks.totems.whale.baseapi.ro.SubnetLinkedDeviceRO;
import com.abtnetworks.totems.whale.baseapi.ro.SubnetListRO;
import com.abtnetworks.totems.whale.baseapi.ro.SubnetRO;
import com.abtnetworks.totems.whale.baseapi.ro.SubnetUuidListRO;
import com.abtnetworks.totems.whale.baseapi.ro.ZoneRO;
import com.abtnetworks.totems.whale.baseapi.service.BusinessSubnetClient;
import com.abtnetworks.totems.whale.baseapi.service.WhaleDeviceObjectClient;
import com.abtnetworks.totems.whale.baseapi.service.WhaleSubnetObjectClient;
import com.abtnetworks.totems.whale.policy.dto.AccessQueryDTO;
import com.abtnetworks.totems.whale.policy.dto.PathAnalyzeDTO;
import com.abtnetworks.totems.whale.policy.dto.SrcDstIntegerDTO;
import com.abtnetworks.totems.whale.policy.dto.SrcDstStringDTO;
import com.abtnetworks.totems.whale.policy.ro.AccessQueryRO;
import com.abtnetworks.totems.whale.policy.ro.AccessQueryTrafficExportRO;
import com.abtnetworks.totems.whale.policy.ro.AccessQueryTrafficItemExportRO;
import com.abtnetworks.totems.whale.policy.ro.AccessQueryTrafficResultRO;
import com.abtnetworks.totems.whale.policy.ro.AnalysisCancel;
import com.abtnetworks.totems.whale.policy.ro.AnalysisStartRO;
import com.abtnetworks.totems.whale.policy.ro.PathAnalyzeRO;
import com.abtnetworks.totems.whale.policy.ro.RealSubnetRO;
import com.abtnetworks.totems.whale.policy.service.WhaleAccessAnalyzeClient;
import com.abtnetworks.totems.whale.policy.service.WhaleAnalysisClient;
import com.abtnetworks.totems.whale.policy.service.WhalePathAnalyzeClient;
import com.abtnetworks.totems.whale.system.ro.TaskStatusRO;
import com.abtnetworks.totems.whale.system.service.WhaleTask;
import com.abtnetworks.totems.whale.topo.dto.LinkVpnDTO;
import com.abtnetworks.totems.whale.topo.dto.SplitSubnetDTO;
import com.abtnetworks.totems.whale.topo.ro.ElementRO;
import com.abtnetworks.totems.whale.topo.ro.IfMatchGroupRO;
import com.abtnetworks.totems.whale.topo.ro.LinkVpnRO;
import com.abtnetworks.totems.whale.topo.ro.SplitSubnetDataRO;
import com.abtnetworks.totems.whale.topo.ro.SplitSubnetRO;
import com.abtnetworks.totems.whale.topo.ro.TopoUnDoRO;
import com.abtnetworks.totems.whale.topo.service.WhaleTopoClient;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("whaleAgencyService")
public class WhaleAgencyServiceImpl implements WhaleAgencyService {
    private static final Logger log = LoggerFactory.getLogger(WhaleAgencyServiceImpl.class);
    @Resource
    WhaleDeviceObjectClient whaleDeviceObjectClient;
    @Resource
    WhaleSubnetObjectClient whaleSubnetObjectClient;
    @Resource
    WhaleAccessAnalyzeClient whaleAccessAnalyzeClient;
    @Resource
    WhalePathAnalyzeClient whalePathAnalyzeClient;
    @Resource
    TopoNodeMapper topoNodeMapper;
    @Resource
    WhaleTopoClient whaleTopoClient;
    @Resource
    UmsUserMapper umsUserMapper;
    @Resource
    WhaleAnalysisClient whaleAnalysisClient;
    @Resource
    WhaleTask whaleTask;
    @Resource
    ProtocolMapConfig protocolMapConfig;
    @Resource
    BusinessSubnetClient businessSubnetClient;
    @Value("${traffic-directory.fileDir}")
    private String trafficDirectoryFileDir;
    @Value("${traffic-directory.handler}")
    private String trafficDirectoryHandler;

    public WhaleAgencyServiceImpl() {
    }

    public MemoryPagination<InterfaceExtendDataRO> listSubnets(ListSubnetsDTO listSubnetsDTO) throws Exception {
        Integer pageNum = listSubnetsDTO.getPageNum();
        Integer pageSize = listSubnetsDTO.getPageSize();
        InterfaceExtendRO interfaceExtendRO = this.whaleDeviceObjectClient.getDeviceInterfaceExtendRO(listSubnetsDTO.getDeviceUuid(), true);
        if (interfaceExtendRO.getData() == null) {
            return new MemoryPagination(pageNum, pageSize, new ArrayList());
        } else {
            Boolean linkedDevice = listSubnetsDTO.getLinkedDevice();
            String ipType = listSubnetsDTO.getIpType();
            String ip4Address = listSubnetsDTO.getIp4Address();
            Pattern ipPattern = null;
            if (StringUtils.isNotEmpty(ip4Address)) {
                log.info("模糊查询的ip为：{}", ip4Address);
                ipPattern = Pattern.compile(ip4Address);
            }

            String subnetType = listSubnetsDTO.getSubnetType();
            String interfaceName = listSubnetsDTO.getInterfaceName();
            Pattern interfaceNamePattern = null;
            if (StringUtils.isNotEmpty(interfaceName)) {
                log.info("模糊查询的interfaceName为：{}", interfaceName);
                interfaceNamePattern = Pattern.compile(interfaceName);
            }

            List<InterfaceExtendDataRO> interfaceVOS = interfaceExtendRO.getData();
            Iterator iterator = ((List)interfaceVOS).iterator();

            while(true) {
                while(iterator.hasNext()) {
                    InterfaceExtendDataRO interfaceVO = (InterfaceExtendDataRO)iterator.next();
                    if (linkedDevice != null) {
                        if (linkedDevice) {
                            if (!interfaceVO.getLinkedDevice()) {
                                iterator.remove();
                                continue;
                            }
                        } else {
                            log.debug("非连接设备处理");
                            if (interfaceVO.getLinkedDevice()) {
                                iterator.remove();
                                continue;
                            }

                            if (StringUtils.isNotEmpty(subnetType)) {
                                if (!StringUtils.equals(subnetType, interfaceVO.getSubnet().getType())) {
                                    iterator.remove();
                                    continue;
                                }
                            } else {
                                log.debug("不传子网类型时：RT子网和VNI子网的接口删除");
                                if (StringUtils.equalsAny(interfaceVO.getSubnet().getType(), new CharSequence[]{"GENERAL_LINK", "VXLAN_TUNNEL"})) {
                                    iterator.remove();
                                    continue;
                                }
                            }
                        }
                    }

                    if (StringUtils.isNotBlank(ipType) && !interfaceVO.getIpType().equals(ipType)) {
                        iterator.remove();
                    } else {
                        if (ipPattern != null) {
                            if (interfaceVO.getIp4Address() != null && !ipPattern.matcher(interfaceVO.getIp4Address()).find()) {
                                iterator.remove();
                                continue;
                            }

                            if (interfaceVO.getIp6Address() != null && !ipPattern.matcher(interfaceVO.getIp6Address()).find()) {
                                iterator.remove();
                                continue;
                            }
                        }

                        if (interfaceNamePattern != null && !interfaceNamePattern.matcher(interfaceVO.getName()).find()) {
                            iterator.remove();
                        }
                    }
                }

                if (linkedDevice != null && linkedDevice) {
                    List<InterfaceExtendDataRO> interfaceVOS1 = new ArrayList();
                    log.info("处理有连接InterfaceExtendDataRO设备时一个接口对象拆分成多个子网对象，每个接口对象有且仅有一个连接的设备对象");
                    Iterator var15 = ((List)interfaceVOS).iterator();

                    while(true) {
                        while(var15.hasNext()) {
                            InterfaceExtendDataRO interfaceVO = (InterfaceExtendDataRO)var15.next();
                            List<DeviceSummaryDataRO> devices = interfaceVO.getDevices();
                            if (devices.size() != 1) {
                                Iterator var18 = devices.iterator();

                                while(var18.hasNext()) {
                                    DeviceSummaryDataRO device = (DeviceSummaryDataRO)var18.next();
                                    InterfaceExtendDataRO interfaceCopy = new InterfaceExtendDataRO();
                                    BeanUtils.copyProperties(interfaceVO, interfaceCopy);
                                    interfaceCopy.setDevices(Collections.singletonList(device));
                                    interfaceVOS1.add(interfaceCopy);
                                }
                            } else {
                                interfaceVOS1.add(interfaceVO);
                            }
                        }

                        interfaceVOS = interfaceVOS1;
                        String linkedDeviceName = listSubnetsDTO.getLinkedDeviceName();
                        if (StringUtils.isNotEmpty(linkedDeviceName)) {
                            log.info("模糊查询<设备名>或<设备ip>或<设备虚墙名>为：[{}]", linkedDeviceName);
                            Pattern deviceNamePattern = Pattern.compile(linkedDeviceName);
                            interfaceVOS = (List)interfaceVOS1.stream().filter((interfaceVOx) -> {
                                DeviceSummaryDataRO deviceSummaryData = (DeviceSummaryDataRO)interfaceVOx.getDevices().get(0);
                                boolean deviceAliasAndPrimaryId = deviceNamePattern.matcher(deviceSummaryData.getDeviceAlias()).find() || deviceNamePattern.matcher(deviceSummaryData.getPrimaryId()).find();
                                if (deviceAliasAndPrimaryId) {
                                    return true;
                                } else {
                                    return deviceSummaryData.getVsysName() != null ? deviceNamePattern.matcher(deviceSummaryData.getVsysName()).find() : false;
                                }
                            }).collect(Collectors.toList());
                        }
                        break;
                    }
                }

                return new MemoryPagination(pageNum, pageSize, (List)interfaceVOS);
            }
        }
    }

    public SubnetLinkedDeviceRO listSubnetLinkedDevice(String subnetUuid) throws Exception {
        SubnetLinkedDeviceRO subnetLinkedDeviceRO = this.whaleSubnetObjectClient.getSubnetLinkedDevice(subnetUuid);
        log.info("排除掉设备中冗余的接口，只留子网为{}的接口", subnetUuid);
        if (subnetLinkedDeviceRO.getSuccess()) {
            List<DeviceSummaryDataRO> deviceSummaryDataROS = subnetLinkedDeviceRO.getData();
            Iterator var4 = deviceSummaryDataROS.iterator();

            while(var4.hasNext()) {
                DeviceSummaryDataRO deviceSummaryDataRO = (DeviceSummaryDataRO)var4.next();
                List<InterfacesRO> interfaces = deviceSummaryDataRO.getInterfaces();
                interfaces.removeIf((anInterface) -> {
                    return !StringUtils.equalsAny(subnetUuid, new CharSequence[]{anInterface.getIp4SubnetUuid(), anInterface.getIp6SubnetUuid()});
                });
            }
        }

        return subnetLinkedDeviceRO;
    }

    public DeviceSummaryRO getDevicesSummary(Boolean skipHost) throws Exception {
        DeviceSummaryRO deviceSummaryRO = this.whaleDeviceObjectClient.getDevicesSummaryRO(skipHost, false);
        if (deviceSummaryRO.getSuccess()) {
            List<DeviceSummaryDataRO> deviceSummaryDataROS = deviceSummaryRO.getData();
            Map<String, String> uuidDeviceAliasMap = (Map)deviceSummaryDataROS.stream().filter((x) -> {
                return StringUtils.isNotEmpty(x.getDeviceAlias());
            }).collect(Collectors.toMap(DeviceSummaryDataRO::getUuid, DeviceSummaryDataRO::getDeviceAlias));
            deviceSummaryDataROS.stream().filter((x) -> {
                return StringUtils.equals("true", x.getIsVsys());
            }).forEach((deviceSummaryDataRO) -> {
                deviceSummaryDataRO.setName(deviceSummaryDataRO.getVsysName());
                if (StringUtils.isEmpty(deviceSummaryDataRO.getDeviceAlias())) {
                    deviceSummaryDataRO.setDeviceAlias((String)uuidDeviceAliasMap.get(deviceSummaryDataRO.getRootDeviceUuid()));
                }

            });
        }

        return deviceSummaryRO;
    }

    public JSONObject getDeviceObject(String deviceUuid, String type) throws Exception {
        return this.whaleDeviceObjectClient.getDeviceObject(deviceUuid, type);
    }

    public ZoneVO getDeviceZoneObject(String deviceUuid, String zoneUuid) throws Exception {
        ZoneRO zoneRO = this.whaleDeviceObjectClient.getDeviceZoneRO(deviceUuid, zoneUuid);
        ZoneVO zoneVO = (ZoneVO)JSONObject.toJavaObject((JSON)JSONObject.toJSON(zoneRO), ZoneVO.class);
        if (zoneVO.getData() != null && zoneVO.getData().size() != 0) {
            List<ZoneDataVO> zoneDataVOS = zoneVO.getData();
            Iterator var6 = zoneDataVOS.iterator();

            while(var6.hasNext()) {
                ZoneDataVO zoneDataVO = (ZoneDataVO)var6.next();
                if (zoneDataVO.getInterfaces() != null) {
                    zoneDataVO.getInterfaces().forEach((interfaceVO) -> {
                        log.debug("处理子网的ipaddress不正确的情况");
                        String ipType = interfaceVO.getIpType();
                        SubnetListRO subnetListRO;
                        if ("IP6".equals(ipType)) {
                            subnetListRO = this.whaleSubnetObjectClient.getSubnetObject(interfaceVO.getIp6SubnetUuid());
                        } else {
                            subnetListRO = this.whaleSubnetObjectClient.getSubnetObject(interfaceVO.getIp4SubnetUuid());
                        }

                        if (subnetListRO.getSuccess() && subnetListRO.getData().size() == 1) {
                            SubnetRO subnetRO = (SubnetRO)subnetListRO.getData().get(0);
                            interfaceVO.setIp4SubnetAddress(subnetRO.getIp4BaseAddress());
                            interfaceVO.setIp4MaskLength(subnetRO.getIp4MaskLength());
                            interfaceVO.setIp6SubnetAddress(subnetRO.getIp6BaseAddress());
                            interfaceVO.setIp6MaskLength(subnetRO.getIp6MaskLength());
                            interfaceVO.setSubnetName(subnetRO.getName());
                        }

                    });
                }
            }
        }

        return zoneVO;
    }

    public AccessQueryRO getAccessQueryObject(AccessQueryDTO accessQueryDTO) throws Exception {
        return this.whaleAccessAnalyzeClient.getAccessQueryObject(accessQueryDTO);
    }

    public AccessQueryTrafficResultRO getAccessQueryTraffic(AccessQueryDTO accessQueryDTO) {
        return this.whaleAccessAnalyzeClient.getAccessQueryTraffic(accessQueryDTO);
    }

    public TrafficFileVO queryExport(AccessQueryDTO accessQueryDTO) {
        if (!(new File(this.trafficDirectoryFileDir)).exists()) {
            TotemsFileUtils.createDirectory(this.trafficDirectoryFileDir);
        }

        AccessQueryTrafficExportRO result = this.whaleAccessAnalyzeClient.getTrafficExportList(accessQueryDTO);
        List<AccessQueryTrafficItemExportDTO> list = new ArrayList();
        List<AccessQueryTrafficItemExportRO> trafficList = result.getData();
        if (CollectionUtils.isNotEmpty(trafficList)) {
            list = (List)trafficList.stream().map((item) -> {
                AccessQueryTrafficItemExportDTO ae = new AccessQueryTrafficItemExportDTO();
                BeanUtils.copyProperties(item, ae);
                return ae;
            }).collect(Collectors.toList());
        }

        TrafficFileVO trafficFileVO = new TrafficFileVO();
        String fileName = "数据流结果" + TotemsDateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
        String fileFullPath = this.trafficDirectoryFileDir + fileName;

        try {
            TotemsExcelExport ee = new TotemsExcelExport("数据流结果", AccessQueryTrafficItemExportDTO.class);
            Throwable var9 = null;

            try {
                ee.setDataList((List)list).writeFile(fileFullPath);
                trafficFileVO.setData(this.trafficDirectoryHandler + fileName);
            } catch (Throwable var20) {
                var9 = var20;
                throw var20;
            } finally {
                if (ee != null) {
                    if (var9 != null) {
                        try {
                            ee.close();
                        } catch (Throwable var19) {
                            var9.addSuppressed(var19);
                        }
                    } else {
                        ee.close();
                    }
                }

            }
        } catch (FileNotFoundException var22) {
            var22.printStackTrace();
        } catch (IOException var23) {
            var23.printStackTrace();
        }

        return trafficFileVO;
    }

    public PathAnalyzeModifyVO getDetailPathObject(PathAnalyzeDTO analyzeDTO, Boolean isFilterNoPath) throws Exception {
        PathAnalyzeRO pathAnalyzeRO = this.whalePathAnalyzeClient.getDetailPathObject(analyzeDTO);
        PathAnalyzeModifyVO pathAnalyzeModifyVO = (PathAnalyzeModifyVO)JSONObject.toJavaObject((JSON)JSONObject.toJSON(pathAnalyzeRO), PathAnalyzeModifyVO.class);
        log.info("循环查询，通过设备和接口的uuid查询子网的uuid,并将数据写入data");
        if (pathAnalyzeModifyVO.getData() != null && pathAnalyzeModifyVO.getData().size() != 0) {
            Map<String, TopoNodeDO> nodeMap = (Map)this.topoNodeMapper.listTopoNode((String)null).stream().filter((topoNodeDOx) -> {
                return StringUtils.isNotEmpty(topoNodeDOx.getUuid());
            }).collect(Collectors.toMap(TopoNodeDO::getUuid, (v) -> {
                return v;
            }));
            List<PathAnalyzeDataModifyVO> pathAnalyzeDataModifyVOS = pathAnalyzeModifyVO.getData();
            Iterator var7 = pathAnalyzeDataModifyVOS.iterator();

            while(var7.hasNext()) {
                PathAnalyzeDataModifyVO pathAnalyzeDataModifyVO = (PathAnalyzeDataModifyVO)var7.next();
                List<PathInfoModifyVO> pathInfoModifyVOS = pathAnalyzeDataModifyVO.getPathList();
                if (pathInfoModifyVOS == null || pathInfoModifyVOS.size() == 0) {
                    log.info("数据异常，路径不通");
                    pathAnalyzeModifyVO.setSuccess(false);
                    if (StringUtils.isNotBlank(pathAnalyzeDataModifyVO.getMsg())) {
                        pathAnalyzeModifyVO.setMsg(pathAnalyzeDataModifyVO.getMsg());
                    } else {
                        pathAnalyzeModifyVO.setMsg("查无路径，请重新选择源子网或目的子网再查询");
                    }
                    break;
                }

                for(int i = 0; i < pathInfoModifyVOS.size(); ++i) {
                    PathInfoModifyVO pathInfoModifyVO = (PathInfoModifyVO)pathInfoModifyVOS.get(i);
                    if (isFilterNoPath != null && isFilterNoPath) {
                        log.info("过滤掉pathType为'NO_PATH'的路径");
                        String pathType = pathInfoModifyVO.getPathType();
                        if (StringUtils.isNotEmpty(pathType) && "NO_PATH".equals(pathType)) {
                            pathInfoModifyVOS.remove(pathInfoModifyVO);
                            --i;
                            continue;
                        }
                    }

                    List<PathDetailModifyVO> pathDetailModifyVOS = pathInfoModifyVO.getDeviceDetails();
                    if (pathDetailModifyVOS != null && pathDetailModifyVOS.size() != 0) {
                        Iterator var13 = pathDetailModifyVOS.iterator();

                        while(var13.hasNext()) {
                            PathDetailModifyVO pathDetailModifyVO = (PathDetailModifyVO)var13.next();
                            String deviceUuid = pathDetailModifyVO.getDeviceUuid();
                            if (!nodeMap.containsKey(deviceUuid)) {
                                log.error("deviceUuid:[{}]不存在", deviceUuid);
                                pathAnalyzeModifyVO.setSuccess(false);
                                pathAnalyzeModifyVO.setMsg("deviceUuid:" + deviceUuid + "不存在");
                                return pathAnalyzeModifyVO;
                            }

                            pathDetailModifyVO.setDevice((TopoNodeDO)nodeMap.get(deviceUuid));
                            this.pathDetailModifyHandle(pathDetailModifyVO);
                        }
                    }

                    List<PathDetailModifyVO> hostDetails = pathInfoModifyVO.getHostDetails();
                    if (hostDetails != null && hostDetails.size() != 0) {
                        Iterator var23 = hostDetails.iterator();

                        while(var23.hasNext()) {
                            PathDetailModifyVO pathDetailModifyVO = (PathDetailModifyVO)var23.next();
                            String deviceUuid = pathDetailModifyVO.getDeviceUuid();
                            DeviceRO deviceRO = this.whaleDeviceObjectClient.getDeviceROByUuid(deviceUuid);
                            if (!deviceRO.getSuccess() || deviceRO.getData() == null || deviceRO.getData().size() != 1) {
                                log.error("deviceUuid:[{}]的主机不存在", deviceUuid);
                                pathAnalyzeModifyVO.setSuccess(false);
                                pathAnalyzeModifyVO.setMsg("deviceUuid:" + deviceUuid + "的主机不存在");
                                return pathAnalyzeModifyVO;
                            }

                            DeviceDataRO deviceDataRO = (DeviceDataRO)deviceRO.getData().get(0);
                            TopoNodeDO topoNodeDO = new TopoNodeDO();
                            topoNodeDO.setUuid(deviceUuid);
                            topoNodeDO.setIp(deviceDataRO.getPrimaryId());
                            String name = deviceDataRO.getName();
                            topoNodeDO.setHostname(name);
                            topoNodeDO.setDeviceName(name);
                            pathDetailModifyVO.setDevice(topoNodeDO);
                            this.pathDetailModifyHandle(pathDetailModifyVO);
                        }
                    }

                    List<PathDetailModifyVO> edrDetails = pathInfoModifyVO.getEdrDetails();
                    if (edrDetails != null && edrDetails.size() != 0) {
                        Iterator var26 = edrDetails.iterator();

                        while(var26.hasNext()) {
                            PathDetailModifyVO pathDetailModifyVO = (PathDetailModifyVO)var26.next();
                            String deviceUuid = pathDetailModifyVO.getDeviceUuid();
                            if (!nodeMap.containsKey(deviceUuid)) {
                                log.error("deviceUuid:[{}]不存在", deviceUuid);
                                throw new Exception("deviceUuid:" + deviceUuid + "不存在");
                            }

                            pathDetailModifyVO.setDevice((TopoNodeDO)nodeMap.get(deviceUuid));
                        }
                    }

                    List<PathDetailModifyVO> arpDetails = pathInfoModifyVO.getArpDetails();
                    if (arpDetails != null && arpDetails.size() != 0) {
                        Iterator var29 = arpDetails.iterator();

                        while(var29.hasNext()) {
                            PathDetailModifyVO pathDetailModifyVO = (PathDetailModifyVO)var29.next();
                            String deviceUuid = pathDetailModifyVO.getDeviceUuid();
                            if (!nodeMap.containsKey(deviceUuid)) {
                                log.error("deviceUuid:[{}]不存在", deviceUuid);
                                pathAnalyzeModifyVO.setSuccess(false);
                                pathAnalyzeModifyVO.setMsg("deviceUuid:" + deviceUuid + "不存在");
                                return pathAnalyzeModifyVO;
                            }

                            pathDetailModifyVO.setDevice((TopoNodeDO)nodeMap.get(deviceUuid));
                            this.pathDetailModifyHandle(pathDetailModifyVO);
                        }
                    }
                }

                log.debug("处理协议号转字符串");
                pathAnalyzeDataModifyVO.getPathList().forEach((pathInfoModifyVOx) -> {
                    this.protocolToStrHandler(pathInfoModifyVOx.getBeginFlow());
                    this.protocolToStrHandler(pathInfoModifyVOx.getEndFlow());
                });
            }
        } else {
            log.info("数据异常，无路径");
            pathAnalyzeModifyVO.setSuccess(false);
            pathAnalyzeModifyVO.setMsg("数据异常，无路径");
        }

        return pathAnalyzeModifyVO;
    }

    private void pathDetailModifyHandle(PathDetailModifyVO pathDetailModifyVO) {
        String deviceUuid = pathDetailModifyVO.getDeviceUuid();
        String inIfId = pathDetailModifyVO.getInIfId();
        String outIfId = pathDetailModifyVO.getOutIfId();
        InterfaceExtendRO interfaceExtend = this.whaleDeviceObjectClient.getDeviceInterfaceExtendRO(deviceUuid, false);
        if (interfaceExtend.getData() != null) {
            interfaceExtend.getData().forEach((interfaceExtendData) -> {
                String uuid = interfaceExtendData.getUuid();
                if (uuid.equals(inIfId) || uuid.equals(outIfId)) {
                    InterfaceVO interfaceVO = new InterfaceVO();
                    BeanUtils.copyProperties(interfaceExtendData, interfaceVO);
                    SubnetRO subnetRO = interfaceExtendData.getSubnet();
                    interfaceVO.setIp4SubnetAddress(subnetRO.getIp4BaseAddress());
                    interfaceVO.setIp4MaskLength(subnetRO.getIp4MaskLength());
                    interfaceVO.setIp6SubnetAddress(subnetRO.getIp6BaseAddress());
                    interfaceVO.setIp6MaskLength(subnetRO.getIp6MaskLength());
                    interfaceVO.setSubnetName(subnetRO.getName());
                    if (uuid.equals(inIfId)) {
                        log.info("进接口{}匹配成功", inIfId);
                        pathDetailModifyVO.setInInterface(interfaceVO);
                    }

                    if (uuid.equals(outIfId)) {
                        log.info("出接口{}匹配成功", outIfId);
                        pathDetailModifyVO.setOutInterface(interfaceVO);
                    }
                }

            });
        }

    }

    private void protocolToStrHandler(List<PathFlowVO> pathFlowVOList) {
        if (pathFlowVOList != null && pathFlowVOList.size() > 0) {
            Map<Integer, String> numMap = this.protocolMapConfig.getNumMap();
            pathFlowVOList.forEach((pathFlowVO) -> {
                List<SrcDstIntegerDTO> protocols = pathFlowVO.getProtocols();
                if (protocols != null && protocols.size() > 0) {
                    List<SrcDstStringDTO> srcDstStringDTOS = new ArrayList();
                    protocols.forEach((srcDstIntegerDTO) -> {
                        SrcDstStringDTO srcDstStringDTO = new SrcDstStringDTO();
                        Integer start = srcDstIntegerDTO.getStart();
                        Integer end = srcDstIntegerDTO.getEnd();
                        srcDstStringDTO.setStart(numMap.containsKey(start) ? (String)numMap.get(start) : String.valueOf(start));
                        srcDstStringDTO.setEnd(numMap.containsKey(end) ? (String)numMap.get(end) : String.valueOf(end));
                        srcDstStringDTOS.add(srcDstStringDTO);
                    });
                    pathFlowVO.setProtocolsToString(srcDstStringDTOS);
                }

            });
        }

    }
    public Map<String, Map<String, Map<String, Object>>> listTopoNodeByLayerUuidMetoo(String layerUuid, Boolean isFilter, String userId, String branchLevel) throws Exception {
//        String branchLevel = "00";
       if(branchLevel == null || branchLevel.equals("")){
           if (!AliStringUtils.isEmpty(userId)) {
               branchLevel = this.umsUserMapper.getBranchLevelById(userId);
           }
       }

        Map<String, Map<String, Map<String, Object>>> deviceTypeMap = new HashMap(16);
        Map<String, DeviceSummaryDataRO> deviceNameMap = new HashMap(16);
        DeviceSummaryRO deviceSummaryRO = this.whaleDeviceObjectClient.getDevicesSummaryRO(true, true);
        List topoNodeDOS;
        if (deviceSummaryRO.getSuccess()) {
            topoNodeDOS = deviceSummaryRO.getData();
            deviceNameMap = (Map)topoNodeDOS.stream().collect(Collectors.toMap(DeviceSummaryDataRO::getUuid, (y) -> {
                return y;
            }));
        }

        if (isFilter) {
            log.info("根据图层uuid过滤设备");
            topoNodeDOS = this.topoNodeMapper.listTopoNodeByLayerUuid(layerUuid, branchLevel);
        } else {
            log.info("不过滤设备");
            topoNodeDOS = this.topoNodeMapper.listTopoNode(branchLevel);
        }

        Iterator var9 = topoNodeDOS.iterator();

        while(var9.hasNext()) {
            TopoNodeDO topoNodeDO = (TopoNodeDO)var9.next();
            if (topoNodeDO.getUuid() == null) {
                log.info("设备:{}的uuid为null,跳过该设备", topoNodeDO.getDeviceName());
            } else {
                DeviceSummaryDataRO deviceSummaryDataRO = (DeviceSummaryDataRO)((Map)deviceNameMap).get(topoNodeDO.getUuid());
                if (deviceSummaryDataRO == null) {
                    log.error("设备:[{}]在mysql中存在，在mongo数据库中不存在，数据不同步", topoNodeDO.getUuid());
                    throw new RuntimeException("设备:[" + topoNodeDO.getUuid() + "]在mysql中存在，在mongo数据库中不存在，数据不同步");
                }

                if (StringUtils.equals("true", deviceSummaryDataRO.getIsVsys())) {
                    topoNodeDO.setIsVsys("true");
                    topoNodeDO.setName(deviceSummaryDataRO.getVsysName());
                } else {
                    topoNodeDO.setName(deviceSummaryDataRO.getName());
                }

                String type = topoNodeDO.getType();
                log.debug("处理是否存在设备类型");
                Object vendorNameMap;
                if (deviceTypeMap.containsKey(type)) {
                    vendorNameMap = deviceTypeMap.get(type);
                } else {
                    vendorNameMap = new HashMap(16);
                    deviceTypeMap.put(type, (Map<String, Map<String, Object>>) vendorNameMap);
                }

                String vendorName = topoNodeDO.getVendorName();
                log.debug("处理是否存在厂商类型");
                if (((Map)vendorNameMap).containsKey(vendorName)) {
                    Map<String, Object> dataMap = (Map)((Map)vendorNameMap).get(vendorName);
                    int total = (Integer)dataMap.get("total");
                    dataMap.put("total", total + 1);
                    List<TopoNodeDO> topoNodeDOList = (List)dataMap.get("data");
                    topoNodeDOList.add(topoNodeDO);
                } else {
                    Map<String, Object> dataMap = new HashMap(16);
                    List<TopoNodeDO> topoNodeDOList = new ArrayList();
                    topoNodeDOList.add(topoNodeDO);
                    dataMap.put("total", 1);
                    dataMap.put("data", topoNodeDOList);
                    ((Map)vendorNameMap).put(vendorName, dataMap);
                }
            }
        }

        deviceTypeMap.forEach((typex, typeMap) -> {
            typeMap.forEach((vendor, vendorMap) -> {
                vendorMap.forEach((x, y) -> {
                    if (x.equals("data")) {
                        List<TopoNodeDO> topoNodeDOList = (List)y;
                        List<TopoNodeDO> topoNodeDOListOrder = (List)topoNodeDOList.stream().sorted(Comparator.comparing(TopoNodeDO::getCreatedTime).reversed()).collect(Collectors.toList());
                        vendorMap.put("data", topoNodeDOListOrder);
                    }

                });
            });
        });
        return deviceTypeMap;
    }


    public Map<String, Map<String, Map<String, Object>>> listTopoNodeByLayerUuid(String layerUuid, Boolean isFilter, String userId) throws Exception {
        String branchLevel = "00";
        if (!AliStringUtils.isEmpty(userId)) {
            branchLevel = this.umsUserMapper.getBranchLevelById(userId);
        }

        Map<String, Map<String, Map<String, Object>>> deviceTypeMap = new HashMap(16);
        Map<String, DeviceSummaryDataRO> deviceNameMap = new HashMap(16);
        DeviceSummaryRO deviceSummaryRO = this.whaleDeviceObjectClient.getDevicesSummaryRO(true, true);
        List topoNodeDOS;
        if (deviceSummaryRO.getSuccess()) {
            topoNodeDOS = deviceSummaryRO.getData();
            deviceNameMap = (Map)topoNodeDOS.stream().collect(Collectors.toMap(DeviceSummaryDataRO::getUuid, (y) -> {
                return y;
            }));
        }

        if (isFilter) {
            log.info("根据图层uuid过滤设备");
            topoNodeDOS = this.topoNodeMapper.listTopoNodeByLayerUuid(layerUuid, branchLevel);
        } else {
            log.info("不过滤设备");
            topoNodeDOS = this.topoNodeMapper.listTopoNode(branchLevel);
        }

        Iterator var9 = topoNodeDOS.iterator();

        while(var9.hasNext()) {
            TopoNodeDO topoNodeDO = (TopoNodeDO)var9.next();
            if (topoNodeDO.getUuid() == null) {
                log.info("设备:{}的uuid为null,跳过该设备", topoNodeDO.getDeviceName());
            } else {
                DeviceSummaryDataRO deviceSummaryDataRO = (DeviceSummaryDataRO)((Map)deviceNameMap).get(topoNodeDO.getUuid());
                if (deviceSummaryDataRO == null) {
                    log.error("设备:[{}]在mysql中存在，在mongo数据库中不存在，数据不同步", topoNodeDO.getUuid());
                    throw new RuntimeException("设备:[" + topoNodeDO.getUuid() + "]在mysql中存在，在mongo数据库中不存在，数据不同步");
                }

                if (StringUtils.equals("true", deviceSummaryDataRO.getIsVsys())) {
                    topoNodeDO.setIsVsys("true");
                    topoNodeDO.setName(deviceSummaryDataRO.getVsysName());
                } else {
                    topoNodeDO.setName(deviceSummaryDataRO.getName());
                }

                String type = topoNodeDO.getType();
                log.debug("处理是否存在设备类型");
                Object vendorNameMap;
                if (deviceTypeMap.containsKey(type)) {
                    vendorNameMap = (Map)deviceTypeMap.get(type);
                } else {
                    vendorNameMap = new HashMap(16);
                    deviceTypeMap.put(type, (Map<String, Map<String, Object>>) vendorNameMap);
                }

                String vendorName = topoNodeDO.getVendorName();
                log.debug("处理是否存在厂商类型");
                if (((Map)vendorNameMap).containsKey(vendorName)) {
                    Map<String, Object> dataMap = (Map)((Map)vendorNameMap).get(vendorName);
                    int total = (Integer)dataMap.get("total");
                    dataMap.put("total", total + 1);
                    List<TopoNodeDO> topoNodeDOList = (List)dataMap.get("data");
                    topoNodeDOList.add(topoNodeDO);
                } else {
                    Map<String, Object> dataMap = new HashMap(16);
                    List<TopoNodeDO> topoNodeDOList = new ArrayList();
                    topoNodeDOList.add(topoNodeDO);
                    dataMap.put("total", 1);
                    dataMap.put("data", topoNodeDOList);
                    ((Map)vendorNameMap).put(vendorName, dataMap);
                }
            }
        }

        deviceTypeMap.forEach((typex, typeMap) -> {
            typeMap.forEach((vendor, vendorMap) -> {
                vendorMap.forEach((x, y) -> {
                    if (x.equals("data")) {
                        List<TopoNodeDO> topoNodeDOList = (List)y;
                        List<TopoNodeDO> topoNodeDOListOrder = (List)topoNodeDOList.stream().sorted(Comparator.comparing(TopoNodeDO::getCreatedTime).reversed()).collect(Collectors.toList());
                        vendorMap.put("data", topoNodeDOListOrder);
                    }

                });
            });
        });
        return deviceTypeMap;
    }

    public LayerSplitSubnetRO splitSubnet(SplitSubnetDTO splitSubnetDTO) throws Exception {
        SplitSubnetRO splitSubnetRO = this.whaleTopoClient.splitSubnet(splitSubnetDTO);
        LayerSplitSubnetRO result = new LayerSplitSubnetRO();
        result.setData(new ArrayList());
        result.setSuccess(splitSubnetRO.getSuccess());
        List<SplitSubnetDataRO> splitSubnetROList = splitSubnetRO.getData();
        if (splitSubnetROList != null && splitSubnetROList.size() != 0) {
            for(int i = 0; i < splitSubnetROList.size(); ++i) {
                SplitSubnetDataRO splitSubnetDataRO = (SplitSubnetDataRO)splitSubnetROList.get(i);
                LayerSplitSubnetDataRO lsplitSubnetDataRO = new LayerSplitSubnetDataRO(splitSubnetDataRO);
                String[] resultSubnetUuids = splitSubnetDataRO.getResultSubnetUuids();
                List<ElementRO> elementROs = splitSubnetDataRO.getElements();
                List<LayerElementRO> newElementROs = new ArrayList();
                if (elementROs != null && elementROs.size() != 0 && resultSubnetUuids != null && resultSubnetUuids.length == elementROs.size()) {
                    for(int j = 0; j < elementROs.size(); ++j) {
                        ElementRO elementRO = (ElementRO)elementROs.get(j);
                        LayerElementRO newElementRO = new LayerElementRO();
                        newElementRO.setIfIdGroup(elementRO.getIfIdGroup());
                        List<IfMatchGroupRO> matchDTOS = elementRO.getIfMatchGroup();
                        List<LayerIfMatchGroupRO> newGroupDTOS = new ArrayList();
                        if (matchDTOS != null && matchDTOS.size() != 0) {
                            for(int k = 0; k < matchDTOS.size(); ++k) {
                                IfMatchGroupRO matchDTO = (IfMatchGroupRO)matchDTOS.get(k);
                                LayerIfMatchGroupRO newmatchDTO = new LayerIfMatchGroupRO();
                                newmatchDTO.setNetInterface(matchDTO.getNetInterface());
                                newmatchDTO.setDeviceMatchInfo(matchDTO.getDeviceMatchInfo());
                                newmatchDTO.setLinkedSubnet(matchDTO.getLinkedSubnet());
                                DeviceRO deviceRO = this.whaleDeviceObjectClient.getDeviceROByUuid(matchDTO.getDeviceMatchInfo().getDeviceUuid());
                                SubnetListRO subnetListRO = this.whaleSubnetObjectClient.getSubnetObject(resultSubnetUuids[j]);
                                if (deviceRO != null && deviceRO.getData() != null && deviceRO.getData().size() > 0) {
                                    DeviceDataRO deviceData = (DeviceDataRO)deviceRO.getData().get(0);
                                    DeviceDataVO deviceDataVO = (DeviceDataVO)JSONObject.toJavaObject((JSON)JSONObject.toJSON(deviceData), DeviceDataVO.class);
                                    String deviceUuid = matchDTO.getDeviceMatchInfo().getDeviceUuid();
                                    TopoNodeDO topoNode = this.topoNodeMapper.getTopoNodeByNodeUuid(deviceUuid);
                                    String deviceName = topoNode.getDeviceName();
                                    deviceDataVO.setDeviceName(deviceName);
                                    newmatchDTO.setDeviceDataRO(deviceDataVO);
                                }

                                if (subnetListRO != null && subnetListRO.getData() != null && subnetListRO.getData().size() > 0) {
                                    newmatchDTO.setResultSubnet((SubnetRO)subnetListRO.getData().get(0));
                                }

                                newGroupDTOS.add(newmatchDTO);
                            }
                        }

                        newElementRO.setIfMatchGroup(newGroupDTOS);
                        newElementROs.add(newElementRO);
                    }
                }

                lsplitSubnetDataRO.setElements(newElementROs);
                result.getData().add(lsplitSubnetDataRO);
            }
        }

        return result;
    }

    public LinkVpnRO linkVpn(LinkVpnDTO linkVpnDTO) throws Exception {
        return this.whaleTopoClient.linkVpn(linkVpnDTO);
    }

    public LinkVpnRO getLinkVpn() throws Exception {
        return this.whaleTopoClient.getLinkVpn();
    }

    public LinkVpnRO deleteLinkVpn(String uuid) throws Exception {
        return this.whaleTopoClient.deleteLinkVpn(uuid);
    }

    public Map<String, Object> listVpnSubnets(String deviceUuid) throws Exception {
        List<InterfacesRO> interfaceROList = new ArrayList();
        DeviceRO deviceVO = this.whaleDeviceObjectClient.getDeviceROByUuid(deviceUuid);
        if (deviceVO.getSuccess()) {
            interfaceROList = ((DeviceDataRO)deviceVO.getData().get(0)).getInterfaces();
        }

        Map<String, Object> map = new HashMap(2);
        map.put("total", ((List)interfaceROList).size());
        map.put("rows", interfaceROList);
        return map;
    }

    public SubnetListVO getSubnetObject(String subnetUuid, String ip4Addr) throws Exception {
        SubnetListVO subnetListVO = new SubnetListVO();
        if (StringUtils.isNotEmpty(ip4Addr)) {
            log.info("查询子网的ip4Address为[{}]的子网对象列表", ip4Addr);
            Set<String> uuidSet = new HashSet();
            SubnetUuidListRO subnetUuidListRO = this.whaleSubnetObjectClient.getSubnetUuidList(ip4Addr);
            if (!subnetUuidListRO.getSuccess()) {
                log.info("查询错误：{}", subnetUuidListRO.getMessage());
                subnetListVO = (SubnetListVO)JSONObject.toJavaObject((JSON)JSONObject.toJSON(subnetUuidListRO), SubnetListVO.class);
            } else {
                List<String> subnetUuidList = subnetUuidListRO.getData();
                uuidSet.addAll(subnetUuidList);
            }

            List<String> businessUuidList = new ArrayList();
            Iterator var8;
            if (IPUtil.isIP(ip4Addr) || IPUtil.isIpRange(ip4Addr) || IPUtil.isSubnetMask(ip4Addr)) {
                log.info("查询[{}]业务子网", ip4Addr);
                ResultRO<List<SubnetRO>> listResultRO = this.businessSubnetClient.getSubnetByIp4(ip4Addr);
                if (listResultRO.getData() != null && ((List)listResultRO.getData()).size() > 0) {
                    var8 = ((List)listResultRO.getData()).iterator();

                    while(var8.hasNext()) {
                        SubnetRO subnetRO = (SubnetRO)var8.next();
                        String uuid = subnetRO.getUuid();
                        boolean addStatus = uuidSet.add(uuid);
                        if (addStatus) {
                            businessUuidList.add(uuid);
                        }
                    }
                }
            }

            if (uuidSet.size() == 0) {
                subnetListVO.setSuccess(false);
            } else {
                subnetListVO.setSuccess(true);
                List<SubnetVO> subnetVOS = new ArrayList();

                SubnetVO subnetVO;
                for(var8 = uuidSet.iterator(); var8.hasNext(); subnetVOS.add(subnetVO)) {
                    String uuid = (String)var8.next();
                    SubnetRO subnetRO = (SubnetRO)this.whaleSubnetObjectClient.getSubnetObject(uuid).getData().get(0);
                    subnetVO = (SubnetVO)JSONObject.toJavaObject((JSON)JSONObject.toJSON(subnetRO), SubnetVO.class);
                    if (businessUuidList.contains(uuid)) {
                        subnetVO.setBusinessSubnet(true);
                    }
                }

                subnetListVO.setData(subnetVOS);
            }
        } else {
            SubnetListRO subnetListRO = this.whaleSubnetObjectClient.getSubnetObject(subnetUuid);
            subnetListVO = (SubnetListVO)JSONObject.toJavaObject((JSON)JSONObject.toJSON(subnetListRO), SubnetListVO.class);
        }

        return subnetListVO;
    }

    public SubnetListRO updateSubnetObject(SubnetDTO subnetDTO) throws Exception {
        return this.whaleSubnetObjectClient.updateSubnetObject(subnetDTO);
    }

    public TopoUnDoRO splitSubnetUnDo(String opUuid) throws Exception {
        return this.whaleTopoClient.topoUnDo("SPLIT_SUBNET", opUuid);
    }

    public ResultRO<List<SplitSubnetDataRO>> getAllSplitSubnetSummary() throws Exception {
        return this.whaleTopoClient.getAllSplitSubnetSummary();
    }

    public AnalysisStartRO analysisAccessStart() throws Exception {
        return this.whaleAnalysisClient.analysisAccessStart();
    }

    public AnalysisCancel analysisAccessCancel() throws Exception {
        return this.whaleAnalysisClient.analysisAccessCancel();
    }

    public TaskStatusRO getTaskStatus(String taskType, String taskRunUuid, Integer page, Integer psize) throws Exception {
        return this.whaleTask.getTaskStatus(taskType, taskRunUuid, page, psize);
    }

    public RealSubnetModifyVO getDetailPathSubnet(DetailPathSubnetDTO detailPathSubnetDTO) throws Exception {
        RealSubnetRO realSubnetRO = this.whalePathAnalyzeClient.getDetailPathSubnet(detailPathSubnetDTO);
        RealSubnetModifyVO realSubnetModifyVO = (RealSubnetModifyVO)JSONObject.toJavaObject((JSON)JSONObject.toJSON(realSubnetRO), RealSubnetModifyVO.class);
        log.info("循环查询，通过设备和接口的uuid查询子网的uuid,并将数据写入data");
        if (realSubnetModifyVO.getSuccess() && realSubnetModifyVO.getData() != null && realSubnetModifyVO.getData().size() == 1) {
            RealSubnetDataModifyVO realSubnetDataModifyVO = (RealSubnetDataModifyVO)realSubnetModifyVO.getData().get(0);
            if (realSubnetDataModifyVO.getNoPathList() != null && realSubnetDataModifyVO.getNoPathList().size() != 0) {
                log.info("查询到pathType为'NO_PATH'的路径");
                Map<String, TopoNodeDO> nodeMap = (Map)this.topoNodeMapper.listTopoNode((String)null).stream().filter((topoNodeDO) -> {
                    return StringUtils.isNotEmpty(topoNodeDO.getUuid());
                }).collect(Collectors.toMap(TopoNodeDO::getUuid, (v) -> {
                    return v;
                }));
                List<PathInfoModifyVO> noPathList = realSubnetDataModifyVO.getNoPathList();
                Iterator var7 = noPathList.iterator();

                while(true) {
                    List pathDetailModifyVOS;
                    do {
                        do {
                            if (!var7.hasNext()) {
                                return realSubnetModifyVO;
                            }

                            PathInfoModifyVO pathInfoModifyVO = (PathInfoModifyVO)var7.next();
                            pathDetailModifyVOS = pathInfoModifyVO.getDeviceDetails();
                        } while(pathDetailModifyVOS == null);
                    } while(pathDetailModifyVOS.size() == 0);

                    Iterator var10 = pathDetailModifyVOS.iterator();

                    while(var10.hasNext()) {
                        PathDetailModifyVO pathDetailModifyVO = (PathDetailModifyVO)var10.next();
                        String deviceUuid = pathDetailModifyVO.getDeviceUuid();
                        if (!nodeMap.containsKey(deviceUuid)) {
                            log.error("deviceUuid:[{}]不存在", deviceUuid);
                            throw new Exception("deviceUuid:" + deviceUuid + "不存在");
                        }

                        pathDetailModifyVO.setDevice((TopoNodeDO)nodeMap.get(deviceUuid));
                        this.pathDetailModifyHandle(pathDetailModifyVO);
                    }
                }
            }
        }

        return realSubnetModifyVO;
    }

    public ResultRO<List<InterfacesRO>> findNetInterfaceByUuid(String uuid) {
        return this.whaleDeviceObjectClient.findNetInterfaceByUuid(uuid);
    }
}
