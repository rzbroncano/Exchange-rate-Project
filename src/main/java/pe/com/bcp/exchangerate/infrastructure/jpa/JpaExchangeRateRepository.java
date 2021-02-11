package pe.com.bcp.exchangerate.infrastructure.jpa;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import pe.com.bcp.exchangerate.domain.ExchangeRate;

@Repository
public interface JpaExchangeRateRepository extends JpaRepository<ExchangeRate, Integer> {

	@Query("SELECT ex FROM ExchangeRate ex WHERE codeSourceCurrency=:source AND codeTargetCurrency=:target")
	public Optional<ExchangeRate> fromByNameFromCurrencyAndNameToCurrency(@Param("source") String source,
			@Param("target") String target);

	@Query("SELECT ex FROM ExchangeRate ex WHERE codeSourceCurrency=:source AND codeTargetCurrency=:target AND date=:date")
	public Optional<ExchangeRate> fromByNameFromCurrencyAndNameToCurrencyAndDate(@Param("source") String source,
			@Param("target") String target, @Param("date") String date);

	public Optional<ExchangeRate> findByCodeSourceCurrencyAndCodeTargetCurrencyAndDate(String codeSourceCurrency,
			String codeTargetCurrency, String date);
}
