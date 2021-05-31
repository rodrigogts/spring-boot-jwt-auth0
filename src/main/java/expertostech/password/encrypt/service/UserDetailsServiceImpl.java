package expertostech.password.encrypt.service;

import expertostech.password.encrypt.data.UserDetailsData;
import expertostech.password.encrypt.model.UserModel;
import expertostech.password.encrypt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository repository;

    public UserDetailsServiceImpl(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel user = repository.findByLogin(username).orElse(null);
        return new UserDetailsData(user);
    }
}
