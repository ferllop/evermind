package com.ferllop.evermind.repositories.mappers;

import com.ferllop.evermind.models.Model;

import java.util.Map;

public interface ModelMapper<T extends Model> {
    T execute(String id, Map<String, Object> map);
    Map<String, Object> execute(T model);
}
