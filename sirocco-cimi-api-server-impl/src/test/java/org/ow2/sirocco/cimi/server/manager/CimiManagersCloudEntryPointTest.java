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
package org.ow2.sirocco.cimi.server.manager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.easymock.EasyMock;
import org.jglue.cdiunit.AdditionalClasses;
import org.jglue.cdiunit.CdiRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cimi.domain.CimiCloudEntryPoint;
import org.ow2.sirocco.cimi.server.manager.cep.CimiManagerReadCloudEntryPoint;
import org.ow2.sirocco.cimi.server.manager.machine.CimiManagerActionMachine;
import org.ow2.sirocco.cimi.server.manager.machine.CimiManagerCreateMachine;
import org.ow2.sirocco.cimi.server.manager.machine.CimiManagerDeleteMachine;
import org.ow2.sirocco.cimi.server.manager.machine.CimiManagerReadMachine;
import org.ow2.sirocco.cimi.server.manager.machine.CimiManagerUpdateMachine;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cimi.server.request.CimiContextImpl;
import org.ow2.sirocco.cimi.server.request.CimiExpand;
import org.ow2.sirocco.cimi.server.request.CimiRequest;
import org.ow2.sirocco.cimi.server.request.CimiResponse;
import org.ow2.sirocco.cimi.server.request.CimiSelect;
import org.ow2.sirocco.cimi.server.request.RequestParams;
import org.ow2.sirocco.cimi.server.test.util.ManagerProducers;
import org.ow2.sirocco.cimi.server.utils.ConstantsPath;
import org.ow2.sirocco.cloudmanager.core.api.ICredentialsManager;
import org.ow2.sirocco.cloudmanager.core.api.IEventManager;
import org.ow2.sirocco.cloudmanager.core.api.IJobManager;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.IMachineManager;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.core.api.ISystemManager;
import org.ow2.sirocco.cloudmanager.core.api.IVolumeManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryResult;
import org.ow2.sirocco.cloudmanager.model.cimi.Address;
import org.ow2.sirocco.cloudmanager.model.cimi.AddressTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.Credentials;
import org.ow2.sirocco.cloudmanager.model.cimi.CredentialsTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.ForwardingGroup;
import org.ow2.sirocco.cloudmanager.model.cimi.ForwardingGroupTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.Job;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.Network;
import org.ow2.sirocco.cloudmanager.model.cimi.NetworkConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.NetworkPort;
import org.ow2.sirocco.cloudmanager.model.cimi.NetworkPortConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.NetworkPortTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.NetworkTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.Volume;
import org.ow2.sirocco.cloudmanager.model.cimi.VolumeConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.VolumeImage;
import org.ow2.sirocco.cloudmanager.model.cimi.VolumeTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.event.EventLog;
import org.ow2.sirocco.cloudmanager.model.cimi.event.EventLogTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.system.System;
import org.ow2.sirocco.cloudmanager.model.cimi.system.SystemTemplate;

/**
 * Basic tests "end to end" for managers Machine.
 */
@RunWith(CdiRunner.class)
@AdditionalClasses({ManagerProducers.class, CimiManagerActionMachine.class, CimiManagerReadMachine.class,
    CimiManagerUpdateMachine.class, CimiManagerDeleteMachine.class, CimiManagerCreateMachine.class,
    CimiManagerReadCloudEntryPoint.class, CallServiceHelperImpl.class, MergeReferenceHelperImpl.class})
public class CimiManagersCloudEntryPointTest {

    @Inject
    private ICredentialsManager serviceCredentials;

    @Inject
    private IJobManager serviceJob;

    @Inject
    private IMachineManager serviceMachine;

    @Inject
    private IMachineImageManager serviceMachineImage;

    @Inject
    private ISystemManager serviceSystem;

    @Inject
    private IVolumeManager serviceVolume;

    @Inject
    private INetworkManager serviceNetwork;

    @Inject
    private IEventManager serviceEvent;

    @Inject
    @Manager("CimiManagerReadCloudEntryPoint")
    private CimiManager managerRead;

    private CimiRequest request;

    private CimiResponse response;

    private CimiContext context;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {

        this.request = new CimiRequest();
        this.request.setBaseUri("/");
        RequestParams header = new RequestParams();
        header.setCimiSelect(new CimiSelect());
        header.setCimiExpand(new CimiExpand());
        this.request.setParams(header);

        this.response = new CimiResponse();
        this.context = new CimiContextImpl(this.request, this.response);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        EasyMock.reset(this.serviceCredentials);
        EasyMock.reset(this.serviceJob);
        EasyMock.reset(this.serviceMachine);
        EasyMock.reset(this.serviceMachineImage);
        EasyMock.reset(this.serviceSystem);
        EasyMock.reset(this.serviceVolume);
        EasyMock.reset(this.serviceNetwork);
        EasyMock.reset(this.serviceEvent);
    }

