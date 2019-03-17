package online.zhaopei.mqoperation.service.impl;

import online.zhaopei.mqoperation.dao.QueueDao;
import online.zhaopei.mqoperation.dao.QueueManagerDao;
import online.zhaopei.mqoperation.domain.Queue;
import online.zhaopei.mqoperation.domain.QueueManager;
import online.zhaopei.mqoperation.service.QueueManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class QueueManagerServiceImpl implements QueueManagerService {

    @Autowired
    private QueueManagerDao queueManagerDao;

    @Autowired
    private QueueDao queueDao;

    @Override
    public void insert(QueueManager queueManager) {
        this.queueManagerDao.insert(queueManager);
    }

    @Override
    public long count(QueueManager queueManager) {
        return this.queueManagerDao.count(queueManager);
    }

    @Override
    public void delete(QueueManager queueManager) {
        this.queueManagerDao.delete(queueManager);
    }

    @Override
    public void deleteById(long id) {
        this.queueDao.delete(new Queue() {{
            this.setManagerId(id);
        }});
        this.queueManagerDao.deleteById(id);
    }

    @Override
    public void update(QueueManager queueManager) {
        this.queueManagerDao.update(queueManager);
    }

    @Override
    public List<QueueManager> select(QueueManager queueManager) {
        return this.queueManagerDao.select(queueManager);
    }

    @Override
    public QueueManager selectById(long id) {
        return this.queueManagerDao.selectById(id);
    }
}
