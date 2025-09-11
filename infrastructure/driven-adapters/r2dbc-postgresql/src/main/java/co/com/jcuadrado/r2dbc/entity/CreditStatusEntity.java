package co.com.jcuadrado.r2dbc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

@Table(name = "status")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditStatusEntity {
    @Id
    private UUID id;
    private String name;
    private String description;
}
