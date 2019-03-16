package online.zhaopei.mqoperation.task;

import com.alibaba.fastjson.JSONObject;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import online.zhaopei.mqoperation.domain.Queue;
import online.zhaopei.mqoperation.service.QueueService;
import online.zhaopei.mqoperation.utils.CommonUtils;
import online.zhaopei.mqoperation.websocket.QueueWebSocket;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@Component
public class MonitorQueueStatusTask {

    private static final Log logger = LogFactory.getLog(MonitorQueueStatusTask.class);

    @Autowired
    private QueueWebSocket queueWebSocket;

    @Autowired
    private QueueService queueService;

    private ConcurrentHashMap<Long, MQQueue> queueMap = new ConcurrentHashMap<Long, MQQueue>();

    private ConcurrentHashMap<String, MQQueueManager> queueManagerMap = new ConcurrentHashMap<String, MQQueueManager>();

//    @Scheduled(fixedDelay = 2000)
    public MQQueue getQueueTaskById(long id) {
        logger.info("id=[" + id + "]");
        Queue queue = this.queueService.getQueueAndManager(id);
        logger.info("queue=[" + queue + "]");
        Properties properties = new Properties();
        properties.put("hostname", queue.getQueueManager().getIp());
        properties.put("port", queue.getQueueManager().getPort());
        properties.put("channel", queue.getQueueManager().getChannel());
        properties.put("CCSID", queue.getQueueManager().getCcsid());

        String key = properties.getProperty("hostname") + ":" + properties.getProperty("port");

        MQQueueManager manager = this.queueManagerMap.get(key);
        MQQueue mqqueue = queueMap.get(id);
        if (null == mqqueue) {
            try {
                if (null == manager) {
                    manager = new MQQueueManager(queue.getQueueManager().getName(), properties);
                    this.queueManagerMap.put(key, manager);
                }
                mqqueue = manager.accessQueue(queue.getName(), MQConstants.MQOO_INQUIRE);
                this.queueMap.put(id, mqqueue);
            } catch (MQException e) {
                CommonUtils.logError(logger, e);
            }
        }
        return mqqueue;
    }

    public int getQueueDepth(long id) {
        try {
            return this.getQueueTaskById(id).getCurrentDepth();
        } catch (MQException e) {
            CommonUtils.logError(logger, e);
            return 0;
        }
//        return (int)(Math.random() * 1000);
    }

    public void closeMQ() {
        for (Map.Entry<Long, MQQueue> entry: queueMap.entrySet()) {
            if (null != entry.getValue()) {
                try {
                    entry.getValue().close();
                } catch (MQException e) {
                    CommonUtils.logError(logger, e);
                }
            }
        }

        for (Map.Entry<String, MQQueueManager> entry: queueManagerMap.entrySet()) {
            if (null != entry.getValue()) {
                try {
                    entry.getValue().close();
                } catch (MQException e) {
                    CommonUtils.logError(logger, e);
                }
            }
        }
        queueMap.clear();
        queueManagerMap.clear();
    }
}
