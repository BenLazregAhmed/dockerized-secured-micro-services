package summer.camp.security_service.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor@NoArgsConstructor@Getter@Setter@Builder
@Entity
public class AppUser {
    @Id@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER)
    private List<AppRole>roles=new ArrayList<>();
}
