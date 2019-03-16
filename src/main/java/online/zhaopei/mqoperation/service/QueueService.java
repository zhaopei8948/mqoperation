package online.zhaopei.mqoperation.service;

import online.zhaopei.mqoperation.domain.Queue;

public interface QueueService extends BaseService<Queue> {

    Queue getQueueAndManager(long id);
}
