/*
 * $Header: /home/jerenkrantz/tmp/commons/commons-convert/cvs/home/cvs/jakarta-commons//beanutils/src/test/org/apache/commons/beanutils/TestResultSet.java,v 1.1 2002/07/12 22:38:21 craigmcc Exp $
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


import java.io.InputStream;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URL;
import java.sql.Array;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.Date;
import java.sql.Ref;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.SQLWarning;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Map;


/**
 * <p>Mock object that implements enough of <code>java.sql.ResultSet</code>
 * to exercise the {@link ResultSetDyaClass} functionality.</p>
 *
 * @author Craig R. McClanahan
 * @version $Revision: 1.1 $ $Date: 2002/07/12 22:38:21 $
 */

public class TestResultSet implements ResultSet {


    // ----------------------------------------------------- Instance Variables


    /**
     * Current row number (0 means "before the first one").
     */
    protected int row = 0;


    /**
     * The constant (per run) value used to initialize date/time/timestamp.
     */
    protected long timestamp = System.currentTimeMillis();


    // ---------------------------------------------------- Implemented Methods


    public void close() throws SQLException {
        ; // No action required
    }


    public ResultSetMetaData getMetaData() throws SQLException {
        return (new TestResultSetMetaData());
    }


    public Object getObject(String columnName) throws SQLException {
        if (row > 5) {
            throw new SQLException("No current row");
        }
        if ("bigdecimalproperty".equals(columnName)) {
            return (new BigDecimal(123.45));
        } else if ("booleanproperty".equals(columnName)) {
            if ((row % 2) == 0) {
                return (Boolean.TRUE);
            } else {
                return (Boolean.FALSE);
            }
        } else if ("byteproperty".equals(columnName)) {
            return (new Byte((byte) row));
        } else if ("dateproperty".equals(columnName)) {
            return (new Date(timestamp));
        } else if ("doubleproperty".equals(columnName)) {
            return (new Double(321.0));
        } else if ("floatproperty".equals(columnName)) {
            return (new Float((float) 123.0));
        } else if ("intproperty".equals(columnName)) {
            return (new Integer(100 + row));
        } else if ("longproperty".equals(columnName)) {
            return (new Long(200 + row));
        } else if ("nullproperty".equals(columnName)) {
            return (null);
        } else if ("shortproperty".equals(columnName)) {
            return (new Short((short) (300 + row)));
        } else if ("stringproperty".equals(columnName)) {
            return ("This is a string");
        } else if ("timeproperty".equals(columnName)) {
            return (new Time(timestamp));
        } else if ("timestampproperty".equals(columnName)) {
            return (new Timestamp(timestamp));
        } else {
            throw new SQLException("Unknown column name " + columnName);
        }
    }


    public boolean next() throws SQLException {
        if (row++ < 5) {
            return (true);
        } else {
            return (false);
        }
    }


    public void updateObject(String columnName, Object x)
        throws SQLException {
        if (row > 5) {
            throw new SQLException("No current row");
        }
        ; // FIXME - updateObject()
    }


    // -------------------------------------------------- Unimplemented Methods


    public boolean absolute(int row) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void afterLast() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void beforeFirst() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void cancelRowUpdates() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void clearWarnings() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void deleteRow() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public int findColumn(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean first() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Array getArray(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Array getArray(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public InputStream getAsciiStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public InputStream getAsciiStream(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public BigDecimal getBigDecimal(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public BigDecimal getBigDecimal(int columnIndex, int scale)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public BigDecimal getBigDecimal(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public BigDecimal getBigDecimal(String columnName, int scale)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public InputStream getBinaryStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public InputStream getBinaryStream(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Blob getBlob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Blob getBlob(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean getBoolean(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean getBoolean(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public byte getByte(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public byte getByte(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public byte[] getBytes(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public byte[] getBytes(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Reader getCharacterStream(int columnIndex)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Reader getCharacterStream(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Clob getClob(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Clob getClob(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public int getConcurrency() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public String getCursorName() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Date getDate(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Date getDate(int columnIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Date getDate(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Date getDate(String columnName, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public double getDouble(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public double getDouble(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public int getFetchDirection() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public int getFetchSize() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public float getFloat(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public float getFloat(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public int getInt(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public int getInt(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public long getLong(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public long getLong(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Object getObject(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Object getObject(int columnIndex, Map map) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Object getObject(String columnName, Map map) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Ref getRef(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Ref getRef(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public int getRow() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public short getShort(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public short getShort(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Statement getStatement() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public String getString(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public String getString(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Time getTime(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Time getTime(int columnIndex, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Time getTime(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Time getTime(String columnName, Calendar cal) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Timestamp getTimestamp(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Timestamp getTimestamp(int columnIndex, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Timestamp getTimestamp(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public Timestamp getTimestamp(String columnName, Calendar cal)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public int getType() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public InputStream getUnicodeStream(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public InputStream getUnicodeStream(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public URL getURL(int columnIndex) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public URL getURL(String columnName) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public SQLWarning getWarnings() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void insertRow() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean isAfterLast() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean isBeforeFirst() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean isFirst() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean isLast() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean last() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void moveToCurrentRow() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void moveToInsertRow() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean previous() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void refreshRow() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean relative(int rows) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean rowDeleted() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean rowInserted() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean rowUpdated() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void setFetchDirection(int direction) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void setFetchSize(int size) throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateArray(int columnPosition, Array x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateArray(String columnName, Array x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateAsciiStream(int columnPosition, InputStream x, int len)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateAsciiStream(String columnName, InputStream x, int len)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBigDecimal(int columnPosition, BigDecimal x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBigDecimal(String columnName, BigDecimal x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBinaryStream(int columnPosition, InputStream x, int len)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBinaryStream(String columnName, InputStream x, int len)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBlob(int columnPosition, Blob x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBlob(String columnName, Blob x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBoolean(int columnPosition, boolean x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBoolean(String columnName, boolean x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateByte(int columnPosition, byte x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateByte(String columnName, byte x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBytes(int columnPosition, byte x[])
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateBytes(String columnName, byte x[])
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateCharacterStream(int columnPosition, Reader x, int len)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateCharacterStream(String columnName, Reader x, int len)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateClob(int columnPosition, Clob x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateClob(String columnName, Clob x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateDate(int columnPosition, Date x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateDate(String columnName, Date x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateDouble(int columnPosition, double x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateDouble(String columnName, double x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateFloat(int columnPosition, float x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateFloat(String columnName, float x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateInt(int columnPosition, int x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateInt(String columnName, int x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateLong(int columnPosition, long x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateLong(String columnName, long x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateNull(int columnPosition)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateNull(String columnName)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateObject(int columnPosition, Object x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateObject(int columnPosition, Object x, int scale)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateObject(String columnName, Object x, int scale)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateRef(int columnPosition, Ref x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateRef(String columnName, Ref x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateRow() throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateShort(int columnPosition, short x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateShort(String columnName, short x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateString(int columnPosition, String x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateString(String columnName, String x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateTime(int columnPosition, Time x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateTime(String columnName, Time x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateTimestamp(int columnPosition, Timestamp x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public void updateTimestamp(String columnName, Timestamp x)
        throws SQLException {
        throw new UnsupportedOperationException();
    }


    public boolean wasNull() throws SQLException {
        throw new UnsupportedOperationException();
    }


}
