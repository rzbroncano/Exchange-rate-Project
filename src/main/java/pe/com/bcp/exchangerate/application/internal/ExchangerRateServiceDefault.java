package pe.com.bcp.exchangerate.application.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.reactivex.Completable;
import io.reactivex.Single;
import lombok.extern.slf4j.Slf4j;
import pe.com.bcp.exchangerate.application.ExchangerService;
import pe.com.bcp.exchangerate.domain.ExchangeRate;
import pe.com.bcp.exchangerate.infrastructure.jpa.JpaExchangeRateRepository;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateRequest;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateUpdateRequest;
import pe.com.bcp.exchangerate.view.dto.response.ExchangeRateResponse;

@Service
@Slf4j
public class ExchangerRateServiceDefault implements ExchangerService {

	@Autowired
	private JpaExchangeRateRepository jpaExchangeRateRepository;

	@Override
	public Single<ExchangeRate> create(ExchangeRateRequest exchangeRateRequest) {
		return Single.create(singleSubscriber -> {
			Optional<ExchangeRate> element = jpaExchangeRateRepository.fromByNameFromCurrencyAndNameToCurrencyAndDate(
					exchangeRateRequest.getSourceCurrency(), exchangeRateRequest.getTargetCurrency(),
					exchangeRateRequest.getDate());
			if (element.isPresent()) {
				log.info("Elemento ya existente.");
				singleSubscriber.onError(new EntityExistsException());
			} else {
				log.info("Elemento para guardar");
				ExchangeRate exchangeRate = jpaExchangeRateRepository.save(toExchangeRate(exchangeRateRequest));
				singleSubscriber.onSuccess(exchangeRate);
			}
		});
	}

	private ExchangeRate toExchangeRate(ExchangeRateRequest exchangeRateRequest) {
		ExchangeRate modelExchangeRate = new ExchangeRate();
		modelExchangeRate.setCodeSourceCurrency(exchangeRateRequest.getSourceCurrency());
		modelExchangeRate.setCodeTargetCurrency(exchangeRateRequest.getTargetCurrency());
		modelExchangeRate.setValueExchangeRate(exchangeRateRequest.getAmount());
		modelExchangeRate.setDate(exchangeRateRequest.getDate());

		return modelExchangeRate;
	}

	@Override
	public Single<ExchangeRateResponse> apply(ExchangeRateRequest exchangeRateRequest) {
		log.trace("Init generateExchangeRate");
		return Single.create(singleSubscriber -> {
			Optional<ExchangeRate> element = jpaExchangeRateRepository.fromByNameFromCurrencyAndNameToCurrencyAndDate(
					exchangeRateRequest.getSourceCurrency(), exchangeRateRequest.getTargetCurrency(),
					exchangeRateRequest.getDate());
			if (!element.isPresent()) {
				singleSubscriber.onError(new EntityNotFoundException());
			}
			ExchangeRateResponse response = ExchangeRateResponse.builder().amount(exchangeRateRequest.getAmount())
					.sourceCurrency(exchangeRateRequest.getSourceCurrency())
					.targetCurrency(exchangeRateRequest.getTargetCurrency())
					.amountExchangeRate(element.get().getValueExchangeRate().multiply(exchangeRateRequest.getAmount()))
					.valueExchangeRate(element.get().getValueExchangeRate()).build();

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
//			Optional<ExchangeRate> element = jpaExchangeRateRepository.findById(exchangeRateUpdateRequest.getId());
			Optional<ExchangeRate> element = jpaExchangeRateRepository
					.findByCodeSourceCurrencyAndCodeTargetCurrencyAndDate(exchangeRateUpdateRequest.getSourceCurrency(),
							exchangeRateUpdateRequest.getTargetCurrency(), exchangeRateUpdateRequest.getDate());
			if (element.isPresent()) {
				ExchangeRate exRate = element.get();
				exRate.setCodeSourceCurrency(exchangeRateUpdateRequest.getSourceCurrency());
				exRate.setCodeTargetCurrency(exchangeRateUpdateRequest.getTargetCurrency());
				exRate.setValueExchangeRate(exchangeRateUpdateRequest.getAmount());
				jpaExchangeRateRepository.save(exRate);
				completableSubscriber.onComplete();
			} else {
				completableSubscriber.onError(new EntityNotFoundException());
			}
		});
	}

}
