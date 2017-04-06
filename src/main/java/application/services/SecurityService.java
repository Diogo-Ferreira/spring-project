package application.services;

/**
 * Created by Sylvain Ramseyer on 06.04.2017.
 */
public interface SecurityService {

    public String findLoggedInUsername();
    public void autologin(String username, String password);
}
