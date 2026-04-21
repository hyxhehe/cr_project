package com.itheima.consultant.mapper;

import com.itheima.consultant.ConsultantApplication;
import com.itheima.consultant.pojo.Reservation;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ConsultantApplication.class)
@Transactional(rollbackFor = Exception.class)
@Rollback
@DisplayName("预约Mapper集成测试")
class ReservationMapperIntegrationTest {

    @Resource
    private ReservationMapper reservationMapper;

    @Test
    @DisplayName("新增预约并按手机号查询 - 成功")
    void testInsertAndFindByPhone_Success() {
        // Given
        String phone = "13900000001";
        Reservation reservation = new Reservation(
                null,
                "李四",
                phone,
                LocalDateTime.now().plusDays(1),
                "星聚会KTV(拱墅区万达店)"
        );

        // When
        reservationMapper.insert(reservation);
        List<Reservation> result = reservationMapper.findByPhone(phone);

        // Then
        assertNotNull(result);
    }
}
