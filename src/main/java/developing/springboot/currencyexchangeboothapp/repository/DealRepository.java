package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.model.Deal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface DealRepository extends JpaRepository<Deal, Long> {
    @Query("FROM Deal d where d.id = ?1")
    Deal findCoincidenceById(Long id);

    @Modifying
    @Transactional
    @Query("DELETE FROM Deal d where d.phone = ?1 AND d.status = 'NEW'")
    void deleteBy(String phone);
}
