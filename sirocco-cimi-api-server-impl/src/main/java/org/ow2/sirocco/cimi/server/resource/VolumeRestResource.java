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

import org.ow2.sirocco.cimi.domain.CimiVolume;
import org.ow2.sirocco.cimi.domain.CimiVolumeCreate;
import org.ow2.sirocco.cimi.domain.CimiVolumeVolumeImage;
import org.ow2.sirocco.cimi.server.manager.CimiManager;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cimi.server.request.ContextHelper;
import org.ow2.sirocco.cimi.server.request.IdRequest;
import org.ow2.sirocco.cimi.server.request.ResponseHelper;
import org.ow2.sirocco.cimi.server.utils.ConstantsPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Volume REST resource.
 * <p>
 * Operations supports :
 * <ul>
 * <li>Create a volume</li>
 * <li>Delete a volume</li>
 * <li>Read a volume</li>
 * <li>Read a collection of volumes</li>
 * <li>Update a volume</li>
 * </ul>
 * </p>
 */
@Component
@Path(ConstantsPath.VOLUME_PATH)
public class VolumeRestResource extends RestResourceAbstract {

    @Autowired
    @Qualifier("CimiManagerReadVolume")
    private CimiManager cimiManagerReadVolume;

    @Autowired
    @Qualifier("CimiManagerReadVolumeCollection")
    private CimiManager cimiManagerReadVolumeCollection;

    @Autowired
    @Qualifier("CimiManagerDeleteVolume")
    private CimiManager cimiManagerDeleteVolume;

    @Autowired
    @Qualifier("CimiManagerUpdateVolume")
    private CimiManager cimiManagerUpdateVolume;

    @Autowired
    @Qualifier("CimiManagerCreateVolume")
    private CimiManager cimiManagerCreateVolume;

    @Autowired
    @Qualifier("CimiManagerReadVolumeVolumeImage")
    private CimiManager cimiManagerReadVolumeVolumeImage;

    @Autowired
    @Qualifier("CimiManagerReadVolumeVolumeImageCollection")
    private CimiManager cimiManagerReadVolumeVolumeImageCollection;

    @Autowired
    @Qualifier("CimiManagerCreateVolumeVolumeImage")
    private CimiManager cimiManagerCreateVolumeVolumeImage;

    @Autowired
    @Qualifier("CimiManagerDeleteVolumeVolumeImage")
    private CimiManager cimiManagerDeleteVolumeVolumeImage;

    @Autowired
    @Qualifier("CimiManagerUpdateVolumeVolumeImage")
    private CimiManager cimiManagerUpdateVolumeVolumeImage;

    /**
     * Get a volume.
     * 
     * @param id The ID of volume to get
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response read(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerReadVolume.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Get a collection of volumes.
     * 
     * @return The REST response
     */
    @GET
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response read() {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos());
        this.cimiManagerReadVolumeCollection.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Update a volume.
     * 
     * @param id The ID of volume to update
     * @return The REST response
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{id}")
    public Response update(@PathParam("id") final String id, final CimiVolume cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id, cimiData);
        this.cimiManagerUpdateVolume.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Create a volume.
     * 
     * @return The REST response
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response create(final CimiVolumeCreate cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), cimiData);
        this.cimiManagerCreateVolume.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Delete a volume.
     * 
     * @param id The ID of volume to delete
     * @return The REST response
     */
    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), id);
        this.cimiManagerDeleteVolume.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Read a collection of volumeImages of a volume.
     * 
     * @param idParent ID volume
     * @return The REST response
     */
    @GET
    @Path("{idParent}" + ConstantsPath.VOLUME_IMAGE_PATH)
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response readVolumeImages(@PathParam("idParent") final String idParent) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(null, idParent));
        this.cimiManagerReadVolumeVolumeImageCollection.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Read a volumeImage of a volume.
     * 
     * @param idParent ID volume
     * @param id ID volumeImage to read
     * @return The REST response
     */
    @GET
    @Path("/{idParent}" + ConstantsPath.VOLUME_IMAGE_PATH + "/{id}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Response readVolumeImage(@PathParam("idParent") final String idParent, @PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent));
        this.cimiManagerReadVolumeVolumeImage.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Update a volumeImage of a volume.
     * 
     * @param idParent ID volume
     * @param id ID volumeImage to update
     * @return The REST response
     */
    @PUT
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{idParent}" + ConstantsPath.VOLUME_IMAGE_PATH + "/{id}")
    public Response updateVolumeImage(@PathParam("idParent") final String idParent, @PathParam("id") final String id,
        final CimiVolumeVolumeImage cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent), cimiData);
        this.cimiManagerUpdateVolumeVolumeImage.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Create a volumeImage of a volume.
     * 
     * @param idParent ID volume
     * @return The REST response
     */
    @POST
    @Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    @Path("/{idParent}" + ConstantsPath.VOLUME_IMAGE_PATH)
    public Response createVolumeImage(@PathParam("idParent") final String idParent, final CimiVolumeVolumeImage cimiData) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(null, idParent), cimiData);
        this.cimiManagerCreateVolumeVolumeImage.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

    /**
     * Delete a volumeImage of a volume.
     * 
     * @param idParent ID volume
     * @param id ID volumeImage to delete
     * @return The REST response
     */
    @DELETE
    @Path("/{idParent}" + ConstantsPath.VOLUME_IMAGE_PATH + "/{id}")
    public Response deleteVolumeImage(@PathParam("idParent") final String idParent, @PathParam("id") final String id) {
        CimiContext context = ContextHelper.buildContext(this.getJaxRsRequestInfos(), new IdRequest(id, idParent));
        this.cimiManagerDeleteVolumeVolumeImage.execute(context);
        return ResponseHelper.buildResponse(context.getResponse());
    }

}
