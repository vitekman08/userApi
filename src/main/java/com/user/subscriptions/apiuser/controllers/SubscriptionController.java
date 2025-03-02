package com.user.subscriptions.apiuser.controllers;

import com.user.subscriptions.apiuser.dto.SubscriptionDto;
import com.user.subscriptions.apiuser.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users/{userId}/subscriptions")
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    public SubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Добавляет подписку для указанного пользователя.
     */
    @PostMapping
    @Operation(summary = "Добавляет подписку для указанного id пользователя")
    public SubscriptionDto addSubscription(@PathVariable Long userId, @Valid @RequestBody SubscriptionDto subscriptionDto) {
        return subscriptionService.addSubscription(userId, subscriptionDto);
    }

    /**
     * Получает список подписок для указанного пользователя.
     */
    @GetMapping
    @Operation(summary = "Получает список подписок для указанного id пользователя")
    public List<SubscriptionDto> getSubscriptionsByUserId(@Valid @PathVariable Long userId) {
        return subscriptionService.getSubscriptionsByUserId(userId);
    }

    /**
     * Удаляет подписку у указанного пользователя.
     */
    @DeleteMapping("/{subscriptionId}")
    @Operation(summary = "Удаляет подписку у указанного id пользователя")
    public void deleteSubscription(@PathVariable Long userId, @Valid @PathVariable Long subscriptionId) {
        subscriptionService.deleteSubscription(userId, subscriptionId);
    }


}