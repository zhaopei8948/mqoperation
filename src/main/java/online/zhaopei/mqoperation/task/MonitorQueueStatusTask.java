package online.zhaopei.mqoperation.task;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.ibm.mq.MQException;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import online.zhaopei.mqoperation.constant.CommonConstant;
import online.zhaopei.mqoperation.domain.Queue;
import online.zhaopei.mqoperation.service.QueueService;
import online.zhaopei.mqoperation.utils.CommonUtils;
import online.zhaopei.mqoperation.websocket.QueueWebSocket;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

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
                mqqueue = manager.accessQueue(queue.getName(), MQConstants.MQOO_INQUIRE | MQConstants.MQOO_INPUT_AS_Q_DEF);
                this.queueMap.put(id, mqqueue);
            } catch (MQException e) {
                CommonUtils.logError(logger, e);
            }
        }
        return mqqueue;
    }

    public int getQueueDepth(long id) {
        if (CommonConstant.DEBUG) {
            return (int)(Math.random() * 1000000);
        } else {
            try {
                return this.getQueueTaskById(id).getCurrentDepth();
            } catch (MQException e) {
                CommonUtils.logError(logger, e);
                return 0;
            }
        }
    }

    public List<Integer> getQueueDepthByQueueMangerId(long id) {
        List<Queue> queueList = this.queueService.select(new Queue(){{
            this.setManagerId(id);
        }});
//        List<Integer> depthList = queueList.stream().map(queue -> getQueueDepth(queue.getId())).collect(Collectors.toList());
        List<Integer> depthList = Lists.transform(queueList, new Function<Queue, Integer>() {
            @Override
            public Integer apply(@Nullable Queue input) {
                return getQueueDepth(input.getId());
            }
        });

        return depthList;
    }

    public List<Integer> getQueueDepthByQueueMangerIds(String mangerIds) {
        List<Long> managerIdList = Lists.newArrayList(Iterables.transform(Arrays.asList(mangerIds.split(",")),
                new Function<String, Long>() {
                    @Nullable
                    @Override
                    public Long apply(@Nullable String input) {
                        return Long.valueOf(input);
                    }
                }));
        List<Queue> queueList = this.queueService.select(new Queue(){{
            this.setManagerIdList(managerIdList);
        }});

        List<Integer> depthList = Lists.transform(queueList, new Function<Queue, Integer>() {
            @Override
            public Integer apply(@Nullable Queue input) {
                return getQueueDepth(input.getId());
            }
        });

        return depthList;
    }


    public List<Integer> getAllQueueDepth() {
        List<Queue> queueList = this.queueService.select(new Queue());
        return Lists.transform(queueList, new Function<Queue, Integer>() {
            @Override
            public Integer apply(@Nullable Queue input) {
                return getQueueDepth(input.getId());
            }
        });
    }

    public JSONObject getQueueDepthJsonByManagerId(long qmid) {
        return this.convertListDepthToJson(this.getQueueDepthByQueueMangerId(qmid));
    }

    public JSONObject getQueueDepthJsonByManagerIds(String qmids) {
        return this.convertListDepthToJson(this.getQueueDepthByQueueMangerIds(qmids));
    }

    public JSONObject getAllQueueDepthJson() {
        return this.convertListDepthToJson(this.getAllQueueDepth());
    }

    public JSONObject convertListDepthToJson(List<Integer> depthList) {
        String time = CommonConstant.DATE_TIME_FORMAT.format(Calendar.getInstance().getTime());
        JSONObject timeDepthJson = new JSONObject();
        JSONArray timeDepthArray = new JSONArray();
        JSONArray tempTimeDepth = null;
        for (int d : depthList) {
            tempTimeDepth = new JSONArray();
            tempTimeDepth.add(time);
            tempTimeDepth.add(d);
            timeDepthArray.add(tempTimeDepth);
        }
        timeDepthJson.put("data", timeDepthArray);
        return timeDepthJson;
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
