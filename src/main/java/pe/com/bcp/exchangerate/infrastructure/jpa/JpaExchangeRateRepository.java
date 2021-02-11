package pe.com.bcp.exchangerate.infrastructure.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import pe.com.bcp.exchangerate.domain.ExchangeRate;

@Repository
public interface JpaExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

	public Optional<ExchangeRate> findByCodeSourceCurrencyAndCodeTargetCurrencyAndDate(String codeSourceCurrency,
			String codeTargetCurrency, String date);
}
