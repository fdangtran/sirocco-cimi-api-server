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

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
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

import org.ow2.sirocco.cimi.domain.CimiAction;
import org.ow2.sirocco.cimi.domain.CimiNetwork;
import org.ow2.sirocco.cimi.domain.CimiNetworkCreate;
import org.ow2.sirocco.cimi.domain.CimiNetworkNetworkPort;
import org.ow2.sirocco.cimi.server.manager.CimiManager;
import org.ow2.sirocco.cimi.server.manager.Manager;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cimi.server.request.ContextHelper;
import org.ow2.sirocco.cimi.server.request.IdRequest;
import org.ow2.sirocco.cimi.server.request.ResponseHelper;
import org.ow2.sirocco.cimi.server.utils.ConstantsPath;

/**
 * Network REST resource.
 * <p>
 * Operations supports :
 * <ul>
 * <li>Create a network</li>
 * <li>Delete a network</li>
 * <li>Read a network</li>
 * <li>Read a collection of networks</li>
 * <li>Update a network</li>
 * </ul>
 * </p>
 */
@ResourceInterceptorBinding
@RequestScoped
@Path(ConstantsPath.NETWORK_PATH)
public class NetworkRestResource extends RestResourceAbstract {

    @Inject
    @Manager("CimiManagerReadNetwork")
    private CimiManager cimiManagerReadNetwork;

    @Inject
    @Manager("CimiManagerReadNetworkCollection")
    private CimiManager cimiManagerReadNetworkCollection;

    @Inject
    @Manager("CimiManagerDeleteNetwork")
    private CimiManager cimiManagerDeleteNetwork;

    @Inject
    @Manager("CimiManagerUpdateNetwork")
    private CimiManager cimiManagerUpdateNetwork;

    @Inject
    @Manager("CimiManagerCreateNetwork")
    private CimiManager cimiManagerCreateNetwork;

    @Inject
    @Manager("CimiManagerActionNetwork")
    private CimiManager cimiManagerActionNetwork;

    @Inject
    @Manager("CimiManagerReadNetworkNetworkPort")
    private CimiManager cimiManagerReadNetworkNetworkPort;

    @Inject
    @Manager("CimiManagerReadNetworkNetworkPortCollection")
    private CimiManager cimiManagerReadNetworkNetworkPortCollection;

    @Inject
    @Manager("CimiManagerCreateNetworkNetworkPort")
    private CimiManager cimiManagerCreateNetworkNetworkPort;

    @Inject
    @Manager("CimiManagerDeleteNetworkNetworkPort")
    private CimiManager cimiManagerDeleteNetworkNetworkPort;

    @Inject
    @Manager("CimiManagerUpdateNetworkNetworkPort")
    private CimiManager cimiManagerUpdateNetworkNetworkPort;

    /**
     * Get a network.
     * 
     * @param id The ID of network to get
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response read(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerReadNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Get a collection of networks.
     * 
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response read() {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos());
        this.cimiManagerReadNetworkCollection.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Update a network.
     * 
     * @param id The ID of network to update
     * @return The REST response
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response update(@PathParam("id") final String id, final CimiNetwork cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id, cimiData);
        this.cimiManagerUpdateNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Create a network.
     * 
     * @return The REST response
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(final CimiNetworkCreate cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), cimiData);
        this.cimiManagerCreateNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Actions on network.
     * 
     * @return The REST response
     */
    @POST
    @Path("/{id}")
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response action(@PathParam("id") final String id, final CimiAction cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id, cimiData);
        this.cimiManagerActionNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Delete a network.
     * 
     * @param id The ID of network to delete
     * @return The REST response
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerDeleteNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Read a collection of networkPorts of a network.
     * 
     * @param idParent ID network
     * @return The REST response
     */
    @GET
    @Path("{idParent}" + ConstantsPath.NETWORK_PORT_PATH)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response readNetworkPorts(@PathParam("idParent") final String idParent) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(null, idParent));
        this.cimiManagerReadNetworkNetworkPortCollection.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Read a networkPort of a network.
     * 
     * @param idParent ID network
     * @param id ID networkPort to read
     * @return The REST response
     */
    @GET
    @Path("/{idParent}" + ConstantsPath.NETWORK_PORT_PATH + "/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response readNetworkPort(@PathParam("idParent") final String idParent, @PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent));
        this.cimiManagerReadNetworkNetworkPort.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Update a networkPort of a network.
     * 
     * @param idParent ID network
     * @param id ID networkPort to update
     * @return The REST response
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{idParent}" + ConstantsPath.NETWORK_PORT_PATH + "/{id}")
    public Response updateNetworkPort(@PathParam("idParent") final String idParent, @PathParam("id") final String id,
        final CimiNetworkNetworkPort cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent), cimiData);
        this.cimiManagerUpdateNetworkNetworkPort.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Create a networkPort of a network.
     * 
     * @param idParent ID network
     * @return The REST response
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{idParent}" + ConstantsPath.NETWORK_PORT_PATH)
    public Response createNetworkPort(@PathParam("idParent") final String idParent, final CimiNetworkNetworkPort cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(null, idParent), cimiData);
        this.cimiManagerCreateNetworkNetworkPort.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Delete a networkPort of a network.
     * 
     * @param idParent ID network
     * @param id ID networkPort to delete
     * @return The REST response
     */
    @DELETE
    @Path("/{idParent}" + ConstantsPath.NETWORK_PORT_PATH + "/{id}")
    public Response deleteNetworkPort(@PathParam("idParent") final String idParent, @PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent));
        this.cimiManagerDeleteNetworkNetworkPort.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

}
