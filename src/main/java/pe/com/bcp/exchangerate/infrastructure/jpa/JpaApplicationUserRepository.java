package pe.com.bcp.exchangerate.infrastructure.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import pe.com.bcp.exchangerate.domain.ApplicationUser;

public interface JpaApplicationUserRepository extends JpaRepository<ApplicationUser, Long> {
	
	Optional<ApplicationUser> findByUsername(String username);
}