package itcode.service;

import itcode.entity.Result;

import java.util.Map;

public interface OrderService {

    Result submit(Map order);
}
