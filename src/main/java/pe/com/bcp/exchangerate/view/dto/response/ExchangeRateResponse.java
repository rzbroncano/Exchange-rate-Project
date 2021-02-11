package pe.com.bcp.exchangerate.view.dto.response;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ExchangeRateResponse {

	private BigDecimal amount;
	private BigDecimal amountExchangeRate;
	private String sourceCurrency;
	private String targetCurrency;
	private BigDecimal valueExchangeRate;
}
