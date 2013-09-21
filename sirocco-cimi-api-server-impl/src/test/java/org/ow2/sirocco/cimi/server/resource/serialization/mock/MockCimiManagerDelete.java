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
package org.ow2.sirocco.cimi.server.resource.serialization.mock;

import javax.enterprise.inject.Alternative;
import javax.ws.rs.core.Response.Status;

import org.ow2.sirocco.cimi.server.manager.CimiManager;
import org.ow2.sirocco.cimi.server.request.CimiContext;

/**
 * Mock CimiManagerDeleteMachineImage.
 */
@Alternative
public class MockCimiManagerDelete implements CimiManager {

    /**
     * {@inheritDoc}
     * <p>
     * Build a new MachineImage and compare it with the MachineImage in request.
     * </p>
     * 
     * @see org.ow2.sirocco.cimi.server.manager.CimiManager#execute(org.ow2.sirocco.cimi.server.request.CimiRequest,
     *      org.ow2.sirocco.cimi.server.request.CimiResponse)
     */
    @Override
    public void execute(final CimiContext context) {
        try {
            // Test ID
            Integer.valueOf(context.getRequest().getId());

            // Build response
            context.getResponse().setCimiData(null);
            context.getResponse().setStatus(Status.OK);
        } catch (Exception e) {
            context.getResponse().setErrorMessage(e.getMessage());
            context.getResponse().setStatus(Status.SERVICE_UNAVAILABLE);
        }
    }

}