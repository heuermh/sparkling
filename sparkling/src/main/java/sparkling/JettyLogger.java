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

import org.eclipse.jetty.util.log.Logger;

/**
 * Jetty Logger
 *
 * @author Per Wendel
 */
public class JettyLogger implements Logger {
    private org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(getClass());

    public void debug(final String msg, final Throwable th) {
        logger.debug(msg, th);
    }

    public Logger getLogger(final String arg) {
        return this;
    }

    @Override
    public boolean isDebugEnabled() {
        return logger.isDebugEnabled();
    }

    @Override
    public void warn(final String msg, final Throwable th) {
        logger.warn(msg, th);
    }

    @Override
    public void debug(final Throwable thrown) {
        logger.debug("", thrown);

    }

    @Override
    public void debug(final String msg, final Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        logger.debug(log.toString());
    }

    @Override
    public void debug(String msg, long value) {
        logger.debug(msg, value);
    }

    @Override
    public String getName() {
        return "Sparkling Jetty Logger";
    }

    @Override
    public void ignore(final Throwable ignored) {
        //
    }

    @Override
    public void info(final Throwable thrown) {
        logger.info("", thrown);
    }

    @Override
    public void info(final String msg, final Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        logger.info(log.toString());
    }

    @Override
    public void info(final String msg, final Throwable thrown) {
        logger.info(msg, thrown);
    }

    @Override
    public void setDebugEnabled(final boolean enabled) {
        // 
    }

    @Override
    public void warn(final Throwable thrown) {
        logger.warn("", thrown);
    }

    @Override
    public void warn(final String msg, final Object... args) {
        StringBuffer log = new StringBuffer(msg);
        for (Object arg : args) {
            log.append(", ");
            log.append(arg);
        }
        logger.warn(log.toString());
    }
}
