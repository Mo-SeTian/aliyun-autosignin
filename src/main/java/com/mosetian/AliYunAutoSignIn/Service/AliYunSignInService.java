package com.mosetian.AliYunAutoSignIn.Service;

import com.mosetian.AliYunAutoSignIn.Utils.AliYunAutoSignInUtil;
import com.mosetian.AliYunAutoSignIn.Utils.ProjectUtils;
import com.mosetian.AliYunAutoSignIn.Utils.ServerChanAPI;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Set;

@Service
public class AliYunSignInService {

    public void manualExecution() throws Exception {
        StringBuilder Msg = new StringBuilder();
        HashMap<String, String> refreshTokenMap = ProjectUtils.getRefreshToken();
        Set<String> NoteSet = refreshTokenMap.keySet();
        for (String note : NoteSet) {
            String refreshToken = refreshTokenMap.get(note);
            //更新AccessToken
            String AccessToken = AliYunAutoSignInUtil.refreshAccessToken(refreshToken, note);
            if (AccessToken.contains("失效") || AccessToken.contains("失败")) {
                Msg.append(note).append(AccessToken).append("\n");
                continue;
            } else {
                Msg.append(note).append("RefreshToken更新成功!\n");
            }
            //签到
            String day = AliYunAutoSignInUtil.signIn(AccessToken);
            if (!isNumeric(day)) {
                Msg.append(note).append(day).append("\n");
                continue;
            } else {
                Msg.append(note).append("签到成功!签到第").append(day).append("天\n");
            }
            //领取奖励
            String claimRewards = AliYunAutoSignInUtil.claimRewards(AccessToken, Integer.parseInt(day));
            if (claimRewards.contains("失败")) {
                Msg.append(note).append(claimRewards).append("\n");
                continue;
            } else {
                Msg.append(note).append("领取奖励成功!奖励为:").append(claimRewards).append("\n");
            }
        }
        ServerChanAPI.sendMessage("阿里云盘签到", Msg.toString());
    }

    public static boolean isNumeric(String str) {
        return str != null && str.matches("\\d+");
    }
}
