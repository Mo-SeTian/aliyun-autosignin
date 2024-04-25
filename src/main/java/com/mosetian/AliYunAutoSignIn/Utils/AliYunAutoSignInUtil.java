package com.mosetian.AliYunAutoSignIn.Utils;

import cn.hutool.http.HttpException;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;

import java.util.HashMap;
import java.util.Objects;

public class AliYunAutoSignInUtil {

    private final static String updateAccessTokenURL = "https://auth.aliyundrive.com/v2/account/token";
    private final static String signInURL = "https://member.aliyundrive.com/v1/activity/sign_in_list?_rx-s=mobile";
    private final static String rewardURL = "https://member.aliyundrive.com/v1/activity/sign_in_reward?_rx-s=mobile";

    //更新AccessToken
    public static String refreshAccessToken(String refreshToken, String note) throws Exception {
        String access_token = null;
        try {
            HashMap<String, String> bodyMap = new HashMap<>();
            bodyMap.put("grant_type", "refresh_token");
            bodyMap.put("refresh_token", refreshToken);
            String jsonBody = JSONUtil.toJsonStr(bodyMap);
            // 创建POST请求
            HttpRequest request = HttpRequest.post(updateAccessTokenURL)
                    .header("Content-Type", "application/json") // 添加Header
                    .body(jsonBody);
            // 发送请求，并接收响应
            HttpResponse response = request.execute();
            // 输出响应的body
            String responseBody = response.body();
            JSONObject jsonObject = JSONUtil.parseObj(responseBody);
            String code = String.valueOf(jsonObject.get("code"));
            access_token = String.valueOf(jsonObject.get("access_token"));
            String refresh_token = String.valueOf(jsonObject.get("refresh_token"));
            if (Objects.equals(code, "RefreshTokenExpired") || Objects.equals(code, "InvalidParameter.RefreshToken")) {
                return "RefreshToken失效!";
            }
            //更新refresh_token
            XmlModifierUtils.updateXmlNode(note, refresh_token);
        } catch (HttpException e) {
            return "更新RefreshToken失败!" + e.getMessage();
        }
        //返回access_token
        return access_token;
    }

    //签到
    public static String signIn(String accessToken) {
        try {
            HashMap<String, String> bodyMap = new HashMap<>();
            bodyMap.put("isReward", "false");
            String jsonBody = JSONUtil.toJsonStr(bodyMap);

            HttpRequest request = HttpRequest.post(signInURL)
                    .header("Content-Type", "application/json") // 添加Header
                    .header("Authorization", accessToken)
                    .body(jsonBody);

            // 发送请求，并接收响应
            HttpResponse response = request.execute();
            // 输出响应的body
            String responseBody = response.body();
            JSONObject jsonObject = JSONUtil.parseObj(responseBody);
            String successCode = String.valueOf(jsonObject.get("success"));
            if (Objects.equals(successCode, "true")) {
                // 获取嵌套的JSONObject
                JSONObject resultObject = jsonObject.getJSONObject("result");
                // 从result对象中获取signInCount
                return String.valueOf(resultObject.getInt("signInCount"));
            } else {
                return (String) jsonObject.get("message");
            }
        } catch (HttpException e) {
            return "签到失败" + e.getMessage();
        }
    }

    //领取奖励
    public static String claimRewards(String accessToken, int today) {
        try {
            HashMap<String, Integer> bodyMap = new HashMap<>();
            bodyMap.put("signInDay", today);
            String jsonBody = JSONUtil.toJsonStr(bodyMap);

            HttpRequest request = HttpRequest.post(rewardURL)
                    .header("Authorization", accessToken)
                    .body(jsonBody);
            // 发送请求，并接收响应
            HttpResponse response = request.execute();
            // 输出响应的body
            String responseBody = response.body();
            JSONObject jsonObject = JSONUtil.parseObj(responseBody);
            String successCode = String.valueOf(jsonObject.get("success"));
            if (Objects.equals(successCode, "true")) {
                // 获取嵌套的JSONObject
                JSONObject resultObject = jsonObject.getJSONObject("result");
                // 从result对象中获取signInCount
                return String.valueOf(resultObject.get("notice"));
            } else {
                return (String) jsonObject.get("message") + "领取奖励失败!";
            }
        } catch (HttpException e) {
            return "领取奖励失败!" + e.getMessage();
        }
    }
}
