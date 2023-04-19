package ch.uzh.ifi.hase.soprafs23.repository;

import ch.uzh.ifi.hase.soprafs23.entity.Statistics;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("statisticsRepository")
public interface StatisticsRepository extends JpaRepository<Statistics, Long> {
    Statistics findByUserId(Long id);
}
