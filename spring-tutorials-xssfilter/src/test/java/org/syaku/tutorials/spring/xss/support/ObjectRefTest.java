package org.syaku.tutorials.spring.xss.support;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.syaku.tutorials.spring.xss.support.reflection.ObjectRef;

/**
 * @author Seok Kyun. Choi. 최석균 (Syaku)
 * @site http://syaku.tistory.com
 * @since 2017. 4. 25.
 */
public class ObjectRefTest {
	private static final Logger logger = LoggerFactory.getLogger(ObjectRefTest.class);

	@Test
	public void referenceType() {
		ReferenceType referenceType = new ReferenceType();

		ReferenceTypeConverter converter = new ReferenceTypeConverter();
		ObjectRef ref = new ObjectRef(converter);
		ReferenceType type = ref.getValue(referenceType,  ReferenceType.class);

		logger.debug("{}", type);

		boolean bool = true;
		float flot = 1.0f;
		long big = 1000L;
		int number = 1;
		String str = "syaku";

		type.setABool(bool);
		type.setBool(bool);
		type.setAFlo(flot);
		type.setFlo(flot);
		type.setALon(big);
		type.setLon(big);
		type.setANumber(number);
		type.setNumber(number);
		type.setStr(str);

		ReferenceType type2 = ref.getValue(type, ReferenceType.class);

		Assert.assertEquals(type.getLon(), type2.getLon());
		Assert.assertEquals(type.getABool(), type2.getABool());
		Assert.assertEquals(type.isBool(), type2.isBool());
		Assert.assertEquals(type.getAFlo(), type2.getAFlo());
		Assert.assertEquals(type.getFlo(), type2.getFlo(), 0);
		Assert.assertEquals(type.getStr(), type2.getStr());

		Assert.assertSame(type.getALon(), type2.getALon());
		Assert.assertNotSame(type.getLon(), type.getLon());
		Assert.assertSame(type.getABool(), type2.getABool());
		Assert.assertSame(type.isBool(), type2.isBool());
		Assert.assertSame(type.getAFlo(), type2.getAFlo());
		Assert.assertNotSame(type.getFlo(), type2.getFlo());
		Assert.assertSame(type.getStr(), type2.getStr());

		Assert.assertSame(type, type2);
	}
}
