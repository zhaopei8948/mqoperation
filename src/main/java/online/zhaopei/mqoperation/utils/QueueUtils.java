package online.zhaopei.mqoperation.utils;

import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;

import java.util.Properties;

public class QueueUtils {

    public MQQueue getMQQueue() {
//        Properties properties = new Properties();
//        properties.put("hostname", "172.16.33.55");
//        properties.put("port", 1800);
//        properties.put("channel", "JAVA.CHANNEL");
//        properties.put("CCSID", 1381);
//
//        MQQueueManager manager = new MQQueueManager("DFWTINTRA", properties);
//        System.out.println(manager.getResolvedObjectString());
//        System.out.println(manager.getDescription());
//        System.out.println(manager.getName());
//        System.out.println(manager.getAlternateUserId());
//        System.out.println(manager.getCharacterSet());
//        System.out.println(manager.getCloseOptions());
//        System.out.println(manager.getCommandLevel());
//        System.out.println(manager.getOpenOptions());
//
//        int openOptions = MQConstants.MQOO_INQUIRE;
//        MQQueue queue = manager.accessQueue("WICH.RECE.ENT", openOptions);
//
//        System.out.println("该队列当前的深度为:" + queue.getCurrentDepth());
//        System.out.println("该队列打开输入计数:" + queue.getOpenInputCount());
//        System.out.println("该队列打开输出计数为:" + queue.getOpenOutputCount());
//        System.out.println("该队列最大消息大小为:" + queue.getMaximumMessageLength());
//        queue.close();
//        manager.disconnect();
        return null;
    }
}
