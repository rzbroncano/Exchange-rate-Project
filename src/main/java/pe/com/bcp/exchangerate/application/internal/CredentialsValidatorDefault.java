package pe.com.bcp.exchangerate.application.internal;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.reactivex.Single;
import lombok.AllArgsConstructor;
import pe.com.bcp.exchangerate.application.CredentialsValidator;
import pe.com.bcp.exchangerate.application.exceptions.ApplicationUserNotFound;
import pe.com.bcp.exchangerate.application.exceptions.BadCredentialsException;
import pe.com.bcp.exchangerate.domain.ApplicationUser;
import pe.com.bcp.exchangerate.infrastructure.jpa.JpaApplicationUserRepository;
import pe.com.bcp.exchangerate.view.dto.response.UserResponse;

@Service
@AllArgsConstructor
public class CredentialsValidatorDefault implements CredentialsValidator {

	private final JpaApplicationUserRepository jpaApplicationUserRepository;

	private PasswordEncoder passwordEncoder;

	@Override
	public Single<UserResponse> login(String user, String password) {

		return Single.create(singleSubscriber -> {
			Optional<ApplicationUser> aplicationUser = jpaApplicationUserRepository.findByUsername(user);
			
			if (aplicationUser.isPresent()) {
				
			
				boolean isPasswordMatch = passwordEncoder.matches(password, aplicationUser.get().getPassword());
				
				if(isPasswordMatch) {
					singleSubscriber.onSuccess(fromEntity(aplicationUser.get()));
				}else{
					singleSubscriber.onError(new BadCredentialsException("La contraseña es incorrecta"));
				}
				
			}else {
				singleSubscriber.onError(new ApplicationUserNotFound("El usuario no existe"));
			}
		});

	}

	private static UserResponse fromEntity(ApplicationUser aplicationUser) {
		UserResponse userResponse = new UserResponse();
		userResponse.setUser(aplicationUser.getUsername());
		userResponse.setToken(getJWTToken(aplicationUser.getUsername()));
		return userResponse;
	}

	private static String getJWTToken(String username) {
		String secretKey = "mySecretKey";
		List<GrantedAuthority> grantedAuthorities = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER");

		String token = Jwts.builder().setId("softtekJWT").setSubject(username)
				.claim("authorities",
						grantedAuthorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 600000))
				.signWith(SignatureAlgorithm.HS512, secretKey.getBytes()).compact();

		return "Bearer " + token;
	}

}
