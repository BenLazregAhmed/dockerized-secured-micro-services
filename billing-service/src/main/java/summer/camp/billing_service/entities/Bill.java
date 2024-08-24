package summer.camp.billing_service.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;
import summer.camp.billing_service.model.Customer;

import java.util.Date;
import java.util.List;
@AllArgsConstructor@NoArgsConstructor@Getter@Setter
@Builder
@Entity
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date billingDate;
    @OneToMany(mappedBy = "bill")
    private List<ProductItem>ProductItems;
    private Long customerId;
    @Transient
    private Customer customer;
}
