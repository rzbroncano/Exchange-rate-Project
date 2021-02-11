package pe.com.bcp.exchangerate.application.assembler;

import pe.com.bcp.exchangerate.domain.ExchangeRate;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateCreateRequest;

public class ExchangeRateAssembler {

	
	public static ExchangeRate fromDTO(ExchangeRateCreateRequest exchangeRateCreateRequest) {
		ExchangeRate modelExchangeRate = new ExchangeRate();
		modelExchangeRate.setCodeSourceCurrency(exchangeRateCreateRequest.getSourceCurrency());
		modelExchangeRate.setCodeTargetCurrency(exchangeRateCreateRequest.getTargetCurrency());
		modelExchangeRate.setValueExchangeRateBuy(exchangeRateCreateRequest.getBuyAmount());
		modelExchangeRate.setValueExchangeRateSell(exchangeRateCreateRequest.getSellAmount());
		modelExchangeRate.setDate(exchangeRateCreateRequest.getDate());

		return modelExchangeRate;
	}
}
