package cog.com.EcommercApplication.domain;
import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "suplemento")
public class Suplementos{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Size(min = 3, max = 100, message = "O nome deve ter entre 3 e 100 caracteres")
    private String nome;
    @NotBlank(message = "O campo imagem é obrigatório")
    private String imageUri;
    @NotNull(message = "O preço não pode ser nulo.")
    @DecimalMin(value = "0.1", inclusive = true, message = "O preço deve ser pelo menos 0.1.")
    private float preco;
    @Size(min = 3, max = 100, message = "A descrição deve ter entre 3 e 100 caracteres")
    private String descricao;
    @Size(min = 3, max = 100, message = "A categoria deve ter entre 3 e 100 caracteres")
    private String categoria;
    private LocalDate  isDeleted;
}