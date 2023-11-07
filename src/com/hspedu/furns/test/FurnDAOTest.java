package com.hspedu.furns.test;

import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.dao.impl.FurnDAOImpl;
import com.hspedu.furns.entity.Furn;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class FurnDAOTest {
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Test
    public void queryFurns() {
        List<Furn> furns = furnDAO.queryFurns();
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }

//    @Test
//    public void addFurn() {
//        Furn furn = new Furn(null, "沙发", "顺平家居",
//                new BigDecimal(999.99), 100, 10,
//                "/assets/images/product-image/default.jpg");
//        System.out.println(furnDAO.addFurn(furn));
//    }

    @Test
    public void getTotalRow() {
        System.out.println(furnDAO.getTotalRow());
    }

    @Test
    public void getPageItems() {
        List<Furn> pageItems = furnDAO.getPageItems(1, 3);
        for (Furn furn : pageItems) {
            System.out.println(furn);
        }
    }

    @Test
    public void getTotalRowByName() {
        System.out.println(furnDAO.getTotalRowByName("test"));
    }

    @Test
    public void getPageItemsByName() {
        List<Furn> pageItemsList = furnDAO.getPageItemsByName(0, 10, "test");
        for (Furn furn : pageItemsList) {
            System.out.println(furn);
        }
    }
}
