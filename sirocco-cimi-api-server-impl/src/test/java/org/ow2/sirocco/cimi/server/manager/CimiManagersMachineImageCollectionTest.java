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
import java.util.Arrays;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ow2.sirocco.cimi.server.domain.collection.CimiMachineImageCollection;
import org.ow2.sirocco.cimi.server.manager.CimiManager;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cimi.server.request.CimiContextImpl;
import org.ow2.sirocco.cimi.server.request.CimiExpand;
import org.ow2.sirocco.cimi.server.request.CimiFilter;
import org.ow2.sirocco.cimi.server.request.CimiIntegerParam;
import org.ow2.sirocco.cimi.server.request.CimiRequest;
import org.ow2.sirocco.cimi.server.request.CimiResponse;
import org.ow2.sirocco.cimi.server.request.CimiSelect;
import org.ow2.sirocco.cimi.server.request.RequestParams;
import org.ow2.sirocco.cimi.server.utils.ConstantsPath;
import org.ow2.sirocco.cloudmanager.core.api.IMachineImageManager;
import org.ow2.sirocco.cloudmanager.core.api.QueryResult;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Basic tests "end to end" for managers MachineImageCollection.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"/context/managerContext.xml"})
public class CimiManagersMachineImageCollectionTest {

    @Autowired
    @Qualifier("IMachineImageManager")
    private IMachineImageManager service;

    @Autowired
    @Qualifier("CimiManagerReadMachineImageCollection")
    private CimiManager manager;

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
        RequestParams params = new RequestParams();
        params.setCimiSelect(new CimiSelect());
        params.setCimiExpand(new CimiExpand());
        params.setCimiFilter(new CimiFilter());
        params.setCimiFirst(new CimiIntegerParam());
        params.setCimiLast(new CimiIntegerParam());
        this.request.setParams(params);

