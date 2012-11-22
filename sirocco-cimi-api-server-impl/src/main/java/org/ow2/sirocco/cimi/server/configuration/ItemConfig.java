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

import java.util.HashMap;
import java.util.Map;

import org.ow2.sirocco.cimi.domain.ExchangeType;

public class ItemConfig {

    private ExchangeType type;

    private Class<?> klass;

    private Map<String, Object> datas;

    /**
     * @param type
     * @param klass
     */
    public ItemConfig() {
        this.datas = new HashMap<String, Object>();
    }

    /**
     * @param type
     * @param klass
     */
    public ItemConfig(final Class<?> klass) {
        this();
        this.setKlass(klass);
    }

    /**
     * @param klass
     * @param type
     */
    public ItemConfig(final Class<?> klass, final ExchangeType type) {
        this();
        this.setType(type);
        this.setKlass(klass);
    }

    /**
     * @return the type
     */
    public ExchangeType getType() {
        return this.type;
    }

    /**
     * @param type the type to set
     */
    public void setType(final ExchangeType type) {
        this.type = type;
    }

    /**
     * @return the klass
     */
    public Class<?> getKlass() {
        return this.klass;
    }

    /**
     * @param klass the klass to set
     */
    public void setKlass(final Class<?> klass) {
        this.klass = klass;
    }

    /**
     * @return the datas
     */
    public Map<String, Object> getDatas() {
        return this.datas;
    }

    /**
     * @param datas the datas to set
     */
    public void setDatas(final Map<String, Object> datas) {
        this.datas = datas;
    }

    /**
     * @param datas the datas to set
     */
    public Object putData(final String key, final Object value) {
        return this.datas.put(key, value);
    }

    /**
     * @param datas the datas to set
     */
    public Object getData(final String key) {
        return this.datas.get(key);
    }

}