package online.zhaopei.mqoperation.dao;

import java.util.List;

public interface BaseDao <T> {

    int insert(T t);

    long count(T t);

    int delete(T t);

    int deleteById(long id);

    int update(T t);

    List<T> select(T t);

    T selectById(long id);
}
