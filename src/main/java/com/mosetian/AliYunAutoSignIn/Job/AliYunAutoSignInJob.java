package com.mosetian.AliYunAutoSignIn.Job;

import com.mosetian.AliYunAutoSignIn.Service.AliYunSignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class AliYunAutoSignInJob {

    @Autowired
    private AliYunSignInService aliYunSignInService;

    @Scheduled(cron = "${CRON_VALUE:0 32 7 * * ?}")
    public void jobMain() throws Exception {
        aliYunSignInService.manualExecution();
    }
}
