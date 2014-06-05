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
package org.ow2.sirocco.cimi.domain;

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
 * Class Address Create.
 */
@XmlRootElement(name = "AddressCreate")
@XmlType(propOrder = {"name", "description", "propertyArray", "addressTemplate", "providerAccountId", "location",
    "xmlExtensionAttributes"})
@JsonPropertyOrder({"resourceURI", "name", "description", "properties", "addressTemplate", "providerAccountId", "location"})
@JsonSerialize(include = Inclusion.NON_NULL)
public class CimiAddressCreate extends CimiCommonResourceUriAbstract {

    /** Serial number */
    private static final long serialVersionUID = 1L;

    /**
     * Field "addressTemplate".
     */
    @ValidChild
    @NotNull(groups = {GroupCreateByValue.class})
    private CimiAddressTemplate addressTemplate;

    private String providerAccountId;

    private String location;

    /**
     * Return the value of field "addressTemplate".
     * 
     * @return The value
     */
    public CimiAddressTemplate getAddressTemplate() {
        return this.addressTemplate;
    }

    /**
     * Set the value of field "addressTemplate".
     * 
     * @param addressTemplate The value
     */
    public void setAddressTemplate(final CimiAddressTemplate addressTemplate) {
        this.addressTemplate = addressTemplate;
    }

    public String getProviderAccountId() {
        return this.providerAccountId;
    }

    public void setProviderAccountId(final String providerAccountId) {
        this.providerAccountId = providerAccountId;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(final String location) {
        this.location = location;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.domain.CimiExchange#getExchangeType()
     */
    @Override
    @XmlTransient
    @JsonIgnore
    public ExchangeType getExchangeType() {
        return ExchangeType.AddressCreate;
    }
}
