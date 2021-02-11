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

	@Column(name = "CODESOURCECURRENCY")
	private String codeSourceCurrency;
	@Column(name = "CODETARGETCURRENCY")
	private String codeTargetCurrency;

	@Column(name = "VALUEEXCHANGERATE")
	private BigDecimal valueExchangeRate;
	@Column(name = "DATE")
	private String date;

}
