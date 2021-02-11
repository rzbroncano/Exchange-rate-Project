package pe.com.bcp.exchangerate.view.rest;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.com.bcp.exchangerate.application.ExchangerService;
import pe.com.bcp.exchangerate.domain.ExchangeRate;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateRequest;
import pe.com.bcp.exchangerate.view.dto.request.ExchangeRateUpdateRequest;
import pe.com.bcp.exchangerate.view.dto.response.ExchangeRateResponse;

@RestController
@CrossOrigin
@RequestMapping("api/exchange-rate")
@Slf4j
@AllArgsConstructor
public class ExchangeController {

	private ExchangerService exchangerService;

	@PostMapping(value = "/apply", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<ExchangeRateResponse>> apply(@RequestBody ExchangeRateRequest exchangeRateRequest) {
		log.trace("Init apply exchange rate");
		return new ResponseEntity<>(exchangerService.apply(exchangeRateRequest), HttpStatus.OK);
	}

	@PostMapping(value = "/create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<ExchangeRate>> upload(@RequestBody ExchangeRateRequest exchangeRateRequest) {
		log.trace("Init upload exchange rate");
		return new ResponseEntity<>(exchangerService.create(exchangeRateRequest), HttpStatus.OK);
	}
	
	@GetMapping(value = "/list", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<List<ExchangeRate>>> getAll() {
		log.trace("Inicio getAll exchange rates BD");
		return new ResponseEntity<>(exchangerService.listAll(), HttpStatus.OK);
	}
	
	@PutMapping(value = "/update", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Single<String>> upateExchangeRate(@RequestBody ExchangeRateUpdateRequest exchangeRateRequest) {
		log.trace("Inicio create exchange rate");
		return new ResponseEntity<>(exchangerService.update(exchangeRateRequest).subscribeOn(Schedulers.io())
                .toSingle(() -> "Registro actualizado"), HttpStatus.OK);
	}
}
