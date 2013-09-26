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
package org.ow2.sirocco.cimi.server.resource.serialization;

import java.io.StringReader;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import junit.framework.Assert;
import net.javacrumbs.jsonunit.JsonAssert;

import org.custommonkey.xmlunit.XMLAssert;
import org.junit.Ignore;
import org.junit.Test;
import org.ow2.sirocco.cimi.server.resource.serialization.json.JsonLocator;
import org.ow2.sirocco.cimi.server.resource.serialization.xml.XmlLocator;
import org.ow2.sirocco.cimi.server.utils.Constants;
import org.ow2.sirocco.cimi.server.utils.ConstantsPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Ignore
public class CloudEntryPointResourceSerializationTest extends SerializationTestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CloudEntryPointResourceSerializationTest.class);

    /**
     * Test GET.
     * 
     * @throws Exception In case of error
     */
    @Test
    public final void testGetCloudEntryPointJson() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // JSON : id = 2
        clientResponse = this.target().path(ConstantsPath.CLOUDENTRYPOINT).request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).get();
        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CloudEntryPointResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getHeaders());

        Assert.assertEquals(200, statusResponse);
        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "CloudEntryPoint-2.json"),
            new StringReader(entityResponse));
    }

    /**
     * Test GET.
     * 
     * @throws Exception In case of error
     */
    @Test
    public final void testGetCloudEntryPointXml() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // XML : id = 2
        clientResponse = this.target().path(ConstantsPath.CLOUDENTRYPOINT).request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).get();

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CloudEntryPointResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getHeaders());

        Assert.assertEquals(200, statusResponse);
        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "CloudEntryPoint-2.xml"),
            new StringReader(entityResponse));
    }

    /**
     * Test PUT.
     * 
     * @throws Exception In case of error
     */
    @Ignore
    public final void testPutCloudEntryPointJson() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // JSON : id = 2
        clientResponse = this
            .target()
            .path(ConstantsPath.CLOUDENTRYPOINT)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .put(
                Entity.entity(SerializationHelper.getResourceAsString(JsonLocator.class, "CloudEntryPoint-2.json"),
                    MediaType.APPLICATION_JSON_TYPE));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CloudEntryPointResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getHeaders());

        Assert.assertEquals(200, statusResponse);
        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "Job-2.json"), new StringReader(
            entityResponse));

    }

    /**
     * Test PUT.
     * 
     * @throws Exception In case of error
     */
    @Ignore
    public final void testPutCloudEntryPointXml() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // XML : id = 2
        clientResponse = this
            .target()
            .path(ConstantsPath.CLOUDENTRYPOINT)
            .request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .put(
                Entity.entity(SerializationHelper.getResourceAsString(XmlLocator.class, "CloudEntryPoint-2.xml"),
                    MediaType.APPLICATION_XML));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CloudEntryPointResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CloudEntryPointResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getHeaders());

        Assert.assertEquals(200, statusResponse);
        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "Job-2.xml"), new StringReader(
            entityResponse));

    }

}
