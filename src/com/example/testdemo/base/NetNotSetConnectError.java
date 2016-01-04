package com.example.testdemo.base;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

public class NetNotSetConnectError extends VolleyError {

	private static final long serialVersionUID = -4903134526926471298L;

	public NetNotSetConnectError() {
		super();
	}

	public NetNotSetConnectError(NetworkResponse response) {
		super(response);
	}

	public NetNotSetConnectError(String exceptionMessage, Throwable reason) {
		super(exceptionMessage, reason);
	}

	public NetNotSetConnectError(String exceptionMessage) {
		super(exceptionMessage);
	}

	public NetNotSetConnectError(Throwable cause) {
		super(cause);
	}

}
