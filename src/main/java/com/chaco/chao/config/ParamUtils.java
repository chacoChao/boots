package com.chaco.chao.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.*;
import java.util.Map.Entry;

/**
 * author:zhaopeiyan001
 * Date:2019-03-15 10:58
 */
public class ParamUtils {
    private static final ObjectMapper mapper = new ObjectMapper();

    public ParamUtils() {
    }

    public static String plainText(String jsonString) {
        Objects.requireNonNull(jsonString, "jsonString is null");
        JsonNode jsonNode = null;

        try {
            jsonNode = mapper.readTree(jsonString);
        } catch (Exception var6) {
            throw new RuntimeException("jsonString format error - ", var6);
        }

        Objects.requireNonNull(jsonNode, "jsonNode is null");
        Map<String, String> treeMap = new TreeMap();
        Iterator fields = jsonNode.fields();

        while(fields.hasNext()) {
            Entry<String, JsonNode> entry = (Entry)fields.next();
            String value = null;
            if (entry.getValue() != null) {
                if (((JsonNode)entry.getValue()).isValueNode()) {
                    value = ((JsonNode)entry.getValue()).asText();
                } else {
                    value = ((JsonNode)entry.getValue()).toString();
                }
            }

            if (!Arrays.asList("", "signMsg").contains(entry.getKey()) && value != null && !Arrays.asList("", "[]", "{}").contains(value)) {
                treeMap.put(entry.getKey(), value.replaceAll(", +\"", ",\""));
            }
        }

        StringBuilder sBuilder = new StringBuilder();
        Iterator var8 = treeMap.entrySet().iterator();

        while(var8.hasNext()) {
            Entry<String, String> entry = (Entry)var8.next();
            sBuilder.append((String)entry.getKey()).append("=").append((String)entry.getValue()).append("&");
        }

        return sBuilder.length() > 0 ? sBuilder.substring(0, sBuilder.length() - 1) : "";
    }

    public static void main(String[] args) throws Exception {
        String jsonString = "{\"platformNo\":\"p001\", \"data\":[],\"payInfo\":[{\"srcMchNo\":\"m0001\",   \"sources\":[{\"flowNo\":\"flow21544359862059\"}]}],\"reqTime\":\"2018-12-01 10:10:00\",\"bizNo\":\"123456\",\"title\":\"分账订单\"}";
        System.out.println("jsonString = " + jsonString);
        String plainText = plainText(jsonString);
        System.out.println("plainText = " + plainText);
        String sign = SignUtils.sign(plainText, "123456", Algorithm.HMAC_SHA_256);
        System.out.println("sign = " + sign);
        boolean verify = SignUtils.verify(plainText, "123456", Algorithm.HMAC_SHA_256, sign);
        System.out.println("verify = " + verify);
    }
}
