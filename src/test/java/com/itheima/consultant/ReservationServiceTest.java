package com.itheima.consultant;

import com.itheima.consultant.mapper.ReservationMapper;
import com.itheima.consultant.pojo.Reservation;
import com.itheima.consultant.service.ReservationService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("预约服务单元测试(兼容旧用例)")
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("测试添加预约 - 正常调用Mapper")
    void testInsert() {
        Reservation reservation = new Reservation(null, "小王", "13800000001", LocalDateTime.now(), "上海");
        reservationService.insert(reservation);
        verify(reservationMapper).insert(reservation);
    }

    @Test
    @DisplayName("测试按手机号查询预约 - 返回结果列表")
    void testFindByPhone() {
        String phone = "13800000001";
        when(reservationMapper.findByPhone(phone))
                .thenReturn(List.of(new Reservation(1L, "小王", phone, LocalDateTime.now(), "上海")));

        List<Reservation> reservation = reservationService.findByPhone(phone);

        assertNotNull(reservation);
        assertEquals(1, reservation.size());
        verify(reservationMapper).findByPhone(phone);
    }
}
