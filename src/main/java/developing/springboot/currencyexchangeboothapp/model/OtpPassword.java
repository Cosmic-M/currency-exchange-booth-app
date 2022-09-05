package developing.springboot.currencyexchangeboothapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "otp_password")
public class OtpPassword {
    @Id
    @Column(name = "deal_id")
    private Long id;
    private String password;
}
