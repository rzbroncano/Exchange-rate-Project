package pe.com.bcp.exchangerate.view.dto.request;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateCreateRequest {

	private BigDecimal sellAmount;
	private BigDecimal buyAmount;
	private String sourceCurrency;
	private String targetCurrency;
	private String date;
}
