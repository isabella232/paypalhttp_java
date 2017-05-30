package com.braintreepayments.http;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.NonFinal;

import java.util.Base64;

@Data()
@NonFinal
@Accessors(chain=true, fluent = true)
public class HttpRequest<T> {

	public HttpRequest(String path, String verb, Class<T> responseClass) {
		this.path = path;
		this.verb = verb;
		this.responseClass = responseClass;
	}

	private String baseUrl;
	private String path;
	private String verb;
	private Object requestBody;

	@Getter
	@Setter(AccessLevel.NONE)
	private Class<T> responseClass;

	@Getter
	@Setter(AccessLevel.NONE)
	private Headers headers = new Headers();


	public HttpRequest<T> header(String header, String value) {
		headers.header(header, value);
		return this;
	}

	public String url() {
		return baseUrl + path;
	}

	public void basicAuthorization(String publicKey, String privateKey) {
		String auth = new String(Base64.getEncoder().encode((publicKey + ":" + privateKey).getBytes()));
		headers.headerIfNotPresent("Authorization", "Basic " + auth);
	}
}
