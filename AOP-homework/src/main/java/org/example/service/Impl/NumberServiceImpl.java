package org.example.service.Impl;

import org.example.service.NumberService;
import org.springframework.stereotype.Service;

@Service(value = "service")
public class NumberServiceImpl implements NumberService {
    @Override
    public Integer addNumber(Integer n1, Integer n2, Integer n3) {
        return n1+n2+n3;
    }
}
