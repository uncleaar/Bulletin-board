package ru.gold.ordance.board.model.entity.domain;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"locality_id", "street_id"}) })
public class LnkLocalityStreet implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "locality_street_sequence-generator")
    @GenericGenerator(
            name = "locality_street_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "locality_street_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private Long id;

    @ManyToOne
    private Locality locality;

    @ManyToOne
    private Street street;
}