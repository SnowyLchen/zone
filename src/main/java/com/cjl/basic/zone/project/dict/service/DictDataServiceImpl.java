package com.cjl.basic.zone.project.dict.service;

import com.cjl.basic.zone.common.support.Convert;
import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.project.dict.domain.DictData;
import com.cjl.basic.zone.project.dict.mapper.DictDataMapper;
import com.cjl.basic.zone.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author chen
 */
@Service
public class DictDataServiceImpl implements IDictDataService {
    @Resource
    private DictDataMapper dictDataMapper;

    /**
     * 根据条件分页查询字典数据
     *
     * @param dictData 字典数据信息
     * @return 字典数据集合信息
     */
    @Override
    public List<DictData> selectDictDataList(DictData dictData) {
        dictData.setDictLabel(StringUtils.replacelike(dictData.getDictLabel()));
        return dictDataMapper.selectDictDataList(dictData);
    }

    /**
     * 根据字典类型查询字典数据
     *
     * @param dictType 字典类型
     * @return 字典数据集合信息
     */
    @Override
    public List<DictData> selectDictDataByType(String dictType) {
        return dictDataMapper.selectDictDataByType(dictType);
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    @Override
    public String selectDictLabel(String dictType, String dictValue) {
        return dictDataMapper.selectDictLabel(dictType, dictValue);
    }

    /**
     * 根据字典数据ID查询信息
     *
     * @param dictCode 字典数据ID
     * @return 字典数据
     */
    @Override
    public DictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }


    /**
     * 批量删除字典数据
     *
     * @param ids 需要删除的数据
     * @return 结果
     */
    @Override
    public int deleteDictDataByIds(String ids) {
        Long[] dictDataIds = Convert.toLongArray(ids);
        for (Long dictDataId : dictDataIds) {
            DictData dictData = selectDictDataById(dictDataId);
            dictData.setDelFlag("1");
            InsertOrUpdateUtils.addUpdateAttr(dictData);
            dictDataMapper.updateDictData(dictData);
        }
        return 1;

    }

    /**
     * 新增保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int insertDictData(DictData dictData) {
//        dictData.setCreateBy(ShiroAuthenticateUtils.getLoginName());
        InsertOrUpdateUtils.addInsertAttr(dictData);
        return dictDataMapper.insertDictData(dictData);
    }

    /**
     * 修改保存字典数据信息
     *
     * @param dictData 字典数据信息
     * @return 结果
     */
    @Override
    public int updateDictData(DictData dictData) {
        InsertOrUpdateUtils.addUpdateAttr(dictData);
        return dictDataMapper.updateDictData(dictData);
    }

    @Override
    public int statusDictDataById(Long dictCode) {
        DictData dictData = selectDictDataById(dictCode);
        if (dictData.getStatus().equals("1")) {
            dictData.setStatus("0");
        } else {
            dictData.setStatus("1");
        }
        InsertOrUpdateUtils.addUpdateAttr(dictData);
        return dictDataMapper.updateDictData(dictData);
    }
}
