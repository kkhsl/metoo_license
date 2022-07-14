package com.abtnetworks.data.totems.topology.userms.license;

import com.abtnetworks.data.totems.topology.userms.license.data.License;
import com.abtnetworks.data.totems.topology.userms.license.service.LicenseService;
import com.abtnetworks.data.totems.topology.userms.license.utils.Base64;
import com.abtnetworks.data.totems.topology.userms.license.utils.DateTimeUtil;
import com.abtnetworks.data.totems.topology.userms.license.utils.DesCBC;
import com.abtnetworks.data.totems.topology.userms.license.utils.LicenseUtils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Encoder;

@Service
public class LicenseManager {
    private static final Logger logger = Logger.getLogger(LicenseManager.class);
    @Autowired
    private LicenseService licenseService;
    @Autowired
    private LicenseUtils licenseUtils;
    @Autowired
    private LicenseConfig licenseConfig;
    private License license;
    private List<String> existActivationCodeList;
    private String sn;
    private static final int ACTIVATION_CODE_LENGTH = 19;
    private static final int SN_CODE_LENGTH = 24;
    private static final int SN_CPU_LENGTH = 9;
    private static final int SN_MAC_LENGTH = 9;
    private static final String DEFAULT_CUSTOM = "81";
    private static final String DEFAULT_LICENSE_SPLIT_SIGN = "-";
    private static final String DEFAULT_BUSINESSMODULE_SPLIT_SIGN = ";";
    private static final String DEFAULT_LICENSE_NA = "NA";
    private static final int DEFAULT_LICENSE_SPLIT_SIZE = 5;
    private static final int DEFAULT_LICENSE_TRYOUT_DAY = 3;
    private static final String DEFAULT_LICENSE_TRYOUT_DEVNUM = "99999;99999;99999;99999;99999;99999;";
    private static final String LICENSE_TYPE_STANDARD = "S";
    private static final String LICENSE_TYPE_TRYOUT = "T";
    private static final boolean LICENSE_DECIPHER_SUCCESS = true;
    private static final int MAX_NUMBER = 99999;
    private static final int VERSION_1_7 = 0;
    private static final int VERSION_1_8_3 = 1;
    private static final int VERSION_1_8_4 = 2;
    private static final int DEFAULT_MENU_MODULE = 0;
    private static final String PVAP_PLAT_ALL = "A;B;C;D;F";
    private static final String password = "totems99";
    @Value("${licenseSnFile}")
    private String licenseSnFile;

    public LicenseManager() {
    }

    public void init() {
        logger.info("LicenseManage init begin");
        String sn = this.getFileSN();
        logger.info("getFileSN ----：" + sn);
        if (sn == null) {
            sn = this.getSN("81");
            logger.info("getSN ----：" + sn);
        }

        if ("817481827396635869442271".equals(sn) || "817481827390547070977335".equals(sn)) {
            logger.info("mac地址和uuid均未获取到");
            sn = this.setFileSN();
            logger.info("setFileSN ----：" + sn);
        }

        this.sn = sn;
        this.initDefaultLicense();
        this.licenseMerge();
        logger.info("LicenseManage init end");
    }

    private String setFileSN() {
        String path = this.licenseSnFile;
        File file = new File(path);
        String fileSN = null;

        try {
            if (!file.exists()) {
                file.createNewFile();
                FileWriter writer = new FileWriter(file);
                String uuid = RandomStringUtils.randomNumeric(18);
                uuid = "81" + uuid;
                int[] units = this.licenseUtils.getCalculationUnits(new StringBuffer(uuid));
                String result = this.licenseUtils.getValCode(units);
                uuid = uuid + result;
                fileSN = uuid;
                byte[] desResult = DesCBC.encrypt(uuid.getBytes(), "totems99");
                BASE64Encoder encoder = new BASE64Encoder();
                byte[] encode64 = encoder.encode(desResult).getBytes();
                String desSN = new String(encode64);
                logger.info("加密sn:" + desSN);
                writer.write(desSN);
                writer.flush();
                writer.close();
                logger.info("新生成文件和sn:" + file.getPath() + file.getName() + ";" + uuid);
            }
        } catch (Exception var12) {
            logger.error("sn文件创建或读取失败", var12);
        }

        return fileSN;
    }

