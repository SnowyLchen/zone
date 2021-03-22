package com.cjl.basic.zone.common.utils.http;

import com.cjl.basic.zone.common.constant.Constants;
import com.cjl.basic.zone.common.utils.ServletUtils;
import com.cjl.basic.zone.framework.web.domain.AjaxResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * httpClient封装
 *
 * @author hd_zhu
 */
@Component
public class SpringHttpClient {
    private static final String DATA_KEY = "data";
    private static final String CODE_KEY = "code";
    private static final String MSG_KEY = "msg";
    private static final int SUCCESS_CODE = 0;
    private static RestTemplate restTemplate;
    private static ObjectMapper objectMapper;
    public static final Logger logger = LoggerFactory.getLogger(SpringHttpClient.class);

    @Autowired
    public void setObjectMapper(ObjectMapper objectMapper) {
        SpringHttpClient.objectMapper = objectMapper;
    }

    //    短线重试3此

    /**
     * 初始换工具类
     *
     * @param restTemplate RestTemplate工具
     */
    @Autowired
    public SpringHttpClient(RestTemplate restTemplate) {
        SpringHttpClient.restTemplate = restTemplate;
    }

    /**
     * 发起一次无参Get请求
     *
     * @param url   请求的url地址
     * @param clazz 返回结果类型
     * @param <T>   {@link T}
     * @return {@link HttpResultAdapter}
     * @throws NoHttpResponseBodyException 解析错误
     * @throws HttpClientErrorException    客户端发送时错误
     */
    @NonNull
    public static <T> HttpResultAdapter<T> get(String url, Class<T> clazz) throws NoHttpResponseBodyException, HttpClientErrorException {
        return get(url, null, clazz);
    }

    /**
     * 通过java对象作为参数的方式发起一次Get请求
     *
     * @param url     请求的url地址
     * @param request 参数 javaObject
     * @param clazz   返回结果类型
     * @param <T>     {@link T}
     * @return {@link HttpResultAdapter}
     * @throws NoHttpResponseBodyException 解析错误
     * @throws HttpClientErrorException    客户端发送时错误
     */
    @NonNull
    public static <T> HttpResultAdapter<T> getByJavaObjectParam(String url, Object request, Class<T> clazz) throws NoHttpResponseBodyException {
        Map<String, Object> params = null;
        try {
            if (request != null) {
                String json = objectMapper.writeValueAsString(request);
                Map<?, ?> map = objectMapper.readValue(json, Map.class);
                for (Object o : map.keySet()) {
                    if (params == null) {
                        params = new HashMap<>(16);
                    }
                    Object r = map.get(o);
                    if (r == null) {
                        continue;
                    }
                    if ("params".equals(o.toString()) && ((Map<?, ?>) r).isEmpty()) {
                        continue;
                    }
                    params.put(o.toString(), r);
                }
            }
        } catch (IOException e) {
            logger.error("json序列化失败", e);
        }
        return get(url, params, clazz);
    }

    /**
     * 通过Map对象作为参数的方式发起一次Get请求
     *
     * @param url     请求的url地址
     * @param request 参数 Map<String,Object>
     * @param clazz   返回结果类型
     * @param <T>     {@link T}
     * @return {@link HttpResultAdapter}
     * @throws NoHttpResponseBodyException 解析错误
     * @throws HttpClientErrorException    客户端发送时错误
     */
    @NonNull
    public static <T> HttpResultAdapter<T> get(String url, Map<String, Object> request, Class<T> clazz) throws NoHttpResponseBodyException, HttpClientErrorException {
        String params = convertMapToRequest(request);
        if (isPaginationExist()) {
            params = paginationOption(params);
        }
        return new HttpResultAdapter<>(restTemplate.getForEntity(url + params, AjaxResult.class), clazz);
    }

    /**
     * 发起一次无参Post请求
     *
     * @param url   请求的url地址
     * @param clazz 返回结果类型
     * @param <T>   {@link T}
     * @return {@link HttpResultAdapter}
     * @throws NoHttpResponseBodyException 解析错误
     * @throws HttpClientErrorException    客户端发送时错误
     */
    @NonNull
    public static <T> HttpResultAdapter<T> post(String url, Class<T> clazz) throws NoHttpResponseBodyException, HttpClientErrorException {
        return post(url, null, clazz);
    }

