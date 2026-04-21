package com.itheima.consultant.controller;

import com.itheima.consultant.aiservice.ConsultantService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("聊天控制器单元测试")
@ExtendWith(MockitoExtension.class)
class ChatControllerTest {

    @Mock
    private ConsultantService consultantService;

    @InjectMocks
    private ChatController chatController;

    @Test
    @DisplayName("聊天接口 - 参数完整返回流式内容")
    void testChat_ValidParams_ReturnFlux() {
        // Given
        String memoryId = "10001";
        String message = "你好";
        when(consultantService.chat(memoryId, message)).thenReturn(Flux.just("你", "好"));

        // When
        Flux<String> result = chatController.chat(memoryId, message);
        List<String> content = result.collectList().block();

        // Then
        assertNotNull(content);
        assertEquals(2, content.size());
        assertEquals("你", content.get(0));
        assertEquals("好", content.get(1));
        verify(consultantService).chat(memoryId, message);
    }

    @Test
    @DisplayName("聊天接口 - 参数为空时按当前逻辑透传调用")
    void testChat_EmptyParams_DelegateService() {
        // Given
        when(consultantService.chat("", "")).thenReturn(Flux.just("ok"));

        // When
        Flux<String> result = chatController.chat("", "");
        List<String> content = result.collectList().block();

        // Then
        assertNotNull(content);
        assertEquals(1, content.size());
        verify(consultantService).chat("", "");
    }
}
