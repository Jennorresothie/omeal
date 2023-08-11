package com.shinhan.omeal;

import com.shinhan.omeal.dto.todayMeal.FeedbackDTO;
import com.shinhan.omeal.entity.Feedback;
import com.shinhan.omeal.entity.Members;
import com.shinhan.omeal.entity.Menu;
import com.shinhan.omeal.entity.Subscription;
import com.shinhan.omeal.repository.FeedbackRepository;
import com.shinhan.omeal.repository.MembersRepository;
import com.shinhan.omeal.repository.MenuRepository;
import com.shinhan.omeal.repository.SubscriptionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@SpringBootTest
public class KyungYunTest {

    @Autowired
    MembersRepository memRepo;
    @Autowired
    SubscriptionRepository subRepo;
    @Autowired
    MenuRepository menuRepo;
    @Autowired
    FeedbackRepository feedbackRepo;

    // 피드백 읽어오기
//    @Test
    void getFeedback() {
        FeedbackDTO dto = FeedbackDTO.builder()
                .memberId("test4@mail.com")
                .menuName("돼지고기국수")
                .build();

        FeedbackDTO feedbackDTO = null;

        Members member = memRepo.findById(dto.getMemberId()).orElse(null);
        List<Menu> menuIdList = menuRepo.findByMenuName(dto.getMenuName());

        Feedback oneFbFromDB = feedbackRepo.findByMemberAndMenu(member, menuIdList.get(0));

        if (oneFbFromDB != null) {
            feedbackDTO = FeedbackDTO.builder()
                    .feedback(oneFbFromDB.getFeedback())
                    .feedbackContent(oneFbFromDB.getFeedbackContent())
                    .build();
        }

        System.out.println(feedbackDTO);
    }

    // 피드백 남기기
//    @Test
    void submitFeedback() {
        FeedbackDTO dto = FeedbackDTO.builder()
                .memberId("test4@mail.com")
                .menuName("육회비빔밥")
                .feedback("dislike")
                .feedbackContent("아쉬워용")
                .build();

        Members member = memRepo.findById(dto.getMemberId()).orElse(null);
        List<Menu> menuIdList = menuRepo.findByMenuName(dto.getMenuName());

        Feedback oneFbFromDB = feedbackRepo.findByMemberAndMenu(member, menuIdList.get(0));
        // 피드백 테이블에서 회원 정보와 메뉴 이름으로 조회
        if (oneFbFromDB == null) { // 없으면
            for (Menu menu : menuIdList) {
                Feedback feedback = Feedback.builder()
                        .member(member)
                        .menu(menu)
                        .feedback(dto.getFeedback())
                        .feedbackContent(dto.getFeedbackContent())
                        .build();
                feedbackRepo.save(feedback);
            }
            System.out.println("SUCCESS(insert)");
        } else { // 있으면
            if (oneFbFromDB.getFeedback().equals(dto.getFeedback()) && oneFbFromDB.getFeedbackContent().equals(dto.getFeedbackContent())) { // 피드백이 같으면
                System.out.println("do NOTHING");
            } else { // 다르면
                for (Menu menu : menuIdList) {
                    Feedback feedback = feedbackRepo.findByMemberAndMenu(member, menu);
                    feedback.updateFeedback(dto.getFeedback(), dto.getFeedbackContent());
                    feedbackRepo.save(feedback);
                }
            }
        }

        System.out.println("SUCCESS(update)");
    }

    // 회원의 연속 기간 가져오기
//    @Test
    void getContinuousDays() {
        String input = "test5@mail.com";

        Members member = memRepo.findById(input).orElse(null);
        Subscription sub = subRepo.findByMember(member);
        if (sub == null) {
            System.out.println(input + " : 구독하지 않은 회원임.");
        } else {
            int days = subRepo.findContinuousDaysByMemberId(input);
            System.out.println("memberId: "+input+", 연속 구독 기간(일): "+days);
        }
    }

    // 닉네임 중복 체크
//    @Test
    void nickDup() {
        String input = "노찌롱";

        Members member = memRepo.findByMemberNick(input);
        if(member == null) {
            System.out.println("닉네임 중복 아님. 아이디 사용 가능.");
        } else {
            System.out.println("닉네임 중복. " + member);
        }
    }

    // 아이디 중복 체크
//    @Test
    void idDup() {
        String input = "kky417@kakao.com";

        Members member = memRepo.findById(input).orElse(null);
        if(member == null) {
            System.out.println("아이디 중복 아님. 아이디 사용 가능.");
        } else {
            System.out.println("아이디 중복. " + member);
        }
    }

    // 회원가입. 카드 번호, 알러지 정보 제외.
//    @Test
    void signUp() {
//        Members member = Members.builder()
//                .memberId("kky417@kakao.com")
//                .memberPwd("1234")
//                .memberName("김경윤")
//                .memberNick("티모준위")
//                .memberTel("01038129323")
//                .memberAddr("고양시 화신로 233")
//                .build();

        Members member = Members.builder()
                .memberId("test1@mail.com")
                .memberPwd("1234")
                .memberName("노홍철")
                .memberNick("노찌롱")
                .memberTel("01011112222")
                .memberAddr("지구 어딘가")
                .build();

        memRepo.save(member);
    }
}
