package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpPasswordRepository extends JpaRepository<OtpPassword, Long> {
}