        this.response = new CimiResponse();
        this.context = new CimiContextImpl(this.request, this.response);
    }

    /**
     * @throws java.lang.Exception
     */
    @After
    public void tearDown() throws Exception {
        EasyMock.reset(this.service);
    }

    @Test
    public void testRead() throws Exception {
        MachineImage machine;
        List<MachineImage> list = new ArrayList<MachineImage>();
        for (int i = 0; i < 3; i++) {
            machine = new MachineImage();
            machine.setId(i + 13);
            list.add(machine);
        }

        EasyMock.expect(this.service.getMachineImages()).andReturn(list);
        EasyMock.replay(this.service);

        this.manager.execute(this.context);

        Assert.assertEquals(200, this.response.getStatus());
        Assert.assertEquals(ConstantsPath.MACHINE_IMAGE_PATH,
            ((CimiMachineImageCollection) this.response.getCimiData()).getId());
        CimiMachineImageCollection cimiCollect = (CimiMachineImageCollection) this.response.getCimiData();
        Assert.assertNotNull(cimiCollect.getArray());
        Assert.assertEquals(3, cimiCollect.getArray().length);
        for (int i = 0; i < cimiCollect.getArray().length; i++) {
            Assert.assertEquals(ConstantsPath.MACHINE_IMAGE_PATH + "/" + (i + 13), cimiCollect.getArray()[i].getId());
        }
        EasyMock.verify(this.service);
    }

    @Test
    public void testReadWithSelect() throws Exception {
        QueryResult<MachineImage> results = new QueryResult<MachineImage>(0, new ArrayList<MachineImage>());
        EasyMock.expect(
            this.service.getMachineImages(EasyMock.eq(-1), EasyMock.eq(-1), EasyMock.eq((List<String>) null),
                EasyMock.eq(Arrays.asList(new String[] {"operations"})))).andReturn(results);
        EasyMock.replay(this.service);

        this.request.getParams().getCimiSelect().setInitialValues(new String[] {"operations"});
        this.manager.execute(this.context);

        Assert.assertEquals(200, this.response.getStatus());
        Assert.assertEquals(ConstantsPath.MACHINE_IMAGE_PATH,
            ((CimiMachineImageCollection) this.response.getCimiData()).getId());

        EasyMock.verify(this.service);
    }

    @Test
    public void testReadWithFilter() throws Exception {
        QueryResult<MachineImage> results = new QueryResult<MachineImage>(0, new ArrayList<MachineImage>());
        EasyMock.expect(
            this.service.getMachineImages(EasyMock.eq(-1), EasyMock.eq(-1),
                EasyMock.eq(Arrays.asList(new String[] {"filterOne", "filterTwo"})), EasyMock.eq((List<String>) null)))
            .andReturn(results);
        EasyMock.replay(this.service);

        this.request.getParams().getCimiFilter().setInitialValues(new String[] {"filterOne,filterTwo"});
        this.manager.execute(this.context);

        Assert.assertEquals(200, this.response.getStatus());
        Assert.assertEquals(ConstantsPath.MACHINE_IMAGE_PATH,
            ((CimiMachineImageCollection) this.response.getCimiData()).getId());

        EasyMock.verify(this.service);
    }

    @Test
    public void testReadWithFirst() throws Exception {
        QueryResult<MachineImage> results = new QueryResult<MachineImage>(0, new ArrayList<MachineImage>());
        EasyMock.expect(
            this.service.getMachineImages(EasyMock.eq(0), EasyMock.eq(-1), EasyMock.eq((List<String>) null),
                EasyMock.eq((List<String>) null))).andReturn(results);
        EasyMock.replay(this.service);

        this.request.getParams().getCimiFirst().setInitialValue("1");
        this.manager.execute(this.context);

        Assert.assertEquals(200, this.response.getStatus());
        Assert.assertEquals(ConstantsPath.MACHINE_IMAGE_PATH,
            ((CimiMachineImageCollection) this.response.getCimiData()).getId());

        EasyMock.verify(this.service);
    }

    @Test
    public void testReadWithLast() throws Exception {
        QueryResult<MachineImage> results = new QueryResult<MachineImage>(0, new ArrayList<MachineImage>());
        EasyMock.expect(
            this.service.getMachineImages(EasyMock.eq(-1), EasyMock.eq(49), EasyMock.eq((List<String>) null),
                EasyMock.eq((List<String>) null))).andReturn(results);
        EasyMock.replay(this.service);

        this.request.getParams().getCimiLast().setInitialValue("50");
        this.manager.execute(this.context);

        Assert.assertEquals(200, this.response.getStatus());
        Assert.assertEquals(ConstantsPath.MACHINE_IMAGE_PATH,
            ((CimiMachineImageCollection) this.response.getCimiData()).getId());

        EasyMock.verify(this.service);
    }

    @Test
    public void testReadWithFirstLastFilterSelect() throws Exception {
        QueryResult<MachineImage> results = new QueryResult<MachineImage>(0, new ArrayList<MachineImage>());
        EasyMock.expect(
            this.service.getMachineImages(EasyMock.eq(9), EasyMock.eq(99),
                EasyMock.eq(Arrays.asList(new String[] {"filterOne", "filterTwo"})),
                EasyMock.eq(Arrays.asList(new String[] {"selectOne", "selectTwo"})))).andReturn(results);
        EasyMock.replay(this.service);

        this.request.getParams().getCimiFirst().setInitialValue("10");
        this.request.getParams().getCimiLast().setInitialValue("100");
        this.request.getParams().getCimiFilter().setInitialValues(new String[] {"filterOne,filterTwo"});
        this.request.getParams().getCimiSelect().setInitialValues(new String[] {"selectOne, selectTwo"});
        this.manager.execute(this.context);

        Assert.assertEquals(200, this.response.getStatus());
        Assert.assertEquals(ConstantsPath.MACHINE_IMAGE_PATH,
            ((CimiMachineImageCollection) this.response.getCimiData()).getId());

        EasyMock.verify(this.service);
    }
}