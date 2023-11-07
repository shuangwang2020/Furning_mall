package com.hspedu.furns.entity;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Set;

/**
 * Cart就是购物车，包含多个CartItem
 */
public class Cart {
    // 包含多个CartItem,使用HashMap
    private HashMap<Integer, CartItem> items = new HashMap();

    public void clear() {
        items.clear();
    }

    public void delItem(int id) {
        items.remove(id);
    }

    /**
     * 修改指定的CartItem的数量和总价 根据传入的 id和count
     *
     * @param id
     * @param count
     */
    public void updateCount(int id, int count) {
        CartItem item = items.get(id);
        if (null != item) {
            // 更新数量
            item.setCount(count); // 将来传进去的这个值有可能会被处理过 使用get来获取
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }

    //    private Integer totalCount = 0; 没有意义 ${sessionScope.cart.totalCount}
//    本质就是调用cart对象getTotalCount()
    public int getTotalCount() {
        int totalCount = 0;
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            totalCount += items.get(id).getCount();
        }
        return totalCount;
    }

    public BigDecimal getCartTotalPrice() {
        BigDecimal cartTotalPrice = new BigDecimal(0);

        // 遍历items
        Set<Integer> keys = items.keySet();
        for (Integer id : keys) {
            CartItem item = items.get(id);

            // 一定要把add后的值，重新赋给cartTotalPrice，这样才是累加
            cartTotalPrice = cartTotalPrice.add(item.getTotalPrice());
        }
        return cartTotalPrice;
    }

    public HashMap<Integer, CartItem> getItems() {
        return items;
    }

    // 添加家居到cart
    public void addItem(CartItem cartItem) {
        CartItem item = items.get(cartItem.getId());
        if (null == item) {
            items.put(cartItem.getId(), cartItem);
        } else {
            item.setCount(item.getCount() + 1);
//            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
            item.setTotalPrice(item.getTotalPrice().add(item.getPrice()));
        }
    }

    @Override
    public String toString() {
        return "Cart{" +
                "items=" + items +
                '}';
    }
}
