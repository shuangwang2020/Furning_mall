package com.hspedu.furns.dao.impl;

import com.hspedu.furns.dao.BasicDAO;
import com.hspedu.furns.dao.FurnDAO;
import com.hspedu.furns.entity.Furn;

import java.util.List;

public class FurnDAOImpl extends BasicDAO<Furn> implements FurnDAO {
    @Override
    public List<Furn> queryFurns() {
        // 反射是根据查回来的字段去找对应的属性
        String sql = "SELECT `id`, `name`, `maker`, `price`, `sales`, `stock`, `img_path` imgPath " +
                "FROM furn";
        return queryMulti(sql, Furn.class);
    }

    @Override
    public int addFurn(Furn furn) {
        String sql = "INSERT INTO furn(`id` , `name` , `maker` , `price` , `sales` , `stock` , `img_path`) " +
                "VALUES(NULL , ? , ?, ?, ?, ?, ?)";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(),
                furn.getSales(), furn.getStock(), furn.getImgPath());
    }

    @Override
    public int deleteFurnById(int id) {
        String sql = "DELETE FROM furn WHERE id = ?";
        return update(sql, id);
    }

    @Override
    public Furn queryFurnById(int id) {
        String sql = "SELECT `id`, `name`, `maker`, `price`, `sales`, `stock`, `img_path` imgPath " +
                "FROM furn WHERE id = ?";
        return querySingle(sql, Furn.class, id);
    }


    /**
     * 根据id更新
     *
     * @param furn
     * @return
     */
    @Override
    public int updateFurn(Furn furn) {
        String sql = "UPDATEXX `furn` SET `name` = ? , `maker` = ?, `price` = ?, " +
                "`sales` = ?, `stock` = ?, `img_path` = ? " +
                "WHERE id = ?";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(), furn.getSales(),
                furn.getStock(), furn.getImgPath(), furn.getId());
    }

    @Override
    public int getTotalRow() {
        String sql = "SELECT COUNT(*) FROM `furn`";

//        return (Integer) queryScalar(sql);
        // 所有包装类都可以转成Number
        return ((Number) queryScalar(sql)).intValue();
    }

    @Override
    public List<Furn> getPageItems(int begin, int pageSize) {
        String sql = "SELECT `id`, `name`, `maker`, `price`, `sales`, `stock`, `img_path` imgPath " +
                "FROM furn LIMIT ?, ?";
        return queryMulti(sql, Furn.class, begin, pageSize);
    }

    @Override
    public int getTotalRowByName(String name) {
        String sql = "SELECT COUNT(*) FROM `furn` WHERE `name` like ?";

        // 在名字里只要含有沙发
        return ((Number) queryScalar(sql, "%" + name + "%")).intValue();
//        return 0;
    }

    @Override
    public List<Furn> getPageItemsByName(int begin, int pageSize, String name) {
        String sql = "SELECT `id`, `name`, `maker`, `price`, `sales`, `stock`, `img_path` imgPath " +
                "FROM furn WHERE `name` like ? LIMIT ?, ?";
        return queryMulti(sql, Furn.class, "%" + name + "%", begin, pageSize);
    }
}
