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

/**
 * Class MachineImage.
 * <p>
 */
@XmlRootElement(name = "MachineImage")
@XmlType(propOrder = {"id", "name", "description", "created", "updated", "propertyArray", "state", "type", "imageLocation",
    "relatedImage", "operations", "visibility", "xmlExtensionAttributes"})
@JsonPropertyOrder({"resourceURI", "id", "name", "description", "created", "updated", "properties", "state", "type",
    "imageLocation", "relatedImage", "operations", "visibility"})
@JsonSerialize(include = Inclusion.NON_NULL)
public class CimiMachineImage extends CimiObjectCommonAbstract {

    /** Serial number */
    private static final long serialVersionUID = 1L;

    /**
     * Field "state".
     */
    private String state;

    /**
     * Field "type".
     */
    private String type;

    /**
     * Field "imageLocation".
     */
    // @Valid
    @NotNull(groups = {GroupCreateByValue.class})
    private String imageLocation;

    private RelatedMachineImage relatedImage;

    private String visibility;

    /**
     * Default constructor.
     */
    public CimiMachineImage() {
        super();
    }

    /**
     * Parameterized constructor.
     * 
     * @param href The reference
     */
    public CimiMachineImage(final String href) {
        super(href);
    }

    /**
     * Parameterized constructor.
     * 
     * @param imageLocation The image location
     */
    // public CimiMachineImage(final ImageLocation imageLocation) {
    // super();
    // this.imageLocation = imageLocation;
    // }

    /**
     * Return the value of field "state".
     * 
     * @return The value
     */
    public String getState() {
        return this.state;
    }

    /**
     * Set the value of field "state".
     * 
     * @param state The value
     */
    public void setState(final String state) {
        this.state = state;
    }

    /**
     * Return the value of field "imageLocation".
     * 
     * @return The value
     */
    public String getImageLocation() {
        return this.imageLocation;
    }

    /**
     * Set the value of field "imageLocation".
     * 
     * @param imageLocation The value
     */
    public void setImageLocation(final String imageLocation) {
        this.imageLocation = imageLocation;
    }

    /**
     * Return the value of field "relatedImage"
     * 
     * @return The value
     */
    public RelatedMachineImage getRelatedImage() {
        return this.relatedImage;
    }

    /**
     * Set the value of field "relatedImage"
     * 
     * @param relatedImage The value
     */
    public void setRelatedImage(final RelatedMachineImage relatedImage) {
        this.relatedImage = relatedImage;
    }

    /**
     * @return the type
     */
    public String getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final String type) {
        this.type = type;
    }

    public String getVisibility() {
        return this.visibility;
    }

    public void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    /**
     * {@inheritDoc}
     * 
     * @see org.ow2.sirocco.cimi.domain.CimiObjectCommonAbstract#hasValues()
     */
    @Override
    public boolean hasValues() {
        boolean has = super.hasValues();
        has = has || (null != this.getImageLocation());
        return has;
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
        return ExchangeType.MachineImage;
    }

}
