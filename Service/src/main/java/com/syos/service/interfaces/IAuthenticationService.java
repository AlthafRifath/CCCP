package main.java.com.syos.service.interfaces;

import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.LoginRequest;

public interface IAuthenticationService {
    LoginDTO login(LoginRequest loginRequest);
}
