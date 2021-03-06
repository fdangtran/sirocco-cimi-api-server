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

import org.ow2.sirocco.cimi.domain.CimiEventLog;
import org.ow2.sirocco.cimi.domain.CimiMachine;
import org.ow2.sirocco.cimi.domain.CimiMachineVolume;
import org.ow2.sirocco.cimi.domain.CimiVolume;
import org.ow2.sirocco.cimi.domain.CimiVolume.MachineAttachment;
import org.ow2.sirocco.cimi.domain.collection.CimiVolumeVolumeImageCollection;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cloudmanager.model.cimi.MachineVolume;
import org.ow2.sirocco.cloudmanager.model.cimi.Volume;
import org.ow2.sirocco.cloudmanager.model.cimi.VolumeVolumeImage;

/**
 * Convert the data of the CIMI model and the service model in both directions.
 * <p>
 * Converted classes:
 * <ul>
 * <li>CIMI model: {@link CimiVolume}</li>
 * <li>Service model: {@link Volume}</li>
 * </ul>
 * </p>
 */
public class VolumeConverter extends ObjectCommonConverter {

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#toCimi(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object)
     */
    @Override
    public Object toCimi(final CimiContext context, final Object dataService) {
        CimiVolume cimi = new CimiVolume();
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
        this.doCopyToCimi(context, (Volume) dataService, (CimiVolume) dataCimi);
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#toService(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object)
     */
    @Override
    public Object toService(final CimiContext context, final Object dataCimi) {
        Volume service = new Volume();
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
        this.doCopyToService(context, (CimiVolume) dataCimi, (Volume) dataService);
    }

    /**
     * Copy data from a service object to a CIMI object.
     * 
     * @param context The current context
     * @param dataService Source service object
     * @param dataCimi Destination CIMI object
     */
    protected void doCopyToCimi(final CimiContext context, final Volume dataService, final CimiVolume dataCimi) {
        this.fill(context, dataService, dataCimi);
        if (true == context.mustBeExpanded(dataCimi)) {
            dataCimi.setBootable(dataService.getBootable());
            dataCimi.setCapacity(dataService.getCapacity());
            dataCimi.setEventLog((CimiEventLog) context.convertNextCimi(dataService.getEventLog(), CimiEventLog.class));
            dataCimi.setImages((CimiVolumeVolumeImageCollection) context.convertNextCimi(dataService.getImages(),
                CimiVolumeVolumeImageCollection.class));
            dataCimi.setState(ConverterHelper.toString(dataService.getState()));
            dataCimi.setType(PathHelper.makeCimiURI(dataService.getType()));
            dataCimi.setProviderInfo(ProviderInfoConverter.convert(dataService));
            if (dataService.getAttachments() != null) {
                List<MachineAttachment> attachments = new ArrayList<MachineAttachment>();
                for (MachineVolume mv : dataService.getAttachments()) {
                    MachineAttachment attachment = new MachineAttachment();
                    CimiMachine cimiMachine = new CimiMachine();
                    cimiMachine.setHref(context.makeHref(cimiMachine, mv.getOwner().getUuid()));
                    cimiMachine.setName(mv.getOwner().getName());
                    attachment.setMachine(cimiMachine);
                    CimiMachineVolume cimiMachineVolume = new CimiMachineVolume();
                    cimiMachineVolume.setHref(cimiMachine.getHref() + "/volumes/" + mv.getUuid());
                    attachment.setMachineVolume(cimiMachineVolume);
                    attachment.setState(mv.getState().toString());
                    attachments.add(attachment);
                }
                dataCimi.setAttachments(attachments);
            }

        }
    }

    /**
     * Copy data from a CIMI object to a service object.
     * 
     * @param context The current context
     * @param dataCimi Source CIMI object
     * @param dataService Destination Service object
     */
    @SuppressWarnings("unchecked")
    protected void doCopyToService(final CimiContext context, final CimiVolume dataCimi, final Volume dataService) {
        this.fill(context, dataCimi, dataService);
        dataService.setBootable(dataCimi.getBootable());
        dataService.setCapacity(dataCimi.getCapacity());
        dataService.setImages((List<VolumeVolumeImage>) context.convertNextService(dataCimi.getImages()));

        // Next Read only
        // dataService.setState(dataService.getState());
        // dataService.setType(dataService.getType());
    }
}
