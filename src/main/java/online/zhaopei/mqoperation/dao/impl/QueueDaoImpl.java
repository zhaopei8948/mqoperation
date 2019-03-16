package online.zhaopei.mqoperation.dao.impl;

import online.zhaopei.mqoperation.dao.QueueDao;
import online.zhaopei.mqoperation.domain.Queue;
import online.zhaopei.mqoperation.domain.QueueManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class QueueDaoImpl implements QueueDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<Queue> rowMapper = new RowMapper<Queue>() {
        @Override
        public Queue mapRow(ResultSet rs, int rowNum) throws SQLException {
            Queue queue = new Queue();
            queue.setId(rs.getLong("id"));
            queue.setName(rs.getString("name"));
            queue.setDescribe(rs.getString("describe"));
            queue.setManagerId(rs.getLong("manager_id"));
            return queue;
        }
    };

    private String buildConditionSql(Queue queue, boolean vague) {
        StringBuilder conditionSql = new StringBuilder();
        if(null == queue) {
            return "";
        }

        if (null != queue.getId()) {
            conditionSql.append("and id = :id");
        }

        if (!StringUtils.isEmpty(queue.getName())) {
            if (vague) {
                conditionSql.append("and name like ':name%'");
            } else {
                conditionSql.append("and name = :name");
            }
        }

        if (!StringUtils.isEmpty(queue.getDescribe())) {
            if (vague) {
                conditionSql.append("and describe like ':describe%'");
            } else {
                conditionSql.append("and describe = :describe");
            }
        }

        if (null != queue.getManagerId()) {
            conditionSql.append("and manager_id = :managerId");
        }
        return conditionSql.toString();
    }

    @Override
    public int insert(Queue queue) {
        String sql = "insert into queue(name, describe, manager_id) values (:name, :describe, :managerId)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queue);
        return this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public long count(Queue queue) {
        StringBuilder sql = new StringBuilder("select count(id) from queue where 1 = 1 ");
        sql.append(this.buildConditionSql(queue, false));
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queue);
        return this.namedParameterJdbcTemplate.queryForObject(sql.toString(), namedParameters, Long.class);
    }

    @Override
    public int delete(Queue queue) {
        StringBuilder sql = new StringBuilder("delete from queue where ");
        sql.append(this.buildConditionSql(queue, false));
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queue);
        return this.namedParameterJdbcTemplate.update(sql.toString(), namedParameters);
    }

    @Override
    public int deleteById(long id) {
        String sql = "delete from queue where id = ?";
        return this.namedParameterJdbcTemplate.getJdbcOperations().update(sql, id);
    }

    @Override
    public int update(Queue queue) {
        String sql = new String("update queue set name = :name, describe = :describe where id = :id");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queue);
        return this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public List<Queue> select(Queue queue) {
        StringBuilder sql = new StringBuilder("select * from queue where 1 = 1 ");
        sql.append(this.buildConditionSql(queue, false));
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queue);
        return this.namedParameterJdbcTemplate.query(sql.toString(), namedParameters, this.rowMapper);
    }

    @Override
    public Queue selectById(long id) {
        String sql = "select * from queue where id = ?";
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, this.rowMapper, id);
    }

    @Override
    public Queue getQueueAndManager(long id) {
        StringBuilder sql = new StringBuilder("select t.id as qid, t.name as qname, t.describe as qdescribe");
        sql.append(", t1.id as qmid, t1.name as qmname, t1.describe as qmdescribe, t1.ip, t1.port, t1.ccsid, t1.channel ");
        sql.append("from queue t inner join queue_manager t1 on t.manager_id = t1.id where t.id = ?");
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql.toString(), new RowMapper<Queue>() {

            @Override
            public Queue mapRow(ResultSet rs, int rowNum) throws SQLException {
                Queue queue = new Queue();
                queue.setId(rs.getLong("qid"));
                queue.setName(rs.getString("qname"));
                queue.setDescribe(rs.getString("qdescribe"));
                queue.setManagerId(rs.getLong("qmid"));
                QueueManager queueManager = new QueueManager();
                queueManager.setId(rs.getLong("qmid"));
                queueManager.setName(rs.getString("qmname"));
                queueManager.setDescribe(rs.getString("qmdescribe"));
                queueManager.setIp(rs.getString("ip"));
                queueManager.setPort(rs.getInt("port"));
                queueManager.setCcsid(rs.getInt("ccsid"));
                queueManager.setChannel(rs.getString("channel"));
                queue.setQueueManager(queueManager);
                return queue;
            }
        }, id);
    }
}
