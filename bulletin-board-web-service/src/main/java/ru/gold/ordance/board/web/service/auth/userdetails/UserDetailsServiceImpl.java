package ru.gold.ordance.board.web.service.auth.userdetails;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.gold.ordance.board.model.entity.domain.Client;
import ru.gold.ordance.board.service.base.heir.ClientService;

import java.util.Collections;
import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final ClientService service;

    public UserDetailsServiceImpl(ClientService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Optional<Client> client = service.findByLogin(login);

        return client.map(value ->
                new UserDetailsImpl(
                        value.getId(),
                        value.getLogin(),
                        value.getPassword(),
                        Collections.singleton(new SimpleGrantedAuthority(value.getRole().getName()))))
                .orElse(null);
    }
}
