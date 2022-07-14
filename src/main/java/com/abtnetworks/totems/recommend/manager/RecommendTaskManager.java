//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.totems.recommend.manager;

import com.abtnetworks.totems.common.dto.commandline.DNatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.NatPolicyDTO;
import com.abtnetworks.totems.common.dto.commandline.SNatPolicyDTO;
import com.abtnetworks.totems.common.entity.NodeEntity;
import com.abtnetworks.totems.push.vo.PushTaskVO;
import com.abtnetworks.totems.recommend.dto.push.TaskStatusBranchLevelsDTO;
import com.abtnetworks.totems.recommend.dto.task.SearchRecommendTaskDTO;
import com.abtnetworks.totems.recommend.entity.CheckResultEntity;
import com.abtnetworks.totems.recommend.entity.CommandTaskEditableEntity;
import com.abtnetworks.totems.recommend.entity.DeviceDimension;
import com.abtnetworks.totems.recommend.entity.PathDeviceDetailEntity;
import com.abtnetworks.totems.recommend.entity.PathInfoEntity;
import com.abtnetworks.totems.recommend.entity.PolicyRiskEntity;
import com.abtnetworks.totems.recommend.entity.RecommendPolicyEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskCheckEntity;
import com.abtnetworks.totems.recommend.entity.RecommendTaskEntity;
import com.abtnetworks.totems.recommend.entity.RiskRuleDetailEntity;
import com.abtnetworks.totems.recommend.entity.RiskRuleInfoEntity;
import com.abtnetworks.totems.recommend.vo.BatchTaskVO;
import com.abtnetworks.totems.recommend.vo.PathDetailVO;
import com.abtnetworks.totems.recommend.vo.PolicyTaskDetailVO;
import com.abtnetworks.totems.recommend.vo.RecommendPolicyVO;
import com.abtnetworks.totems.recommend.vo.TaskStatusVO;
import com.github.pagehelper.PageInfo;
import java.util.List;
import org.springframework.security.core.Authentication;

public interface RecommendTaskManager {
    int getGatherStateByDeviceUuid(String uuid);

    NodeEntity getTheNodeByUuid(String deviceUuid);

    String getDeviceModelNumber(String uuid);

    String getDeviceName(String uuid);

    Integer getDeviceGatherPort(String uuid);

    List<RiskRuleInfoEntity> getRiskInfoBySecondSortId(int secondSortId);

    RiskRuleDetailEntity getRiskDetailEntityByRuleId(String ruleId);

    List<CommandTaskEditableEntity> listPolicyRecommendPolicyByTaskIds(String ids);

    String getRecommendZip(String ids, String pathPrefix);

    PageInfo<PathInfoEntity> getAnalyzePathInfoVOList(int taskId, int page, int psize);

    PageInfo<PathInfoEntity> getVerifyPathInfoVOList(int taskId, int page, int psize);

    PageInfo<RecommendTaskEntity> getTaskList(SearchRecommendTaskDTO searchRecommendTaskDTO);

    PageInfo<PolicyTaskDetailVO> getNatPolicyTaskList(String theme, String type, int page, int psize, String taskIds, Integer id, String userName, String deviceUuid, String status, Authentication authentication);

    PageInfo<PolicyTaskDetailVO> getNatPolicyTaskList(String theme, String type, int page, int psize, String taskIds, Integer id, String userName, String deviceUuid, String status, Authentication authentication, String branchLevel);

    List<PolicyTaskDetailVO> getNatTaskList(String theme, String type, String taskIds, Integer id, String userName, String deviceUuid, String startTime, String endTime, Authentication authentication);

    PageInfo<PolicyTaskDetailVO> getSecurityPolicyTaskList(String theme, int page, int psize, String userName, String deviceUuid, String status, Authentication authentication);

    PageInfo<PolicyTaskDetailVO> getSecurityPolicyTaskList(String theme, int page, int psize, String userName, String deviceUuid, String status, Authentication authentication, String branchLevel);

    List<PolicyTaskDetailVO> getSecurityTaskList(String theme, String userName, String deviceUuid, String startTime, String endTime, Authentication authentication);

    PageInfo<PolicyTaskDetailVO> getCustomizeCmdTaskList(String theme, int page, int psize, String userName, String deviceUuid, Integer status, Authentication authentication);

    TaskStatusVO getTaskStatusByTaskId(int taskId);

    PathDetailVO getPathDetail(int pathInfoId, boolean isVerifyData);

    PathDeviceDetailEntity getDevieceDetail(int pathInfoId, String deviceUuid, boolean isVerifyData, String index);

    List<PolicyRiskEntity> getRiskByPathInfoId(int pathInfoId);

    List<RecommendPolicyVO> getPolicyByPathInfoId(int pathInfoId);

    List<RecommendPolicyVO> getMergedPolicyByTaskId(int taskId);

    List<CheckResultEntity> getCheckResultByPolicyId(int policyId);

    int insertSrcNatPolicy(SNatPolicyDTO sNatPolicyDTO, Authentication authentication);

