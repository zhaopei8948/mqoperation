package online.zhaopei.mqoperation.service.impl;

import online.zhaopei.mqoperation.dao.QueueDao;
import online.zhaopei.mqoperation.domain.Queue;
import online.zhaopei.mqoperation.service.QueueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QueueServiceImpl implements QueueService {

    @Autowired
    private QueueDao queueDao;

    @Override
    public void insert(Queue queue) {
        this.queueDao.insert(queue);
    }

    @Override
    public long count(Queue queue) {
        return this.queueDao.count(queue);
    }

    @Override
    public void delete(Queue queue) {
        this.queueDao.delete(queue);
    }

    @Override
    public void deleteById(long id) {
        this.queueDao.deleteById(id);
    }

    @Override
    public void update(Queue queue) {
        this.queueDao.update(queue);
    }

    @Override
    public List<Queue> select(Queue queue) {
        return this.queueDao.select(queue);
    }

    @Override
    public Queue selectById(long id) {
        return this.queueDao.selectById(id);
    }

    @Override
    public Queue getQueueAndManager(long id) {
        return this.queueDao.getQueueAndManager(id);
    }
}
