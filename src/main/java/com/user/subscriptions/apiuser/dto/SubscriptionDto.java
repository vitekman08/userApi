package com.user.subscriptions.apiuser.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    Long id;

    @NotBlank(message = "Имя сервиса не должен быть пустой")
    @Size(max = 50, message = "Имя сервиса не должен превышать 50 символов")
    String serviceName;
    String description;
    Long userId;
}