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
package org.ow2.sirocco.cimi.server.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.ow2.sirocco.cimi.domain.CimiNetworkPortConfiguration;
import org.ow2.sirocco.cimi.server.manager.CimiManager;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cimi.server.request.ContextHelper;
import org.ow2.sirocco.cimi.server.request.ResponseHelper;
import org.ow2.sirocco.cimi.server.utils.ConstantsPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * NetworkPort Configuration REST resource.
 * <p>
 * Operations supports :
 * <ul>
 * <li>Create a networkPort configuration</li>
 * <li>Delete a networkPort configuration</li>
 * <li>Read a networkPort configuration</li>
 * <li>Read a collection of networkPorts configurations</li>
 * <li>Update a networkPort configuration</li>
 * </ul>
 * </p>
 */
@Component
@Path(ConstantsPath.NETWORK_PORT_CONFIGURATION_PATH)
public class NetworkPortConfigurationRestResource extends RestResourceAbstract {

    @Autowired
    @Qualifier("CimiManagerReadNetworkPortConfiguration")
    private CimiManager cimiManagerReadNetworkPortConfiguration;

    @Autowired
    @Qualifier("CimiManagerReadNetworkPortConfigurationCollection")
    private CimiManager cimiManagerReadNetworkPortConfigurationCollection;

    @Autowired
    @Qualifier("CimiManagerDeleteNetworkPortConfiguration")
    private CimiManager cimiManagerDeleteNetworkPortConfiguration;

    @Autowired
    @Qualifier("CimiManagerUpdateNetworkPortConfiguration")
    private CimiManager cimiManagerUpdateNetworkPortConfiguration;

    @Autowired
    @Qualifier("CimiManagerCreateNetworkPortConfiguration")
    private CimiManager cimiManagerCreateNetworkPortConfiguration;

    /**
     * Get a networkPort configuration.
     * 
     * @param id The ID of networkPort configuration to get
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response read(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerReadNetworkPortConfiguration.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Get a collection of networkPorts configurations.
     * 
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response read() {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos());
        this.cimiManagerReadNetworkPortConfigurationCollection.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Update a networkPort configuration.
     * 
     * @param id The ID of networkPort configuration to update
     * @return The REST response
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response update(@PathParam("id") final String id, final CimiNetworkPortConfiguration cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id, cimiData);
        this.cimiManagerUpdateNetworkPortConfiguration.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Create a networkPort configuration.
     * 
     * @return The REST response
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(final CimiNetworkPortConfiguration cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), cimiData);
        this.cimiManagerCreateNetworkPortConfiguration.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Delete a networkPort configuration.
     * 
     * @param id The ID of networkPort configuration to delete
     * @return The REST response
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerDeleteNetworkPortConfiguration.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }
}
