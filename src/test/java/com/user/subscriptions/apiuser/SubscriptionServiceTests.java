package com.user.subscriptions.apiuser;

import com.user.subscriptions.apiuser.dto.SubscriptionDto;
import com.user.subscriptions.apiuser.exception.UserNotFoundException;
import com.user.subscriptions.apiuser.exception.UserSubscriptionNotFoundException;
import com.user.subscriptions.apiuser.mappers.UserMapper;
import com.user.subscriptions.apiuser.model.Subscription;
import com.user.subscriptions.apiuser.model.User;
import com.user.subscriptions.apiuser.repository.SubscriptionRepository;
import com.user.subscriptions.apiuser.repository.UserRepository;
import com.user.subscriptions.apiuser.service.SubscriptionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class SubscriptionServiceTests {

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private SubscriptionService subscriptionService;

    private User user;
    private Subscription subscription;
    private SubscriptionDto subscriptionDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Создаем тестового пользователя и подписку
        user = new User();
        user.setId(1L);

        subscription = new Subscription();
        subscription.setId(1L);
        subscription.setUser(user);

        subscriptionDto = new SubscriptionDto();
        subscriptionDto.setId(1L);

        // Используем ReflectionTestUtils для инъекции зависимостей, если необходимо
        // (например, если сервис работает с реальными зависимостями, а не моками).
    }

    @Test
    void testAddSubscription() {
        // Подготовка
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(userMapper.toSubscription(subscriptionDto)).thenReturn(subscription);
        when(subscriptionRepository.save(subscription)).thenReturn(subscription);
        when(userMapper.toSubscriptionDto(subscription)).thenReturn(subscriptionDto);

        // Выполнение
        SubscriptionDto result = subscriptionService.addSubscription(user.getId(), subscriptionDto);

        // Проверка
        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(subscriptionRepository, times(1)).save(subscription);
    }

    @Test
    void testAddSubscription_UserNotFound() {
        // Подготовка
        when(userRepository.findById(user.getId())).thenReturn(Optional.empty());

        // Выполнение и проверка
        assertThrows(UserNotFoundException.class, () -> subscriptionService.addSubscription(user.getId(), subscriptionDto));
    }

    @Test
    void testGetSubscriptionsByUserId() {
        // Подготовка
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(subscriptionRepository.findByUserId(user.getId())).thenReturn(Arrays.asList(subscription));
        when(userMapper.toSubscriptionDto(subscription)).thenReturn(subscriptionDto);

        // Выполнение
        List<SubscriptionDto> result = subscriptionService.getSubscriptionsByUserId(user.getId());

        // Проверка
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getId());
        verify(userRepository, times(1)).existsById(user.getId());
        verify(subscriptionRepository, times(1)).findByUserId(user.getId());
    }

    @Test
    void testGetSubscriptionsByUserId_UserNotFound() {
        // Подготовка
        when(userRepository.existsById(user.getId())).thenReturn(false);

        // Выполнение и проверка
        assertThrows(UserNotFoundException.class, () -> subscriptionService.getSubscriptionsByUserId(user.getId()));
    }

    @Test
    void testDeleteSubscription() {
        // Подготовка
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.of(subscription));

        // Выполнение
        subscriptionService.deleteSubscription(user.getId(), subscription.getId());

        // Проверка
        verify(subscriptionRepository, times(1)).deleteById(subscription.getId());
    }

    @Test
    void testDeleteSubscription_UserNotFound() {
        // Подготовка
        when(userRepository.existsById(user.getId())).thenReturn(false);

        // Выполнение и проверка
        assertThrows(UserNotFoundException.class, () -> subscriptionService.deleteSubscription(user.getId(), subscription.getId()));
    }

    @Test
    void testDeleteSubscription_SubscriptionNotFound() {
        // Подготовка
        when(userRepository.existsById(user.getId())).thenReturn(true);
        when(subscriptionRepository.findById(subscription.getId())).thenReturn(Optional.empty());

        // Выполнение и проверка
        assertThrows(UserSubscriptionNotFoundException.class, () -> subscriptionService.deleteSubscription(user.getId(), subscription.getId()));
    }

    @Test
    void testGetTop3Subscriptions() {
        // Подготовка
        when(subscriptionRepository.findTop3ByOrderByIdDesc()).thenReturn(Arrays.asList("Sub1", "Sub2", "Sub3"));

        // Выполнение
        List<String> result = subscriptionService.getTop3Subscriptions();

        // Проверка
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Sub1", result.get(0));
        verify(subscriptionRepository, times(1)).findTop3ByOrderByIdDesc();
    }
}
