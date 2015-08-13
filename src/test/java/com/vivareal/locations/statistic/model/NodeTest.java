package com.vivareal.locations.statistic.model;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class NodeTest {

	@Test
	public void testeCompareWith() {
		Node node1 = new Node("Total");
		node1.setPercent(50l);

		Node node2 = new Node("Total");
		node2.setPercent(55l);

		node2.compareWith(node1);
		assertThat(node2.getDiff(), equalTo(5l));

		node2.setPercent(45l);
		node2.compareWith(node1);
		assertThat(node2.getDiff(), equalTo(-5l));
	}

	@Test
	public void testCompareTree() {
		Node n1 = node("N1", 10);
		Node n11 = node("N1.1", 10);
		n1.getChildren().add(n11);
		
		Node n2 = node("N1", 9);
		Node n22 = node("N1.1", 15);
		n2.getChildren().add(n22);
		n2.compareWith(n1);
		assertThat(n2.getDiff(), equalTo(-1l));
		assertThat(n22.getDiff(), equalTo(5l));
	}

	private Node node(String name, long percent) {
		Node node = new Node(name);
		node.setPercent(percent);
		return node;
	}
}
