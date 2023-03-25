package ch.uzh.ifi.hase.soprafs23.service;

import ch.uzh.ifi.hase.soprafs23.entity.User;
import ch.uzh.ifi.hase.soprafs23.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TokenAuthenticationService {
    private final UserRepository userRepository;

    @Autowired
    public TokenAuthenticationService(@Qualifier("userRepository") UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /***
     * checks if a user with the token exists
     */
    public boolean checkToken(String token) {
        User userByToken = userRepository.findByToken(token);
        return userByToken!= null;
    }
}
