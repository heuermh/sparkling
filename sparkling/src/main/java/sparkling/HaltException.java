/*
 * Copyright 2011- Per Wendel
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package sparkling;

import javax.servlet.http.HttpServletResponse;

/**
 * Exception used for stopping the execution
 *
 * @author Per Wendel
 */
public class HaltException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    static final int DEFAULT_STATUS_CODE = HttpServletResponse.SC_OK;
    static final String DEFAULT_BODY = null;
    private final int statusCode;
    private final String body;

    HaltException() {
        this(DEFAULT_STATUS_CODE, DEFAULT_BODY);
    }

    HaltException(final int statusCode) {
        this(statusCode, DEFAULT_BODY);
    }

    HaltException(final String body) {
        this(DEFAULT_STATUS_CODE, body);
    }

    HaltException(final int statusCode, final String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    /**
     * @return the statusCode
     */
    public int getStatusCode() {
        return statusCode;
    }

    
    /**
     * @return the body
     */
    public String getBody() {
        return body;
    }
}
