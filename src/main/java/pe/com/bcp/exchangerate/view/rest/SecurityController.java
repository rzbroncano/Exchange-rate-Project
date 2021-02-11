package pe.com.bcp.exchangerate.view.rest;

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.Single;
import lombok.AllArgsConstructor;
import pe.com.bcp.exchangerate.application.CredentialsValidator;
import pe.com.bcp.exchangerate.view.dto.response.UserResponse;

@RestController
@AllArgsConstructor
public class SecurityController {

	private final CredentialsValidator credentialsValidator;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields();
	}

	@PostMapping("user")
	public Single<UserResponse> login(@RequestParam("user") String username, @RequestParam("password") String pwd) {
		return credentialsValidator.login(username, pwd);
	}

}
