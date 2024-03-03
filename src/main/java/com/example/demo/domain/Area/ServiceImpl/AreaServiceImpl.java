package com.example.demo.domain.Area.ServiceImpl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.example.demo.domain.Area.AreaService;
import com.example.demo.domain.Area.AreaRepository;
import com.example.demo.domain.Area.Area;
import java.util.List;

@RequiredArgsConstructor//final 생성자 생성
@Service
public class AreaServiceImpl implements AreaService {

    private final AreaRepository areaRepository;

    @Override
    public List<Area> searchAreaNames(String areaName) {
        System.out.println("Area name: " + areaName); //데이터 출력 확인
        return areaRepository.findByAreaNameStartingWith(areaName);
    }

}