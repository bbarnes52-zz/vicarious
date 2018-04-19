package vicarious;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.inject.Inject;
import java.io.IOException;
import java.util.HashMap;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;

public class VicariousAppEngine extends HttpServlet {

  @Inject private SecureRandomNumberGenerator randomNumberGenerator;

  private static final Logger log = Logger.getLogger(VicariousAppEngine.class.getName());

  @Override
  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.setStatus(200);
    // Set cache-control to False since GETs are idempotent.
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    JSONObject json = new JSONObject();
    try {
      json.put("retval", this.randomNumberGenerator.get(0, 100));
    } catch (JSONException e) {
      // This indicates a bug in the system.
      throw new AssertionError();
    }
    response.getWriter().write(json.toString());
  }

  @Override
  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String requestBody =
        request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    log.info(String.format("Received a POST request with the following body %s", requestBody));
    HashMap<String, Integer> requestBodyMap;
    String errorMessage;
    try {
      requestBodyMap =
          new Gson().fromJson(requestBody, new TypeToken<HashMap<String, Integer>>() {}.getType());
    } catch (JsonSyntaxException e) {
      errorMessage = String.format("Request body could not be parsed as JSON %s", requestBody);
      log.severe(errorMessage);
      response.sendError(400, errorMessage);
      return;
    }
    errorMessage = this.validateRequestBodyFields(requestBodyMap);
    // TODO(bgb): Best practices for null checks.
    if (errorMessage != null) {
      log.severe(errorMessage);
      response.sendError(400, errorMessage);
      return;
    }
    JSONObject responseBody = new JSONObject();
    try {
      responseBody.put(
          "retval",
          this.randomNumberGenerator.get(
              requestBodyMap.get("minVal"), requestBodyMap.get("maxVal")));
    } catch (JSONException e) {
      // This indicates a bug in the system.
      throw new AssertionError();
    }
    response.setStatus(200);
    response.setContentType("application/json");
    response.getWriter().write(responseBody.toString());
  }

  // TODO(bgb): Annotate with @Nullable or use `java.util.Optional`.
  // TODO(bgb): Handle case where user specifies range outside system bounds.
  private String validateRequestBodyFields(HashMap<String, Integer> requestBodyMap) {
    String errorMessage;
    if (requestBodyMap.size() != 2
        || !(requestBodyMap.containsKey("minVal"))
        || !(requestBodyMap.containsKey("maxVal"))) {
      errorMessage =
          String.format(
              "Request body must contain exactly two int fields named `minVal` and `maxVal`, found  %s",
              requestBodyMap.keySet().toString());
      return errorMessage;
    }
    int maxVal = requestBodyMap.get("maxVal");
    int minVal = requestBodyMap.get("minVal");
    if (minVal >= maxVal) {
      errorMessage =
          String.format("`maxVal` must be greater than `minVal`, found %d and %d", maxVal, minVal);
      return errorMessage;
    }
    return null;
  }
}
