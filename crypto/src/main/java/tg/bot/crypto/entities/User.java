package tg.bot.crypto.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * @author nnikolaev
 * @since 16.05.2023
 */
@Entity
@Getter
@Setter
@Table(name = "TBL_USER")
public class User {

    @Id
    @Column(name = "user_id", nullable = false)
    private Long id;

    @Column
    private String username;

    @Column(name = "last_state")
    @Enumerated(value = EnumType.STRING)
    private UserLastState lastState;
}
