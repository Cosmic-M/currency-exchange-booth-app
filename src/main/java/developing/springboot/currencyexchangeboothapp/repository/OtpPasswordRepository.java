package developing.springboot.currencyexchangeboothapp.repository;

import developing.springboot.currencyexchangeboothapp.model.OtpPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OtpPasswordRepository extends JpaRepository<OtpPassword, Long> {
    OtpPassword findByPassword(String password);
}
