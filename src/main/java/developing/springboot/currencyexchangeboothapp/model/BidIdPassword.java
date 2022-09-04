package developing.springboot.currencyexchangeboothapp.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name ="bid_id_password")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BidIdPassword {
    @Id
    @Column(name = "bid_id")
    Long id;
    String password;
}
