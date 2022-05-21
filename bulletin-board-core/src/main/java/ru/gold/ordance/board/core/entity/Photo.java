package ru.gold.ordance.board.core.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"urn"}) })
public class Photo {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "photo_sequence-generator")
    @GenericGenerator(
            name = "photo_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequence_name", value = "photo_sequence"),
                    @org.hibernate.annotations.Parameter(name = "initial_value", value = "1"),
                    @org.hibernate.annotations.Parameter(name = "increment_size", value = "1")
            })
    private Long id;

    private String urn;
}
