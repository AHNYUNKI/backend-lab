package dev.backendlab.goldguard.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor
@Table(
        name = "daily_reward_claim",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_daily_reward_claim_user_date",
                        columnNames = {"user_name", "reward_date"}
                )
        }
)
public class DailyRewardClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private LocalDate rewardDate;

    private Integer rewardAmount;

    @Builder
    private DailyRewardClaim(String userName, LocalDate rewardDate, Integer rewardAmount) {
        this.userName = userName;
        this.rewardDate = rewardDate;
        this.rewardAmount = rewardAmount;
    }

    public static DailyRewardClaim create(String userName, LocalDate rewardDate, Integer rewardAmount) {
        return DailyRewardClaim.builder()
                .userName(userName)
                .rewardDate(rewardDate)
                .rewardAmount(rewardAmount)
                .build();
    }

}
