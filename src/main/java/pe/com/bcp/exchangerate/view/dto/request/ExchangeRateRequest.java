package pe.com.bcp.exchangerate.view.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateRequest {

	private BigDecimal amount;
	private String sourceCurrency;
	private String targetCurrency;
	private String date;
}
