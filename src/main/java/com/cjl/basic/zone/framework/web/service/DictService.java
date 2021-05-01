package com.cjl.basic.zone.framework.web.service;

import com.cjl.basic.zone.common.utils.SpringRedisUtil;
import com.cjl.basic.zone.project.manage.dict.domain.DictData;
import com.cjl.basic.zone.project.manage.dict.service.IDictDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * UUSTOP首创 html调用 thymeleaf 实现字典读取
 *
 * @author chen
 */
@Service("dict")
public class DictService {
    @Autowired
    private IDictDataService dictDataService;
    @Autowired
    private SpringRedisUtil springRedisUtil;
    public static final String KEY = DictData.class.getSimpleName();

    /**
     * 根据字典类型查询字典数据信息
     *
     * @param dictType 字典类型
     * @return 参数键值
     */
    public List<DictData> getType(String dictType) {
//        Map<Object, Object> map = springRedisUtil.hashGetAll(getKey(dictType));
//        if (map.size() == 0) {
//            return dictDataService.selectDictDataByType(dictType);
//        } else {
//            List<DictData> dictDataList = new ArrayList<>();
//            for (Object key : map.keySet()) {
//                dictDataList.add((DictData) map.get(key));
//            }
//            return dictDataList;
//        }
        return dictDataService.selectDictDataByType(dictType);
    }

    public String gettep(String dictType) {
        System.out.println(dictType);
        return "1";
    }

    /**
     * 根据字典类型和字典键值查询字典数据信息
     *
     * @param dictType  字典类型
     * @param dictValue 字典键值
     * @return 字典标签
     */
    public String getLabel(String dictType, String dictValue) {

        return dictDataService.selectDictLabel(dictType, dictValue);
    }

    public String getKey(String key) {
        String type = KEY + ":" + key;
        return type;
    }
}
