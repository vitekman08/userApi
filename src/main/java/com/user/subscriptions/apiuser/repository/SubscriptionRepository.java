package com.user.subscriptions.apiuser.repository;

import com.user.subscriptions.apiuser.model.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    List<Subscription> findByUserId(Long userId);

    @Query(value = "SELECT s.service_name FROM subscriptions s GROUP BY s.service_name ORDER BY COUNT(*) DESC LIMIT 3", nativeQuery = true)
    List<String> findTop3ByOrderByIdDesc();

}
