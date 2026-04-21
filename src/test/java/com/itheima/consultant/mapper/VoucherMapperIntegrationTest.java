package com.itheima.consultant.mapper;

import com.itheima.consultant.ConsultantApplication;
import com.itheima.consultant.pojo.Voucher;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConsultantApplication.class)
@Transactional(rollbackFor = Exception.class)
@Rollback
@DisplayName("优惠券Mapper集成测试")
class VoucherMapperIntegrationTest {

    @Resource
    private VoucherMapper voucherMapper;

    @Test
    @DisplayName("根据商家ID查询优惠券 - 返回优惠券列表")
    void testFindVoucherByShopId_HasData_ReturnList() {
        // Given
        Long shopId = 1L;

        // When
        List<Voucher> result = voucherMapper.findVoucherByShopId(shopId);

        // Then
        assertNotNull(result);
    }

    @Test
    @DisplayName("根据优惠券ID查询 - 存在返回详情")
    void testFindByIds_Exists_ReturnVoucher() {
        // Given
        Long voucherId = 10L;

        // When
        Voucher result = voucherMapper.findByIds(voucherId);

        // Then
        assertNotNull(result);
    }
}
