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
package org.ow2.sirocco.cimi.server.manager.forwarding.template;

import javax.inject.Inject;

import org.ow2.sirocco.cimi.server.manager.CimiManagerUpdateAbstract;
import org.ow2.sirocco.cimi.server.request.CimiContext;
import org.ow2.sirocco.cloudmanager.core.api.INetworkManager;
import org.ow2.sirocco.cloudmanager.model.cimi.ForwardingGroupTemplate;

/**
 * Manage UPDATE request of ForwardingGroupTemplate.
 */
@org.ow2.sirocco.cimi.server.manager.Manager("CimiManagerUpdateForwardingGroupTemplate")
public class CimiManagerUpdateForwardingGroupTemplate extends CimiManagerUpdateAbstract {

    @Inject
    private INetworkManager manager;

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.manager.CimiManagerAbstract#callService(org.ow2.sirocco.cimi.server.request.CimiContext,
     *      java.lang.Object)
     */
    @Override
    protected Object callService(final CimiContext context, final Object dataService) throws Exception {
        if (false == context.hasParamSelect()) {
            this.manager.updateForwardingGroupTemplate((ForwardingGroupTemplate) dataService);
        } else {
            this.manager.updateForwardingGroupTemplateAttributes(context.getRequest().getId(),
                context.copyBeanAttributesOfSelect(dataService));
        }
        return null;
    }
}
