/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//beanutils/src/java/org/apache/commons/beanutils/ResultSetDynaClass.java,v 1.1 2002/07/12 22:38:21 craigmcc Exp $
 * $Revision: 1.1 $
 * $Date: 2002/07/12 22:38:21 $
 *
 * ====================================================================
 *
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 1999-2002 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Commons", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 *
 */


package org.apache.commons.beanutils;


import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;


/**
 * <p>Implementation of <code>DynaClass</code> for DynaBeans that wrap the
 * <code>java.sql.Row</code> objects of a <code>java.sql.ResultSet</code>.
 * The normal usage pattern is something like:</p>
 * <pre>
 *   ResultSet rs = ...;
 *   ResutSetDynaClass rsdc = new ResultSetDynaClass(rs);
 *   Iterator rows = rsdc.iterator();
 *   while (rows.hasNext())  {
 *     DynaBean row = (DynaBean) rows.next();
 *     ... process this row ...
 *   }
 *   rs.close();
 * </pre>
 *
 * <p>Each column in the result set will be represented as a DynaBean
 * property of the corresponding name (forced to lower case for portability).
 * </p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2002/07/12 22:38:21 $
 */

public class ResultSetDynaClass implements DynaClass {


    // ----------------------------------------------------------- Constructors


    /**
     * <p>Construct a new ResultSetDynaClass for the specified
     * <code>ResultSet</code>.</p>
     *
     * @param resultSet The result set to be wrapped
     *
     * @exception NullPointerException if <code>resultSet</code>
     *  is <code>false</code>
     * @exception SQLException if the metadata for this result set
     *  cannot be introspected
     */
    public ResultSetDynaClass(ResultSet resultSet) throws SQLException {

        if (resultSet == null) {
            throw new NullPointerException();
        }
        this.resultSet = resultSet;
        introspect();

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The set of dynamic properties that are part of this DynaClass.
     */
    protected DynaProperty properties[] = null;


    /**
     * The set of dynamic properties that are part of this DynaClass,
     * keyed by the property name.  Individual descriptor instances will
     * be the same instances as those in the <code>properties</code> list.
     */
    protected HashMap propertiesMap = new HashMap();


    /**
     * <p>The <code>ResultSet</code> we are wrapping.</p>
     */
    protected ResultSet resultSet = null;



    // ------------------------------------------------------ DynaClass Methods



    /**
     * Return the name of this DynaClass (analogous to the
     * <code>getName()</code> method of <code>java.lang.Class</code), which
     * allows the same <code>DynaClass</code> implementation class to support
     * different dynamic classes, with different sets of properties.
     */
    public String getName() {

        return (this.getClass().getName());

    }


    /**
     * Return a property descriptor for the specified property, if it exists;
     * otherwise, return <code>null</code>.
     *
     * @param name Name of the dynamic property for which a descriptor
     *  is requested
     *
     * @exception IllegalArgumentException if no property name is specified
     */
    public DynaProperty getDynaProperty(String name) {

        if (name == null) {
            throw new IllegalArgumentException
                    ("No property name specified");
        }
        return ((DynaProperty) propertiesMap.get(name));

    }


    /**
     * <p>Return an array of <code>ProperyDescriptors</code> for the properties
     * currently defined in this DynaClass.  If no properties are defined, a
     * zero-length array will be returned.</p>
     *
     * <p><strong>FIXME</strong> - Should we really be implementing
     * <code>getBeanInfo()</code> instead, which returns property descriptors
     * and a bunch of other stuff?</p>
     */
    public DynaProperty[] getDynaProperties() {

        return (properties);

    }


    /**
     * <p>Instantiate and return a new DynaBean instance, associated
     * with this DynaClass.  <strong>NOTE</strong> - This operation is not
     * supported, and throws an exception.  The <code>Iterator</code> that
     * is returned by <code>iterator()</code> will create DynaBean instances
     * for each row as needed.</p>
     *
     * @exception IllegalAccessException if the Class or the appropriate
     *  constructor is not accessible
     * @exception InstantiationException if this Class represents an abstract
     *  class, an array class, a primitive type, or void; or if instantiation
     *  fails for some other reason
     */
    public DynaBean newInstance()
            throws IllegalAccessException, InstantiationException {

        throw new UnsupportedOperationException("newInstance() not supported");

    }


    // --------------------------------------------------------- Public Methods


    /**
     * <p>Return an <code>Iterator</code> of {@link DynaBean} instances for
     * each row of the wrapped <code>ResultSet</code>, in "forward" order.
     * Unless the underlying result set supports scrolling, this method
     * should be called only once.</p>
     */
    public Iterator iterator() {

        return (new ResultSetIterator(this));

    }


    // -------------------------------------------------------- Package Methods


    /**
     * <p>Return the result set we are wrapping.</p>
     */
    ResultSet getResultSet() {

        return (this.resultSet);

    }


    // ------------------------------------------------------ Protected Methods


    /**
     * <p>Introspect the metadata associated with our result set, and populate
     * the <code>properties</code> and <code>propertiesMap</code> instance
     * variables.</p>
     *
     * @exception SQLException if an error is encountered processing the
     *  result set metadata
     */
    protected void introspect() throws SQLException {

        // Accumulate an ordered list of DynaProperties
        ArrayList list = new ArrayList();
        ResultSetMetaData metadata = resultSet.getMetaData();
        int n = metadata.getColumnCount();
        for (int i = 1; i <= n; i++) { // JDBC is one-relative!
            String name = metadata.getColumnName(i).toLowerCase();
            String className = metadata.getColumnClassName(i);
            Class clazz = null;
            try {
                clazz = Class.forName(className);
            } catch (Exception e) {
                throw new SQLException("Cannot load column class '" +
                                       className + "': " + e);
            }
            list.add(new DynaProperty(name, clazz));
        }

        // Convert this list into the internal data structures we need
        properties =
            (DynaProperty[]) list.toArray(new DynaProperty[list.size()]);
        for (int i = 0; i < properties.length; i++) {
            propertiesMap.put(properties[i].getName(), properties[i]);
        }

    }


}
