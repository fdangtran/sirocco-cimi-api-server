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
import javax.ws.rs.core.MultivaluedMap;
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
public class CredentialsTemplateResourceSerializationTest extends SerializationTestBase {

    private static final Logger LOGGER = LoggerFactory.getLogger(CredentialsTemplateResourceSerializationTest.class);

    /**
     * Test GET.
     * 
     * @throws Exception In case of error
     */
    @Test
    public final void testGetCredentialsTemplateJson() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // JSON : id = 0
        clientResponse = this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE + "/0").request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).get();
        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "CredentialsTemplate-0.json"),
            new StringReader(entityResponse));

        // JSON : id = 1
        clientResponse = this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE + "/1").request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).get();
        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "CredentialsTemplate-1.json"),
            new StringReader(entityResponse));

        // JSON : id = 2
        clientResponse = this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE + "/2").request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).get();

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "CredentialsTemplate-2.json"),
            new StringReader(entityResponse));
    }

    /**
     * Test GET.
     * 
     * @throws Exception In case of error
     */
    @Test
    public final void testGetCredentialsTemplateXml() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // XML : id = 0
        clientResponse = this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE + "/0").request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).get();
        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "CredentialsTemplate-0.xml"),
            new StringReader(entityResponse));

        // XML : id = 1
        clientResponse = this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE + "/1").request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).get();
        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "CredentialsTemplate-1.xml"),
            new StringReader(entityResponse));

        // XML : id = 2
        clientResponse = this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE + "/2").request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).get();

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "CredentialsTemplate-2.xml"),
            new StringReader(entityResponse));
    }

    /**
     * Test GET.
     * 
     * @throws Exception In case of error
     */
    // @Test
    // @Ignore
    // public final void testGetCredentialsCollectionJson() throws Exception {
    // Response clientResponse = null;
    // String entityResponse;
    // int statusResponse;
    //
    // // JSON : id = 0
    // clientResponse =
    // this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE).request(MediaType.APPLICATION_JSON_TYPE)
    // .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
    // .header(Constants.HEADER_SIROCCO_INFO_TEST_ID,
    // 0).get(Response.class);
    //
    // statusResponse = clientResponse.getStatus();
    // entityResponse = clientResponse.readEntity(String.class);
    //
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}",
    // clientResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}",
    // statusResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}",
    // entityResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}",
    // clientResponse.getStringHeaders());
    //
    // Assert.assertEquals(200, statusResponse);
    // JsonAssert.assertJsonEquals(
    // SerializationHelper.getResourceAsReader(JsonLocator.class,
    // "CredentialsTemplateCollection-0.json"),
    // new StringReader(entityResponse));
    //
    // // JSON : id = 1
    // clientResponse =
    // this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE).request(MediaType.APPLICATION_JSON_TYPE)
    // .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
    // .header(Constants.HEADER_SIROCCO_INFO_TEST_ID,
    // 1).get(Response.class);
    //
    // statusResponse = clientResponse.getStatus();
    // entityResponse = clientResponse.readEntity(String.class);
    //
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}",
    // clientResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}",
    // statusResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}",
    // entityResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}",
    // clientResponse.getStringHeaders());
    //
    // Assert.assertEquals(200, statusResponse);
    // JsonAssert.assertJsonEquals(
    // SerializationHelper.getResourceAsReader(JsonLocator.class,
    // "CredentialsTemplateCollection-1.json"),
    // new StringReader(entityResponse));
    //
    // // JSON : id = 3
    // clientResponse =
    // this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE).request(MediaType.APPLICATION_JSON_TYPE)
    // .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
    // .header(Constants.HEADER_SIROCCO_INFO_TEST_ID,
    // 3).get(Response.class);
    //
    // statusResponse = clientResponse.getStatus();
    // entityResponse = clientResponse.readEntity(String.class);
    //
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}",
    // clientResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}",
    // statusResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}",
    // entityResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}",
    // clientResponse.getStringHeaders());
    //
    // Assert.assertEquals(200, statusResponse);
    // JsonAssert.assertJsonEquals(
    // SerializationHelper.getResourceAsReader(JsonLocator.class,
    // "CredentialsTemplateCollection-3.json"),
    // new StringReader(entityResponse));
    //
    // // JSON : id = 3, expand
    // clientResponse =
    // this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE).request(MediaType.APPLICATION_JSON_TYPE)
    // .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
    // .header(Constants.HEADER_SIROCCO_INFO_TEST_ID,
    // 3).header(Constants.HEADER_SIROCCO_INFO_TEST_EXPAND, true)
    // .get(Response.class);
    //
    // statusResponse = clientResponse.getStatus();
    // entityResponse = clientResponse.readEntity(String.class);
    //
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}",
    // clientResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}",
    // statusResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}",
    // entityResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}",
    // clientResponse.getStringHeaders());
    //
    // Assert.assertEquals(200, statusResponse);
    // JsonAssert.assertJsonEquals(
    // SerializationHelper.getResourceAsReader(JsonLocator.class,
    // "CredentialsTemplateCollection-3-expand.json"),
    // new StringReader(entityResponse));
    // }

    /**
     * Test GET.
     * 
     * @throws Exception In case of error
     */
    // @Test
    // @Ignore
    // public final void testGetCredentialsCollectionXml() throws Exception {
    // Response clientResponse = null;
    // String entityResponse;
    // int statusResponse;
    //
    // // XML : id = 0
    // clientResponse =
    // this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE).request(MediaType.APPLICATION_XML_TYPE)
    // .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
    // .header(Constants.HEADER_SIROCCO_INFO_TEST_ID,
    // 0).get(Response.class);
    //
    // statusResponse = clientResponse.getStatus();
    // entityResponse = clientResponse.readEntity(String.class);
    //
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}",
    // clientResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}",
    // statusResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}",
    // entityResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}",
    // clientResponse.getStringHeaders());
    //
    // Assert.assertEquals(200, statusResponse);
    // XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class,
    // "CredentialsTemplateCollection-0.xml"), new
    // StringReader(entityResponse));
    //
    // // XML : id = 1
    // clientResponse =
    // this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE).request(MediaType.APPLICATION_XML_TYPE)
    // .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
    // .header(Constants.HEADER_SIROCCO_INFO_TEST_ID,
    // 1).get(Response.class);
    //
    // statusResponse = clientResponse.getStatus();
    // entityResponse = clientResponse.readEntity(String.class);
    //
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}",
    // clientResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}",
    // statusResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}",
    // entityResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}",
    // clientResponse.getStringHeaders());
    //
    // Assert.assertEquals(200, statusResponse);
    // XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class,
    // "CredentialsTemplateCollection-1.xml"), new
    // StringReader(entityResponse));
    //
    // // XML : id = 3
    // clientResponse =
    // this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE).request(MediaType.APPLICATION_XML_TYPE)
    // .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
    // .header(Constants.HEADER_SIROCCO_INFO_TEST_ID,
    // 3).get(Response.class);
    //
    // statusResponse = clientResponse.getStatus();
    // entityResponse = clientResponse.readEntity(String.class);
    //
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}",
    // clientResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}",
    // statusResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}",
    // entityResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}",
    // clientResponse.getStringHeaders());
    //
    // Assert.assertEquals(200, statusResponse);
    // XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class,
    // "CredentialsTemplateCollection-3.xml"), new
    // StringReader(entityResponse));
    //
    // // XML : id = 3, expand
    // clientResponse =
    // this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE).request(MediaType.APPLICATION_XML_TYPE)
    // .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
    // .header(Constants.HEADER_SIROCCO_INFO_TEST_ID,
    // 3).header(Constants.HEADER_SIROCCO_INFO_TEST_EXPAND, true)
    // .get(Response.class);
    //
    // statusResponse = clientResponse.getStatus();
    // entityResponse = clientResponse.readEntity(String.class);
    //
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}",
    // clientResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}",
    // statusResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}",
    // entityResponse);
    // CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}",
    // clientResponse.getStringHeaders());
    //
    // Assert.assertEquals(200, statusResponse);
    // XMLAssert.assertXMLEqual(
    // SerializationHelper.getResourceAsReader(XmlLocator.class,
    // "CredentialsTemplateCollection-3-expand.xml"),
    // new StringReader(entityResponse));
    // }

    /**
     * Test PUT.
     * 
     * @throws Exception In case of error
     */
    @Test
    public final void testPutCredentialsTemplateJson() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // JSON : id = 0
        clientResponse = this
            .target()
            .path(ConstantsPath.CREDENTIAL_TEMPLATE + "/0")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .put(
                Entity.entity(SerializationHelper.getResourceAsString(JsonLocator.class, "CredentialsTemplate-0.json"),
                    MediaType.APPLICATION_JSON_TYPE));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "Job-0.json"), new StringReader(
            entityResponse));

        // JSON : id = 1
        clientResponse = this
            .target()
            .path(ConstantsPath.CREDENTIAL_TEMPLATE + "/1")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .put(
                Entity.entity(SerializationHelper.getResourceAsString(JsonLocator.class, "CredentialsTemplate-1.json"),
                    MediaType.APPLICATION_JSON_TYPE));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "Job-1.json"), new StringReader(
            entityResponse));

        // JSON : id = 2
        clientResponse = this
            .target()
            .path(ConstantsPath.CREDENTIAL_TEMPLATE + "/2")
            .request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .put(
                Entity.entity(SerializationHelper.getResourceAsString(JsonLocator.class, "CredentialsTemplate-2.json"),
                    MediaType.APPLICATION_JSON_TYPE));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "Job-2.json"), new StringReader(
            entityResponse));

    }

    /**
     * Test PUT.
     * 
     * @throws Exception In case of error
     */
    @Test
    public final void testPutCredentialsTemplateXml() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // XML : id = 0
        clientResponse = this
            .target()
            .path(ConstantsPath.CREDENTIAL_TEMPLATE + "/0")
            .request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .put(
                Entity.entity(SerializationHelper.getResourceAsString(XmlLocator.class, "CredentialsTemplate-0.xml"),
                    MediaType.APPLICATION_XML));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "Job-0.xml"), new StringReader(
            entityResponse));

        // XML : id = 1
        clientResponse = this
            .target()
            .path(ConstantsPath.CREDENTIAL_TEMPLATE + "/1")
            .request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .put(
                Entity.entity(SerializationHelper.getResourceAsString(XmlLocator.class, "CredentialsTemplate-1.xml"),
                    MediaType.APPLICATION_XML));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "Job-1.xml"), new StringReader(
            entityResponse));

        // XML : id = 2
        clientResponse = this
            .target()
            .path(ConstantsPath.CREDENTIAL_TEMPLATE + "/2")
            .request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .put(
                Entity.entity(SerializationHelper.getResourceAsString(XmlLocator.class, "CredentialsTemplate-2.xml"),
                    MediaType.APPLICATION_XML));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "Job-2.xml"), new StringReader(
            entityResponse));

    }

    /**
     * Test POST.
     * 
     * @throws Exception In case of error
     */
    @Test
    public final void testPostCredentialsTemplateJson() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;
        MultivaluedMap<String, String> heardersResponse;

        // JSON : id = 1
        clientResponse = this
            .target()
            .path(ConstantsPath.CREDENTIAL_TEMPLATE)
            .request(MediaType.APPLICATION_JSON_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .post(
                Entity.entity(SerializationHelper.getResourceAsString(JsonLocator.class, "CredentialsTemplate-1.json"),
                    MediaType.APPLICATION_JSON));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);
        heardersResponse = clientResponse.getStringHeaders();

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(202, statusResponse);

        Assert.assertTrue(heardersResponse.containsKey(Constants.HEADER_CIMI_JOB_URI));
        Assert.assertTrue(heardersResponse.get(Constants.HEADER_CIMI_JOB_URI).get(0).endsWith("idValue_1"));

        Assert.assertTrue(heardersResponse.containsKey(Constants.HEADER_LOCATION));
        Assert.assertTrue(heardersResponse.get(Constants.HEADER_LOCATION).get(0)
            .endsWith(ConstantsPath.CREDENTIAL_TEMPLATE + "/" + "targetResourceValue_1"));

        JsonAssert.assertJsonEquals(SerializationHelper.getResourceAsReader(JsonLocator.class, "Job-1.json"), new StringReader(
            entityResponse));
    }

    /**
     * Test POST.
     * 
     * @throws Exception In case of error
     */
    @Test
    public final void testPostCredentialsTemplateXml() throws Exception {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;
        MultivaluedMap<String, String> heardersResponse;

        // XML : id = 1
        clientResponse = this
            .target()
            .path(ConstantsPath.CREDENTIAL_TEMPLATE)
            .request(MediaType.APPLICATION_XML_TYPE)
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI)
            .post(
                Entity.entity(SerializationHelper.getResourceAsString(XmlLocator.class, "CredentialsTemplate-1.xml"),
                    MediaType.APPLICATION_XML));

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);
        heardersResponse = clientResponse.getStringHeaders();

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(202, statusResponse);

        Assert.assertTrue(heardersResponse.containsKey(Constants.HEADER_CIMI_JOB_URI));
        Assert.assertTrue(heardersResponse.get(Constants.HEADER_CIMI_JOB_URI).get(0).endsWith("idValue_1"));

        Assert.assertTrue(heardersResponse.containsKey(Constants.HEADER_LOCATION));
        Assert.assertTrue(heardersResponse.get(Constants.HEADER_LOCATION).get(0)
            .endsWith(ConstantsPath.CREDENTIAL_TEMPLATE + "/" + "targetResourceValue_1"));

        XMLAssert.assertXMLEqual(SerializationHelper.getResourceAsReader(XmlLocator.class, "Job-1.xml"), new StringReader(
            entityResponse));
    }

    @Test
    public final void testDeleteCredentialsTemplate() {
        Response clientResponse = null;
        String entityResponse;
        int statusResponse;

        // JSON and XML : id = 0
        clientResponse = this.target().path(ConstantsPath.CREDENTIAL_TEMPLATE + "/0").request()
            .header(Constants.HEADER_CIMI_VERSION, Constants.VERSION_DMTF_CIMI).delete();

        statusResponse = clientResponse.getStatus();
        entityResponse = clientResponse.readEntity(String.class);

        CredentialsTemplateResourceSerializationTest.LOGGER.debug("COMPLETE:\n\t{}", clientResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("STATUS: {}", statusResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("ENTITY:\n\t{}", entityResponse);
        CredentialsTemplateResourceSerializationTest.LOGGER.debug("HEADER:\n\t{}", clientResponse.getStringHeaders());

        Assert.assertEquals(200, statusResponse);
    }

}
