//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.abtnetworks.data.totems.topology.userms.license.schedule;

import com.abtnetworks.data.totems.topology.userms.license.LicenseManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Configurable
//@EnableScheduling
public class LicenseTask {
    private static final Logger logger = Logger.getLogger(LicenseTask.class);
    public static boolean started = false;
    @Autowired
    private LicenseManager licenseManager;

    public LicenseTask() {
    }

    @Scheduled(
            cron = "0 5 * * * ?"
    )
    public void jobByCron() {
        if (!started) {
            started = true;

            try {
                logger.info("licenseManager.licenseMerge() begin");
//                this.licenseManager.licenseMerge();
                logger.info("licenseManager.licenseMerge() end");
            } catch (Exception var2) {
                logger.error(var2.getMessage(), var2);
            }

            started = false;
        }
    }
}
