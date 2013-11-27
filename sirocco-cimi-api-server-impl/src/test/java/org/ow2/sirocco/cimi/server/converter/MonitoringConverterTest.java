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
package org.ow2.sirocco.cimi.server.converter;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.ow2.sirocco.cimi.domain.CimiEvent;
import org.ow2.sirocco.cimi.domain.CimiJob;
import org.ow2.sirocco.cimi.domain.CimiMachine;
import org.ow2.sirocco.cimi.domain.ExchangeType;
import org.ow2.sirocco.cimi.domain.collection.CimiJobCollection;
import org.ow2.sirocco.cimi.domain.collection.CimiJobCollectionRoot;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cimi.server.request.CimiContextImpl;
import org.ow2.sirocco.cimi.server.request.CimiExpand;
import org.ow2.sirocco.cimi.server.request.CimiRequest;
import org.ow2.sirocco.cimi.server.request.CimiResponse;
import org.ow2.sirocco.cimi.server.request.CimiSelect;
import org.ow2.sirocco.cimi.server.request.RequestParams;
import org.ow2.sirocco.cimi.server.utils.ConstantsPath;
import org.ow2.sirocco.cloudmanager.model.cimi.CloudResource;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.ow2.sirocco.cloudmanager.model.cimi.event.AccessEventType;
import org.ow2.sirocco.cloudmanager.model.cimi.event.AlarmEventType;
import org.ow2.sirocco.cloudmanager.model.cimi.event.Event;
import org.ow2.sirocco.cloudmanager.model.cimi.event.Event.Outcome;
import org.ow2.sirocco.cloudmanager.model.cimi.event.Event.Severity;
import org.ow2.sirocco.cloudmanager.model.cimi.event.EventType;
import org.ow2.sirocco.cloudmanager.model.cimi.event.ModelEventType;
import org.ow2.sirocco.cloudmanager.model.cimi.event.StateEventType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Converters tests of monitoring resources.
 */
