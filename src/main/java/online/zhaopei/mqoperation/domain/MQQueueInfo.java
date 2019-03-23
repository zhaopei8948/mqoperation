package online.zhaopei.mqoperation.domain;

import lombok.Data;

@Data
public class MQQueueInfo {

    private int currentDepth;

    private boolean inhibitGet;

    private boolean inhibitPut;

    private int maximumDepth;

    private int maximumMessageLength;

    private int openInputCount;

    private int openOutputCount;

    private int queueType;
}
