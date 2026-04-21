package com.itheima.consultant.mapper;

import com.itheima.consultant.ConsultantApplication;
import com.itheima.consultant.pojo.Shop;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.test.annotation.Rollback;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConsultantApplication.class)
@Transactional(rollbackFor = Exception.class)
@Rollback
@DisplayName("商家Mapper集成测试")
class ShopMapperIntegrationTest {

    @Resource
    private ShopMapper shopMapper;

    @Test
    @DisplayName("根据商家名称查询 - 商家存在返回详情")
    void testFindShop_Exists_ReturnShop() {
        // Given
        String shopName = "星聚会KTV(拱墅区万达店)";

        // When
        Shop result = shopMapper.findShop(shopName);

        // Then
        assertNotNull(result);
    }
}
