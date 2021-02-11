package pe.com.bcp.exchangerate.domain;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "exchange_rates")
public class ExchangeRate {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID")
	private Integer id;

	@Column(name = "CODE_SOURCE_CURRENCY")
	private String codeSourceCurrency;
	@Column(name = "CODE_TARGET_CURRENCY")
	private String codeTargetCurrency;

	@Column(name = "VALUE_EXCHANGERATE_SELL",precision = 16, scale = 5)
	private BigDecimal valueExchangeRateSell;
	
	@Column(name = "VALUE_EXCHANGERATE_BUY",precision = 16, scale = 5)
	private BigDecimal valueExchangeRateBuy;
	
	@Column(name = "DATE")
	private String date;
	
}
