package main.java.com.syos.service.interfaces;

import main.java.com.syos.dto.LoginDTO;
import main.java.com.syos.request.CustomerLoginRequest;
import main.java.com.syos.request.CustomerSignupRequest;

public interface ICustomerAuthService {
    LoginDTO login(CustomerLoginRequest loginRequest);
    LoginDTO register(CustomerSignupRequest customerSignupRequest);
}
