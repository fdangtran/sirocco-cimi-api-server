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

import org.ow2.sirocco.cimi.domain.CimiForwardingGroup;
import org.ow2.sirocco.cimi.domain.CimiForwardingGroupCreate;
import org.ow2.sirocco.cimi.domain.CimiForwardingGroupNetwork;
import org.ow2.sirocco.cimi.server.manager.CimiManager;
import org.ow2.sirocco.cimi.server.manager.Manager;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cimi.server.request.ContextHelper;
import org.ow2.sirocco.cimi.server.request.IdRequest;
import org.ow2.sirocco.cimi.server.request.ResponseHelper;
import org.ow2.sirocco.cimi.server.utils.ConstantsPath;

/**
 * ForwardingGroup REST resource.
 * <p>
 * Operations supports :
 * <ul>
 * <li>Create a forwardingGroup</li>
 * <li>Delete a forwardingGroup</li>
 * <li>Read a forwardingGroup</li>
 * <li>Read a collection of forwardingGroups</li>
 * <li>Update a forwardingGroup</li>
 * </ul>
 * </p>
 */
@ResourceInterceptorBinding
@RequestScoped
@Path(ConstantsPath.FORWARDING_GROUP_PATH)
public class ForwardingGroupRestResource extends RestResourceAbstract {

    @Inject
    @Manager("CimiManagerReadForwardingGroup")
    private CimiManager cimiManagerReadForwardingGroup;

    @Inject
    @Manager("CimiManagerReadForwardingGroupCollection")
    private CimiManager cimiManagerReadForwardingGroupCollection;

    @Inject
    @Manager("CimiManagerDeleteForwardingGroup")
    private CimiManager cimiManagerDeleteForwardingGroup;

    @Inject
    @Manager("CimiManagerUpdateForwardingGroup")
    private CimiManager cimiManagerUpdateForwardingGroup;

    @Inject
    @Manager("CimiManagerCreateForwardingGroup")
    private CimiManager cimiManagerCreateForwardingGroup;

    @Inject
    @Manager("CimiManagerReadForwardingGroupNetwork")
    private CimiManager cimiManagerReadForwardingGroupNetwork;

    @Inject
    @Manager("CimiManagerReadForwardingGroupNetworkCollection")
    private CimiManager cimiManagerReadForwardingGroupNetworkCollection;

    @Inject
    @Manager("CimiManagerCreateForwardingGroupNetwork")
    private CimiManager cimiManagerCreateForwardingGroupNetwork;

    @Inject
    @Manager("CimiManagerDeleteForwardingGroupNetwork")
    private CimiManager cimiManagerDeleteForwardingGroupNetwork;

    @Inject
    @Manager("CimiManagerUpdateForwardingGroupNetwork")
    private CimiManager cimiManagerUpdateForwardingGroupNetwork;

    /**
     * Get a forwardingGroup.
     * 
     * @param id The ID of forwardingGroup to get
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response read(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerReadForwardingGroup.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Get a collection of forwardingGroups.
     * 
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response read() {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos());
        this.cimiManagerReadForwardingGroupCollection.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Update a forwardingGroup.
     * 
     * @param id The ID of forwardingGroup to update
     * @return The REST response
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response update(@PathParam("id") final String id, final CimiForwardingGroup cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id, cimiData);
        this.cimiManagerUpdateForwardingGroup.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Create a forwardingGroup.
     * 
     * @return The REST response
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(final CimiForwardingGroupCreate cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), cimiData);
        this.cimiManagerCreateForwardingGroup.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Delete a forwardingGroup.
     * 
     * @param id The ID of forwardingGroup to delete
     * @return The REST response
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerDeleteForwardingGroup.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Read a collection of networks of a forwardingGroup.
     * 
     * @param idParent ID forwardingGroup
     * @return The REST response
     */
    @GET
    @Path("{idParent}" + ConstantsPath.NETWORK_PATH)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response readNetworks(@PathParam("idParent") final String idParent) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(null, idParent));
        this.cimiManagerReadForwardingGroupNetworkCollection.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Read a network of a forwardingGroup.
     * 
     * @param idParent ID forwardingGroup
     * @param id ID network to read
     * @return The REST response
     */
    @GET
    @Path("/{idParent}" + ConstantsPath.NETWORK_PATH + "/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response readNetwork(@PathParam("idParent") final String idParent, @PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent));
        this.cimiManagerReadForwardingGroupNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Update a network of a forwardingGroup.
     * 
     * @param idParent ID forwardingGroup
     * @param id ID network to update
     * @return The REST response
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{idParent}" + ConstantsPath.NETWORK_PATH + "/{id}")
    public Response updateNetwork(@PathParam("idParent") final String idParent, @PathParam("id") final String id,
        final CimiForwardingGroupNetwork cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent), cimiData);
        this.cimiManagerUpdateForwardingGroupNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Create a network of a forwardingGroup.
     * 
     * @param idParent ID forwardingGroup
     * @return The REST response
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{idParent}" + ConstantsPath.NETWORK_PATH)
    public Response createNetwork(@PathParam("idParent") final String idParent, final CimiForwardingGroupNetwork cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(null, idParent), cimiData);
        this.cimiManagerCreateForwardingGroupNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Delete a network of a forwardingGroup.
     * 
     * @param idParent ID forwardingGroup
     * @param id ID network to delete
     * @return The REST response
     */
    @DELETE
    @Path("/{idParent}" + ConstantsPath.NETWORK_PATH + "/{id}")
    public Response deleteNetwork(@PathParam("idParent") final String idParent, @PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent));
        this.cimiManagerDeleteForwardingGroupNetwork.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

}
