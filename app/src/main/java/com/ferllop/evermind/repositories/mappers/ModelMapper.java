package com.ferllop.evermind.repositories.mappers;

import com.ferllop.evermind.models.Model;

import java.util.Map;

public interface ModelMapper<T extends Model> {
    T execute(String id, Map map);
    Map<String, Object> execute(T model);
}
