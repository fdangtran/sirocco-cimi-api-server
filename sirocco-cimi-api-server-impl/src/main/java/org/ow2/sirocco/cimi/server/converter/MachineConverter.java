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

import java.util.Set;

import org.ow2.sirocco.cimi.domain.ActionType;
import org.ow2.sirocco.cimi.domain.CimiEventLog;
import org.ow2.sirocco.cimi.domain.CimiMachine;
import org.ow2.sirocco.cimi.domain.CimiObjectCommon;
import org.ow2.sirocco.cimi.domain.CimiOperation;
import org.ow2.sirocco.cimi.domain.Operation;
import org.ow2.sirocco.cimi.domain.collection.CimiMachineDiskCollection;
import org.ow2.sirocco.cimi.domain.collection.CimiMachineNetworkInterfaceCollection;
import org.ow2.sirocco.cimi.domain.collection.CimiMachineVolumeCollection;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cloudmanager.model.cimi.Identifiable;
import org.ow2.sirocco.cloudmanager.model.cimi.Machine;

/**
 * Convert the data of the CIMI model and the service model in both directions.
 * <p>
 * Converted classes:
 * <ul>
 * <li>CIMI model: {@link CimiMachine}</li>
 * <li>Service model: {@link Machine}</li>
 * </ul>
 * </p>
 */
public class MachineConverter extends ObjectCommonConverter {

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#toCimi(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object)
     */
    @Override
    public Object toCimi(final CimiContext context, final Object dataService) {
        CimiMachine cimi = new CimiMachine();
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
        this.doCopyToCimi(context, (Machine) dataService, (CimiMachine) dataCimi);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#toService(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object)
     */
    @Override
    public Object toService(final CimiContext context, final Object dataCimi) {
        Machine service = new Machine();
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
        this.doCopyToService(context, (CimiMachine) dataCimi, (Machine) dataService);
    }

    /**
     * Copy data from a service object to a CIMI object.
     * 
     * @param context The current context
     * @param dataService Source service object
     * @param dataCimi Destination CIMI object
     */
    protected void doCopyToCimi(final CimiContext context, final Machine dataService, final CimiMachine dataCimi) {
        this.fill(context, dataService, dataCimi);
        if (true == context.mustBeExpanded(dataCimi)) {
            dataCimi.setCpu(dataService.getCpu());
            dataCimi.setMemory(dataService.getMemory());
            dataCimi.setDisks((CimiMachineDiskCollection) context.convertNextCimi(dataService.getDisks(),
                CimiMachineDiskCollection.class));
            dataCimi.setEventLog((CimiEventLog) context.convertNextCimi(dataService.getEventLog(), CimiEventLog.class));
            dataCimi.setNetworkInterfaces((CimiMachineNetworkInterfaceCollection) context.convertNextCimi(
                dataService.getNetworkInterfaces(), CimiMachineNetworkInterfaceCollection.class));
            dataCimi.setState(ConverterHelper.toString(dataService.getState()));
            dataCimi.setVolumes((CimiMachineVolumeCollection) context.convertNextCimi(dataService.getVolumes(),
                CimiMachineVolumeCollection.class));
            dataCimi.setProviderInfo(ProviderInfoConverter.convert(dataService));
        }
    }

    /**
     * Copy data from a CIMI object to a service object.
     * 
     * @param context The current context
     * @param dataCimi Source CIMI object
     * @param dataService Destination Service object
     */
    protected void doCopyToService(final CimiContext context, final CimiMachine dataCimi, final Machine dataService) {
        this.fill(context, dataCimi, dataService);
        dataService.setCpu(dataCimi.getCpu());
        dataService.setMemory(dataCimi.getMemory());

        // Next Read only
        // cpuArch ???
        // dataService.setDisks((List<MachineDisk>)
        // context.convertNextService(dataCimi.getDisks()));
        // dataService.setNetworkInterfaces((List<MachineNetworkInterface>)
        // context.convertNextService(dataCimi
        // .getNetworkInterfaces()));
        // dataService.setVolumes((List<MachineVolume>)
        // context.convertNextService(dataCimi.getVolumes()));
        // dataService.setState(dataService.getState());
    }

    /**
     * Add operation and action of a machine.
     * 
     * @see org.ow2.sirocco.cimi.server.converter.ObjectCommonConverter#fillOperations(org.ow2.sirocco.cimi.server.request.CimiContext,
     *      org.ow2.sirocco.cloudmanager.model.cimi.Identifiable,
     *      org.ow2.sirocco.cimi.domain.CimiObjectCommon)
     */
    @Override
    protected void fillOperations(final CimiContext context, final Identifiable dataService, final CimiObjectCommon dataCimi) {
        String href = context.makeHref(dataCimi, dataService.getUuid());
        dataCimi.add(new CimiOperation(Operation.EDIT.getRel(), href));

        Set<String> serviceActions = ((Machine) dataService).getOperations();
        for (String valueAction : serviceActions) {
            Operation operation = ConverterHelper.toOperation(valueAction);
            ActionType action = ActionType.findValueOf(valueAction);
            if ((null == action) && (null == operation)) {
                throw new InvalidConversionException("Unknown CIMI operation for a machine : " + valueAction);
            }
            if (null != action) {
                dataCimi.add(new CimiOperation(action.getPath(), href));
            } else {
                dataCimi.add(new CimiOperation(operation.getRel(), href));
            }
        }
    }
}
