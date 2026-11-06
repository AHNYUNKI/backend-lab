package dev.backendlab.goldguard.application;

import dev.backendlab.goldguard.domain.DailyRewardClaim;
import dev.backendlab.goldguard.domain.DailyRewardClaimRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DailyRewardClaimService {

    private final Integer DAILY_REWARD_AMOUNT = 10000;

    private final DailyRewardClaimRepository dailyRewardClaimRepository;

    public void claimDailyReward(String userName, LocalDate date) {

        dailyRewardClaimRepository.findByUserNameAndClaimDate(userName, date)
                .ifPresent(claim -> {
                    throw new IllegalStateException("유저는 이미 보상을 받았습니다.");
                });

        dailyRewardClaimRepository.save(DailyRewardClaim.create(userName, date, DAILY_REWARD_AMOUNT));
    }

}
