
package services;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Configuration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class ConfigurationServiceTest extends AbstractTest {

	//Service neder test ------------------------------------------

	@Autowired
	private ConfigurationService	configurationService;


	//Tests -------------------------------------------------------

	@Test
	public void testSave() {
		this.authenticate("admin");

		final Configuration c = this.configurationService.findOne(super.getEntityId("configuration"));

		c.setVatTax(0.18);
		final Configuration saved = this.configurationService.save(c);

		final Collection<Configuration> config = this.configurationService.findAll();
		Assert.isTrue(config.contains(saved), "----- Fallo metodo save -----");

		super.authenticate(null);
	}
}
