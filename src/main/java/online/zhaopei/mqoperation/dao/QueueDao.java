package online.zhaopei.mqoperation.dao;

import online.zhaopei.mqoperation.domain.Queue;

import java.util.List;

public interface QueueDao extends BaseDao<Queue> {

    Queue getQueueAndManager(long id);
}
