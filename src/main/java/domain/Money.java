
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Embeddable
@Access(AccessType.PROPERTY)
public class Money {

	private Double	amount;
	private String	currency;


	public Money() {
		super();
	}

	public Money(final Double amount, final String currency) {
		this.amount = amount;
		this.currency = currency;
	}

	@Min(0)
	@Digits(integer = 9, fraction = 2)
	@NotNull
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(final Double amount) {
		this.amount = amount;
	}

	@NotBlank
	public String getCurrency() {
		return this.currency;
	}

	public void setCurrency(final String currency) {
		this.currency = currency;
	}

	@Valid
	Money add(final Money m) {
		if (this.currency.equals(m.getCurrency()))
			return new Money(this.amount += m.getAmount(), m.getCurrency());
		else
			throw new IllegalArgumentException("You can not add two different currencies.");

	}

	@Valid
	Money substract(final Money m) {
		if (this.currency.equals(m.getCurrency()))
			return new Money(this.amount -= m.getAmount(), m.getCurrency());
		else
			throw new IllegalArgumentException("You can not substract two different currencies.");
	}
}
