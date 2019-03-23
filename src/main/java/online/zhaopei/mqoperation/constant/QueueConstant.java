package online.zhaopei.mqoperation.constant;

import com.ibm.mq.constants.MQConstants;

import java.util.HashMap;

public enum QueueConstant {

    ;

    public static final HashMap<Integer, String> MQQUEUE_TYPE_MAP = new HashMap<>();

    static {
        MQQUEUE_TYPE_MAP.put(MQConstants.MQQT_ALIAS, "别名队列");
        MQQUEUE_TYPE_MAP.put(MQConstants.MQQT_LOCAL, "本地队列");
        MQQUEUE_TYPE_MAP.put(MQConstants.MQQT_MODEL, "模型队列");
        MQQUEUE_TYPE_MAP.put(MQConstants.MQQT_REMOTE, "远程队列");
    }

    private String value;

    private QueueConstant(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }

}
