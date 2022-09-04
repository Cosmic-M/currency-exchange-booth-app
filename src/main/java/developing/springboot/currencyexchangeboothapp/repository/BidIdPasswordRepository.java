package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.model.BidIdPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidIdPasswordRepository extends JpaRepository<BidIdPassword, Long> {
}
