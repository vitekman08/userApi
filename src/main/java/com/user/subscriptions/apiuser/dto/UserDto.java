package com.user.subscriptions.apiuser.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(max = 50, message = "Имя не должно превышать 50 символов")
    String firstName;

    @NotBlank(message = "Фамилия не должна быть пустой")
    @Size(max = 50, message = "Фамилия не должна превышать 50 символов")
    String lastName;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Не корректный формат email")
    String email;

    Set<SubscriptionDto> subscriptions;
}