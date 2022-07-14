package com.abtnetworks.totems.policy.controller.report;

import com.abtnetworks.totems.common.ro.ResultRO;
import com.abtnetworks.totems.common.utils.DateUtil;
import com.abtnetworks.totems.policy.common.utils.FileUtils;
import com.abtnetworks.totems.policy.domain.Node;
import com.abtnetworks.totems.policy.general.PolicyViewGen;
import com.abtnetworks.totems.policy.service.NodeService;
import com.abtnetworks.totems.policy.vo.PolicyTotalVo;
import com.abtnetworks.totems.policy.vo.PolicyViewVO;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(
        description = "策略概览报表Excel下载"
)
@Controller
public class PolicyViewExcelDownLoadController extends ReportBaseController {
    private static final Logger log = LoggerFactory.getLogger(PolicyViewExcelDownLoadController.class);
    @Value("${policy.download-file}")
    protected String dirPath;
    @Autowired
    protected NodeService nodeService;
    @Autowired
    private PolicyViewGen policyViewGen;

    public PolicyViewExcelDownLoadController() {
    }

    @ApiOperation(
            value = "策略概览excel导出",
            nickname = "鲁薇"
    )
    @ApiResponses({@ApiResponse(
            code = 200,
            message = ""
    )})
    @RequestMapping(
            value = {"/report/policyView/download"},
            produces = {"application/json; charset=utf-8"},
            method = {RequestMethod.GET}
    )
    @ResponseBody
    public ResultRO<JSONObject> download(HttpServletResponse response, @ApiParam(name = "isReload",defaultValue = "false",value = "是否重新生成",required = false) @RequestParam(required = false) String isReload, Authentication attributePrincipal) throws Exception {
        ResultRO<JSONObject> resultRO = new ResultRO(true);
        JSONObject jsonObject = new JSONObject();
        String standardDateTime = DateUtil.getTimeStamp();
        String preFilename = "策略概览报表";

        try {
            preFilename = new String(preFilename.getBytes("UTF-8"), "UTF-8");
            preFilename = preFilename + "_" + standardDateTime;
        } catch (UnsupportedEncodingException var17) {
            log.error("生成策略概览报表文件名称异常", var17);
        }

        String userId = null;
        if (attributePrincipal != null) {
            userId = attributePrincipal.getName();
        }

        String destDirName = this.dirPath + "/" + "policyOverViewExcel";
        String filePath = destDirName + "/" + preFilename + ".xlsx";
        String doingFileTemp = destDirName + "/overViewdoing.temp";

        try {
            if (!(new File(destDirName)).exists()) {
                FileUtils.createDir(destDirName);
            }

            String fileIsExistsName = FileUtils.isDirExistFile(destDirName);
            boolean doingFileTempIsExists = FileUtils.fileIsExists(doingFileTemp);
            boolean fileIsExists = FileUtils.fileIsExists(destDirName + "/" + fileIsExistsName);
            File doingFile;
            if (null == isReload) {
                if (fileIsExists && !doingFileTempIsExists) {
                    resultRO.setMessage("文件生成成功");
                    jsonObject.put("filePath", fileIsExistsName);
                    jsonObject.put("status", 1);
                    resultRO.setData(jsonObject);
                    return resultRO;
                }

                if (doingFileTempIsExists) {
                    resultRO.setMessage("文件生成中");
                    jsonObject.put("filePath", preFilename + ".xlsx");
                    jsonObject.put("status", 2);
                    resultRO.setData(jsonObject);
                    return resultRO;
                }

                if (!doingFileTempIsExists && !fileIsExists) {
                    doingFile = new File(doingFileTemp);
                    doingFile.createNewFile();
                    resultRO.setMessage("生成成功");
                    jsonObject.put("filePath", preFilename + ".xlsx");
                    jsonObject.put("status", 2);
                    (new PolicyViewExcelDownLoadController.PolicyOverViewThread(filePath, doingFile, userId)).start();
                    resultRO.setData(jsonObject);
                    return resultRO;
                }
            }

            if ("true".equals(isReload)) {
                FileUtils.deleteFileByPath(destDirName + "/" + fileIsExistsName);
                doingFile = new File(doingFileTemp);
                doingFile.createNewFile();
                (new PolicyViewExcelDownLoadController.PolicyOverViewThread(filePath, doingFile, userId)).start();
                resultRO.setMessage("正在生成文件");
                jsonObject.put("filePath", preFilename + ".xlsx");
                jsonObject.put("status", 2);
                resultRO.setData(jsonObject);
                return resultRO;
            } else {
                this.downLoadPolicyOverView(response, destDirName + "/" + fileIsExistsName, destDirName);
                return null;
            }
        } catch (Exception var16) {
            File doingFile = new File(doingFileTemp);
            doingFile.delete();
            log.error("下载策略概览Excel表格失败:", var16);
            resultRO.setMessage("数据导出失败");
            resultRO.setSuccess(false);
            jsonObject.put("filePath", filePath);
            jsonObject.put("status", 3);
            resultRO.setData(jsonObject);
            return resultRO;
        }
    }

