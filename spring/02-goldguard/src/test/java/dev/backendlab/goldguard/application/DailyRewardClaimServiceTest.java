package dev.backendlab.goldguard.application;

import dev.backendlab.goldguard.domain.DailyRewardClaimRepository;
import dev.backendlab.goldguard.exception.BusinessException;
import dev.backendlab.goldguard.exception.ErrorCode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoSpyBean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mockingDetails;

@SpringBootTest
@ActiveProfiles("test")
class DailyRewardClaimServiceTest {

    @Autowired
    private DailyRewardClaimService dailyRewardClaimService;

    @MockitoSpyBean
    private DailyRewardClaimRepository dailyRewardClaimRepository;

    @BeforeEach
    void setUp() {
        dailyRewardClaimRepository.deleteAllInBatch();
    }

    @Test
    void 유저는_일일_보상을_받을_수_있다() {
        // given
        String userName = "user";
        LocalDate now = LocalDate.now();

        // when
        dailyRewardClaimService.claimDailyReward(userName, now);

        // then
        int size = dailyRewardClaimRepository.findAll().size();
        assertEquals(1, size);

    }

    @Test
    void 유저가_일일_보상을_두_번_받을_수_없다() {
        // given
        String userName = "user";
        LocalDate now = LocalDate.now();

        dailyRewardClaimService.claimDailyReward(userName, now);

        // when // then
        BusinessException exception = assertThrows(
                BusinessException.class,
                () -> dailyRewardClaimService.claimDailyReward(userName, now)
        );

        assertEquals(
                ErrorCode.REWARD_ALREADY_CLAIMED,
                exception.getErrorCode()
        );

    }

    @Test
    void A유저와_B유저는_각각_일일_보상을_받을_수_있다() {
        // given
        String userA = "userA";
        String userB = "userB";
        LocalDate now = LocalDate.now();

        // when
        dailyRewardClaimService.claimDailyReward(userA, now);
        dailyRewardClaimService.claimDailyReward(userB, now);

        // then
        int size = dailyRewardClaimRepository.findAll().size();
        assertEquals(2, size);


    }

    @Test
    void 같은_유저가_동시에_보상을_요청해도_한_번만_저장된다() throws Exception {
        // given
        String userName = "concurrent-user";
        LocalDate rewardDate = LocalDate.of(2026, 1, 1);
        CountDownLatch bothReadsCompleted = new CountDownLatch(2);

        // 실제 DB 조회 결과를 유지하면서, 두 요청이 모두 조회를 마친 뒤 저장하도록 맞춥니다.
        var realRepositoryAnswer = mockingDetails(dailyRewardClaimRepository)
                .getMockCreationSettings().getDefaultAnswer();
        doAnswer(invocation -> {
            Object result = realRepositoryAnswer.answer(invocation);
            assertEquals(Optional.empty(), result, "두 요청 모두 수령 기록이 없는 상태를 조회해야 합니다.");
            bothReadsCompleted.countDown();
            assertTrue(bothReadsCompleted.await(5, TimeUnit.SECONDS), "두 요청의 조회가 제한 시간 안에 끝나야 합니다.");
            return result;
        }).when(dailyRewardClaimRepository).findByUserNameAndRewardDate(userName, rewardDate);

        ExecutorService executor = Executors.newFixedThreadPool(2);
        try {
            Callable<Boolean> claimReward = () -> {
                try {
                    // 각 스레드에서 Spring 서비스 프록시를 호출하여 별도 트랜잭션으로 실행합니다.
                    dailyRewardClaimService.claimDailyReward(userName, rewardDate);
                    return true;
                } catch (DataIntegrityViolationException exception) {
                    return false;
                }
            };

            // when
            Future<Boolean> first = executor.submit(claimReward);
            Future<Boolean> second = executor.submit(claimReward);
            List<Boolean> results = List.of(
                    first.get(5, TimeUnit.SECONDS),
                    second.get(5, TimeUnit.SECONDS)
            );

            // then
            assertEquals(1L, results.stream().filter(Boolean::booleanValue).count(), "성공한 요청은 하나여야 합니다.");
            var claims = dailyRewardClaimRepository.findAll();
            assertEquals(1, claims.size(), "DB에는 수령 기록이 하나만 저장되어야 합니다.");
            assertEquals(userName, claims.getFirst().getUserName());
            assertEquals(rewardDate, claims.getFirst().getRewardDate());
            assertEquals(10000, claims.getFirst().getRewardAmount());
        } finally {
            executor.shutdownNow();
            assertTrue(executor.awaitTermination(5, TimeUnit.SECONDS), "테스트 작업 스레드가 종료되어야 합니다.");
        }
    }

}
