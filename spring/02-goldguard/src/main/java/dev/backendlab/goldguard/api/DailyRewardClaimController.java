package dev.backendlab.goldguard.api;

import dev.backendlab.goldguard.application.DailyRewardClaimService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DailyRewardClaimController {

    private final DailyRewardClaimService dailyRewardClaimService;

    @PostMapping("/users/daily-reward")
    public ResponseEntity<Void> claimDailyReward(Authentication authentication) {
        LocalDate now = LocalDate.now();
        dailyRewardClaimService.claimDailyReward(authentication.getName(), now);
        return ResponseEntity.ok().build();
    }
}
