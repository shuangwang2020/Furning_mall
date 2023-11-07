package com.hspedu.furns.service.impl;

import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.dao.impl.FurnDAOImpl;
import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;

import java.util.List;

public class FurnServiceImpl implements FurnService {
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Override
    public List<Furn> queryFurns() {
        return furnDAO.queryFurns();
    }

    @Override
    public int addFurn(Furn furn) {
        return furnDAO.addFurn(furn);
    }

    @Override
    public int deleteFurn(int id) {
        return furnDAO.deleteFurnById(id);
    }

    @Override
    public Furn queryFurnById(int id) {
        return furnDAO.queryFurnById(id);
    }

    @Override
    public int updateFurn(Furn furn) {
        return furnDAO.updateFurn(furn);
    }

    @Override
    public Page<Furn> page(int pageNo, int pageSize) {
        Page<Furn> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int totalRow = furnDAO.getTotalRow();
        page.setTotalRow(totalRow);
        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            pageTotalCount += 1;
        }
        page.setPageTotalCount(pageTotalCount);

        int begin = (pageNo - 1) * pageSize;
        List<Furn> pageItems = furnDAO.getPageItems(begin, pageSize);
        page.setItems(pageItems);
        return page;
    }

    @Override
    public Page<Furn> pageByName(int pageNo, int pageSize, String name) {
        Page<Furn> page = new Page<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        int totalRow = furnDAO.getTotalRowByName(name);
        page.setTotalRow(totalRow);
        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize > 0) {
            pageTotalCount += 1;
        }
        page.setPageTotalCount(pageTotalCount);

        int begin = (pageNo - 1) * pageSize;
        List<Furn> pageItems = furnDAO.getPageItemsByName(begin, pageSize, name);
        page.setItems(pageItems);
        return page;
    }
}
