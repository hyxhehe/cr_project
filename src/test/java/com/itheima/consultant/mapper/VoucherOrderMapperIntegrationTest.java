package com.itheima.consultant.mapper;

import com.itheima.consultant.ConsultantApplication;
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
@DisplayName("优惠券订单Mapper集成测试")
class VoucherOrderMapperIntegrationTest {

    @Resource
    private VoucherOrderMapper voucherOrderMapper;

    @Test
    @DisplayName("根据手机号查询优惠券ID - 返回ID列表")
    void testFindByPhone_HasData_ReturnVoucherIds() {
        // Given
        String phone = "17620368888";

        // When
        List<Long> result = voucherOrderMapper.findByPhone(phone);

        // Then
        assertNotNull(result);
    }
}
