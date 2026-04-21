package com.itheima.consultant.service;

import com.itheima.consultant.mapper.ReservationMapper;
import com.itheima.consultant.pojo.Reservation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("预约服务单元测试")
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {

    @Mock
    private ReservationMapper reservationMapper;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    @DisplayName("新增预约信息 - 成功调用Mapper")
    void testInsert_Success() {
        // Given
        Reservation reservation = new Reservation(null, "张三", "13800138000", LocalDateTime.now().plusDays(1), "星聚会KTV");

        // When
        reservationService.insert(reservation);

        // Then
        verify(reservationMapper).insert(reservation);
    }

    @Test
    @DisplayName("根据手机号查询预约信息 - 有数据返回列表")
    void testFindByPhone_HasData_ReturnList() {
        // Given
        String phone = "13800138000";
        when(reservationMapper.findByPhone(phone))
                .thenReturn(List.of(new Reservation(1L, "张三", phone, LocalDateTime.now(), "星聚会KTV")));

        // When
        List<Reservation> result = reservationService.findByPhone(phone);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(reservationMapper).findByPhone(phone);
    }

    @Test
    @DisplayName("根据手机号查询预约信息 - 无数据返回空列表")
    void testFindByPhone_NoData_ReturnEmptyList() {
        // Given
        String phone = "13900000000";
        when(reservationMapper.findByPhone(phone)).thenReturn(Collections.emptyList());

        // When
        List<Reservation> result = reservationService.findByPhone(phone);

        // Then
        assertNotNull(result);
        assertEquals(0, result.size());
        verify(reservationMapper).findByPhone(phone);
    }
}
