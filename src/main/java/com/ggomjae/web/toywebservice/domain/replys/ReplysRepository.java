package com.ggomjae.web.toywebservice.domain.replys;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplysRepository extends JpaRepository<Replys, Long> {

    @Query("SELECT r FROM Replys r WHERE r.bno = ?1 AND r.rno > 0 ORDER BY r.rno ASC")
    List<Replys> getReplys(Long bno);
}
