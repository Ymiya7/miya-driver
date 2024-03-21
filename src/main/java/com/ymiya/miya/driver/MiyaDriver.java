package com.ymiya.miya.driver;

import lombok.extern.slf4j.Slf4j;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;
import java.sql.*;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * @author : Ymiya
 * @version V1.0
 * @Project: miya-driver
 * @Package com.ymiya.miya.driver
 * @Description: 驱动
 * @mail :  ymiyan007@gmail.com
 * @date Date : 2024年03月21日 22:48
 */
@Slf4j
public class MiyaDriver implements Driver {

    private static final MiyaDriver instance = new MiyaDriver();

    private String acceptPrefix = "jdbc:wrap-jdbc:";

    private int majorVersion = 7;
    private int minorVersion = 0;

    public static boolean registerDriver(Driver driver) {
        try {
            DriverManager.registerDriver(driver);

            try {
                MBeanServer mbeanServer = ManagementFactory.getPlatformMBeanServer();
                ObjectName objectName = new ObjectName("com.ymiya.miya:type=MiyaDriver");
                if (!mbeanServer.isRegistered(objectName)) {
                    mbeanServer.registerMBean(instance, objectName);
                }
            } catch (Throwable var3) {
                log.warn("register miya-driver mbean error", var3);
            }

            return true;
        } catch (Exception var4) {
            log.error("registerDriver error", var4);
            return false;
        }
    }

    public MiyaDriver() {}



    @Override
    public Connection connect(String url, Properties info) throws SQLException {
        if (!this.acceptsURL(url)) return null;

    }

    @Override
    public boolean acceptsURL(String url) throws SQLException {
        if (Objects.isNull(url) || url.isEmpty()) return false;
        return url.startsWith(this.acceptPrefix);
    }


    @Override
    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    @Override
    public int getMajorVersion() {
        return 0;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public boolean jdbcCompliant() {
        return false;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
