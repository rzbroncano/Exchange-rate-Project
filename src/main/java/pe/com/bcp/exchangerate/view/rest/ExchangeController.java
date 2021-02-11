package pe.com.bcp.exchangerate.view.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import pe.com.bcp.exchangerate.application.ExchangerService;
import pe.com.bcp.exchangerate.domain.ExchangeRate;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateApplyRequest;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateCreateRequest;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateUpdateRequest;
import pe.com.bcp.exchangerate.view.dto.response.ExchangeRateResponse;

@RestController
@CrossOrigin
@RequestMapping("api/exchange-rate")
@AllArgsConstructor
public class ExchangeController {

	private ExchangerService exchangerService;

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.setDisallowedFields();
	}

	@PostMapping(value = "/apply", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<ExchangeRateResponse>> apply(
			@RequestBody ExchangeRateApplyRequest exchangeRateApplyRequest) {
		return new ResponseEntity<>(exchangerService.apply(exchangeRateApplyRequest), HttpStatus.OK);
	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<ExchangeRate>> upload(
			@RequestBody ExchangeRateCreateRequest exchangeRateCreateRequest) {
		return new ResponseEntity<>(exchangerService.create(exchangeRateCreateRequest), HttpStatus.OK);
	}

	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<List<ExchangeRate>>> getAll() {
		return new ResponseEntity<>(exchangerService.listAll(), HttpStatus.OK);
	}

	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<String>> upateExchangeRate(
			@RequestBody ExchangeRateUpdateRequest exchangeRateRequest) {
		return new ResponseEntity<>(exchangerService.update(exchangeRateRequest).subscribeOn(Schedulers.io())
				.toSingle(() -> "Se actualizo el tipo de cambio existosamente"), HttpStatus.OK);
	}
}