    private String getFileSN() {
        String path = this.licenseSnFile;
        File file = new File(path);
        String fileSN = null;

        try {
            if (file.exists()) {
                String fileString = null;

                BufferedReader br;
                String line;
                for(br = new BufferedReader(new FileReader(file)); (line = br.readLine()) != null; fileString = line) {
                    ;
                }

                br.close();

                try {
                    byte[] base64Decoded = Base64.decode(fileString.getBytes());
                    byte[] decryResult = DesCBC.decrypt(base64Decoded, "totems99");
                    fileSN = new String(decryResult);
                } catch (Exception var9) {
                    var9.printStackTrace();
                }

                logger.info("文件存在，sn：" + file.getPath() + file.getName() + ";" + fileSN);
            }
        } catch (Exception var10) {
            logger.error("sn文件创建或读取失败", var10);
        }

        return fileSN;
    }

    private void initDefaultLicense() {
        logger.info("LicenseManage initDefaultLicense begin");

        try {
            List<License> licenses = this.licenseService.findAll();
            if (licenses == null || licenses.size() == 0) {
                License defaultLicense = new License();
                String licenseInfo = this.createDefaultLicenseInfo();
                defaultLicense.setLicenseInfoEncrypt(this.licenseUtils.encode(licenseInfo));
                this.licenseService.saveDefaultLicense(defaultLicense);
                logger.info("LicenseManage initDefaultLicense success");
            }

            logger.info("LicenseManage initDefaultLicense end");
        } catch (Exception var4) {
            logger.error("initDefaultLicense error", var4);
        }

    }

