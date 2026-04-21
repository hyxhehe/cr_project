package com.itheima.consultant.repository;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.ChatMessageSerializer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Redis会话存储单元测试")
@ExtendWith(MockitoExtension.class)
class RedisChatMemoryStoreTest {

    @Mock
    private StringRedisTemplate redisTemplate;

    @Mock
    private ValueOperations<String, String> valueOperations;

    @Test
    @DisplayName("更新会话消息 - 成功写入Redis并设置过期时间")
    void testUpdateMessages_Success() {
        // Given
        RedisChatMemoryStore store = createStoreWithMock();
        String memoryId = "m-001";
        List<ChatMessage> messages = List.of(AiMessage.from("你好"));
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        // When
        store.updateMessages(memoryId, messages);

        // Then
        verify(redisTemplate).opsForValue();
        verify(valueOperations).set(memoryId, ChatMessageSerializer.messagesToJson(messages), Duration.ofDays(1));
    }

    @Test
    @DisplayName("查询会话消息 - Redis存在数据返回反序列化结果")
    void testGetMessages_HasData_ReturnList() {
        // Given
        RedisChatMemoryStore store = createStoreWithMock();
        String memoryId = "m-002";
        List<ChatMessage> expected = List.of(AiMessage.from("测试内容"));
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(memoryId)).thenReturn(ChatMessageSerializer.messagesToJson(expected));

        // When
        List<ChatMessage> result = store.getMessages(memoryId);

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get(memoryId);
    }

    @Test
    @DisplayName("查询会话消息 - Redis无数据时返回空列表")
    void testGetMessages_NoData_ReturnEmptyList() {
        // Given
        RedisChatMemoryStore store = createStoreWithMock();
        String memoryId = "m-003";
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);
        when(valueOperations.get(memoryId)).thenReturn(null);

        // When
        List<ChatMessage> result = store.getMessages(memoryId);

        // Then
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(redisTemplate).opsForValue();
        verify(valueOperations).get(memoryId);
    }

    @Test
    @DisplayName("删除会话消息 - 成功调用Redis删除")
    void testDeleteMessages_Success() {
        // Given
        RedisChatMemoryStore store = createStoreWithMock();
        String memoryId = "m-004";

        // When
        store.deleteMessages(memoryId);

        // Then
        verify(redisTemplate).delete(memoryId);
    }

    private RedisChatMemoryStore createStoreWithMock() {
        RedisChatMemoryStore store = new RedisChatMemoryStore();
        ReflectionTestUtils.setField(store, "redisTemplate", redisTemplate);
        return store;
    }
}
