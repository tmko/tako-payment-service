package tako.design.order;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class OrderDAO {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private LocalDateTime createdTime;

    @Column
    private Integer version;

    @Column
    private String phone;

    @Column
    private String street;

    @Column
    private String city;

    @Column
    private String orderItems;

    @Column
    private String status;
}
