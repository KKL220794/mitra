package com.security.service;

import com.security.model.AuthenticationResponse;
import com.security.model.Token;
import com.security.model.User;
import com.security.repo.TokenRepo;
import com.security.repo.UserRepo;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final TokenRepo tokenRepo;

    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepo userRepo, PasswordEncoder passwordEncoder, JwtService jwtService, TokenRepo tokenRepo, AuthenticationManager authenticationManager) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepo = tokenRepo;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User  request){
        if(userRepo.findByUsername(request.getUsername()).isPresent()){
            return null;
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setRole(request.getRole());

        userRepo.save(user);

        String jwt = jwtService.generateToken(user);

        RevokeAllTokensByUser(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt);
    }

    private void RevokeAllTokensByUser(User user) {
        List<Token> validTokens = tokenRepo.findAllTokensByUser(user.getId());

        if(!validTokens.isEmpty()){
            validTokens.forEach(token -> {
                token.setIs_logged_out(true);
            });
            tokenRepo.saveAll(validTokens);
        }
    }

    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setUser(user);
        token.setIs_logged_out(false);
        tokenRepo.save(token);
    }

    public AuthenticationResponse authenticate(User request){
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            User user = userRepo.findByUsername(request.getUsername()).orElseThrow();

            String jwt = jwtService.generateToken(user);

            RevokeAllTokensByUser(user);

            saveUserToken(jwt, user);

            return new AuthenticationResponse(jwt);
        }
        catch (Exception e){
            return null;
        }
    }
}
