package item.test.itemType;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import base.test.BaseTestCase;

/**
 * @author Rodrigo Itursarry (itursarry@gmail.com)
 */
public class ItemTypeServiceTest extends BaseTestCase {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("rawtypes")
	public static Collection<Class> getClassesTestToPerform() {
		// en este metodo se agregan todos los test a realizar relacionados
		Collection<Class> itemTypeTestsClasses = new ArrayList<Class>();
		itemTypeTestsClasses.add(ItemTypeCreationTest.class);
		return itemTypeTestsClasses;
	}
}
