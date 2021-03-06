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

import java.util.HashMap;
import java.util.Map;

import org.ow2.sirocco.cimi.domain.CimiEventLogCreate;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cloudmanager.model.cimi.event.EventLogCreate;
import org.ow2.sirocco.cloudmanager.model.cimi.event.EventLogTemplate;

/**
 * Convert the data of the CIMI model and the service model in both directions.
 * <p>
 * Converted classes:
 * <ul>
 * <li>CIMI model: {@link CimiEventLogCreate}</li>
 * <li>Service model: {@link EventLogCreate}</li>
 * </ul>
 * </p>
 */
public class EventLogCreateConverter implements CimiConverter {

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#toCimi(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object)
     */
    @Override
    public Object toCimi(final CimiContext context, final Object dataService) {
        // Unnecessary
        return null;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#copyToCimi(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object, java.lang.Object)
     */
    @Override
    public void copyToCimi(final CimiContext context, final Object dataService, final Object dataCimi) {
        // Unnecessary
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.converter.CimiConverter#toService(org.ow2.sirocco.cimi.server.utils.CimiContextImpl,
     *      java.lang.Object)
     */
    @Override
    public Object toService(final CimiContext context, final Object dataCimi) {
        EventLogCreate service = new EventLogCreate();
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
        this.doCopyToService(context, (CimiEventLogCreate) dataCimi, (EventLogCreate) dataService);
    }

    /**
     * Fill the common data from a CIMI object to a service object.
     * 
     * @param dataCimi Source CIMI object
     * @param dataService Destination Service object
     */
    protected void fill(final CimiEventLogCreate dataCimi, final EventLogCreate dataService) {
        dataService.setDescription(dataCimi.getDescription());
        dataService.setName(dataCimi.getName());
        if (null != dataCimi.getProperties()) {
            Map<String, String> props = new HashMap<String, String>();
            dataService.setProperties(props);
            props.putAll(dataCimi.getProperties());
        }
    }

    /**
     * Copy data from a CIMI object to a service object.
     * 
     * @param context The current context
     * @param dataCimi Source CIMI object
     * @param dataService Destination Service object
     */
    protected void doCopyToService(final CimiContext context, final CimiEventLogCreate dataCimi,
        final EventLogCreate dataService) {
        this.fill(dataCimi, dataService);
        dataService.setEventLogTemplate((EventLogTemplate) context.convertNextService(dataCimi.getEventLogTemplate()));
    }
}
