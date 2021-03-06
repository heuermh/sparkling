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
package sparkling.examples.staticresources;

import sparkling.Request;
import sparkling.Response;
import sparkling.Route;

import static sparkling.Sparkling.get;
import static sparkling.Sparkling.staticFileLocation;

/**
 * Example showing how serve static resources.
 */
public class StaticResources {

    public static void main(String[] args) {

        // Will serve all static file are under "/public" in classpath if the route isn't consumed by others routes.
        staticFileLocation("/public");

        get(new Route("/hello") {
            @Override
            public Object handle(Request request, Response response) {
                return "Hello World!";
            }
        });
    }
}
