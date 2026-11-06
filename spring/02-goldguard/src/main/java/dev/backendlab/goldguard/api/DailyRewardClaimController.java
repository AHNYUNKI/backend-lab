package dev.backendlab.goldguard.api;

import dev.backendlab.goldguard.application.DailyRewardClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DailyRewardClaimController {

    private final DailyRewardClaimService dailyRewardClaimService;

    @GetMapping("/users/daily-reward")
    public void claimDailyReward(Authentication authentication) {
        LocalDate now = LocalDate.now();
        dailyRewardClaimService.claimDailyReward(authentication.getName(), now);
    }
}
