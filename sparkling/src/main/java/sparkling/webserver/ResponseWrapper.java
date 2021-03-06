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

import javax.servlet.http.HttpServletResponse;

import sparkling.Response;

class ResponseWrapper extends Response {

    private Response delegate;

    public void setDelegate(final Response delegate) {
        this.delegate = delegate;
    }

    @Override
    public void status(final int statusCode) {
        delegate.status(statusCode);
    }

    @Override
    public void body(final String body) {
        delegate.body(body);
    }

    @Override
    public boolean equals(final Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public HttpServletResponse raw() {
        return delegate.raw();
    }

    @Override
    public void redirect(final String location) {
        delegate.redirect(location);
    }

    @Override
    public void redirect(final String location, final int httpStatusCode) {
        delegate.redirect(location, httpStatusCode);
    }

    @Override
    public void header(final String header, final String value) {
        delegate.header(header, value);
    }

    @Override
    public String toString() {
        return delegate.toString();
    }

    @Override
    public void type(final String contentType) {
        delegate.type(contentType);
    }

    @Override
    public void cookie(final String name, final String value) {
        delegate.cookie(name, value);
    }

    @Override
    public void cookie(final String name, final String value, final int maxAge) {
        delegate.cookie(name, value, maxAge);
    }

    @Override
    public void cookie(final String name, final String value, final int maxAge, final boolean secured) {
        delegate.cookie(name, value, maxAge, secured);
    }

    @Override
    public void removeCookie(final String name) {
        delegate.removeCookie(name);
    }
}
