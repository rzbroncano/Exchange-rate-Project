package pe.com.bcp.exchangerate.application.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.reactivex.Completable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import pe.com.bcp.exchangerate.application.ExchangerService;
import pe.com.bcp.exchangerate.application.assembler.ExchangeRateAssembler;
import pe.com.bcp.exchangerate.application.exceptions.ExchangeRateExistException;
import pe.com.bcp.exchangerate.application.exceptions.ExchangeRateNotFoundException;
import pe.com.bcp.exchangerate.domain.ExchangeRate;
import pe.com.bcp.exchangerate.infrastructure.jpa.JpaExchangeRateRepository;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateApplyRequest;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateCreateRequest;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateUpdateRequest;
import pe.com.bcp.exchangerate.view.dto.response.ExchangeRateResponse;

@Service
@Slf4j
public class ExchangerRateServiceDefault implements ExchangerService {

	@Autowired
	private JpaExchangeRateRepository jpaExchangeRateRepository;

	@Override
	public Single<ExchangeRate> create(ExchangeRateCreateRequest exchangeRateCreateRequest) {
		return Single.create(singleSubscriber -> {
			Optional<ExchangeRate> element = jpaExchangeRateRepository
					.findByCodeSourceCurrencyAndCodeTargetCurrencyAndDate(exchangeRateCreateRequest.getSourceCurrency(),
							exchangeRateCreateRequest.getTargetCurrency(), exchangeRateCreateRequest.getDate());
			if (element.isPresent()) {
				singleSubscriber.onError(new ExchangeRateExistException("Ya existe un tipo de cambio con la moneda origen, moneda destino y fecha ingresadas."));
			} else {
				ExchangeRate exchangeRate = jpaExchangeRateRepository
						.save(ExchangeRateAssembler.fromDTO(exchangeRateCreateRequest));
				singleSubscriber.onSuccess(exchangeRate);
			}
		});
	}

	@Override
	public Single<ExchangeRateResponse> apply(ExchangeRateApplyRequest exchangeRateRequest) {

		return Single.create(singleSubscriber -> {
			Optional<ExchangeRate> exchangeRateCurrent = jpaExchangeRateRepository
					.findByCodeSourceCurrencyAndCodeTargetCurrencyAndDate(exchangeRateRequest.getSourceCurrency(),
							exchangeRateRequest.getTargetCurrency(), exchangeRateRequest.getDate());

			if (!exchangeRateCurrent.isPresent())
				singleSubscriber.onError(new ExchangeRateNotFoundException("No existe tipo de cambio con la moneda origen, moneda destino y fecha ingresadas."));

			ExchangeRateResponse response = ExchangeRateResponse.builder().amount(exchangeRateRequest.getAmount())
					.sourceCurrency(exchangeRateRequest.getSourceCurrency())
					.targetCurrency(exchangeRateRequest.getTargetCurrency())
					.amountExchangeRateBuy(exchangeRateCurrent.get().getValueExchangeRateBuy()
							.multiply(exchangeRateRequest.getAmount()))
					.amountExchangeRateSell(exchangeRateCurrent.get().getValueExchangeRateSell()
							.multiply(exchangeRateRequest.getAmount()))
					.valueExchangeRateBuy(exchangeRateCurrent.get().getValueExchangeRateBuy())
					.valueExchangeRateSell(exchangeRateCurrent.get().getValueExchangeRateSell()).build();

			singleSubscriber.onSuccess(response);
		});
	}

	@Override
	public Single<List<ExchangeRate>> listAll() {
		return Single.create(singleSubscriber -> {
			List<ExchangeRate> findAll = jpaExchangeRateRepository.findAll();

			if (findAll.isEmpty()) {
				log.info("Lista vacia");
				singleSubscriber.onSuccess(new ArrayList<>());
			} else {
				singleSubscriber.onSuccess(findAll);
			}
		});
	}

	@Override
	public Completable update(ExchangeRateUpdateRequest exchangeRateUpdateRequest) {
		return Completable.create(completableSubscriber -> {
			Optional<ExchangeRate> element = jpaExchangeRateRepository
					.findByCodeSourceCurrencyAndCodeTargetCurrencyAndDate(exchangeRateUpdateRequest.getSourceCurrency(),
							exchangeRateUpdateRequest.getTargetCurrency(), exchangeRateUpdateRequest.getDate());
			if (element.isPresent()) {
				ExchangeRate exRate = element.get();
				exRate.setCodeSourceCurrency(exchangeRateUpdateRequest.getSourceCurrency());
				exRate.setCodeTargetCurrency(exchangeRateUpdateRequest.getTargetCurrency());
				exRate.setValueExchangeRateBuy(exchangeRateUpdateRequest.getBuyAmount());
				exRate.setValueExchangeRateSell(exchangeRateUpdateRequest.getSellAmount());
				jpaExchangeRateRepository.save(exRate);
				completableSubscriber.onComplete();
			} else {
				completableSubscriber.onError(new ExchangeRateNotFoundException("No existe tipo de cambio con la moneda origen, moneda destino y fecha ingresadas."));
			}
		});
	}

}
