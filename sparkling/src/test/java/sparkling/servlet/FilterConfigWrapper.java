package sparkling.servlet;

import java.util.Enumeration;

import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;


public class FilterConfigWrapper implements FilterConfig {
 
    private FilterConfig delegate;

    public FilterConfigWrapper(FilterConfig delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public String getFilterName() {
        return delegate.getFilterName();
    }

    @Override
    public String getInitParameter(String name) {
        if (name.equals("applicationClass")) {
            return "sparkling.servlet.MyApp";
        }
        return delegate.getInitParameter(name);
    }

    @Override
    public Enumeration<String> getInitParameterNames() {
        return delegate.getInitParameterNames();
    }

    @Override
    public ServletContext getServletContext() {
        return delegate.getServletContext();
    }
    
}