package edu.iis.mto.serverloadbalancer;

import static edu.iis.mto.serverloadbalancer.CurrentLoadPercentageMatcher.hasLoadPercentageOf;
import static edu.iis.mto.serverloadbalancer.ServerBuilder.server;
import static edu.iis.mto.serverloadbalancer.VmBuilder.vm;
import static org.hamcrest.MatcherAssert.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

@RunWith(Parameterized.class)
public class ServerLoadBalancerParametrizedTest extends ServerLoadBalancerBaseTest{
	private int capacity;
	private int size;
	private double expected;

	@Parameterized.Parameters
	public static Collection data() {
		return Arrays.asList(new Object[][] { { 1,1,100.0d}, {2,1,50.0d}, {12,7,58.33d}, {4,3,75.0d}, {9,7,77.77d}, {54,39, 72.22d}});
	}

	public ServerLoadBalancerParametrizedTest(int c1, int s1, double e1) {
		this.capacity = c1;
		this.size = s1;
		this.expected = e1;
	}
	@Test
	public void balancingOneServerWithOneSlotCapacity_andOneSlotVm_fillsTheServerWithTheVm() {
		Server theServer = a(server().withCapacity(this.capacity));
		Vm theVm = a(vm().ofSize(this.size));
		balance(aListOfServersWith(theServer), aListOfVmsWith(theVm));

		assertThat(theServer, hasLoadPercentageOf(this.expected));
		assertThat("the server should contain vm", theServer.contains(theVm));
	}
	
	
}
