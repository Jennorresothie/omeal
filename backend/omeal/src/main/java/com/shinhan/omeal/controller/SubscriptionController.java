package com.shinhan.omeal.controller;

import com.shinhan.omeal.dto.delivery.DeliveryContainer;
import com.shinhan.omeal.dto.subscription.SubscriptionDTO;
import com.shinhan.omeal.dto.subscription.SubscriptionType;
import com.shinhan.omeal.service.SubscriptionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class SubscriptionController {

    private final SubscriptionService subscriptionService;

    // 구독 정보 저장
    @PostMapping(value = "/subscribe")
    public String subscribe(@RequestBody SubscriptionDTO subscriptionInfo) {
        return subscriptionService.subscribe(subscriptionInfo);
    }

    /*
    Enum 반환
    1. 배송 주기 선택 SubscriptionType
    2. 배송 용기 선택 DeliveryContainer
    3. 음식 카테고리 선택 SubscriptoinCategory
    4. 배송 시간 선택 DeliveryTime
     */
    @GetMapping(value = "/plan-subtype")
    public SubscriptionType[] subscriptionTypes() {
        return SubscriptionType.values();
    }

    @GetMapping(value = "/plan-container")
    public DeliveryContainer[] ContainerTypes() {
        return DeliveryContainer.values();
    }

}
