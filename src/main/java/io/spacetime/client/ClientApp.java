package io.spacetime.client;

import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import io.spacetime.entity.Spacetime;
import io.spacetime.service.SpacetimeServiceIntf;

public class ClientApp {

	public static void main(String[] args) {
		try {
			Context jndi = new InitialContext();
			
			SpacetimeServiceIntf service = (SpacetimeServiceIntf) 
					 jndi.lookup(
							 //"io.spacetime.service.SpacetimeServiceIntf"
							 "java:global/Spacetime/SpacetimeServiceImpl"
							 );
			
			List<Spacetime> spacetimes = service.getAllSpacetimes();
			
			for (Spacetime s : spacetimes) {
				System.out.println(s);
			}
			
		} catch (NamingException e) {
			System.out.println(e);
		}

	}

}
