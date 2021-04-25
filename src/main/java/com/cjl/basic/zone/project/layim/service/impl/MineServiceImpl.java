//package com.cjl.basic.zone.project.layim.service.impl;
//
//import com.cjl.basic.zone.project.layim.mapper.MineMapper;
//import com.cjl.basic.zone.project.layim.entity.Mine;
//import com.cjl.basic.zone.project.layim.service.MineService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
///**
// * @Author chen
// * @Date 2020/4/7 21:27
// * @Version 1.0
// */
//@Service
//public class MineServiceImpl implements MineService {
//
//    @Autowired
//    private MineMapper mineMapper;
//
//    @Override
//    public boolean upUserMine(Mine mine) {
//        return mineMapper.upUserMine(mine);
//    }
//
//    @Override
//    public Mine getUserInfo(String userId) {
//        return mineMapper.getUserInfo(userId);
//    }
//
//    @Override
//    public List<Mine> getMineList() {
//        return mineMapper.getMineList();
//    }
//}