    private void downLoadPolicyOverView(HttpServletResponse response, String fileExcitPath, String destDirName) {
        File src = new File(fileExcitPath);
        FileUtils.downloadOverView(src, response);
    }

    @ApiOperation(
            value = "策略概览",
            nickname = "鲁薇"
    )
    @ApiResponses({@ApiResponse(
            code = 200,
            message = ""
    )})
    @PostMapping(
            value = {"/report/policyView/viewData"},
            produces = {"application/json; charset=utf-8"}
    )
    @ResponseBody
    public ResultRO<List<PolicyViewVO>> viewData(@ApiParam(name = "type",defaultValue = "0",value = "设备类型:0防火墙、1路由器/交换机、2负载、5网闸，不传则全部",required = false) @RequestParam(required = false) Integer type, @ApiParam(name = "vendor",defaultValue = "华为",value = "厂商,不传则是全部",required = false) @RequestParam(required = false) String vendor, @ApiParam(name = "deviceUuid",defaultValue = "2d4bfa08195544ae83ce738d7948675e",value = "设备uuid，不传则全部",required = false)
                    @RequestParam(required = false) String deviceUuid,
                     @ApiParam(name = "currentPage",defaultValue = "1",value = "当前页页码",required = true)
                     @RequestParam(required = true) Integer currentPage,
                     @ApiParam(name = "pageSize",defaultValue = "20",value = "每页显示的记录数",required = true)
                     @RequestParam(required = true) Integer pageSize,
                     Authentication attributePrincipal,
                     @RequestParam(required = false) String branchLevel) throws Exception {
        String userId = null;
        if (attributePrincipal != null) {
            userId = attributePrincipal.getName();
        }

        Integer total = this.nodeService.findTotalByConditionMetoo(type, vendor, deviceUuid, (String)null, (Integer)null, (Integer)null, userId, branchLevel);
        int start = (currentPage - 1) * 20;
        List<Node> nodeList = this.nodeService.findByConditionMetoo(type, vendor, deviceUuid, (String)null, (Integer)null, (Integer)null, userId, branchLevel, start, pageSize);
        ResultRO<List<PolicyViewVO>> resultRO = new ResultRO(true);
        resultRO.setTotal(total);
        if (nodeList != null && nodeList.size() != 0) {
            List<PolicyViewVO> list = this.policyViewGen.getData(nodeList);
            resultRO.setData(list);
            return resultRO;
        } else {
            return resultRO;
        }
    }

    @ApiOperation(
            value = "策略概览头部获取多个对象的总数合集",
            nickname = "曾涛"
    )
    @PostMapping({"/policyStrategy/top/objectsViewTotal"})
    @ResponseBody
    public ResultRO<PolicyTotalVo> viewTotalListByDeviceUuid(@ApiParam(name = "deviceUuid",defaultValue = "2d4bfa08195544ae83ce738d7948675e",value = "设备uuid，不传则全部") @RequestParam String deviceUuid, @ApiParam(name = "ip",defaultValue = "192.168.1.1",value = "设备ip地址") @RequestParam String ip) throws Exception {
        ResultRO<PolicyTotalVo> resultRO = new ResultRO(true);
        PolicyTotalVo policyTotalVoByDeviceId = this.nodeService.getPolicyTotalVoByDeviceId(deviceUuid, ip);
        resultRO.setData(policyTotalVoByDeviceId);
        return resultRO;
    }

    private class PolicyOverViewThread extends Thread {
        private String filePath;
        private File doingFile;
        private String userId;

        public PolicyOverViewThread(String filePath, File doingFile, String userId) {
            this.filePath = filePath;
            this.doingFile = doingFile;
            this.userId = userId;
        }

        public PolicyOverViewThread() {
        }

        public void run() {
            try {
                List list = null;

                try {
                    List<Node> nodeList = PolicyViewExcelDownLoadController.this.nodeService.getAllDeviceUuid(this.userId);
                    list = PolicyViewExcelDownLoadController.this.policyViewGen.getData(nodeList);
                } catch (InterruptedException var3) {
                    PolicyViewExcelDownLoadController.log.error("获取策略概览数据异常", var3);
                } catch (ExecutionException var4) {
                    PolicyViewExcelDownLoadController.log.error("获取策略概览数据异常", var4);
                }

                if (list == null) {
                    list = Collections.EMPTY_LIST;
                }

                PolicyViewExcelDownLoadController.this.policyViewGen.exportDataToExcel(list, this.filePath);
                this.doingFile.delete();
            } catch (Exception var5) {
                this.doingFile.delete();
                PolicyViewExcelDownLoadController.log.error("策略概览报表异常", var5);
            }

        }
    }
}
