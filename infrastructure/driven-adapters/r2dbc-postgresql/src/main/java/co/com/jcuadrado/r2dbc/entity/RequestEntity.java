package co.com.jcuadrado.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "requests")
public class RequestEntity {
    @Id
    private UUID id;
    private BigDecimal amount;
    private Integer term;
    private String email;
    private UUID typeId;
    private UUID statusId;
}
