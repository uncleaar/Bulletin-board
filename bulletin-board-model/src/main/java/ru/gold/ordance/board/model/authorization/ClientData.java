package ru.gold.ordance.board.model.authorization;

import lombok.*;
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
public class ClientData implements AbstractEntity {
    private static final long serialVersionUID = 1L;

    @Id
    private Long id;

    private String name;

    private String phoneNumber;

    @OneToOne(optional = false,
            cascade = { CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REMOVE })
    @MapsId
    private Client client;

    @OneToMany(mappedBy = "clientData",
            cascade = { CascadeType.REMOVE })
    private Set<Advertisement> advertisements;
}
