package com.itheima.consultant.service;

import com.itheima.consultant.mapper.ShopMapper;
import com.itheima.consultant.pojo.Shop;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("商家服务单元测试")
@ExtendWith(MockitoExtension.class)
class ShopServiceTest {

    @Mock
    private ShopMapper shopMapper;

    @InjectMocks
    private ShopService shopService;

    @Test
    @DisplayName("查询商家信息 - 商家存在返回详情")
    void testFindShop_ShopExists_ReturnShop() {
        // Given
        String shopName = "星聚会KTV(拱墅区万达店)";
        Shop expected = new Shop();
        expected.setId(1L);
        expected.setName(shopName);
        when(shopMapper.findShop(shopName)).thenReturn(expected);

        // When
        Shop result = shopService.findShop(shopName);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(shopName, result.getName());
        verify(shopMapper).findShop(shopName);
    }

    @Test
    @DisplayName("查询商家信息 - 商家不存在返回空")
    void testFindShop_ShopNotExists_ReturnNull() {
        // Given
        String shopName = "不存在的商家";
        when(shopMapper.findShop(shopName)).thenReturn(null);

        // When
        Shop result = shopService.findShop(shopName);

        // Then
        assertNull(result);
        verify(shopMapper).findShop(shopName);
    }
}
