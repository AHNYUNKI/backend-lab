package dev.backendlab.goldguard.application;

import dev.backendlab.goldguard.domain.DailyRewardClaim;
import dev.backendlab.goldguard.domain.DailyRewardClaimRepository;
import dev.backendlab.goldguard.exception.BusinessException;
import dev.backendlab.goldguard.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static dev.backendlab.goldguard.exception.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DailyRewardClaimService {

    private static final Integer DAILY_REWARD_AMOUNT = 10000;

    private final DailyRewardClaimRepository dailyRewardClaimRepository;

    @Transactional
    public void claimDailyReward(String userName, LocalDate date) {

        dailyRewardClaimRepository.findByUserNameAndRewardDate(userName, date)
                .ifPresent(claim -> {
                    throw new BusinessException(REWARD_ALREADY_CLAIMED);
                });

        dailyRewardClaimRepository.save(DailyRewardClaim.create(userName, date, DAILY_REWARD_AMOUNT));
    }

}