public class MonitoringConverterTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(MonitoringConverterTest.class);

    private CimiRequest request;

    private CimiContext context;

    @Before
    public void setUp() throws Exception {

        this.request = new CimiRequest();
        this.request.setBaseUri("http://www.test.org/");
        RequestParams params = new RequestParams();
        params.setCimiSelect(new CimiSelect());
        params.setCimiExpand(new CimiExpand());
        this.request.setParams(params);

        this.context = new CimiContextImpl(this.request, new CimiResponse());
    }

    @Test
    public void testCimiJob() throws Exception {
        CimiJob cimi;
        Job service;
        CloudResource targetResource;

        // Empty Service -> Cimi
        cimi = (CimiJob) this.context.convertToCimi(new Job(), CimiJob.class);
        Assert.assertNull(cimi.getAction());
        Assert.assertNull(cimi.getAffectedResources());
        Assert.assertNull(cimi.getNestedJobs());
        Assert.assertNull(cimi.getParentJob());
        Assert.assertNull(cimi.getProgress());
        Assert.assertNull(cimi.getReturnCode());
        Assert.assertNull(cimi.getStatus());
        Assert.assertNull(cimi.getStatusMessage());
        Assert.assertNull(cimi.getTargetResource());
        Assert.assertNull(cimi.getTimeOfStatusChange());

        // Full Service -> Cimi
        targetResource = new Machine();
        targetResource.setUuid("321");
        Date timeOfStatusChange = new Date();
        Job parentJob = new Job();
        parentJob.setUuid("789");

        service = new Job();
        service.setAction("action");
        service.setIsCancellable(Boolean.TRUE);
        service.setParentJob(parentJob);
        service.setProgress(13);
        service.setReturnCode(11);
        service.setState(Job.Status.RUNNING);
        service.setStatusMessage("statusMessage");
        service.setTargetResource(targetResource);
        service.setTimeOfStatusChange(timeOfStatusChange);

        cimi = (CimiJob) this.context.convertToCimi(service, CimiJob.class);
        Assert.assertEquals("action", cimi.getAction());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/789", cimi.getParentJob().getHref());
        Assert.assertEquals(13, cimi.getProgress().intValue());
        Assert.assertEquals(11, cimi.getReturnCode().intValue());
        Assert.assertEquals(Job.Status.RUNNING.toString(), cimi.getStatus());
        Assert.assertEquals("statusMessage", cimi.getStatusMessage());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Machine.getPathname() + "/321", cimi.getTargetResource()
            .getHref());
        Assert.assertEquals(timeOfStatusChange, cimi.getTimeOfStatusChange());

        // Full Service -> Cimi : NestedJobs empty
        service = new Job();
        service.setNestedJobs(new ArrayList<Job>());

        cimi = (CimiJob) this.context.convertToCimi(service, CimiJob.class);
        Assert.assertNull(cimi.getNestedJobs());

        // Full Service -> Cimi : NestedJobs full
        List<Job> listJob = new ArrayList<Job>();
        for (int i = 0; i < 3; i++) {
            Job job = new Job();
            job.setUuid(new Integer(i + 100).toString());
            listJob.add(job);
        }
        service = new Job();
        service.setNestedJobs(listJob);

        cimi = (CimiJob) this.context.convertToCimi(service, CimiJob.class);
        Assert.assertNotNull(cimi.getNestedJobs());
        Assert.assertEquals(3, cimi.getNestedJobs().length);
        for (int i = 0; i < cimi.getNestedJobs().length; i++) {
            Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/" + (i + 100),
                cimi.getNestedJobs()[i].getHref());
        }
        // Full Service -> Cimi : AffectedResources empty
        service = new Job();
        service.setAffectedResources(new ArrayList<CloudResource>());

        cimi = (CimiJob) this.context.convertToCimi(service, CimiJob.class);
        Assert.assertNull(cimi.getAffectedResources());

        // Full Service -> Cimi : AffectedResources full
        List<CloudResource> listResource = new ArrayList<CloudResource>();
        targetResource = new Machine();
        targetResource.setUuid("321");
        listResource.add(targetResource);
        targetResource = new MachineImage();
        targetResource.setUuid("654");
        listResource.add(targetResource);

        service = new Job();
        service.setAffectedResources(listResource);

        cimi = (CimiJob) this.context.convertToCimi(service, CimiJob.class);
        Assert.assertNotNull(cimi.getAffectedResources());
        Assert.assertEquals(2, cimi.getAffectedResources().length);
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Machine.getPathname() + "/321",
            cimi.getAffectedResources()[0].getHref());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.MachineImage.getPathname() + "/654",
            cimi.getAffectedResources()[1].getHref());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testCimiJobCollection() throws Exception {
        CimiJobCollection cimi;
        List<Job> service;

        // Empty Cimi -> Service
        service = (List<Job>) this.context.convertToService(new CimiJobCollection());
        Assert.assertNotNull(service);
        Assert.assertEquals(0, service.size());

        // Empty Service -> Cimi
        cimi = (CimiJobCollection) this.context.convertToCimi(new ArrayList<Job>(), CimiJobCollection.class);
        Assert.assertNull(cimi.getArray());

        // Full Cimi -> Service
        cimi = new CimiJobCollection();
        cimi.setArray(new CimiJob[] {new CimiJob(), new CimiJob()});

        service = (List<Job>) this.context.convertToService(cimi);
        Assert.assertEquals(2, service.size());

        // Full Service -> Cimi
        Job Job1 = new Job();
        Job1.setUuid("1");
        Job1.setName("nameOne");
        Job Job2 = new Job();
        Job2.setUuid("2");
        Job2.setName("nameTwo");
        Job Job3 = new Job();
        Job3.setUuid("3");
        Job3.setName("nameThree");

        service = new ArrayList<Job>();
        service.add(Job1);
        service.add(Job2);
        service.add(Job3);

        cimi = (CimiJobCollection) this.context.convertToCimi(service, CimiJobCollection.class);
        Assert.assertEquals(3, cimi.getArray().length);
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/1", cimi.getArray()[0].getId());
        Assert.assertNotNull(cimi.getArray()[0].getId());
        Assert.assertNotNull(cimi.getArray()[0].getName());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/2", cimi.getArray()[1].getId());
        Assert.assertNotNull(cimi.getArray()[1].getId());
        Assert.assertNotNull(cimi.getArray()[1].getName());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/3", cimi.getArray()[2].getId());
        Assert.assertNotNull(cimi.getArray()[2].getId());
        Assert.assertNotNull(cimi.getArray()[2].getName());

        cimi = (CimiJobCollection) this.context.convertToCimi(Arrays.asList(new Job[] {Job3, Job1}), CimiJobCollection.class);
        Assert.assertEquals(2, cimi.getArray().length);
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/3", cimi.getArray()[0].getId());
        Assert.assertNotNull(cimi.getArray()[0].getId());
        Assert.assertNotNull(cimi.getArray()[0].getName());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/1", cimi.getArray()[1].getId());
        Assert.assertNotNull(cimi.getArray()[1].getId());
        Assert.assertNotNull(cimi.getArray()[1].getName());
    }

    @Test
    public void testCimiJobCollectionRootExpand() throws Exception {
        CimiJobCollectionRoot cimi;
        List<Job> service;

        // Prepare Trace
        Writer strWriter;
        ObjectMapper mapper = new ObjectMapper();
        JAXBContext context = JAXBContext.newInstance(CimiJobCollectionRoot.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Full Service -> Cimi
        Job Job1 = new Job();
        Job1.setUuid("1");
        Job1.setName("nameOne");
        Job Job2 = new Job();
        Job2.setUuid("2");
        Job2.setName("nameTwo");
        Job Job3 = new Job();
        Job3.setUuid("3");
        Job3.setName("nameThree");

        service = new ArrayList<Job>();
        service.add(Job1);
        service.add(Job2);
        service.add(Job3);

        // expand = *
        this.request.getParams().setCimiExpand(new CimiExpand("*"));
        cimi = (CimiJobCollectionRoot) this.context.convertToCimi(service, CimiJobCollectionRoot.class);
        Assert.assertEquals(3, cimi.getArray().length);
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/1", cimi.getArray()[0].getId());
        Assert.assertEquals(cimi.getArray()[0].getId(), cimi.getArray()[0].getId());
        Assert.assertEquals("nameOne", cimi.getArray()[0].getName());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/2", cimi.getArray()[1].getId());
        Assert.assertEquals(cimi.getArray()[1].getId(), cimi.getArray()[1].getId());
        Assert.assertEquals("nameTwo", cimi.getArray()[1].getName());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/3", cimi.getArray()[2].getId());
        Assert.assertEquals(cimi.getArray()[2].getId(), cimi.getArray()[2].getId());
        Assert.assertEquals("nameThree", cimi.getArray()[2].getName());

        // Trace
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimi);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);
        strWriter = new StringWriter();
        m.marshal(cimi, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // expand = jobs
        // FIXME jobs is not an attribute of Job
        this.request.getParams().setCimiExpand(new CimiExpand("jobs"));

        cimi = (CimiJobCollectionRoot) this.context.convertToCimi(service, CimiJobCollectionRoot.class);
        Assert.assertEquals(3, cimi.getArray().length);
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/1", cimi.getArray()[0].getId());
        Assert.assertEquals(cimi.getArray()[0].getId(), cimi.getArray()[0].getId());
        Assert.assertEquals("nameOne", cimi.getArray()[0].getName());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/2", cimi.getArray()[1].getId());
        Assert.assertEquals(cimi.getArray()[1].getId(), cimi.getArray()[1].getId());
        Assert.assertEquals("nameTwo", cimi.getArray()[1].getName());
        Assert.assertEquals(this.request.getBaseUri() + ExchangeType.Job.getPathname() + "/3", cimi.getArray()[2].getId());
        Assert.assertEquals(cimi.getArray()[2].getId(), cimi.getArray()[2].getId());
        Assert.assertEquals("nameThree", cimi.getArray()[2].getName());

        // Trace
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimi);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);
        strWriter = new StringWriter();
        m.marshal(cimi, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // expand = foo
        // FIXME
        // this.request.getParams().setCimiExpand(new CimiExpand("foo"));

        // cimi = (CimiJobCollectionRoot) this.context.convertToCimi(service,
        // CimiJobCollectionRoot.class);
        // Assert.assertEquals(3, cimi.getArray().length);
        // Assert.assertEquals(this.request.getBaseUri() +
        // ExchangeType.Job.getPathname() + "/1", cimi.getArray()[0].getId());
        // Assert.assertNull(cimi.getArray()[0].getId());
        // Assert.assertNull(cimi.getArray()[0].getName());
        // Assert.assertEquals(this.request.getBaseUri() +
        // ExchangeType.Job.getPathname() + "/2", cimi.getArray()[1].getId());
        // Assert.assertNull(cimi.getArray()[1].getId());
        // Assert.assertNull(cimi.getArray()[1].getName());
        // Assert.assertEquals(this.request.getBaseUri() +
        // ExchangeType.Job.getPathname() + "/3", cimi.getArray()[2].getId());
        // Assert.assertNull(cimi.getArray()[2].getId());
        // Assert.assertNull(cimi.getArray()[2].getName());

        // Trace
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimi);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);
        strWriter = new StringWriter();
        m.marshal(cimi, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

    }

    @Test
    public void testCimiEvent() throws Exception {
        CimiEvent cimi;
        Event service;
        EventType serviceEventType;
        CloudResource targetResource;
        Date timeStamp;

        // Prepare Trace
        Writer strWriter;
        ObjectMapper mapper = new ObjectMapper();
        JAXBContext context = JAXBContext.newInstance(CimiEvent.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // --------------------------------------------
        // Empty Service -> Cimi
        // --------------------------------------------
        // Convert
        cimi = (CimiEvent) this.context.convertToCimi(new Event(), CimiEvent.class);
        // Trace
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimi);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);
        strWriter = new StringWriter();
        m.marshal(cimi, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);
        // Verify
        Assert.assertNull(cimi.getContact());
        Assert.assertNull(cimi.getDescription());
        Assert.assertNull(cimi.getOutcome());
        Assert.assertNull(cimi.getSeverity());
        Assert.assertNull(cimi.getType());
        Assert.assertNull(cimi.getContent());
        Assert.assertNull(cimi.getTimestamp());

        // --------------------------------------------
        // Full Service -> Cimi : Event Access
        // --------------------------------------------

        // Prepare Service
        timeStamp = new Date();
        targetResource = new Machine();
        targetResource.setUuid("321");
        serviceEventType = new AccessEventType();
        ((AccessEventType) serviceEventType).setDetail("EventType_Detail");
        ((AccessEventType) serviceEventType).setResName("EventType_ResName");
        ((AccessEventType) serviceEventType).setResource(targetResource);
        ((AccessEventType) serviceEventType).setInitiator("EventType_Initiator");
        ((AccessEventType) serviceEventType).setOperation("EventType_Operation");

        service = new Event();
        service.setContact("Event_Contact");
        service.setUuid("1");
        service.setName("Event_Name");
        service.setOutcome(Outcome.SUCCESS);
        service.setSeverity(Severity.MEDIUM);
        service.setTimestamp(timeStamp);
        service.setContent(serviceEventType);

        // Convert
        cimi = (CimiEvent) this.context.convertToCimi(service, CimiEvent.class);
        // Trace
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimi);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);

        StringReader strReader = new StringReader(strWriter.toString());
        CimiEvent cimiIn = mapper.readValue(strReader, CimiEvent.class);
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimiIn);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);

        strWriter = new StringWriter();
        m.marshal(cimi, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        Unmarshaller unm = context.createUnmarshaller();
        strReader = new StringReader(strWriter.toString());
        cimiIn = (CimiEvent) unm.unmarshal(strReader);
        strWriter = new StringWriter();
        m.marshal(cimiIn, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // Verify
        Assert.assertEquals("Event_Contact", cimi.getContact());
        Assert.assertEquals("SUCCESS", cimi.getOutcome());
        Assert.assertEquals("MEDIUM", cimi.getSeverity());
        Assert.assertEquals(timeStamp, cimi.getTimestamp());
        Assert.assertEquals(ConstantsPath.CIMI_XML_NAMESPACE + "/event/access", cimi.getType());
        Assert.assertEquals("EventType_Detail", cimi.getContent().getDetail());
        Assert.assertEquals("EventType_ResName", cimi.getContent().getResName());
        Assert.assertEquals(ExchangeType.Machine.getResourceURI(), cimi.getContent().getResType());
        Assert.assertEquals(this.context.makeHref(CimiMachine.class, "321"), cimi.getContent().getResource().getHref());
        Assert.assertEquals("EventType_Initiator", cimi.getContent().getInitiator());
        Assert.assertEquals("EventType_Operation", cimi.getContent().getOperation());

        // --------------------------------------------
        // Full Service -> Cimi : Event Alarm
        // --------------------------------------------

        // Prepare Service
        timeStamp = new Date();
        targetResource = new Machine();
        targetResource.setUuid("321");
        serviceEventType = new AlarmEventType();
        ((AlarmEventType) serviceEventType).setDetail("EventType_Detail");
        ((AlarmEventType) serviceEventType).setResName("EventType_ResName");
        ((AlarmEventType) serviceEventType).setResource(targetResource);
        ((AlarmEventType) serviceEventType).setCode("EventType_Code");

        service = new Event();
        service.setContact("Event_Contact");
        service.setUuid("1");
        service.setName("Event_Name");
        service.setOutcome(Outcome.SUCCESS);
        service.setSeverity(Severity.MEDIUM);
        service.setTimestamp(timeStamp);
        service.setContent(serviceEventType);

        // Convert
        cimi = (CimiEvent) this.context.convertToCimi(service, CimiEvent.class);
        // Trace
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimi);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);

        // StringReader strReader = new StringReader(strWriter.toString());
        // CimiEvent cimiIn = mapper.readValue(strReader, CimiEvent.class);
        // strWriter = new StringWriter();
        // mapper.writeValue(strWriter, cimiIn);
        // MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);

        strWriter = new StringWriter();
        m.marshal(cimi, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // Unmarshaller unm = context.createUnmarshaller();
        // strReader = new StringReader(strWriter.toString());
        // cimiIn = (CimiEvent) unm.unmarshal(strReader);
        // strWriter = new StringWriter();
        // m.marshal(cimiIn, strWriter);
        // MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // Verify
        Assert.assertEquals("Event_Contact", cimi.getContact());
        Assert.assertEquals("SUCCESS", cimi.getOutcome());
        Assert.assertEquals("MEDIUM", cimi.getSeverity());
        Assert.assertEquals(timeStamp, cimi.getTimestamp());
        Assert.assertEquals(ConstantsPath.CIMI_XML_NAMESPACE + "/event/alarm", cimi.getType());
        Assert.assertEquals("EventType_Detail", cimi.getContent().getDetail());
        Assert.assertEquals("EventType_ResName", cimi.getContent().getResName());
        Assert.assertEquals(ExchangeType.Machine.getResourceURI(), cimi.getContent().getResType());
        Assert.assertEquals(this.context.makeHref(CimiMachine.class, "321"), cimi.getContent().getResource().getHref());
        Assert.assertEquals("EventType_Code", cimi.getContent().getCode());

        // --------------------------------------------
        // Full Service -> Cimi : Event Model
        // --------------------------------------------

        // Prepare Service
        timeStamp = new Date();
        targetResource = new Machine();
        targetResource.setUuid("321");
        serviceEventType = new ModelEventType();
        ((ModelEventType) serviceEventType).setDetail("EventType_Detail");
        ((ModelEventType) serviceEventType).setResName("EventType_ResName");
        ((ModelEventType) serviceEventType).setResource(targetResource);
        ((ModelEventType) serviceEventType).setModelChange("EventType_Change");

        service = new Event();
        service.setContact("Event_Contact");
        service.setUuid("1");
        service.setName("Event_Name");
        service.setOutcome(Outcome.SUCCESS);
        service.setSeverity(Severity.MEDIUM);
        service.setTimestamp(timeStamp);
        service.setContent(serviceEventType);

        // Convert
        cimi = (CimiEvent) this.context.convertToCimi(service, CimiEvent.class);
        // Trace
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimi);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);

        // StringReader strReader = new StringReader(strWriter.toString());
        // CimiEvent cimiIn = mapper.readValue(strReader, CimiEvent.class);
        // strWriter = new StringWriter();
        // mapper.writeValue(strWriter, cimiIn);
        // MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);

        strWriter = new StringWriter();
        m.marshal(cimi, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // Unmarshaller unm = context.createUnmarshaller();
        // strReader = new StringReader(strWriter.toString());
        // cimiIn = (CimiEvent) unm.unmarshal(strReader);
        // strWriter = new StringWriter();
        // m.marshal(cimiIn, strWriter);
        // MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // Verify
        Assert.assertEquals("Event_Contact", cimi.getContact());
        Assert.assertEquals("SUCCESS", cimi.getOutcome());
        Assert.assertEquals("MEDIUM", cimi.getSeverity());
        Assert.assertEquals(timeStamp, cimi.getTimestamp());
        Assert.assertEquals(ConstantsPath.CIMI_XML_NAMESPACE + "/event/model", cimi.getType());
        Assert.assertEquals("EventType_Detail", cimi.getContent().getDetail());
        Assert.assertEquals("EventType_ResName", cimi.getContent().getResName());
        Assert.assertEquals(ExchangeType.Machine.getResourceURI(), cimi.getContent().getResType());
        Assert.assertEquals(this.context.makeHref(CimiMachine.class, "321"), cimi.getContent().getResource().getHref());
        Assert.assertEquals("EventType_Change", cimi.getContent().getChange());

        // --------------------------------------------
        // Full Service -> Cimi : Event State
        // --------------------------------------------

        // Prepare Service
        timeStamp = new Date();
        targetResource = new Machine();
        targetResource.setUuid("321");
        serviceEventType = new StateEventType();
        ((StateEventType) serviceEventType).setDetail("EventType_Detail");
        ((StateEventType) serviceEventType).setResName("EventType_ResName");
        ((StateEventType) serviceEventType).setResource(targetResource);
        ((StateEventType) serviceEventType).setState("EventType_State");
        ((StateEventType) serviceEventType).setPreviousState("EventType_Previous");

        service = new Event();
        service.setContact("Event_Contact");
        service.setUuid("1");
        service.setName("Event_Name");
        service.setOutcome(Outcome.SUCCESS);
        service.setSeverity(Severity.MEDIUM);
        service.setTimestamp(timeStamp);
        service.setContent(serviceEventType);

        // Convert
        cimi = (CimiEvent) this.context.convertToCimi(service, CimiEvent.class);
        // Trace
        strWriter = new StringWriter();
        mapper.writeValue(strWriter, cimi);
        MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);

        // StringReader strReader = new StringReader(strWriter.toString());
        // CimiEvent cimiIn = mapper.readValue(strReader, CimiEvent.class);
        // strWriter = new StringWriter();
        // mapper.writeValue(strWriter, cimiIn);
        // MonitoringConverterTest.LOGGER.debug("JSON:\n\t{}", strWriter);

        strWriter = new StringWriter();
        m.marshal(cimi, strWriter);
        MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // Unmarshaller unm = context.createUnmarshaller();
        // strReader = new StringReader(strWriter.toString());
        // cimiIn = (CimiEvent) unm.unmarshal(strReader);
        // strWriter = new StringWriter();
        // m.marshal(cimiIn, strWriter);
        // MonitoringConverterTest.LOGGER.debug("XML:\n\t{}", strWriter);

        // Verify
        Assert.assertEquals("Event_Contact", cimi.getContact());
        Assert.assertEquals("SUCCESS", cimi.getOutcome());
        Assert.assertEquals("MEDIUM", cimi.getSeverity());
        Assert.assertEquals(timeStamp, cimi.getTimestamp());
        Assert.assertEquals(ConstantsPath.CIMI_XML_NAMESPACE + "/event/state", cimi.getType());
        Assert.assertEquals("EventType_Detail", cimi.getContent().getDetail());
        Assert.assertEquals("EventType_ResName", cimi.getContent().getResName());
        Assert.assertEquals(ExchangeType.Machine.getResourceURI(), cimi.getContent().getResType());
        Assert.assertEquals(this.context.makeHref(CimiMachine.class, "321"), cimi.getContent().getResource().getHref());
        Assert.assertEquals("EventType_State", cimi.getContent().getState());
        Assert.assertEquals("EventType_Previous", cimi.getContent().getPrevious());
    }
}