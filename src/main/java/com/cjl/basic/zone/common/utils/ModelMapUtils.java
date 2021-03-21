package com.cjl.basic.zone.common.utils;

import com.cjl.basic.zone.framework.config.ZoneConfig;
import com.cjl.basic.zone.framework.web.domain.BaseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.ModelMap;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;

/**
 * @author hd_zhu
 */
public class ModelMapUtils {

    private static Logger logger = LoggerFactory.getLogger(ModelMapUtils.class);

    public static void addAndReplaceEmoji(ModelMap modelMap, String key, Object value) {
        if (value == null) {
            modelMap.put(key, null);
        } else if (value instanceof String) {
            Object emojiValue = EmojiUtils.emojiRecovery(value.toString());
            modelMap.put(key, emojiValue);
        } else if (value instanceof Serializable) {
            Object emojiValue = emojiSerializable(value);
            modelMap.put(key, emojiValue);
        } else if (value instanceof Collection || value.getClass().isArray()) {
            Collection<?> collection = (Collection<?>) value;
            Object emojiValue = collection.parallelStream().map(ModelMapUtils::emojiSerializable);
            modelMap.put(key, emojiValue);
        } else {
            modelMap.put(key, value);
        }
    }

    /**
     * 解析对象中的表情
     *
     * @param value 属性值
     * @return
     */
    private static Object emojiSerializable(Object value) {
        Class<?> clazz = value.getClass();
        if (!(value instanceof BaseEntity)) {
            return value;
        }
        Arrays.stream(clazz.getMethods())
                .parallel()
                .filter(m -> m.getName().contains("get"))
                .forEach(m -> {
                    String mGetName = m.getName();
                    Class<?> mSetType = m.getReturnType();
                    String mSetName = mGetName.replace("get", "set");
                    try {
                        Object oVal = m.invoke(value);
                        if (!(oVal instanceof String)) {
                            return;
                        }
                        String val = (String) oVal;
                        val = EmojiUtils.emojiRecovery(val);
                        Method mSet = clazz.getMethod(mSetName, mSetType);
                        if (val.contains(ZoneConfig.getReloadImgUrl())) {
                            return;
                        }
                        mSet.invoke(value, val);
                    } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                        logger.error("emoji反序列化失败", e);
                    }
                });
        return value;
    }
}
