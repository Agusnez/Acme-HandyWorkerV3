
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Configuration extends DomainEntity {

	private Collection<String>	spamWord;
	private double				vatTax;
	private String				countryCode;
	private int					finderTime;
	private int					finderResult;


	@ElementCollection
	@NotNull
	public Collection<String> getSpamWord() {
		return this.spamWord;
	}
	public void setSpamWord(final Collection<String> spamWord) {
		this.spamWord = spamWord;
	}

	@Min(0)
	@Max(100)
	public double getVatTax() {
		return this.vatTax;
	}
	public void setVatTax(final Double vatTax) {
		this.vatTax = vatTax;
	}

	@NotBlank
	@Pattern(regexp = "^\\+\\d{1,3}$")
	public String getCountryCode() {
		return this.countryCode;
	}
	public void setCountryCode(final String countryCode) {
		this.countryCode = countryCode;
	}

	@Min(1)
	@Max(24)
	public int getFinderTime() {
		return this.finderTime;
	}
	public void setFinderTime(final Integer finderTime) {
		this.finderTime = finderTime;
	}

	@Min(1)
	@Max(100)
	public int getFinderResult() {
		return this.finderResult;
	}
	public void setFinderResult(final Integer finderResult) {
		this.finderResult = finderResult;
	}
}
