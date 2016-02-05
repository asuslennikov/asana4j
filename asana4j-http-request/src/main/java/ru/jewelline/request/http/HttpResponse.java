package ru.jewelline.request.http;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * This class provides access to HTTP response information.
 *
 */
public interface HttpResponse<T extends OutputStream> {
    /**
     * @return An HTTP server response code.
     */
    int code();

    /**
     * @return a map with pairs header-value, it is HTTP response headers.
     * It is unmodifiable map.
     */
    Map<String, List<String>> headers();

    /**
     * @return The stream which was used as a destination for server response.
     */
    T payload();
}