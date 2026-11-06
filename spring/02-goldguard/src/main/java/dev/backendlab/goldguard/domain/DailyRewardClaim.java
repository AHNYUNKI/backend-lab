package dev.backendlab.goldguard.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@Getter
@NoArgsConstructor
public class DailyRewardClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String userName;

    private LocalDate claimDate;

    private Integer rewardAmount;

    @Builder
    private DailyRewardClaim(String userName, LocalDate claimDate, Integer rewardAmount) {
        this.userName = userName;
        this.claimDate = claimDate;
        this.rewardAmount = rewardAmount;
    }

    public static DailyRewardClaim create(String userName, LocalDate claimDate, Integer rewardAmount) {
        return DailyRewardClaim.builder()
                .userName(userName)
                .claimDate(claimDate)
                .rewardAmount(rewardAmount)
                .build();
    }

}
