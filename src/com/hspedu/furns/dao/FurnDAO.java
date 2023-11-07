package com.hspedu.furns.dao;

import com.hspedu.furns.entity.Furn;

import java.util.List;

public interface FurnDAO {
    public List<Furn> queryFurns();

    public int addFurn(Furn furn);

    public int deleteFurnById(int id);

    public Furn queryFurnById(int id);

    public int updateFurn(Furn furn);

    // Page模型直接从数据库中获取
    public int getTotalRow();

    // 获取当前页要显示的数据
    public List<Furn> getPageItems(int begin, int pageSize);

    public int getTotalRowByName(String name);

    public List<Furn> getPageItemsByName(int begin, int pageSize, String name);
}
