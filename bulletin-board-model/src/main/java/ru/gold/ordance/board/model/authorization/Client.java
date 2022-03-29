package ru.gold.ordance.board.model.authorization;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import ru.gold.ordance.board.model.AbstractEntity;
import ru.gold.ordance.board.model.domain.Advertisement;

import javax.persistence.*;
import java.util.Set;


@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Client implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(generator = "client_sequence-generator")
    @GenericGenerator(
            name = "client_sequence-generator",
            strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {
                    @Parameter(name = "sequence_name", value = "client_sequence"),
                    @Parameter(name = "initial_value", value = "1"),
                    @Parameter(name = "increment_size", value = "1")
            })
    private Long id;

    private String login;

    private String password;

    private String salt;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String name;

    private String phoneNumber;

    @OneToMany(mappedBy = "client",
            cascade = { CascadeType.REMOVE })
    private Set<Advertisement> advertisements;
}
