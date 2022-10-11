package SpringSeason.Sutdy.repository;

import SpringSeason.Sutdy.domain.Board;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<Board, Long>/*, BoardRepositoryCustom*/ {


}
