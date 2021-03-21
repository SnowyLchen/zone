package com.cjl.basic.zone.project.dict.service;

import com.cjl.basic.zone.common.constant.UserConstants;
import com.cjl.basic.zone.common.utils.InsertOrUpdateUtils;
import com.cjl.basic.zone.project.dict.domain.DictData;
import com.cjl.basic.zone.project.dict.domain.DictType;
import com.cjl.basic.zone.project.dict.mapper.DictDataMapper;
import com.cjl.basic.zone.project.dict.mapper.DictTypeMapper;
import com.cjl.basic.zone.utils.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 字典 业务层处理
 *
 * @author wangsen
 */
@Service
public class DictTypeServiceImpl implements IDictTypeService {
    @Resource
    private DictTypeMapper dictTypeMapper;

    @Resource
    private DictDataMapper dictDataMapper;

//    @Autowired
//    @Qualifier("dictTypeCache")
//    private Cache<DictType> cache;
//
//    @Autowired
//    @Qualifier("dictDataCache")
//    private Cache<DictData> cache1;

    /**
     * 根据条件分页查询字典类型
     *
     * @param dictType 字典类型信息
     * @return 字典类型集合信息
     */
    @Override
    public List<DictType> selectDictTypeList(DictType dictType) {
        dictType.setDictName(StringUtils.replacelike(dictType.getDictName()));
        dictType.setDictType(StringUtils.replacelike(dictType.getDictType()));
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    /**
     * 根据所有字典类型
     *
     * @return 字典类型集合信息
     */
    @Override
    public List<DictType> selectDictTypeAll() {
        return dictTypeMapper.selectDictTypeAll();
    }

    /**
     * 根据字典类型ID查询信息
     *
     * @param dictId 字典类型ID
     * @return 字典类型
     */
    @Override
    public DictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    /**
     * 通过字典ID删除字典信息
     *
     * @param dictId 字典ID
     * @return 结果
     */
//    @Override
//    public int deleteDictTypeById(Long dictId)
//    {
//        DictType dictType = selectDictTypeById(dictId);
//        dictType.setDelFlag("1");
//        dictType.setUpdateBy(ShiroAuthenticateUtils.getLoginName());
//        return dictTypeMapper.updateDictType(dictType);
//    }

    /**
     * 删除字典类型
     *
     * @param ids 需要删除的数据id
     * @return 结果
     */
    @Override
    public int deleteDictTypeByIds(String ids) throws Exception {
        Long dictIds = Long.parseLong(ids);
        DictType dictType = selectDictTypeById(dictIds);
        if (dictDataMapper.countDictDataByType(dictType.getDictType()) > 0) {
            throw new Exception(String.format("%1$s已分配,不能删除", dictType.getDictName()));
        }
        dictType.setDelFlag("1");
        InsertOrUpdateUtils.addUpdateAttr(dictType);
        dictTypeMapper.updateDictType(dictType);
//        cache.delete(dictType.getDictId().intValue());
        return 1;
    }

    /**
     * 批量启停字典类型
     *
     * @param dictId 需要启停的数据
     * @return 结果
     */
    @Override
    public int statusdictById(Long dictId) throws Exception {
        DictType dictType = selectDictTypeById(dictId);
        if (dictType.getStatus().equals("1")) {
            dictType.setStatus("0");
            DictData dictDatatemp = new DictData();
            dictDatatemp.setDictType(dictType.getDictType());
            List<DictData> dictDatalist = dictDataMapper.selectDictDataList(dictDatatemp);
            for (DictData dictData : dictDatalist) {
                dictData.setStatus("0");
                InsertOrUpdateUtils.addUpdateAttr(dictData);
                dictDataMapper.updateDictData(dictData);
//                cache1.update(dictData.getDictCode().intValue());
            }
        } else {
            dictType.setStatus("1");
            DictData dictDatatemp = new DictData();
            dictDatatemp.setDictType(dictType.getDictType());
            List<DictData> dictDatalist = dictDataMapper.selectDictDataList(dictDatatemp);
            for (DictData dictData : dictDatalist) {
                dictData.setStatus("1");
                InsertOrUpdateUtils.addUpdateAttr(dictData);
                dictDataMapper.updateDictData(dictData);
//                cache1.update(dictData.getDictCode().intValue());
            }
        }
        InsertOrUpdateUtils.addUpdateAttr(dictType);
        dictTypeMapper.updateDictType(dictType);
//        cache.update(dictType.getDictId().intValue());

        return 1;

    }

    /**
     * 新增保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    public int insertDictType(DictType dictType) {
        InsertOrUpdateUtils.addInsertAttr(dictType);
        Integer count = dictTypeMapper.insertDictType(dictType);
//        cache.update(dictType.getDictId().intValue());
        return count;
    }

    /**
     * 修改保存字典类型信息
     *
     * @param dictType 字典类型信息
     * @return 结果
     */
    @Override
    public int updateDictType(DictType dictType) {
        InsertOrUpdateUtils.addUpdateAttr(dictType);
        DictType oldDict = dictTypeMapper.selectDictTypeById(dictType.getDictId());
        dictDataMapper.updateDictDataType(oldDict.getDictType(), dictType.getDictType());
        return dictTypeMapper.updateDictType(dictType);
    }

    /**
     * 校验字典类型称是否唯一
     *
     * @param dict 字典类型
     * @return 结果
     */
    @Override
    public String checkDictTypeUnique(DictType dict) {
        Long dictId = StringUtils.isNull(dict.getDictId()) ? -1L : dict.getDictId();
        DictType dictType = dictTypeMapper.checkDictTypeUnique(dict.getDictType());
        if (StringUtils.isNotNull(dictType) && dictType.getDictId().longValue() != dictId.longValue()) {
            return UserConstants.DICT_TYPE_NOT_UNIQUE;
        }
        return UserConstants.DICT_TYPE_UNIQUE;
    }
}
