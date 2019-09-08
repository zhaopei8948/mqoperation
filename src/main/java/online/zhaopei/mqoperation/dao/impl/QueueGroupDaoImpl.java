package online.zhaopei.mqoperation.dao.impl;

import online.zhaopei.mqoperation.dao.QueueGroupDao;
import online.zhaopei.mqoperation.domain.Queue;
import online.zhaopei.mqoperation.domain.QueueGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QueueGroupDaoImpl implements QueueGroupDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<QueueGroup> rowMapper = new RowMapper<QueueGroup>() {
        @Override
        public QueueGroup mapRow(ResultSet rs, int rowNum) throws SQLException {
            QueueGroup queueGroup = new QueueGroup();
            queueGroup.setId(rs.getLong("id"));
            queueGroup.setName(rs.getString("name"));
            queueGroup.setDescribe(rs.getString("describe"));
            return queueGroup;
        }
    };

    @Override
    public int insert(QueueGroup queueGroup) {
        return 0;
    }

    @Override
    public long count(QueueGroup queueGroup) {
        return 0;
    }

    @Override
    public int delete(QueueGroup queueGroup) {
        return 0;
    }

    @Override
    public int deleteById(long id) {
        return 0;
    }

    @Override
    public int update(QueueGroup queueGroup) {
        return 0;
    }

    @Override
    public List<QueueGroup> select(QueueGroup queueGroup) {
        return null;
    }

    @Override
    public QueueGroup selectById(long id) {
        return null;
    }
}
