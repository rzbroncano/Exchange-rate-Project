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
	private String sourceCurrency;
	private String targetCurrency;
	private BigDecimal valueExchangeRateBuy;
	private BigDecimal amountExchangeRateBuy;
	private BigDecimal valueExchangeRateSell;
	private BigDecimal amountExchangeRateSell;
}
