package com.vivareal.locations.statistic.service;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListConsumerTest {

	@Test
	public void testCheckIsEmpty() {
		ListConsumer<Integer> consumer = new ListConsumer<>(asList(1, 2, 3, 4, 5, 6, 7));
		assertFalse(consumer.isEmpty());
		assertThat(consumer.consume(), hasSize(7));
		assertTrue(consumer.isEmpty());
	}
}
