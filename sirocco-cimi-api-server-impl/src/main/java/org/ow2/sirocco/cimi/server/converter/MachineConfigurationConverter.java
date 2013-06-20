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

import java.util.ArrayList;
import java.util.List;

import org.ow2.sirocco.cimi.domain.CimiDiskConfiguration;
import org.ow2.sirocco.cimi.domain.CimiMachineConfiguration;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cloudmanager.model.cimi.DiskTemplate;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineConfiguration;
import org.ow2.sirocco.cloudmanager.model.cimi.Visibility;

/**
 * Convert the data of the CIMI model and the service model in both directions.
 * <p>
 * Converted classes:
 * <ul>
 * <li>CIMI model: {@link CimiMachineConfiguration}</li>
 * <li>Service model: {@link MachineConfiguration}</li>
 * </ul>
 * </p>
 */
public class MachineConfigurationConverter extends ObjectCommonConverter {
    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#toCimi(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object)
     */
    @Override
    public Object toCimi(final CimiContext context, final Object dataService) {
        CimiMachineConfiguration cimi = new CimiMachineConfiguration();
        this.copyToCimi(context, dataService, cimi);
        return cimi;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#copyToCimi(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void copyToCimi(final CimiContext context, final Object dataService, final Object dataCimi) {
        this.doCopyToCimi(context, (MachineConfiguration) dataService, (CimiMachineConfiguration) dataCimi);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#toService(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object)
     */
    @Override
    public Object toService(final CimiContext context, final Object dataCimi) {
        MachineConfiguration service = new MachineConfiguration();
        this.copyToService(context, dataCimi, service);
        return service;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#copyToService
     *      (org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void copyToService(final CimiContext context, final Object dataCimi, final Object dataService) {
        this.doCopyToService(context, (CimiMachineConfiguration) dataCimi, (MachineConfiguration) dataService);
    }

    /**
     * Copy data from a service object to a CIMI object.
     * 
     * @param context The current context
     * @param dataService Source service object
     * @param dataCimi Destination CIMI object
     */
    protected void doCopyToCimi(final CimiContext context, final MachineConfiguration dataService,
        final CimiMachineConfiguration dataCimi) {
        this.fill(context, dataService, dataCimi);
        if (true == context.mustBeExpanded(dataCimi)) {
            dataCimi.setCpu(dataService.getCpu());
            dataCimi.setMemory(dataService.getMemory());

            if ((null != dataService.getDisks()) && (dataService.getDisks().size() > 0)) {
                List<CimiDiskConfiguration> listCimis = new ArrayList<CimiDiskConfiguration>();

                for (DiskTemplate serviceItem : dataService.getDisks()) {
                    listCimis.add((CimiDiskConfiguration) context.convertNextCimi(serviceItem, CimiDiskConfiguration.class));
                }
                dataCimi.setDisks(listCimis.toArray(new CimiDiskConfiguration[listCimis.size()]));
            }
            dataCimi.setVisibility(dataService.getVisibility().toString());
        }
    }

    /**
     * Copy data from a CIMI object to a service object.
     * 
     * @param context The current context
     * @param dataCimi Source CIMI object
     * @param dataService Destination Service object
     */
    protected void doCopyToService(final CimiContext context, final CimiMachineConfiguration dataCimi,
        final MachineConfiguration dataService) {
        this.fill(context, dataCimi, dataService);
        dataService.setCpu(dataCimi.getCpu());
        dataService.setMemory(dataCimi.getMemory());

        if ((null != dataCimi.getDisks()) && (dataCimi.getDisks().length > 0)) {
            List<DiskTemplate> listServices = new ArrayList<DiskTemplate>();
            for (CimiDiskConfiguration cimiItem : dataCimi.getDisks()) {
                listServices.add((DiskTemplate) context.convertNextService(cimiItem));
            }
            dataService.setDisks(listServices);
        }
        if (dataCimi.getVisibility() != null) {
            dataService.setVisibility(Visibility.valueOf(dataCimi.getVisibility()));
        }
    }

}
