/*
 * Copyright 2011-2013 Per Wendel, Michael Heuer
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
package sparkling.template.mustache;

import java.io.StringWriter;

import java.util.Map;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;

import sparkling.template.TemplateRoute;

/**
 * Template route based on Mustache.java.
 *
 * Example:
 * <pre>
 * Sparkling.get(new MustacheTemplateRoute("/hello/:name") {
 *    public Object handle(Request request, Response response) {
 *       Person person = Person.find(request.params("name"));
 *       return template("hello.mustache").render("person", person);
 *    }
 * });
 * </pre>
 */
public abstract class MustacheTemplateRoute extends TemplateRoute {
    private final MustacheFactory mustacheFactory;

    /**
     * Constructor
     * 
     * @param path The route path which is used for matching. (e.g. /hello, users/:name) 
     */
    protected MustacheTemplateRoute(final String path) {
        super(path);
        mustacheFactory = new DefaultMustacheFactory();
    }

    /**
     * Constructor
     * 
     * @param path The route path which is used for matching. (e.g. /hello, users/:name)
     * @param mustacheFactory The mustache factory, must not be null.
     */
    protected MustacheTemplateRoute(final String path, final MustacheFactory mustacheFactory) {
        super(path);
        if (mustacheFactory == null) {
            throw new NullPointerException("mustacheFactory must not be null");
        }
        this.mustacheFactory = mustacheFactory;
    }


    @Override
    public final Template template(final String name) {
        return new MustacheTemplate(mustacheFactory.compile(name));
    }

    /**
     * Mustache template.
     */
    private final class MustacheTemplate extends Template {
        private final Mustache mustache;

        private MustacheTemplate(final Mustache mustache) {
            this.mustache = mustache;
        }

        @Override
        public Object render(final Map<String, Object> context) {
            StringWriter writer = new StringWriter();
            mustache.execute(writer, context);
            writer.flush();
            return writer;
        }
    }
}
