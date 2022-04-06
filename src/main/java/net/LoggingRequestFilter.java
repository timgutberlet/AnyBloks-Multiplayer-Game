package net;

import java.io.IOException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.MultivaluedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs incoming requests.
 *
 * @author tbuscher
 */
public class LoggingRequestFilter implements ContainerRequestFilter {

  private static final Logger LOG = LoggerFactory.getLogger(LoggingRequestFilter.class);

  /**
   * Filter request. Throws exception.
   *
   * @param containerRequestContext {@link ContainerRequestFilter}
   */
  @Override
  public void filter(ContainerRequestContext containerRequestContext) throws IOException {
    // e.g. get headers
    MultivaluedMap<String, String> headers = containerRequestContext.getHeaders();

    String userAgent = headers.getFirst("User-Agent");

    if (LOG.isInfoEnabled()) {
      LOG.info("Request from client '{}'", userAgent);
    }
  }
}
