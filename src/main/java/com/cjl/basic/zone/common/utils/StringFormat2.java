package com.cjl.basic.zone.common.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Stream;

/**
 * @author chen
 */
public class StringFormat2 {
    private static final Logger logger = LoggerFactory.getLogger(StringFormat.class);

    public static String format(String string, Map<String, Object> map) {
        if (Objects.isNull(map) || map.isEmpty()) {
            return string;
        }
        Iterator<String> stringIterator = map.keySet().iterator();
        while (stringIterator.hasNext()) {
            String key = stringIterator.next();
            string = string.replace("{{" + key + "}}", map.get(key).toString());
        }
        return string;
    }

    /**
     * 取ids从{{110}}->110
     *
     * @param string
     * @return
     */
    public static Set<Integer> getIdSet(String string) {
        Set<Integer> idSet = new LinkedHashSet<>();
        String[] items = string.split("\\{\\{");
        for (String item : items) {
            String number = item.split("}}")[0];
            if (Strings.isBlank(number)) {
                continue;
            }
            try {
                idSet.add(Integer.valueOf(number));
            } catch (NumberFormatException e) {
                idSet.add(0);
            }
        }
        return idSet;
    }

    /**
     * 获取交集
     *
     * @param m
     * @param n
     * @return
     */
    public static Integer[] getJ(Integer[] m, Integer[] n) {
        List<Integer> rs = new ArrayList<Integer>();
        // 将较长的数组转换为set
        Set<Integer> set = new HashSet<Integer>(Arrays.asList(m.length > n.length ? m : n));
        // 遍历较短的数组，实现最少循环
        for (Integer i : m.length > n.length ? n : m) {
            if (set.contains(i)) {
                rs.add(i);
            }
        }
        Integer[] arr = {};
        return rs.toArray(arr);
    }

    /**
     * 获取差集
     *
     * @param m
     * @param n
     * @return
     */
    public static Integer[] getC(Integer[] m, Integer[] n) {
        // 将较长的数组转换为set
        Set<Integer> set = new HashSet<Integer>(Arrays.asList(m.length > n.length ? m : n));

        // 遍历较短的数组，实现最少循环
        for (Integer i : m.length > n.length ? n : m) {
            // 如果集合里有相同的就删掉，如果没有就将值添加到集合
            if (set.contains(i)) {
                set.remove(i);
            } else {
                set.add(i);
            }
        }

        Integer[] arr = {};
        return set.toArray(arr);
    }

    /**
     * string转Integer[]
     *
     * @param value
     * @return
     */
    public static Integer[] stringConvertInt(String value) {
        Integer[] intArr;
        if (StringUtils.isEmpty(value)) {
            intArr = new Integer[0];
        } else {
            String[] valueArr = value.split(",");
            intArr = new Integer[valueArr.length];
            for (int i = 0; i < valueArr.length; i++) {
                intArr[i] = Integer.parseInt(valueArr[i]);
            }
        }
        return intArr;
    }

    /**
     * 去除重复的，保留不相同的
     *
     * @param arr1
     * @param arr2
     * @return
     */
    public static Integer[] arrContrast(Integer[] arr1, Integer[] arr2) {
        List<Integer> list = new LinkedList<Integer>(Arrays.asList(arr1));
        // 如果第二个数组存在和第一个数组相同的值，就删除
        for (Integer str : arr2) {
            if (list.contains(str)) {
                list.remove(str);
            } else {
                list.add(str);
            }
        }
        // 创建空数组
        Integer[] result = {};
        return list.toArray(result);
    }

    /**
     * String[]转Integer[]（去重）
     *
     * @param arr1
     * @return
     */
    public static Integer[] arrUniqueToInt(String[] arr1) {
        Stream<String> stream = Arrays.stream(arr1);
        String s = StringUtils.join(stream.distinct().toArray(), ",");
        return stringConvertInt(s);
    }

    /**
     * 判断指定时间是否在指定时间区间内
     *
     * @param sRange 开始时间
     * @param eRange 结束时间
     * @param time   指定时间
     * @return
     */
    public static boolean checkTimeIsInRange(String sRange, String eRange, String time) {
        try {
            if (!sRange.contains(":") || !eRange.contains(":")) {
                logger.info(String.format("格式不正确------>%s=>%s", sRange, eRange));
            } else {
                String[] array1 = sRange.split(":");
                int h1 = Integer.parseInt(array1[0]);
                int m1 = Integer.parseInt(array1[1]);
                String[] array2 = eRange.split(":");
                int h2 = Integer.parseInt(array2[0]);
                int m2 = Integer.parseInt(array2[1]);
                if (h1 >= 24 || h2 >= 24 || m1 >= 60 || m2 >= 60) {
                    logger.info(String.format("时间格式不正确------>%s=>%s", sRange, eRange));
                    return false;
                }
                int total1 = h1 * 3600 + m1 * 60;
                int total2 = h2 * 3600 + m2 * 60;
                if (time == null || "".equals(time)) {
                    //获取当前时间
                    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
                    time = sdf.format(new Date());
                } else if (!time.contains(":")) {
                    logger.info(String.format("格式不正确------>%s", time));
                    return false;
                }
                String[] nowArray = time.split(":");
                int h3 = Integer.parseInt(nowArray[0]);
                int m3 = Integer.parseInt(nowArray[1]);
                if (h3 >= 24 || m3 >= 60) {
                    logger.info(String.format("时间格式不正确------>%s=>%s", sRange, eRange));
                    return false;
                }
                int total3 = Integer.parseInt(nowArray[0]) * 3600 + Integer.parseInt(nowArray[1]) * 60;
                return total1 - total2 < 0 && total3 - total1 >= 0 && total2 - total3 >= 0;
            }
        } catch (NumberFormatException e) {
            logger.info(e.getMessage());
            return true;
        }
        return false;
    }


