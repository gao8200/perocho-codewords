package myapp.application.guessword.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import myapp.application.guessword.model.GameRecord;

@Repository
public interface GameRecordRepository extends JpaRepository<GameRecord, Long> {

}
