package pe.com.bcp.exchangerate.application;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import pe.com.bcp.exchangerate.domain.ExchangeRate;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateRequest;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateUpdateRequest;
import pe.com.bcp.exchangerate.view.dto.response.ExchangeRateResponse;

public interface ExchangerService {

	Single<ExchangeRate> create(ExchangeRateRequest exchangeRate);

	Single<List<ExchangeRate>> listAll();

	Single<ExchangeRateResponse> apply(ExchangeRateRequest request);

	Completable update(ExchangeRateUpdateRequest exchangeRate);

}
