package dev.backendlab.goldguard.application;

import dev.backendlab.goldguard.domain.DailyRewardClaimRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class DailyRewardClaimServiceTest {

    @Autowired
    private DailyRewardClaimService dailyRewardClaimService;

    @Autowired
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
        assertThrows(IllegalStateException.class, () -> dailyRewardClaimService.claimDailyReward(userName, now));

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

}