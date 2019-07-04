package com.olayc.common.msg;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author yangzhuo
 */
public class HttpResponse extends BaseResponse {

	@Autowired
	HttpServletResponse response;

	/**
	 * 200 Response
	 */
	public void ok(Object responseData) {
		response.setStatus(200);
		returnResponse(response, responseData);
	}

	/**
	 * 201 created Response
	 */
	public void created(Object responseData) {
		response.setStatus(201);
		returnResponse(response, responseData);
	}

	/**
	 * 202 Async Response
	 */
	public void accepted(Object responseData) {
		response.setStatus(202);
		returnResponse(response, responseData);
	}

	/**
	 * 204 Delete Response
	 */
	public void noContent(Object responseData) {
		response.setStatus(204);
		returnResponse(response, responseData);
	}

	/**
	 * 400 INVALID Response
	 */
	public void invalid(Object responseData) {
		response.setStatus(400);
		returnResponse(response, responseData);
	}

	/**
	 * 401 Response
	 */
	public void unauthorized(Object responseData) {
		response.setStatus(401);
		returnResponse(response, responseData);
	}

	/**
	 * 403 Response
	 */
	public void forbidden(Object responseData) {
		response.setStatus(403);
		returnResponse(response, responseData);
	}

	/**
	 * return Response
	 */
	private void returnResponse(HttpServletResponse response, Object responseObject) {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(toJsonString(responseObject));
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

	private static final ObjectMapper MAPPER;

	static {
		MAPPER = new ObjectMapper();

		MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true);
		MAPPER.configure(JsonParser.Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
	}

	private String toJsonString(Object value) {
		try {
			return MAPPER.writeValueAsString(value);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException("to json fail:" + value, e);
		}
	}
}