    public synchronized void licenseMerge() {
        logger.info("licenseMerge begin");
        this.license = new License();
        this.license.setBusinessModuleList(new ArrayList());
        this.license.setMenuModuleList(new ArrayList());
        this.existActivationCodeList = new ArrayList();
        this.license.setSystemSN(this.sn);
        long minDateTime = 0L;
        long maxDateTime = 0L;
        List<License> licenses = this.licenseService.findAll();
        boolean hasStandardLicense = false;
        boolean hasTryoutdLicense = false;
        long currentTime = System.currentTimeMillis();
        int licenseVersion = 0;
        if (licenses != null && licenses.size() > 0) {
            Iterator var11 = licenses.iterator();

            label121:
            while(true) {
                while(true) {
                    if (!var11.hasNext()) {
                        break label121;
                    }

                    License licenseItem = (License)var11.next();
                    this.convertLicenseInfo(licenseItem);
                    if (!licenseItem.getDecipherSuccess()) {
                        String decipherMsg = this.license.getDecipherMsg();
                        if (decipherMsg == null) {
                            decipherMsg = "";
                        }

                        this.license.setDecipherMsg(decipherMsg + licenseItem.getDecipherMsg());
                    } else if (!this.existActivationCodeList.contains(licenseItem.getActivationCode())) {
                        this.existActivationCodeList.add(licenseItem.getActivationCode());
                        if (currentTime <= licenseItem.getEndTime()) {
                            if ("T".equals(licenseItem.getType())) {
                                if (hasStandardLicense) {
                                    continue;
                                }

                                hasTryoutdLicense = true;
                                this.license.setType("T");
                                this.license.setAvailability(true);
                            } else if ("S".equals(licenseItem.getType())) {
                                hasStandardLicense = true;
                                this.license.setType("S");
                                this.license.setAvailability(true);
                                if (hasTryoutdLicense) {
                                    hasTryoutdLicense = false;
                                    this.license.setLicenseFwNum(0);
                                    this.license.setLicenseRouterNum(0);
                                    this.license.setLicenseHostNum(0);
                                    this.license.setLicenseADNum(0);
                                    this.license.setLicenseGatewayNum(0);
                                    this.license.setLicenseGatekeeperNum(0);
                                    this.license.getBusinessModuleList().clear();
                                    this.license.getMenuModuleList().clear();
                                    minDateTime = 0L;
                                    maxDateTime = 0L;
                                }
                            }

                            if (licenseItem.getLicenseVersion() > licenseVersion) {
                                licenseVersion = licenseItem.getLicenseVersion();
                            }

                            if (!StringUtils.isBlank(licenseItem.getEmpowerCustomer())) {
                                this.license.setEmpowerCustomer(licenseItem.getEmpowerCustomer());
                            }

                            if (minDateTime == 0L || minDateTime > licenseItem.getBeginTime()) {
                                minDateTime = licenseItem.getBeginTime();
                            }

                            if (maxDateTime == 0L || licenseItem.getEndTime() > maxDateTime) {
                                maxDateTime = licenseItem.getEndTime();
                            }

                            this.businessModuleMerge(this.license.getBusinessModuleList(), licenseItem.getBusinessModuleList());
                            this.license.setLicenseFwNum(licenseItem.getLicenseFwNum() + this.license.getLicenseFwNum());
                            this.license.setLicenseRouterNum(licenseItem.getLicenseRouterNum() + this.license.getLicenseRouterNum());
                            this.license.setLicenseHostNum(licenseItem.getLicenseHostNum() + this.license.getLicenseHostNum());
                            this.license.setLicenseADNum(licenseItem.getLicenseADNum() + this.license.getLicenseADNum());
                            this.license.setLicenseGatewayNum(licenseItem.getLicenseGatewayNum() + this.license.getLicenseGatewayNum());
                            this.license.setLicenseGatekeeperNum(licenseItem.getLicenseGatekeeperNum() + this.license.getLicenseGatekeeperNum());
                        }
                    }
                }
            }
        }

        this.license.setLicenseVersion(licenseVersion);
        if (licenseVersion == 0) {
            this.license.setLicenseHostNum(99999);
            this.license.setLicenseADNum(99999);
            this.license.setLicenseGatewayNum(99999);
            this.license.setLicenseGatekeeperNum(99999);
        } else if (licenseVersion == 1) {
            this.license.setLicenseADNum(99999);
            this.license.setLicenseGatewayNum(99999);
            this.license.setLicenseGatekeeperNum(99999);
        }

        if (minDateTime != 0L) {
            this.license.setBeginTime(minDateTime);
            this.license.setBeginTimeStr(DateTimeUtil.longToStr(minDateTime, DateTimeUtil.FORMAT_STANDARD));
            this.license.setUseDay((int)((currentTime - minDateTime) / DateTimeUtil.ONEDAY_TIME));
        }

        if (maxDateTime != 0L) {
            this.license.setEndTime(maxDateTime);
            this.license.setEndTimeStr(DateTimeUtil.longToStr(maxDateTime, DateTimeUtil.FORMAT_STANDARD));
            if (currentTime > maxDateTime) {
                this.license.setSurplusDay(0);
                this.license.setAvailability(false);
            } else {
                this.license.setSurplusDay((int)((maxDateTime - currentTime) / DateTimeUtil.ONEDAY_TIME));
            }
        }

        List<String> businessModuleList = this.license.getBusinessModuleList();
        if (!businessModuleList.isEmpty()) {
            StringBuilder businessModuleNames = new StringBuilder();
            Iterator var21 = businessModuleList.iterator();

            label83:
            while(true) {
                String module;
                do {
                    if (!var21.hasNext()) {
                        this.license.setBusinessModuleStr(businessModuleNames.substring(0, businessModuleNames.length() - 1));
                        break label83;
                    }

                    module = (String)var21.next();
                    if (this.licenseConfig.getBusinessModuleMap().containsKey(module)) {
                        businessModuleNames.append((String)this.licenseConfig.getBusinessModuleMap().get(module)).append("，");
                    }
                } while(!this.licenseConfig.getMenuModuleMap().containsKey(module));

                List<Integer> tempModules = (List)this.licenseConfig.getMenuModuleMap().get(module);
                Iterator var16 = tempModules.iterator();

                while(var16.hasNext()) {
                    int temp = (Integer)var16.next();
                    this.license.getMenuModuleList().add(temp);
                }
            }
        }

        Map<String, Long> moduleEndTimeMap = new HashMap();
        if (licenses != null) {
            licenses.stream().filter((licenseItemx) -> {
                return licenseItemx.getDecipherSuccess() && StringUtils.equalsAny(this.license.getType(), new CharSequence[]{licenseItemx.getType(), null});
            }).forEach((licenseItemx) -> {
                licenseItemx.getBusinessModuleList().forEach((businessModule) -> {
                    if (moduleEndTimeMap.containsKey(businessModule) && (Long)moduleEndTimeMap.get(businessModule) < licenseItemx.getEndTime()) {
                        moduleEndTimeMap.put(businessModule, licenseItemx.getEndTime());
                    } else {
                        moduleEndTimeMap.put(businessModule, licenseItemx.getEndTime());
                    }

                });
            });
        }

        this.license.setModuleEndTimeMap(moduleEndTimeMap);
        logger.info("licenseMerge end");
    }

