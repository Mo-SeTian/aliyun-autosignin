package com.mosetian.AliYunAutoSignIn.Controller;

import com.mosetian.AliYunAutoSignIn.Service.AliYunSignInService;
import com.mosetian.AliYunAutoSignIn.Utils.AliYunAutoSignInUtil;
import com.mosetian.AliYunAutoSignIn.Utils.ProjectUtils;
import com.mosetian.AliYunAutoSignIn.Utils.ServerChanAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Set;

@RestController
@RequestMapping("/start")
public class AliYunSignInController {

    @Autowired
    private AliYunSignInService aliYunSignInService;

    @GetMapping("/job")
    public String manualExecution() throws Exception {
        aliYunSignInService.manualExecution();
        return "执行成功!";
    }
}
