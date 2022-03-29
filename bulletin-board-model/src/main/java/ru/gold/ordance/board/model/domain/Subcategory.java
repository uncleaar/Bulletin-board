package ru.gold.ordance.board.model.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.gold.ordance.board.model.AbstractEntity;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Subcategory implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "subcategory_sequence-generator")
    @GenericGenerator(
            name = "subcategory_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "subcategory_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private Long id;

    private String name;

    @ManyToOne
    private Category category;
}