    public void convertLicenseInfo(License originalLicense) {
        try {
            String licenseInfo = this.licenseUtils.decode(originalLicense.getLicenseInfoEncrypt());
            originalLicense.setLicenseInfo(licenseInfo);
            this.parseLicenseInfo(originalLicense);
            originalLicense.setDecipherSuccess(true);
        } catch (RuntimeException var3) {
            logger.error("convertLicenseInfo decode error,the id = " + originalLicense.getId(), var3);
            originalLicense.setDecipherMsg("license[" + originalLicense.getLicenseInfoEncrypt().substring(0, 20) + "...]convert error:" + var3.getMessage() + ";");
        } catch (Exception var4) {
            logger.error("convertLicenseInfo parse error,the id = " + originalLicense.getId(), var4);
            originalLicense.setDecipherMsg("license activate[" + originalLicense.getActivationCode() + "]convert error:" + var4.getMessage() + ";");
        }

    }

    private void parseLicenseInfo(License originalLicense) throws Exception {
        String licenseInfo = null;

        try {
            licenseInfo = originalLicense.getLicenseInfo();
            originalLicense.setActivationCode(licenseInfo.substring(0, 19));
            originalLicense.setSn(licenseInfo.substring(19, 43));
            if (!this.sn.equals(originalLicense.getSn())) {
                logger.error("getSystemSN = " + this.sn + " not equals originalLicense.getSn() = " + originalLicense.getSn());
                throw new Exception("the pc-sn has change.");
            } else {
                String licenseAuthorizationInfo = licenseInfo.substring(43);
                String[] laiArr = licenseAuthorizationInfo.split("-");
                originalLicense.setType(laiArr[0]);
                if (!"NA".equals(laiArr[1])) {
                    if (!laiArr[1].contains(";")) {
                        logger.info("the old license [" + licenseInfo + "] discard.");
                        throw new Exception("the old license discard.");
                    }

                    String[] deviceNumArray = laiArr[1].split(";");
                    if (deviceNumArray.length <= 2) {
                        originalLicense.setLicenseVersion(0);
                    } else if (deviceNumArray.length == 3) {
                        originalLicense.setLicenseVersion(1);
                    } else if (deviceNumArray.length == 6) {
                        originalLicense.setLicenseVersion(2);
                    }

                    originalLicense.setLicenseFwNum(Integer.parseInt(deviceNumArray[0]));
                    if (deviceNumArray.length > 1 && !deviceNumArray[1].isEmpty()) {
                        originalLicense.setLicenseRouterNum(Integer.parseInt(deviceNumArray[1]));
                        if (deviceNumArray.length > 2 && !deviceNumArray[2].isEmpty()) {
                            originalLicense.setLicenseHostNum(Integer.parseInt(deviceNumArray[2]));
                            if (deviceNumArray.length > 3 && !deviceNumArray[3].isEmpty()) {
                                originalLicense.setLicenseADNum(Integer.parseInt(deviceNumArray[3]));
                                if (deviceNumArray.length > 4 && !deviceNumArray[4].isEmpty()) {
                                    originalLicense.setLicenseGatewayNum(Integer.parseInt(deviceNumArray[4]));
                                    if (deviceNumArray.length > 5 && !deviceNumArray[5].isEmpty()) {
                                        originalLicense.setLicenseGatekeeperNum(Integer.parseInt(deviceNumArray[5]));
                                    }
                                }
                            }
                        }
                    }
                }

                if (!"NA".equals(laiArr[2])) {
                    originalLicense.setBusinessModule(laiArr[2]);
                    originalLicense.setBusinessModuleList(Arrays.asList(laiArr[2].split(";")));
                    List<String> businessModuleList = originalLicense.getBusinessModuleList();
                    StringBuilder businessModuleNames = new StringBuilder();
                    if (!businessModuleList.isEmpty()) {
                        Iterator var7 = businessModuleList.iterator();

                        while(var7.hasNext()) {
                            String module = (String)var7.next();
                            if (this.licenseConfig.getBusinessModuleMap().containsKey(module)) {
                                businessModuleNames.append((String)this.licenseConfig.getBusinessModuleMap().get(module)).append("，");
                            }
                        }
                    }

                    originalLicense.setBusinessModuleStr(businessModuleNames.substring(0, businessModuleNames.length() - 1));
                }

                originalLicense.setBeginTime(DateTimeUtil.strToLong(laiArr[3] + DateTimeUtil.TIME_000000, DateTimeUtil.FORMAT_yyyyMMddHHmmss));
                originalLicense.setBeginTimeStr(DateTimeUtil.longToStr(originalLicense.getBeginTime(), DateTimeUtil.FORMAT_STANDARD));
                if (!"NA".equals(laiArr[4])) {
                    originalLicense.setEndTime(DateTimeUtil.strToLong(laiArr[4] + DateTimeUtil.TIME_235959, DateTimeUtil.FORMAT_yyyyMMddHHmmss));
                    originalLicense.setEndTimeStr(DateTimeUtil.longToStr(originalLicense.getEndTime(), DateTimeUtil.FORMAT_STANDARD));
                }

                if (laiArr.length >= 6) {
                    String empowerCustomer = laiArr[5];
                    if (!StringUtils.isBlank(empowerCustomer)) {
                        originalLicense.setEmpowerCustomer(empowerCustomer);
                    }
                }

            }
        } catch (Exception var9) {
            logger.error("parseLicenseInfo error,the originalLicenseInfo = " + licenseInfo, var9);
            throw var9;
        }
    }

