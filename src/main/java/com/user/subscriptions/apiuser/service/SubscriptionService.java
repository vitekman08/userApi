package com.user.subscriptions.apiuser.service;

import com.user.subscriptions.apiuser.dto.SubscriptionDto;
import com.user.subscriptions.apiuser.exception.UserNotFoundException;
import com.user.subscriptions.apiuser.exception.UserSubscriptionNotFoundException;
import com.user.subscriptions.apiuser.mappers.UserMapper;
import com.user.subscriptions.apiuser.model.Subscription;
import com.user.subscriptions.apiuser.model.User;
import com.user.subscriptions.apiuser.repository.SubscriptionRepository;
import com.user.subscriptions.apiuser.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    private final UserMapper userMapper;


    public SubscriptionService(SubscriptionRepository subscriptionRepository, UserRepository userRepository,
                               UserMapper userMapper) {
        this.subscriptionRepository = subscriptionRepository;
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional
    public SubscriptionDto addSubscription(Long userId, SubscriptionDto subscriptionDto) {
        //Ищем пользователя по id.
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        // Преобразуем DTO в сущность и связываем с пользователем
        Subscription subscriptions = userMapper.toSubscription(subscriptionDto);
        subscriptions.setUser(user);

        // Сохроняем подписку
        Subscription savedSubscription = subscriptionRepository.save(subscriptions);

        return userMapper.toSubscriptionDto(savedSubscription);

    }

    public List<SubscriptionDto> getSubscriptionsByUserId(Long userId) {
        //Проверка на существование user
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found");
        }
        //Получаем список подписок пользователя
        return subscriptionRepository.findByUserId(userId).stream()
                .map(userMapper::toSubscriptionDto)
                .collect(Collectors.toList()); // Преобразуем список в DTO
    }

    public void deleteSubscription(Long userId, Long subscriptionId) {
        //Проверка на существование user
        if (!userRepository.existsById(userId)) {
            throw new UserNotFoundException("User not found " + userId);
        }
        // Проверяем, принадлежит ли подписка указанному пользователю
        Optional<Subscription> subscriptionsOptional = subscriptionRepository.findById(subscriptionId);
        if (subscriptionsOptional.isEmpty() || !subscriptionsOptional.get().getUser().getId().equals(userId)) {
            throw new UserSubscriptionNotFoundException("Subscription not found or not belong to user");
        }

        subscriptionRepository.deleteById(subscriptionId);
    }


    public List<String> getTop3Subscriptions() {
        return subscriptionRepository.findTop3ByOrderByIdDesc();
    }

}
