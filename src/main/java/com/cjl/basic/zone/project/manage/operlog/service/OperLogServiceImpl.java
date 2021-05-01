package com.cjl.basic.zone.project.manage.operlog.service;

import com.cjl.basic.zone.common.support.Convert;
import com.cjl.basic.zone.project.manage.operlog.domain.OperLog;
import com.cjl.basic.zone.project.manage.operlog.mapper.OperLogMapper;
import com.cjl.basic.zone.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * 操作日志 服务层处理
 *
 * @author chen
 */
@Service
public class OperLogServiceImpl implements IOperLogService {
    @Resource
    private OperLogMapper operLogMapper;

    /**
     * 新增操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(OperLog operLog) {
        operLog.setOperTime(new Date());
        if (operLog.getMfrsId() == null) {
            operLog.setMfrsId(0);
        }
        operLogMapper.insertOperlog(operLog);
    }

    /**
     * 查询系统操作日志集合
     *
     * @param operLog 操作日志对象
     * @return 操作日志集合
     */
    @Override
    public List<OperLog> selectOperLogList(OperLog operLog) {
        operLog.setTitle(StringUtils.replacelike(operLog.getTitle()));
        operLog.setOperName(StringUtils.replacelike(operLog.getOperName()));
        return operLogMapper.selectOperLogList(operLog);
    }

    /**
     * 批量删除系统操作日志
     *
     * @param ids 需要删除的数据
     * @return
     */
    @Override
    public int deleteOperLogByIds(String ids) {
        return operLogMapper.deleteOperLogByIds(Convert.toStrArray(ids));
    }

    /**
     * 查询操作日志详细
     *
     * @param operId 操作ID
     * @return 操作日志对象
     */
    @Override
    public OperLog selectOperLogById(Long operId) {
        return operLogMapper.selectOperLogById(operId);
    }

    /**
     * 清空操作日志
     */
    @Override
    public void cleanOperLog() {
        operLogMapper.cleanOperLog();
    }
}
