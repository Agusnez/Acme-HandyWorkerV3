
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
import domain.Warranty;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	//Service under test ------------------------------------------

	@Autowired
	private WarrantyService	warrantyService;


	//Tests -------------------------------------------------------
	@Test
	public void testCreate() {

		this.authenticate("admin");

		final Warranty warranty = this.warrantyService.create();

		Assert.isNull(warranty.getTitle());
		Assert.isNull(warranty.getTerms());
		Assert.isNull(warranty.getLaw());
		Assert.isNull(warranty.getFinalMode());

		super.authenticate(null);

	}

	@Test
	public void testSave() {

		this.authenticate("admin");

		Warranty warranty, saved1;
		Collection<Warranty> warranties;

		warranty = this.warrantyService.findOne(super.getEntityId("warranty2"));
		warranty.setTitle("tttt");

		saved1 = this.warrantyService.save(warranty);
		warranties = this.warrantyService.findAll();
		Assert.isTrue(warranties.contains(saved1));

		super.authenticate(null);

	}

	@Test
	public void testDelete() {

		this.authenticate("admin");

		Warranty warranty;
		Collection<Warranty> warranties;

		warranty = this.warrantyService.findOne(super.getEntityId("warranty2"));

		this.warrantyService.delete(warranty);
		warranties = this.warrantyService.findAll();
		Assert.isTrue(!(warranties.contains(warranty)));

		super.authenticate(null);

	}
}
