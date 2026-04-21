package com.itheima.consultant.service;

import com.itheima.consultant.mapper.ShopMapper;
import com.itheima.consultant.mapper.VoucherMapper;
import com.itheima.consultant.mapper.VoucherOrderMapper;
import com.itheima.consultant.pojo.Shop;
import com.itheima.consultant.pojo.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class VoucherService {

    @Autowired
    private VoucherMapper voucherMapper;

    @Autowired
    private VoucherOrderMapper voucherOrderMapper;

    @Autowired
    private ShopMapper shopMapper;

    //1.查询商家信息
    public List<Voucher> findVoucherByShopName(String shopName) {
        Shop shop = shopMapper.findShop(shopName);
        if (shop == null || shop.getId() == null) {
            return Collections.emptyList();
        }
        return voucherMapper.findVoucherByShopId(shop.getId());
    }

    //2.查询用户拥有的优惠券
    public List<Voucher> findVoucherByUserPhone(String userPhone) {
        List<Long> voucherIds = voucherOrderMapper.findByPhone(userPhone);
        if (voucherIds == null || voucherIds.isEmpty()) {
            return Collections.emptyList();
        }
        List<Voucher> vouchers = new ArrayList<>();
        for (Long voucherId : voucherIds) {
            Voucher voucher = voucherMapper.findByIds(voucherId);
            if (voucher != null) {
                vouchers.add(voucher);
            }
        }
        return vouchers;
    }
}