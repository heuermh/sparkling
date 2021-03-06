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
package sparkling.examples.filter;

import static sparkling.Sparkling.after;
import static sparkling.Sparkling.get;
import sparkling.Filter;
import sparkling.Request;
import sparkling.Response;
import sparkling.Route;

/**
 * Example showing the use of attributes
 *
 * @author Per Wendel
 */
public class FilterExampleAttributes {

    public static void main(String[] args) {
        get(new Route("/hi") {
            @Override
            public Object handle(Request request, Response response) {
                request.attribute("foo", "bar");
                return null;
            }
        });
        
        after(new Filter("/hi") {
            @Override
            public void handle(Request request, Response response) {
                for (String attr : request.attributes()) {
                    System.out.println("attr: " + attr);
                }
            }
        });
        
        after(new Filter("/hi") {
            @Override
            public void handle(Request request, Response response) {
                Object foo = request.attribute("foo");
                response.body(asXml("foo", foo));
            }
        });
    }
    
    private static String asXml(String name, Object value) {
        return "<?xml version=\"1.0\" encoding=\"UTF-8\"?><" + name +">" + value + "</"+ name + ">";
    }
    
}
