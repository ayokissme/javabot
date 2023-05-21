package tg.bot.crypto.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import tg.bot.crypto.callbacks.Currency;

/**
 * @author nnikolaev
 * @since 20.05.2023
 */
@Entity
@Getter
@Setter
@Table(name = "tbl_alert")
public class Alert {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "AlertIdGen")
    @SequenceGenerator(name = "AlertIdGen", sequenceName = "tbl_alert_seq", initialValue = 1000, allocationSize = 1)
    @Column(name = "alert_id", nullable = false)
    private Long id;

    @Column
    @Enumerated(value = EnumType.STRING)
    private Currency currency;

    @Column(name = "on_set_price")
    private Float onSetPrice;

    @Column(name = "required_price")
    private Float requiredPrice;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "tbl_alert_user_id_fkey"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;

    @Override
    public String toString() {
        return "Alert{" + "id=" + id + ", currency=" + currency + ", price=" + requiredPrice + "}";
    }
}
