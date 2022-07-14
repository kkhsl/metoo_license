//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.disposal.controller;

import com.abtnetworks.data.totems.log.common.enums.BusinessLogType;
import com.abtnetworks.data.totems.log.common.enums.LogLevel;
import com.abtnetworks.totems.common.utils.AliStringUtils;
import com.abtnetworks.totems.disposal.BaseController;
import com.abtnetworks.totems.disposal.ReturnT;
import com.abtnetworks.totems.disposal.dto.DisposalScenesDTO;
import com.abtnetworks.totems.disposal.entity.DisposalScenesEntity;
import com.abtnetworks.totems.disposal.entity.DisposalScenesNodeEntity;
import com.abtnetworks.totems.disposal.service.DisposalScenesService;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("应急处置>>场景管理")
@RestController
@RequestMapping({"${startPath}/disposal/scenes"})
public class DisposalScenesController extends BaseController {
    @Autowired
    private DisposalScenesService disposalScenesService;

    public DisposalScenesController() {
    }

    @ApiOperation("场景管理保存or修改，传参JSON格式")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "deviceJson",
            value = "设备信息String类型的JSON",
            required = false,
            dataType = "String"
    )})
    @PostMapping({"/edit"})
    public ReturnT<String> saveOrUpdate(Authentication authentication, @RequestBody DisposalScenesEntity disposalScenes) {
        try {
            if (disposalScenes != null && !AliStringUtils.isEmpty(disposalScenes.getDeviceJson())) {
                List<DisposalScenesNodeEntity> scenesNodeEntityList = JSONObject.parseArray(disposalScenes.getDeviceJson(), DisposalScenesNodeEntity.class);
                Iterator var4 = scenesNodeEntityList.iterator();

                DisposalScenesNodeEntity scenesNodeEntity;
                do {
                    if (!var4.hasNext()) {
                        if(disposalScenes.getCreateUser() == null || disposalScenes.getCreateUser().equals("")){
                            disposalScenes.setCreateUser(authentication.getName());
                        }
                        return this.disposalScenesService.saveOrUpdate(disposalScenes, scenesNodeEntityList);
                    }

                    scenesNodeEntity = (DisposalScenesNodeEntity)var4.next();
                } while(scenesNodeEntity != null && AliStringUtils.areNotEmpty(new String[]{scenesNodeEntity.getDeviceUuid(), scenesNodeEntity.getDeviceName()}));

                return new ReturnT(-1, "deviceJson 序列化对象时，部分数据为空，请检查！");
            } else {
                return new ReturnT(-1, "必要参数缺失:deviceJson or name or remarks！");
            }
        } catch (Exception var6) {
            this.logger.error("", var6);
            return ReturnT.FAIL;
        }
    }

    @ApiOperation("场景管理删除")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "id",
            value = "主键id",
            required = true,
            dataType = "Integer"
    )})
    @PostMapping({"/delete"})
    public ReturnT<String> delete(Authentication authentication, int id) {
        DisposalScenesEntity scenesEntity = this.disposalScenesService.getById(id);
        if (scenesEntity == null) {
            return ReturnT.FAIL;
        } else {
            ReturnT<String> returnT = this.disposalScenesService.delete(id);
            if (authentication != null) {
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.PUSH_DISPOSAL.getId(), authentication.getName() + " 删除场景" + scenesEntity.getName() + "成功！");
            } else {
                this.logger.error("场景管理删除场景，获取用户凭证失败！");
                this.logClientSimple.addBusinessLog(LogLevel.INFO.getId(), BusinessLogType.PUSH_DISPOSAL.getId(), "场景管理删除场景" + scenesEntity.getName() + "，获取用户凭证失败！");
            }

            return returnT;
        }
    }

    @ApiOperation("场景管理编辑，查询场景信息")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "uuid",
            value = "场景uuid",
            required = true,
            dataType = "String"
    )})
    @PostMapping({"/getByUUId"})
    public ReturnT getByUUId(String uuid) {
        try {
            this.disposalScenesService.clearRubbish();
            DisposalScenesEntity scenesEntity = this.disposalScenesService.getByUUId(uuid);
            List<DisposalScenesDTO> scenesDTOList = this.disposalScenesService.findByScenesUuid(uuid);
            Map<String, Object> map = new HashMap();
            map.put("scenes", scenesEntity);
            map.put("scenesNodeList", scenesDTOList);
            return new ReturnT(map);
        } catch (Exception var5) {
            this.logger.error("", var5);
            return ReturnT.FAIL;
        }
    }

    @ApiOperation("场景管理List列表，查询传参JSON格式")
    @ApiImplicitParams({@ApiImplicitParam(
            paramType = "query",
            name = "page",
            value = "页数",
            required = false,
            dataType = "Integer"
    ), @ApiImplicitParam(
            paramType = "query",
            name = "limit",
            value = "每页条数",
            required = false,
            dataType = "Integer"
    )})
    @PostMapping({"/pageList"})
    public ReturnT pageList(Authentication authentication, @RequestBody DisposalScenesEntity disposalScenes) {
        try {
            PageInfo<DisposalScenesEntity> pageInfoList = this.disposalScenesService.findList(disposalScenes, disposalScenes.getPage(), disposalScenes.getLimit());
            ReturnT returnT = new ReturnT(pageInfoList);
            return returnT;
        } catch (Exception var5) {
            this.logger.error("", var5);
            return ReturnT.FAIL;
        }
    }
}