    private String createDefaultLicenseInfo() {
        StringBuilder sb = new StringBuilder();
        this.licenseUtils.getClass();
        sb.append("TTTT-TTTT-TTTT-TTTT");
        sb.append(this.sn);
        sb.append("T");
        sb.append("-");
        sb.append("99999;99999;99999;99999;99999;99999;");
        sb.append("-");
        sb.append("A;B;C;D;F");
        sb.append("-");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String beginDate = DateTimeUtil.dateToStr(calendar.getTime(), DateTimeUtil.FORMAT_yyyyMMdd);
//        String beginDate = DateTimeUtil.dateToStr(new Date(), DateTimeUtil.FORMAT_yyyyMMdd);
        sb.append(beginDate);
        sb.append("-");
        sb.append(DateTimeUtil.addDay(beginDate, DateTimeUtil.FORMAT_yyyyMMdd, 1));
        sb.append("-");
        int remainder = sb.length() % 8;
        if (remainder != 0) {
            for(remainder = 8 - remainder; remainder > 0; --remainder) {
                sb.append("-");
            }
        }

        return sb.toString();
    }

    public String getSN(String customer) {
        logger.info("getSN begin...");
        StringBuffer ret = new StringBuffer();
        ret.append(customer);
        int macAddrValue = 0;
        int cpuIdValue = 0;
        String osName = this.licenseUtils.getPCSystem();
        logger.info("osName = " + osName);
        String linuxUuid;
        if (osName != null) {
            if (osName.contains("WINDOWS")) {
                macAddrValue = this.licenseUtils.getWindowsMac().hashCode();
                cpuIdValue = this.licenseUtils.getWindowsCPUId().hashCode();
            } else if (osName.contains("LINUX")) {
                linuxUuid = this.licenseUtils.getLinuxUUID();
                cpuIdValue = linuxUuid.hashCode();
                this.licenseUtils.getClass();
                if (linuxUuid.equals("AAAAAAAA00000000")) {
                    macAddrValue = this.licenseUtils.getLinuxMac().hashCode();
                } else {
                    this.licenseUtils.getClass();
                    macAddrValue = "AA-AA-AA-AA-AA-AA".hashCode();
                }
            } else {
                this.licenseUtils.getClass();
                macAddrValue = "AA-AA-AA-AA-AA-AA".hashCode();
                this.licenseUtils.getClass();
                cpuIdValue = "AAAAAAAA00000000".hashCode();
            }
        }

        logger.info("macAddrValue = " + macAddrValue);
        logger.info("cpuIdValue = " + cpuIdValue);
        linuxUuid = String.valueOf(Math.abs(macAddrValue));
        int interceptLength;
        if (linuxUuid.length() >= 9) {
            interceptLength = linuxUuid.length() - 9;
            ret.append(linuxUuid.substring(interceptLength));
        } else {
            interceptLength = 9 - linuxUuid.length();
            ret.append(linuxUuid);
            ret.append(this.licenseUtils.getZeroNum(interceptLength));
        }

        String cpuIdStr = String.valueOf(Math.abs(cpuIdValue));
        if (cpuIdStr.length() >= 9) {
            interceptLength = cpuIdStr.length() - 9;
            ret.append(cpuIdStr.substring(interceptLength));
        } else {
            interceptLength = 9 - cpuIdStr.length();
            ret.append(cpuIdStr);
            ret.append(this.licenseUtils.getZeroNum(interceptLength));
        }

        logger.info("macAddrValue +cpuIdValue = " + ret.toString());
        StringBuffer buffer = new StringBuffer(ret.toString());
        int[] units = this.licenseUtils.getCalculationUnits(buffer);
        String result = this.licenseUtils.getValCode(units);
        ret.append(result);
        logger.info("getSN end,SN =" + ret.toString() + ",SN长度 = " + ret.length());
        return ret.toString();
    }

    private void businessModuleMerge(List<String> statisticsList, List<String> singleList) {
        if (singleList != null && !singleList.isEmpty()) {
            Iterator var3 = singleList.iterator();

            while(var3.hasNext()) {
                String module = (String)var3.next();
                if (!statisticsList.contains(module)) {
                    statisticsList.add(module);
                }
            }
        }

    }

    public synchronized License getLicense() {
        return this.license;
    }

    public List<String> getExistActivationCodeList() {
        return this.existActivationCodeList;
    }

    public String getStandard() {
        return "S";
    }

    public String getTryout() {
        return "T";
    }

    public int getDefaultMenuModule() {
        return 0;
    }
}
