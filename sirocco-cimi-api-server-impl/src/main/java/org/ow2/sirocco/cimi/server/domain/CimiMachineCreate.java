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
package org.ow2.sirocco.cimi.server.domain;

import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.annotate.JsonPropertyOrder;
import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.ow2.sirocco.cimi.server.validator.GroupCreateByValue;
import org.ow2.sirocco.cimi.server.validator.ValidChild;

/**
 * Class Machine Create.
 * <p>
 * </p>
 */
@XmlRootElement(name = "MachineCreate")
@XmlType(propOrder = {"name", "description", "propertyArray", "machineTemplate", "xmlExtensionAttributes"})
@JsonPropertyOrder({"resourceURI", "name", "description", "properties", "machineTemplate"})
@JsonSerialize(include = Inclusion.NON_NULL)
public class CimiMachineCreate extends CimiCommonResourceUriAbstract {

    /** Serial number */
    private static final long serialVersionUID = 1L;

    /**
     * Field "machineTemplate".
     */
    @ValidChild
    @NotNull(groups = {GroupCreateByValue.class})
    private CimiMachineTemplate machineTemplate;

    /**
     * Return the value of field "machineTemplate".
     * 
     * @return The value
     */
    public CimiMachineTemplate getMachineTemplate() {
        return this.machineTemplate;
    }

    /**
     * Set the value of field "machineTemplate".
     * 
     * @param machineTemplate The value
     */
    public void setMachineTemplate(final CimiMachineTemplate machineTemplate) {
        this.machineTemplate = machineTemplate;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.server.domain.CimiExchange#getExchangeType()
     */
    @Override
    @XmlTransient
    @JsonIgnore
    public ExchangeType getExchangeType() {
        return ExchangeType.MachineCreate;
    }
}
