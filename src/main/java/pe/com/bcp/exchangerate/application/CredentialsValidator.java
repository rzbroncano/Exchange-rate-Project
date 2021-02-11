package pe.com.bcp.exchangerate.application;

import io.reactivex.Single;
import pe.com.bcp.exchangerate.view.dto.response.UserResponse;

public interface CredentialsValidator {

	Single<UserResponse> login(String user, String password);
}