    /**
     * [是否是高峰期 7、8、9月]
     */
    public static boolean isHighMonth() {
        int month = new Date().getMonth() + 1;
        Integer[] high = {7, 8, 9};
        List<Integer> highList = Arrays.asList(high);
        return highList.contains(month);
    }

    public static Map<String, Object> isType(String rule, Integer ruleId) {
        Map<String, Object> map = new HashMap<>();
        if (rule.contains("TimeFunction")) {
            map.put("type", "1");
            rule = rule.replace("TimeFunction(", "").replace(")", "");
            map.put("rule", rule);
        } else {
            map.put("type", "0");
            map.put("rule", rule);
        }
        String item = "";
        for (int i = 1; i < rule.split("\\{\\{").length; i++) {
            item = item + rule.split("\\{\\{")[i].split("}}")[0] + ",";

        }
        map.put("plcId", item.length() > 0 ? item.substring(0, item.length() - 1) : item);
        map.put("ruleId", ruleId.toString());

        return map;
    }

    public static Boolean splitOperation(Map<String, Object> map, Map<Integer, Object> value) {
        Boolean result = false;
        try {
            String rule = map.get("rule").toString();
            if ("1".equals(map.get("type"))) {
                for (Integer plcId : value.keySet()) {
                    if(plcId.toString().equals(map.get("plcId"))){
                        System.out.println(rule);
                        rule = rule.replace(plcId.toString(),  Long.toString(((System.currentTimeMillis() - (Long) value.get(plcId)) / 60000)));
                    }
                    else {
                        return false;
                    }
                }
            } else {
                for (Integer plcId : value.keySet()) {
                    rule = rule.replace(plcId.toString(), value.get(plcId).toString());
                }
            }
            System.out.println(rule);
            rule = rule.replace("{{", "").replace("}}", "");
            ScriptEngineManager manager = new ScriptEngineManager();
            ScriptEngine engine = manager.getEngineByName("js");
            result = (Boolean) engine.eval(rule);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return result;
    }

//    public static Boolean checkRuleAlarm(String rule, Object value) {
//        return splitOperation(isType(rule), value);
//    }

    public static String plcIdExtract(List<Map<String, Object>> list) {
        String ids = "";
        Set<String> set = new HashSet<>();
        try {
            for (Map<String, Object> idsList : list) {
                set.add((String) idsList.get("plcId"));
            }
            for (String idsSet : set) {
                ids = ids + idsSet + ",";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ids.length() > 0 ? ids.substring(0, ids.length() - 1) : ids;
    }

    public static Map<Integer, Boolean> isFault(List<Map<String, Object>> list, List<Map<String, Object>> actualValue) {
        Map<Integer, Boolean> mapFault = new HashMap<>();
        try {
            for (Map<String, Object> map : actualValue) {
                for (Map<String, Object> idsList : list) {
                    if ((idsList.get("ruleId")).equals(map.get("ruleId").toString())) {
                        if ("0".equals(idsList.get("type"))) {
                            mapFault.put(Integer.parseInt((String) idsList.get("ruleId")), splitOperation(idsList, (Map<Integer, Object>) map.get("value")));
                        } else if ("1".equals(idsList.get("type"))) {
                            mapFault.put(Integer.parseInt((String) idsList.get("ruleId")), splitOperation(idsList, (Map<Integer, Object>) map.get("alarmTime")));
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return mapFault;
    }


    public static void main(String[] args) {
        List<Map<String, Object>> list = new ArrayList<>();
//        list.add(isType("{{528}}-{{529}}+{{530}}>1", 1));
        list.add(isType("TimeFunction({{529}})>2", 1));
//        list.add(isType("{{590}}>3", 3));
//        list.add(isType("{{591}}>4", 4));
//        list.add(isType("{{592}}>5", 5));
        List<Map<String, Object>> map = new ArrayList<>();
//        map.add(new HashMap<String, Object>(){
//            {
//                put("id",528);
//                put("value",528);
//                put("alarmTime",528);
//            }
//        });
        map.add(new HashMap<String, Object>() {
            {
                put("ruleId", "1");
                put("value", new HashMap<Integer, Object>() {{
                    put(528, "6");
                    put(529, "7");
                    put(530, "8");
                }});
                put("alarmTime", new HashMap<Integer, Object>() {{
                    put(529, System.currentTimeMillis() - 180000);
                }});
            }
        });
//        plcIdExtract(list);
//        System.out.println(plcIdExtract(list));
//        isFault(list,map);
        System.out.println(isFault(list, map));
    }
}
