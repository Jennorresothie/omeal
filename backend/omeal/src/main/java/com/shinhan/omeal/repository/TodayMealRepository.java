package com.shinhan.omeal.repository;

import com.shinhan.omeal.entity.DeliveryHistory;
import com.shinhan.omeal.entity.Members;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodayMealRepository extends CrudRepository<DeliveryHistory, Long> {

    // 배송내역이 계속 쌓이므로 delivery_no 내림차순으로 find
    List<DeliveryHistory> findByMemberOrderByDeliveryNoDesc(Members member);

    // 시간별 배송목록
    public List<DeliveryHistory> findAllByMemberIn(List<Members> member);
}
