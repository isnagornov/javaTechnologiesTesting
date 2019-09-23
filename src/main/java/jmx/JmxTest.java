package jmx;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;

public class JmxTest {

    public static void main(String[] args) throws Exception {

        ObjectName objectName = new ObjectName("ru.isnagornov.jmxtest:type=basic,name=game");

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        Game game = new Game();
        game.setPlayerName("Player1");

        mBeanServer.registerMBean(game, objectName);

        System.out.println("MBean of GameMXBean.class registered");

        Thread.sleep(Long.MAX_VALUE);
    }
}
