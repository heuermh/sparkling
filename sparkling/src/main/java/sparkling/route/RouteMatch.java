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
package sparkling.route;

/**
 * 
 *
 * @author Per Wendel
 */
public class RouteMatch {
    private final HttpMethod httpMethod;
    private final Object target;
    private final String matchUri;
    private final String requestURI;

    public RouteMatch(final HttpMethod httpMethod, final Object target, final String matchUri, final String requestUri) {
        super();
        this.httpMethod = httpMethod;
        this.target = target;
        this.matchUri = matchUri;
        this.requestURI = requestUri;
    }


    /**
     * @return the httpMethod
     */
    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    /**
     * @return the target
     */
    public Object getTarget() {
        return target;
    }

    /**
     * @return the matchUri
     */
    public String getMatchUri() {
        return matchUri;
    }

    /**
     * @return the requestUri
     */
    public String getRequestURI() {
        return requestURI;
    }
}
