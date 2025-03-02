package com.user.subscriptions.apiuser.mappers;


import com.user.subscriptions.apiuser.dto.SubscriptionDto;
import com.user.subscriptions.apiuser.dto.UserDto;
import com.user.subscriptions.apiuser.model.Subscription;
import com.user.subscriptions.apiuser.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toUserDto(User user);


    User toUser(UserDto userDto);

    @Mapping(target = "userId", source = "user.id")
    SubscriptionDto toSubscriptionDto(Subscription subscriptions);

    @Mapping(target = "user.id", source = "userId")
    Subscription toSubscription(SubscriptionDto subscriptionsDto);

}
