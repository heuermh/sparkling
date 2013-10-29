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
package sparkling.webserver;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import sparkling.Access;
import sparkling.HaltException;
import sparkling.Request;
import sparkling.RequestResponseFactory;
import sparkling.Response;
import sparkling.Route;
import sparkling.route.HttpMethod;
import sparkling.route.RouteMatch;
import sparkling.route.RouteMatcher;

/**
 * Filter for matching of filters and routes.
 * 
 * @author Per Wendel
 */
public class MatcherFilter implements Filter {
    private static final org.slf4j.Logger LOG = org.slf4j.LoggerFactory.getLogger(MatcherFilter.class);

    private final RouteMatcher routeMatcher;
    private final boolean isServletContext;
    private final boolean hasOtherHandlers;

    /**
     * Constructor
     * 
     * @param routeMatcher The route matcher
     * @param isServletContext If true, chain.doFilter will be invoked if request is not consumed by Sparkling.
     * @param hasOtherHandlers If true, do nothing if request is not consumed by Sparkling in order to let others handlers process the request.
     */
    public MatcherFilter(final RouteMatcher routeMatcher, final boolean isServletContext, final boolean hasOtherHandlers) {
        this.routeMatcher = routeMatcher;
        this.isServletContext = isServletContext;
        this.hasOtherHandlers = hasOtherHandlers;
    }

    @Override
    public void init(final FilterConfig filterConfig) {
        //
    }

    @Override
    public void doFilter(final ServletRequest servletRequest, final ServletResponse servletResponse, final FilterChain chain)
                throws IOException, ServletException {

        long t0 = System.currentTimeMillis();
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String httpMethodStr = httpRequest.getMethod().toLowerCase();
        String uri = httpRequest.getRequestURI();

        String bodyContent = null;
        RequestWrapper req = new RequestWrapper();
        ResponseWrapper res = new ResponseWrapper();

        LOG.debug("httpMethod:" + httpMethodStr + ", uri: " + uri);
        try {
            // BEFORE filters
            List<RouteMatch> matchSet = routeMatcher.findTargetsForRequestedRoute(HttpMethod.before, uri);

            for (RouteMatch filterMatch : matchSet) {
                Object filterTarget = filterMatch.getTarget();
                if (filterTarget instanceof sparkling.Filter) {
                    Request request = RequestResponseFactory.create(filterMatch, httpRequest);
                    Response response = RequestResponseFactory.create(httpResponse);

                    sparkling.Filter filter = (sparkling.Filter) filterTarget;

                    req.setDelegate(request);
                    res.setDelegate(response);

                    filter.handle(req, res);

                    String bodyAfterFilter = Access.getBody(response);
                    if (bodyAfterFilter != null) {
                        bodyContent = bodyAfterFilter;
                    }
                }   
            }

            // BEFORE filters, END
            HttpMethod httpMethod = HttpMethod.valueOf(httpMethodStr);

            RouteMatch match = null;
            match = routeMatcher.findTargetForRequestedRoute(HttpMethod.valueOf(httpMethodStr), uri);

            Object target = null;
            if (match != null) {
                target = match.getTarget();
            }
            else if (httpMethod == HttpMethod.head && bodyContent == null) {
                // See if get is mapped to provide default head mapping
                bodyContent = routeMatcher.findTargetForRequestedRoute(HttpMethod.get, uri) != null ? "" : null;
            }

            if (target != null) {
                try {
                    Object result = null;
                    if (target instanceof Route) {
                        Route route = ((Route) target);
                        Request request = RequestResponseFactory.create(match, httpRequest);
                        Response response = RequestResponseFactory.create(httpResponse);
                        
                        req.setDelegate(request);
                        res.setDelegate(response);
                        
                        result = route.handle(req, res);
                    }
                    if (result != null) {
                        bodyContent = result.toString();
                    }
                    long t1 = System.currentTimeMillis() - t0;
                    LOG.debug("Time for request: " + t1);
                } catch (HaltException hEx) { // NOSONAR
                    throw hEx; // NOSONAR
                } catch (Exception e) {
                    LOG.error("", e);
                    httpResponse.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                    bodyContent = INTERNAL_ERROR;
                }
            }

            // AFTER filters
            matchSet = routeMatcher.findTargetsForRequestedRoute(HttpMethod.after, uri);

            for (RouteMatch filterMatch : matchSet) {
                Object filterTarget = filterMatch.getTarget();
                if (filterTarget instanceof sparkling.Filter) {
                    Request request = RequestResponseFactory.create(filterMatch, httpRequest);
                    Response response = RequestResponseFactory.create(httpResponse);

                    req.setDelegate(request);
                    res.setDelegate(response);

                    sparkling.Filter filter = (sparkling.Filter) filterTarget;
                    filter.handle(req, res);

                    String bodyAfterFilter = Access.getBody(response);
                    if (bodyAfterFilter != null) {
                        bodyContent = bodyAfterFilter;
                    }
                }
            }
            // AFTER filters, END
        }
        catch (HaltException hEx) {
            LOG.debug("halt performed");
            httpResponse.setStatus(hEx.getStatusCode());
            if (hEx.getBody() != null) {
                bodyContent = hEx.getBody();
            }
            else {
                bodyContent = "";
            }
        }

        boolean consumed = bodyContent != null;

        if (!consumed && hasOtherHandlers) {
            throw new NotConsumedException();
        }

        if (!consumed && !isServletContext) {
            httpResponse.setStatus(HttpServletResponse.SC_NOT_FOUND);
            bodyContent = NOT_FOUND;
            consumed = true;
        }

        if (consumed) {
            // Write body content
            if (!httpResponse.isCommitted()) {
                httpResponse.getOutputStream().write(bodyContent.getBytes("utf-8"));
            }
        }
        else if (chain != null) {
            chain.doFilter(httpRequest, httpResponse);
        }
    }

    @Override
    public void destroy() {
        // empty
    }

    private static final String NOT_FOUND = "<html><body><h2>404 Not found</h2>The requested route has not been mapped in Sparkling</body></html>";
    private static final String INTERNAL_ERROR = "<html><body><h2>500 Internal Error</h2></body></html>";
}
