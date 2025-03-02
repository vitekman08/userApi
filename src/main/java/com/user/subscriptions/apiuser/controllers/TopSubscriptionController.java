package com.user.subscriptions.apiuser.controllers;

import com.user.subscriptions.apiuser.dto.SubscriptionDto;
import com.user.subscriptions.apiuser.service.SubscriptionService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/subscriptions")
public class TopSubscriptionController {

    private final SubscriptionService subscriptionService;

    public TopSubscriptionController(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    /**
     * Получает ТОП-3 подписки для всех пользователей.
     */
    @GetMapping("/top")
    public List<String> getTop3Subscriptions() {
        return subscriptionService.getTop3Subscriptions();
    }
}
