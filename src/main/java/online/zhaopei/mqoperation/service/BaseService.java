package online.zhaopei.mqoperation.service;

import java.util.List;

public interface BaseService <T> {

    void insert(T t);

    long count(T t);

    void delete(T t);

    void deleteById(long id);

    void update(T t);

    List<T> select(T t);

    T selectById(long id);
}
