package com.hspedu.furns.test;

import com.hspedu.furns.entity.Furn;
import com.hspedu.furns.entity.Page;
import com.hspedu.furns.service.FurnService;
import com.hspedu.furns.service.impl.FurnServiceImpl;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

public class FurnServiceTest {
    private FurnService furnService = new FurnServiceImpl();

    @Test
    public void queryFurns() {
        List<Furn> furns = furnService.queryFurns();
        for (Furn furn : furns) {
            System.out.println(furn);
        }
    }

    @Test
    public void addFurn() {
        Furn furn = new Furn(null, "沙发~", "顺平家居",
                new BigDecimal(999.99), 100, 10,
                "/assets/images/product-image/default.jpg");
        System.out.println(furnService.addFurn(furn));
    }

    @Test
    public void deleteFurnById() {
        int i = furnService.deleteFurn(30);
        System.out.println(i);
    }

    @Test
    public void queryFurnById() {
        Furn furn = furnService.queryFurnById(8);
        System.out.println(furn);
    }

    @Test
    public void updateFurnById() {
        Furn furn = new Furn(8, "沙发~!", "顺平家居",
                new BigDecimal(999.99), 100, 10,
                "/assets/images/product-image/default.jpg");
        System.out.println(furnService.updateFurn(furn));
    }

    @Test
    public void page() {
        Page<Furn> page = furnService.page(2, 2);

        // 对象比较复杂，可以通过debug
        System.out.println(page);
    }

    @Test
    public void pageByName() {
        Page<Furn> page = furnService.pageByName(1, 3, "test");
        System.out.println(page);
    }
}
