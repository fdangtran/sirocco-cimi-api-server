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
package org.ow2.sirocco.cimi.server.configuration;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ow2.sirocco.cimi.domain.ExchangeType;

public class Config implements Serializable {

    /** Serial Version */
    private static final long serialVersionUID = 1L;

    /** Mapper by class. */
    private Map<Class<?>, ItemConfig> byClasses;

    /** Mapper by ExchangeType. */
    private Map<ExchangeType, ItemConfig> byExchangeTypes;

    /**
     * Set the configuration for CimiEntities.
     * 
     * @param configs A list of ItemConfig
     */
    public void setItems(final List<ItemConfig> configs) {
        this.buildMappers(configs);
    }

    /**
     * Build configuration mapper.
     * 
     * @param configs A list of ItemConfig
     */
    protected void buildMappers(final List<ItemConfig> configs) {
        this.byClasses = new HashMap<Class<?>, ItemConfig>();
        this.byExchangeTypes = new HashMap<ExchangeType, ItemConfig>();
        for (ItemConfig item : configs) {
            this.byClasses.put(item.getKlass(), item);
            if (null != item.getType()) {
                this.byExchangeTypes.put(item.getType(), item);
            }
        }
    }

    /**
     * Find the item associate to the given class.
     * 
     * @param classToFind The class to find
     * @return The item or null if class not found
     */
    public ItemConfig find(final Class<?> classToFind) {
        return this.byClasses.get(classToFind);
    }

    /**
     * Find the item associate to the given ExchangeType.
     * 
     * @param typeToFind The ExchangeType to find
     * @return The item or null if type not found
     */
    public ItemConfig find(final ExchangeType typeToFind) {
        return this.byExchangeTypes.get(typeToFind);
    }
}