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
package org.ow2.sirocco.apis.rest.cimi.resource;

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

import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineTemplate;
import org.ow2.sirocco.apis.rest.cimi.manager.CimiManager;
import org.ow2.sirocco.apis.rest.cimi.request.CimiContext;
import org.ow2.sirocco.apis.rest.cimi.request.ContextHelper;
import org.ow2.sirocco.apis.rest.cimi.request.ResponseHelper;
import org.ow2.sirocco.apis.rest.cimi.utils.ConstantsPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Machine Template REST resource.
 * <p>
 * Operations supports :
 * <ul>
 * <li>Create a machine template</li>
 * <li>Delete a machine template</li>
 * <li>Read a machine template</li>
 * <li>Read a collection of machines templates</li>
 * <li>Update a machine template</li>
 * </ul>
 * </p>
 */
@Component
@Path(ConstantsPath.MACHINE_TEMPLATE_PATH)
public class MachineTemplateRestResource extends RestResourceAbstract {

    @Autowired
    @Qualifier("CimiManagerReadMachineTemplate")
    private CimiManager cimiManagerReadMachineTemplate;

    @Autowired
    @Qualifier("CimiManagerReadMachineTemplateCollection")
    private CimiManager cimiManagerReadMachineTemplateCollection;

    @Autowired
    @Qualifier("CimiManagerDeleteMachineTemplate")
    private CimiManager cimiManagerDeleteMachineTemplate;

    @Autowired
    @Qualifier("CimiManagerUpdateMachineTemplate")
    private CimiManager cimiManagerUpdateMachineTemplate;

    @Autowired
    @Qualifier("CimiManagerCreateMachineTemplate")
    private CimiManager cimiManagerCreateMachineTemplate;

    /**
     * Get a machine template.
     * 
     * @param id The ID of machine template to get
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response read(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerReadMachineTemplate.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Get a collection of machines templates.
     * 
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response read() {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos());
        this.cimiManagerReadMachineTemplateCollection.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Update a machine template.
     * 
     * @param id The ID of machine template to update
     * @return The REST response
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response update(@PathParam("id") final String id, final CimiMachineTemplate cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id, cimiData);
        this.cimiManagerUpdateMachineTemplate.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Create a machine template.
     * 
     * @return The REST response
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(final CimiMachineTemplate cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), cimiData);
        this.cimiManagerCreateMachineTemplate.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Delete a machine template.
     * 
     * @param id The ID of machine template to delete
     * @return The REST response
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerDeleteMachineTemplate.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

}