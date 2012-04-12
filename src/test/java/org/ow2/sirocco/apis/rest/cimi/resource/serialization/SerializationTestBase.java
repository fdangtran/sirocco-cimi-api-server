/**
 *
 * SIROCCO
 * Copyright (C) 2011 France Telecom
 * Contact: sirocco@ow2.org
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
 * USA
 *
 * $Id$
 *
 */
package org.ow2.sirocco.apis.rest.cimi.resource.serialization;

import org.custommonkey.xmlunit.XMLUnit;
import org.junit.After;
import org.junit.BeforeClass;
import org.ow2.sirocco.apis.rest.cimi.server.SiroccoRestCimiApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoaderListener;

import com.sun.jersey.api.json.JSONConfiguration;
import com.sun.jersey.spi.spring.container.servlet.SpringServlet;
import com.sun.jersey.test.framework.AppDescriptor;
import com.sun.jersey.test.framework.JerseyTest;
import com.sun.jersey.test.framework.WebAppDescriptor;
import com.sun.jersey.test.framework.spi.container.TestContainerException;

public class SerializationTestBase extends JerseyTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SerializationTestBase.class);

    /**
     * {@inheritDoc}
     * 
     * @see com.sun.jersey.test.framework.JerseyTest#configure()
     */
    @Override
    protected AppDescriptor configure() {
        return new WebAppDescriptor.Builder("javax.ws.rs.Application", SiroccoRestCimiApplication.class.getName())
            .initParam(JSONConfiguration.FEATURE_POJO_MAPPING, "true").contextPath("sirocco-rest")
            .servletClass(SpringServlet.class).contextListenerClass(ContextLoaderListener.class)
            .contextParam("contextConfigLocation", "classpath:context/serializationResourcesContext.xml").build();
    }

    /**
     * @throws Exception In case of error
     */
    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        XMLUnit.setIgnoreWhitespace(true);
        XMLUnit.setIgnoreComments(true);
        XMLUnit.setIgnoreAttributeOrder(true);
    }

    @Override
    @After
    public void tearDown() throws Exception {
        super.tearDown();
        int delay = 1;
        String propDelay = System.getProperty("SIROCCO_TEST_DELAY");
        if (null != propDelay) {
            try {
                delay = Integer.parseInt(propDelay);
            } catch (NumberFormatException e) {
                throw new TestContainerException("SIROCCO_TEST_DELAY with a value of \"" + propDelay
                    + "\" is not a valid integer.", e);
            }
        }

        if (delay > 0) {
            SerializationTestBase.LOGGER.debug("Begin delay : {}ms", delay);
            Thread.sleep(delay);
            SerializationTestBase.LOGGER.debug("End delay");
        }
    }
}
