package pl.dernovyi.picture_hosting.security;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String email, String password);
}
