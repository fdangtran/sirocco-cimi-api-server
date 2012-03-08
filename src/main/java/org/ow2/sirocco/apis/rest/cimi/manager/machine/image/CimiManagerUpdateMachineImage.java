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
package org.ow2.sirocco.apis.rest.cimi.manager.machine.image;

import java.util.List;

import javax.ws.rs.core.Response.Status;

import org.ow2.sirocco.apis.rest.cimi.domain.CimiMachineImage;
import org.ow2.sirocco.apis.rest.cimi.request.CimiRequest;
import org.ow2.sirocco.apis.rest.cimi.request.CimiResponse;
import org.ow2.sirocco.apis.rest.cimi.utils.ConstantsPath;

public class CimiManagerUpdateMachineImage {

    public CimiManagerUpdateMachineImage() {
    }

    public Status verifyRequest(CimiRequest request) {
        // FIXME le path de la requete doit être au format http://example.com +
        // ConstantePath
        if (request.getHeader().getBaseUri().toString().equals("http://localhost:9998/")
                && request.getHeader().getPath().startsWith(ConstantsPath.MACHINE_IMAGE.substring(1))) {
            return Status.OK;
        } else {
            return Status.BAD_REQUEST;
        }
    }

    public void execute(CimiRequest request, CimiResponse response) {
        // Status status = verifyRequest(request);
        // if (status.equals(Status.OK)) {
        // MachineImage machineImageToUpdate =
        // getMachineImageById(request.getHeader().getId());
        // MachineImage machineImagePropertiesToUpdate = (MachineImage)
        // request.getHeader().getCimiData();
        // updateMachineImage(machineImageToUpdate,
        // machineImagePropertiesToUpdate, request.getHeader()
        // .getListSelect());
        // // status = 202 Accepted
        // response.setStatusHttp(Status.ACCEPTED.getStatusCode());
        // } else {
        // // status = 400 BAD REQUEST
        // response.setStatusHttp(Status.BAD_REQUEST.getStatusCode());
        // }

    }

    private void updateMachineImage(CimiMachineImage machineImageToUpdate, CimiMachineImage machineImagePropertiesToUpdate,
            List<String> queryParam) {
        // FIXME IMachineManager.updateMachineImage(machineImageToUpdate,
        // machineImagePropertiesToUpdate, queryParam);
    }

    public CimiMachineImage getMachineImageById(String id) {
        // FIXME return IMachineManager.getMachineImageById(id);
        return null;
    }

}
