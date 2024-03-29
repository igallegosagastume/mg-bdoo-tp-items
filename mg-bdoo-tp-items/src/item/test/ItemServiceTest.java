package item.test;

import item.test.historicItem.ListHistoricItemsTest;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import base.test.BaseTestCase;

/**
 * @author Rodrigo Itursarry (itursarry@gmail.com)
 */

public class ItemServiceTest extends BaseTestCase {
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@SuppressWarnings("rawtypes")
	public static Collection<Class> getClassesTestToPerform() {
		// en este metodo se agregan todos los test a realizar relacionados
		Collection<Class> itemTestsClasses = new ArrayList<Class>();
		itemTestsClasses.add(ItemCreateTest.class);
		itemTestsClasses.add(ItemTransitionTest.class);
		itemTestsClasses.add(ListHistoricItemsTest.class);
		return itemTestsClasses;
	}
}
