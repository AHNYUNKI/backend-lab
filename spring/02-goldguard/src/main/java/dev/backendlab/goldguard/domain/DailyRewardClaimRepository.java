package dev.backendlab.goldguard.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface DailyRewardClaimRepository extends JpaRepository<DailyRewardClaim, Long> {

    Optional<DailyRewardClaim> findByUserNameAndRewardDate(String userName, LocalDate date);
}
