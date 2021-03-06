package com.wemater.filter;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;
import javax.ws.rs.ext.Provider;

@Provider
public class CORSResponseFilter implements  ContainerResponseFilter{

	@Override
	public void filter(ContainerRequestContext requestContext,
			ContainerResponseContext responseContext) throws IOException {
		System.out.println("CORS FILTER EXECUTED");
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "*");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");	
		responseContext.getHeaders().add("Access-Control-Allow-Headers","Content-Type, Etag,If-None-Match,Authorization");
		responseContext.getHeaders().add("Access-Control-Expose-Headers","Content-Type, Etag,If-None-Match,Authorization");	
	}

}