    int insertDstNatPolicy(DNatPolicyDTO dNatPolicyDTO, Authentication authentication);

    int insertBothNatPolicy(NatPolicyDTO policyDTO, Authentication auth);

    int insertRecommendTaskList(List<RecommendTaskEntity> list);

    RecommendTaskEntity getRecommendTaskByTaskId(int taskId);

    CommandTaskEditableEntity getRecommendTaskById(int Id);

    int addPathInfo(PathInfoEntity entity);

    int addRecommendPolicyList(List<RecommendPolicyEntity> entityList);

    int addCheckResult(CheckResultEntity entity);

    int addCommandTaskEditableEntity(CommandTaskEditableEntity entity);

    List<PathInfoEntity> getPathInfoByTaskId(int taskId);

    PathInfoEntity getPathInfoByPathInfoId(int id);

    List<RecommendPolicyEntity> getPolicyListByPathInfoId(int pathInfoId);

    int savePathDeviceDetail(PathDeviceDetailEntity entity);

    int insertpathDeviceDetailList(List<PathDeviceDetailEntity> list);

    int saveVerifyDeitailPath(int pathInfoId, String detailPath);

    void saveAnalyzeDetailPath(int pathInfoId, String detailPath);

    int updatePathStatus(PathInfoEntity entity);

    void updateTaskStatus(int taskId, int status);

    void updatePathPathStatus(int pathInfoId, int status);

    void updatePathAnalyzeStatus(int pathInfoId, int status);

    void updatePathAdviceStatus(int pathInfoId, int status);

    int updatePathCheckStatus(int pathInfoId, int status);

    void updatePathRiskStatus(int pathInfoId, int status);

    void updatePathCmdStatusByTaskId(int taskId, int status);

    int updatePathPushStatus(int pathInfoId, int status);

    int updatePathGatherStatus(int pathInfoId, int status);

    int updatePathVerifyStatus(int pathInfoId, int status);

    void getAdvancedSettings(RecommendPolicyEntity entity);

    PageInfo<PushTaskVO> getPushTaskList(String taskId, String theme, String taskType, String status, String pushStatus, String revertStatus, int page, int psize, String userName, String branchLevel);

    int updateCommandTaskStatus(int taskId, int status);

    int updateCommandTaskStatus(List<CommandTaskEditableEntity> list, int status);

    int updateCommandTaskPushStatus(int taskId, int status);

    int updateCommandTaskRevertStatus(int taskId, int status);

    int updateCommandTaskPushOrRevertStatus(List<CommandTaskEditableEntity> list, int status, boolean isRever);

    int updateCommandTaskStatusById(int id, int status);

    int updateCommandTaskRevertStatusById(int id, int status);

    int updateStopTaskPushStatus(int taskId, int status);

    void addTaskRisk(int pathInfoId, String riskId);

    void removePolicyTasks(List<Integer> list);

    void deleteTasks(List<Integer> list, int type);

    RecommendPolicyEntity getPolicyByPolicyId(Integer policyId);

    int setPathEnable(Integer pathInfoId, String enable);

    boolean isCheckRule();

    boolean isCheckRisk();

    List<RecommendPolicyEntity> getMergedPolicyList(int taskId);

    int addMergedPolicyList(List<RecommendPolicyEntity> list);

    int addMergedPolicy(RecommendPolicyEntity entity);

    boolean isUseCurrentObject();

    boolean isUseCurrentAddressObject();

    void removeCommandsByTask(int taskId);

    void updateTaskById(RecommendTaskEntity entity);

    List<DeviceDimension> searchDeviceDimensionByTaskId(Integer taskId);

    List<RecommendPolicyEntity> selectByDeviceDimension(DeviceDimension deviceDimension, Integer taskId);

    int addPathInfoList(List<PathInfoEntity> list);

    int getAclDirection(String deviceUuid);

    NodeEntity getDeviceByManageIp(String deviceIp);

    PageInfo<BatchTaskVO> searchBatchTaskList(String theme, String userName, int page, int psize);

    RecommendTaskCheckEntity selectBatchTaskById(Integer id);

    void addBatchTask(RecommendTaskCheckEntity entity);

    void updateBatchTask(RecommendTaskCheckEntity entity);

    RiskRuleInfoEntity getRiskInfoByRuleId(String ruleId);

    List<NodeEntity> getNodeList();

    TaskStatusBranchLevelsDTO getPushTaskStatusList(String userName);

    List<RecommendTaskEntity> getTaskListByTime(String startTime, String endTime, Authentication authentication);

    List<RecommendTaskEntity> selectExecuteRecommendTask();

    int getPushStatusInTaskList(List<CommandTaskEditableEntity> taskEntityList);

    int getPolicyStatusByPushStatus(int pushStatus);

    void setPathInfoStatus(Integer id, Integer gatherStatus, Integer verifyStatus, Integer pathStatus, Integer pushStatus);

    void updateWeTaskId(RecommendTaskEntity entity);
}
