package online.zhaopei.mqoperation.dao.impl;

import online.zhaopei.mqoperation.dao.QueueManagerDao;
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
public class QueueManagerDaoImpl implements QueueManagerDao {

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private RowMapper<QueueManager> rowMapper = new RowMapper<QueueManager>() {
        @Override
        public QueueManager mapRow(ResultSet rs, int rowNum) throws SQLException {
            QueueManager queueManager = new QueueManager();
            queueManager.setId(rs.getLong("id"));
            queueManager.setName(rs.getString("name"));
            queueManager.setDescribe(rs.getString("describe"));
            queueManager.setIp(rs.getString("ip"));
            queueManager.setPort(rs.getInt("port"));
            queueManager.setCcsid(rs.getInt("ccsid"));
            queueManager.setChannel(rs.getString("channel"));
            return queueManager;
        }
    };

    private String buildConditionSql(QueueManager queueManager, boolean vague) {
        StringBuilder conditionSql = new StringBuilder();
        if(null == queueManager) {
            return "";
        }

        if (null != queueManager.getId()) {
            conditionSql.append("and id = :id");
        }

        if (!StringUtils.isEmpty(queueManager.getName())) {
            if (vague) {
                conditionSql.append("and name like ':name%'");
            } else {
                conditionSql.append("and name = :name");
            }
        }

        if (!StringUtils.isEmpty(queueManager.getDescribe())) {
            if (vague) {
                conditionSql.append("and describe like ':describe%'");
            } else {
                conditionSql.append("and describe = :describe");
            }
        }

        return conditionSql.toString();
    }

    @Override
    public int insert(QueueManager queueManager) {
        String sql = "insert into queue_manager(name, describe, ip, port, ccsid, channel) values(:name, :describe, :ip, :port, :ccsid, :channel)";
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queueManager);
        return this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public long count(QueueManager queueManager) {
        StringBuilder sql = new StringBuilder("select count(id) from queue_manager where 1 = 1 ");
        sql.append(this.buildConditionSql(queueManager, false));
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queueManager);
        return this.namedParameterJdbcTemplate.queryForObject(sql.toString(), namedParameters, Long.class);
    }

    @Override
    public int delete(QueueManager queueManager) {
        StringBuilder sql = new StringBuilder("delete from queue_manager where ");
        sql.append(this.buildConditionSql(queueManager, false));
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queueManager);
        return this.namedParameterJdbcTemplate.update(sql.toString(), namedParameters);
    }

    @Override
    public int deleteById(long id) {
        String sql = "delete from queue_manager where id = ?";
        return this.namedParameterJdbcTemplate.getJdbcOperations().update(sql, id);
    }

    @Override
    public int update(QueueManager queueManager) {
        String sql = new String("update queue_manager set name = :name, describe = :describe, ip = :ip, port = :port, ccsid = :ccsid, channel = :channel where id = :id");
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queueManager);
        return this.namedParameterJdbcTemplate.update(sql, namedParameters);
    }

    @Override
    public List<QueueManager> select(QueueManager queueManager) {
        StringBuilder sql = new StringBuilder("select * from queue_manager where 1 = 1 ");
        sql.append(this.buildConditionSql(queueManager, false));
        SqlParameterSource namedParameters = new BeanPropertySqlParameterSource(queueManager);
        return this.namedParameterJdbcTemplate.query(sql.toString(), namedParameters, this.rowMapper);
    }

    @Override
    public QueueManager selectById(long id) {
        String sql = "select * from queue_manager where id = ?";
        return this.namedParameterJdbcTemplate.getJdbcOperations().queryForObject(sql, this.rowMapper, id);
    }
}
