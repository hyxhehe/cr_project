package com.itheima.consultant.service;

import com.itheima.consultant.mapper.ShopMapper;
import com.itheima.consultant.mapper.VoucherMapper;
import com.itheima.consultant.mapper.VoucherOrderMapper;
import com.itheima.consultant.pojo.Shop;
import com.itheima.consultant.pojo.Voucher;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("优惠券服务单元测试")
@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    private VoucherMapper voucherMapper;

    @Mock
    private VoucherOrderMapper voucherOrderMapper;

    @Mock
    private ShopMapper shopMapper;

    @InjectMocks
    private VoucherService voucherService;

    @Test
    @DisplayName("根据商家名称查询优惠券 - 商家存在返回优惠券列表")
    void testFindVoucherByShopName_ShopExists_ReturnList() {
        // Given
        String shopName = "星聚会KTV(拱墅区万达店)";
        Shop shop = new Shop();
        shop.setId(1L);
        when(shopMapper.findShop(shopName)).thenReturn(shop);
        when(voucherMapper.findVoucherByShopId(1L)).thenReturn(List.of(new Voucher().setId(10L)));

        // When
        List<Voucher> result = voucherService.findVoucherByShopName(shopName);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(shopMapper).findShop(shopName);
        verify(voucherMapper).findVoucherByShopId(1L);
    }

    @Test
    @DisplayName("根据商家名称查询优惠券 - 商家不存在返回空列表")
    void testFindVoucherByShopName_ShopNotExists_ReturnEmptyList() {
        // Given
        String shopName = "不存在商家";
        when(shopMapper.findShop(shopName)).thenReturn(null);

        // When
        List<Voucher> result = voucherService.findVoucherByShopName(shopName);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(shopMapper).findShop(shopName);
        verify(voucherMapper, never()).findVoucherByShopId(org.mockito.ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("根据用户手机号查询优惠券 - 用户有券返回明细")
    void testFindVoucherByUserPhone_HasVouchers_ReturnDetails() {
        // Given
        String phone = "17620368888";
        when(voucherOrderMapper.findByPhone(phone)).thenReturn(List.of(10L, 11L));
        when(voucherMapper.findByIds(10L)).thenReturn(new Voucher().setId(10L));
        when(voucherMapper.findByIds(11L)).thenReturn(new Voucher().setId(11L));

        // When
        List<Voucher> result = voucherService.findVoucherByUserPhone(phone);

        // Then
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(voucherOrderMapper).findByPhone(phone);
        verify(voucherMapper).findByIds(10L);
        verify(voucherMapper).findByIds(11L);
    }

    @Test
    @DisplayName("根据用户手机号查询优惠券 - 用户无券返回空列表")
    void testFindVoucherByUserPhone_NoVouchers_ReturnEmptyList() {
        // Given
        String phone = "13800138000";
        when(voucherOrderMapper.findByPhone(phone)).thenReturn(Collections.emptyList());

        // When
        List<Voucher> result = voucherService.findVoucherByUserPhone(phone);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(voucherOrderMapper).findByPhone(phone);
        verify(voucherMapper, never()).findByIds(org.mockito.ArgumentMatchers.anyLong());
    }
}