    /**
     * 发起一次Post请求
     *
     * @param url     请求的url地址
     * @param request 参数，可以为{@link MultiValueMap} 也可以时javaObject对象
     * @param clazz   返回结果类型
     * @param <T>     {@link T}
     * @return {@link HttpResultAdapter}
     * @throws NoHttpResponseBodyException 解析错误
     * @throws HttpClientErrorException    客户端发送时错误
     */
    @NonNull
    public static <T> HttpResultAdapter<T> post(String url, Object request, Class<T> clazz) throws NoHttpResponseBodyException, HttpClientErrorException {
        return new HttpResultAdapter<>(restTemplate.postForEntity(url, request, AjaxResult.class), clazz);
    }

    /**
     * 转换Map
     *
     * @param request 转换
     * @return {@link String}
     */
    private static String convertMapToRequest(Map<String, Object> request) {
        StringBuilder builder = new StringBuilder();
        if (request != null) {
            for (String key : request.keySet()) {
                Object value = request.get(key);
                if (value != null) {
                    builder.append("&").append(key).append("=").append(value);
                }
            }
            if (builder.length() > 0) {
                builder.replace(0, 1, "?");
            }
        }
        return builder.toString();
    }

    public static class HttpResultAdapter<T> {
        private String result;
        private Class<T> clazz;

        HttpResultAdapter(@NonNull ResponseEntity<AjaxResult> responseEntity, @NonNull Class<T> clazz) throws NoHttpResponseBodyException {
            this.clazz = clazz;
            final HttpStatus status = responseEntity.getStatusCode();
            if (status != HttpStatus.OK) {
                throw new HttpClientErrorException(status, "请求失败");
            }
            final AjaxResult result = Objects.requireNonNull(responseEntity.getBody());
            if (!Objects.equals(result.get(SpringHttpClient.CODE_KEY), SUCCESS_CODE)) {
                throw new NoHttpResponseBodyException(result.get(MSG_KEY).toString());
            }
            try {
                this.result = objectMapper.writeValueAsString(Objects.requireNonNull(result.get(DATA_KEY)));
            } catch (NullPointerException | JsonProcessingException e) {
                this.result = null;
            }
        }

        /**
         * 把结果转化转换为集合
         *
         * @return {@link T}
         */
        public List<T> toArray() {
            try {
                return this.result == null ? null : Arrays.stream(objectMapper.readValue(result, Object[].class))
                        .map(s -> {
                            try {
                                return objectMapper.readValue(objectMapper.writeValueAsString(s), clazz);
                            } catch (IOException e) {
                                logger.error("转化失败", e);
                                return null;
                            }
                        })
                        .collect(Collectors.toList());
            } catch (IOException e) {
                logger.error("转化失败", e);
                return null;
            }
        }

        /**
         * 把结果转化转换为java对象
         *
         * @return {@link T}
         */
        public T toJavaObject() {
            try {
                return this.result == null ? null : objectMapper.readValue(result, clazz);
            } catch (IOException e) {
                logger.error("转化失败", e);
                return null;
            }
        }
    }

    /**
     * 是否开启分页
     *
     * @return [返回值]
     * @author stz
     * @date 2020/10/23 14:14
     */
    public static Boolean isPaginationExist() {
        try {
            Objects.requireNonNull(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        } catch (NullPointerException e) {
            return false;
        }
        return true;
    }

    /**
     * [分页方法]
     *
     * @param s []
     * @return [返回值]
     * @author stz
     * @date 2020/10/14 9:12
     */
    private static String paginationOption(String s) {
        Integer pageNum = ServletUtils.getParameterToInt(Constants.PAGE_NUM);
        Integer pageSize = ServletUtils.getParameterToInt(Constants.PAGE_SIZE);
        String orderByColumn = ServletUtils.getParameter(Constants.ORDER_BY_COLUMN);
        String isAsc = ServletUtils.getParameter(Constants.IS_ASC);
        StringBuilder sb;
        if ("".equals(s)) {
            sb = new StringBuilder("?" + s);
        } else {
            sb = new StringBuilder(s);
        }
        if (pageNum != null) {
            sb.append("&").append(Constants.PAGE_NUM).append("=").append(ServletUtils.getParameterToInt(Constants.PAGE_NUM));
        }
        if (pageSize != null) {
            sb.append("&").append(Constants.PAGE_SIZE).append("=").append(ServletUtils.getParameterToInt(Constants.PAGE_SIZE));
        }
        if (orderByColumn != null) {
            sb.append("&").append(Constants.ORDER_BY_COLUMN).append("=").append(ServletUtils.getParameter(Constants.ORDER_BY_COLUMN));
        }
        if (isAsc != null) {
            sb.append("&").append(Constants.IS_ASC).append("=").append(ServletUtils.getParameter(Constants.IS_ASC));
        }
        return sb.toString();
    }
}