    @Test
    // TODO Others resources : Network, ...
    public void testRead() throws Exception {
        // CloudEntryPointAggregate cloud = new CloudEntryPointAggregate();
        // cloud.setId(10);

        // Credentials
        List<Credentials> credentialsCollection = new ArrayList<Credentials>();
        List<CredentialsTemplate> credentialsTemplateCollection = new ArrayList<CredentialsTemplate>();
        // Jobs
        List<Job> jobCollection = new ArrayList<Job>();
        // Machines
        List<Machine> machineCollection = new ArrayList<Machine>();
        QueryResult<Machine> machineQueryResult = new QueryResult<Machine>(machineCollection.size(), machineCollection);

        List<MachineTemplate> machineTemplateCollection = new ArrayList<MachineTemplate>();
        QueryResult<MachineTemplate> machineTemplateQueryResult = new QueryResult<MachineTemplate>(
            machineTemplateCollection.size(), machineTemplateCollection);

        List<MachineConfiguration> machineConfigurationCollection = new ArrayList<MachineConfiguration>();
        QueryResult<MachineConfiguration> machineConfigQueryResult = new QueryResult<MachineConfiguration>(
            machineConfigurationCollection.size(), machineConfigurationCollection);

        List<MachineImage> machineImageCollection = new ArrayList<MachineImage>();
        // Systems
        List<System> systemCollection = new ArrayList<System>();
        List<SystemTemplate> systemTemplateCollection = new ArrayList<SystemTemplate>();
        // Volumes
        List<Volume> volumeCollection = new ArrayList<Volume>();
        QueryResult<Volume> volumeQueryResult = new QueryResult<Volume>(volumeCollection.size(), volumeCollection);
        List<VolumeTemplate> volumeTemplateCollection = new ArrayList<VolumeTemplate>();
        List<VolumeConfiguration> volumeConfigurationCollection = new ArrayList<VolumeConfiguration>();
        List<VolumeImage> volumeImageCollection = new ArrayList<VolumeImage>();
        QueryResult<VolumeImage> volumeImageQueryResult = new QueryResult<VolumeImage>(volumeImageCollection.size(),
            volumeImageCollection);
        // Networks
        List<Network> networkCollection = new ArrayList<Network>();
        QueryResult<Network> networkQueryResult = new QueryResult<Network>(networkCollection.size(), networkCollection);
        List<NetworkTemplate> networkTemplateCollection = new ArrayList<NetworkTemplate>();
        QueryResult<NetworkTemplate> networkTemplateQueryResult = new QueryResult<NetworkTemplate>(
            networkTemplateCollection.size(), networkTemplateCollection);
        List<NetworkConfiguration> networkConfigurationCollection = new ArrayList<NetworkConfiguration>();
        QueryResult<NetworkConfiguration> networkConfigQueryResult = new QueryResult<NetworkConfiguration>(
            networkConfigurationCollection.size(), networkConfigurationCollection);
        // NetworkPorts
        List<NetworkPort> networkPortCollection = new ArrayList<NetworkPort>();
        QueryResult<NetworkPort> networkPortQueryResult = new QueryResult<NetworkPort>(networkPortCollection.size(),
            networkPortCollection);
        List<NetworkPortTemplate> networkPortTemplateCollection = new ArrayList<NetworkPortTemplate>();
        List<NetworkPortConfiguration> networkPortConfigurationCollection = new ArrayList<NetworkPortConfiguration>();
        // Addresss
        List<Address> addressCollection = new ArrayList<Address>();
        QueryResult<Address> addressQueryResult = new QueryResult<Address>(addressCollection.size(), addressCollection);
        List<AddressTemplate> addressTemplateCollection = new ArrayList<AddressTemplate>();
        // ForwardingGroups
        List<ForwardingGroup> forwardingGroupCollection = new ArrayList<ForwardingGroup>();
        QueryResult<ForwardingGroup> forwardingGroupQueryResult = new QueryResult<ForwardingGroup>(
            forwardingGroupCollection.size(), forwardingGroupCollection);
        List<ForwardingGroupTemplate> forwardingGroupTemplateCollection = new ArrayList<ForwardingGroupTemplate>();
        // EventLogs
        List<EventLog> eventLogCollection = new ArrayList<EventLog>();
        List<EventLogTemplate> eventLogTemplateCollection = new ArrayList<EventLogTemplate>();

        EasyMock.expect(this.serviceCredentials.getCredentials()).andReturn(credentialsCollection);
        EasyMock.expect(this.serviceCredentials.getCredentialsTemplates()).andReturn(credentialsTemplateCollection);
        EasyMock.replay(this.serviceCredentials);

        EasyMock.expect(this.serviceJob.getJobs()).andReturn(jobCollection);
        EasyMock.replay(this.serviceJob);

        // EasyMock.expect(this.serviceMachine.getCloudEntryPoint()).andReturn(cloud);
        EasyMock.expect(this.serviceMachine.getMachines()).andReturn(machineQueryResult);
        EasyMock.expect(this.serviceMachine.getMachineTemplates()).andReturn(machineTemplateQueryResult);
        EasyMock.expect(this.serviceMachine.getMachineConfigurations()).andReturn(machineConfigQueryResult);
        EasyMock.replay(this.serviceMachine);

        EasyMock.expect(this.serviceMachineImage.getMachineImages()).andReturn(machineImageCollection);
        EasyMock.replay(this.serviceMachineImage);

        EasyMock.expect(this.serviceSystem.getSystems()).andReturn(systemCollection);
        EasyMock.expect(this.serviceSystem.getSystemTemplates()).andReturn(systemTemplateCollection);
        EasyMock.replay(this.serviceSystem);

        EasyMock.expect(this.serviceVolume.getVolumes()).andReturn(volumeQueryResult);
        EasyMock.expect(this.serviceVolume.getVolumeTemplates()).andReturn(volumeTemplateCollection);
        EasyMock.expect(this.serviceVolume.getVolumeConfigurations()).andReturn(volumeConfigurationCollection);
        EasyMock.expect(this.serviceVolume.getVolumeImages()).andReturn(volumeImageQueryResult);
        EasyMock.replay(this.serviceVolume);

        EasyMock.expect(this.serviceNetwork.getNetworks()).andReturn(networkQueryResult);
        EasyMock.expect(this.serviceNetwork.getNetworkTemplates()).andReturn(networkTemplateQueryResult);
        EasyMock.expect(this.serviceNetwork.getNetworkConfigurations()).andReturn(networkConfigQueryResult);
        EasyMock.expect(this.serviceNetwork.getNetworkPorts()).andReturn(networkPortQueryResult);
        EasyMock.expect(this.serviceNetwork.getNetworkPortTemplates()).andReturn(networkPortTemplateCollection);
        EasyMock.expect(this.serviceNetwork.getNetworkPortConfigurations()).andReturn(networkPortConfigurationCollection);
        EasyMock.expect(this.serviceNetwork.getAddresses()).andReturn(addressQueryResult);
        EasyMock.expect(this.serviceNetwork.getAddressTemplates()).andReturn(addressTemplateCollection);
        EasyMock.expect(this.serviceNetwork.getForwardingGroups()).andReturn(forwardingGroupQueryResult);
        EasyMock.expect(this.serviceNetwork.getForwardingGroupTemplates()).andReturn(forwardingGroupTemplateCollection);
        EasyMock.replay(this.serviceNetwork);

        EasyMock.expect(this.serviceEvent.getEventLog()).andReturn(eventLogCollection);
        EasyMock.expect(this.serviceEvent.getEventLogTemplates()).andReturn(eventLogTemplateCollection);
        EasyMock.replay(this.serviceEvent);

        // this.request.setId("1");
        this.managerRead.execute(this.context);

        Assert.assertEquals(200, this.response.getStatus());
        CimiCloudEntryPoint cimiCloud = (CimiCloudEntryPoint) this.response.getCimiData();

        Assert.assertEquals(ConstantsPath.CLOUDENTRYPOINT_PATH, cimiCloud.getId());

        Assert.assertEquals(ConstantsPath.CREDENTIAL_PATH, cimiCloud.getCredentials().getHref());
        Assert.assertEquals(ConstantsPath.CREDENTIAL_TEMPLATE_PATH, cimiCloud.getCredentialTemplates().getHref());

        Assert.assertEquals(ConstantsPath.JOB_PATH, cimiCloud.getJobs().getHref());

        Assert.assertEquals(ConstantsPath.MACHINE_PATH, cimiCloud.getMachines().getHref());
        Assert.assertEquals(ConstantsPath.MACHINE_CONFIGURATION_PATH, cimiCloud.getMachineConfigs().getHref());
        Assert.assertEquals(ConstantsPath.MACHINE_IMAGE_PATH, cimiCloud.getMachineImages().getHref());
        Assert.assertEquals(ConstantsPath.MACHINE_TEMPLATE_PATH, cimiCloud.getMachineTemplates().getHref());

        Assert.assertEquals(ConstantsPath.SYSTEM_PATH, cimiCloud.getSystems().getHref());
        Assert.assertEquals(ConstantsPath.SYSTEM_TEMPLATE_PATH, cimiCloud.getSystemTemplates().getHref());

        Assert.assertEquals(ConstantsPath.VOLUME_PATH, cimiCloud.getVolumes().getHref());
        Assert.assertEquals(ConstantsPath.VOLUME_CONFIGURATION_PATH, cimiCloud.getVolumeConfigs().getHref());
        Assert.assertEquals(ConstantsPath.VOLUME_IMAGE_PATH, cimiCloud.getVolumeImages().getHref());
        Assert.assertEquals(ConstantsPath.VOLUME_TEMPLATE_PATH, cimiCloud.getVolumeTemplates().getHref());

        EasyMock.verify(this.serviceCredentials);
        EasyMock.verify(this.serviceJob);
        EasyMock.verify(this.serviceMachine);
        EasyMock.verify(this.serviceMachineImage);
        EasyMock.verify(this.serviceSystem);
        EasyMock.verify(this.serviceVolume);
    }

    // @Test
    // @Ignore
    // public void testReadWithCimiSelect() throws Exception {
    // // TODO
    // }
}
