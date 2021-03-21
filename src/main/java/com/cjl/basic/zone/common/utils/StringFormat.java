package com.cjl.basic.zone.common.utils;

import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.*;

public class StringFormat {

    public static Logger log = LoggerFactory.getLogger(StringFormat.class);

    public static void main(String[] args) throws ScriptException {
        Map<String, Object> map =new HashMap<>();
        System.out.println(getIdSet("{{528}}>10||{{530}}<10"));
        System.out.println(getIdSet("{{528}}>10||{{530}}<10").size());
        map.put("528","1");
        map.put("530","1");
        List<String> list = isTpye("{{528}}>10");
        SplitOperation(list,5);
        System.out.println(format("{{528}}>10||{{530}}<10",map));
    }


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
    public static String format1(String string, Map<String, Object> map) {
        if (Objects.isNull(map) || map.isEmpty()) {
            return string;
        }
        Iterator<String> stringIterator = map.keySet().iterator();
        while (stringIterator.hasNext()) {
            String key = stringIterator.next();
            if (string.contains("TimeFunction(")){
                string = string.replace("TimeFunction({{" +key + "}})", map.get(key).toString());
            }else{
                string = string.replace("{{" + key + "}}", map.get(key).toString());
            }
        }
        return string;
    }

    public static Set<Integer> getIdSet(String string) {
        Set<Integer> idSet = new LinkedHashSet<>();
        String[] items = string.split("\\{\\{");
        for (String item : items) {
            String number = item.split("}}")[0];
            if (Strings.isBlank(number)|| number.contains("TimeFunction(")) {
                continue;
            }
            try {
                idSet.add(Integer.valueOf(number));
            } catch (NumberFormatException e) {
                log.error("模板格式化异常", e);
                idSet.add(0);
            }
        }
        return idSet;
    }
    public static List<String> isTpye(String rule){
        List<String> list = new ArrayList<>();
        if (rule.contains("TimeFunction")) {
            list.add("1");
            rule = rule.replace("TimeFunction(", "").replace(")", "");
            list.add(rule);
            list.add(rule.split("\\{\\{")[1].split("}}")[0]);
            return list;
        }else{
            list.add("0");
            list.add(rule);
            list.add(rule.split("\\{\\{")[1].split("}}")[0]);
            return list;
        }
    }

    public static Boolean SplitOperation(List<String> list, Object value) throws ScriptException {
        String rule = null;
        if ("1".equals(list.get(0))) {
            String item[] = list.get(1).split("\\{\\{");
            rule = item[0] +(System.currentTimeMillis() - (Long)value) / 60000 + item[1].split("}}")[1];
        } else {
           String item[] = list.get(1).split("\\{\\{");
           rule = item[0] + value + item[1].split("}}")[1];
        }
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("js");
        Boolean result = null;
        result = (Boolean) engine.eval(rule);
        return result;
    }

    public static  Integer[] getJ(Integer[] m, Integer[] n) {
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
}
