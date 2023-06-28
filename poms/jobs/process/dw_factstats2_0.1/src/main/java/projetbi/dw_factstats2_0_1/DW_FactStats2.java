// ============================================================================
//
// Copyright (c) 2006-2015, Talend SA
//
// Ce code source a été automatiquement généré par_Talend Open Studio for Data Integration
// / Soumis à la Licence Apache, Version 2.0 (la "Licence") ;
// votre utilisation de ce fichier doit respecter les termes de la Licence.
// Vous pouvez obtenir une copie de la Licence sur
// http://www.apache.org/licenses/LICENSE-2.0
// 
// Sauf lorsqu'explicitement prévu par la loi en vigueur ou accepté par écrit, le logiciel
// distribué sous la Licence est distribué "TEL QUEL",
// SANS GARANTIE OU CONDITION D'AUCUNE SORTE, expresse ou implicite.
// Consultez la Licence pour connaître la terminologie spécifique régissant les autorisations et
// les limites prévues par la Licence.

package projetbi.dw_factstats2_0_1;

import routines.Numeric;
import routines.DataOperation;
import routines.TalendDataGenerator;
import routines.TalendStringUtil;
import routines.TalendString;
import routines.StringHandling;
import routines.Relational;
import routines.TalendDate;
import routines.Mathematical;
import routines.system.*;
import routines.system.api.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.math.BigDecimal;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.util.Comparator;

@SuppressWarnings("unused")

/**
 * Job: DW_FactStats2 Purpose: <br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class DW_FactStats2 implements TalendJob {

	protected static void logIgnoredError(String message, Throwable cause) {
		System.err.println(message);
		if (cause != null) {
			cause.printStackTrace();
		}

	}

	public final Object obj = new Object();

	// for transmiting parameters purpose
	private Object valueObject = null;

	public Object getValueObject() {
		return this.valueObject;
	}

	public void setValueObject(Object valueObject) {
		this.valueObject = valueObject;
	}

	private final static String defaultCharset = java.nio.charset.Charset.defaultCharset().name();

	private final static String utf8Charset = "UTF-8";

	// contains type for every context property
	public class PropertiesWithType extends java.util.Properties {
		private static final long serialVersionUID = 1L;
		private java.util.Map<String, String> propertyTypes = new java.util.HashMap<>();

		public PropertiesWithType(java.util.Properties properties) {
			super(properties);
		}

		public PropertiesWithType() {
			super();
		}

		public void setContextType(String key, String type) {
			propertyTypes.put(key, type);
		}

		public String getContextType(String key) {
			return propertyTypes.get(key);
		}
	}

	// create and load default properties
	private java.util.Properties defaultProps = new java.util.Properties();

	// create application properties with default
	public class ContextProperties extends PropertiesWithType {

		private static final long serialVersionUID = 1L;

		public ContextProperties(java.util.Properties properties) {
			super(properties);
		}

		public ContextProperties() {
			super();
		}

		public void synchronizeContext() {

		}

		// if the stored or passed value is "<TALEND_NULL>" string, it mean null
		public String getStringValue(String key) {
			String origin_value = this.getProperty(key);
			if (NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY.equals(origin_value)) {
				return null;
			}
			return origin_value;
		}

	}

	protected ContextProperties context = new ContextProperties(); // will be instanciated by MS.

	public ContextProperties getContext() {
		return this.context;
	}

	private final String jobVersion = "0.1";
	private final String jobName = "DW_FactStats2";
	private final String projectName = "PROJETBI";
	public Integer errorCode = null;
	private String currentComponent = "";

	private final java.util.Map<String, Object> globalMap = new java.util.HashMap<String, Object>();
	private final static java.util.Map<String, Object> junitGlobalMap = new java.util.HashMap<String, Object>();

	private final java.util.Map<String, Long> start_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Long> end_Hash = new java.util.HashMap<String, Long>();
	private final java.util.Map<String, Boolean> ok_Hash = new java.util.HashMap<String, Boolean>();
	public final java.util.List<String[]> globalBuffer = new java.util.ArrayList<String[]>();

	private RunStat runStat = new RunStat();

	// OSGi DataSource
	private final static String KEY_DB_DATASOURCES = "KEY_DB_DATASOURCES";

	private final static String KEY_DB_DATASOURCES_RAW = "KEY_DB_DATASOURCES_RAW";

	public void setDataSources(java.util.Map<String, javax.sql.DataSource> dataSources) {
		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		for (java.util.Map.Entry<String, javax.sql.DataSource> dataSourceEntry : dataSources.entrySet()) {
			talendDataSources.put(dataSourceEntry.getKey(),
					new routines.system.TalendDataSource(dataSourceEntry.getValue()));
		}
		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	public void setDataSourceReferences(List serviceReferences) throws Exception {

		java.util.Map<String, routines.system.TalendDataSource> talendDataSources = new java.util.HashMap<String, routines.system.TalendDataSource>();
		java.util.Map<String, javax.sql.DataSource> dataSources = new java.util.HashMap<String, javax.sql.DataSource>();

		for (java.util.Map.Entry<String, javax.sql.DataSource> entry : BundleUtils
				.getServices(serviceReferences, javax.sql.DataSource.class).entrySet()) {
			dataSources.put(entry.getKey(), entry.getValue());
			talendDataSources.put(entry.getKey(), new routines.system.TalendDataSource(entry.getValue()));
		}

		globalMap.put(KEY_DB_DATASOURCES, talendDataSources);
		globalMap.put(KEY_DB_DATASOURCES_RAW, new java.util.HashMap<String, javax.sql.DataSource>(dataSources));
	}

	private final java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
	private final java.io.PrintStream errorMessagePS = new java.io.PrintStream(new java.io.BufferedOutputStream(baos));

	public String getExceptionStackTrace() {
		if ("failure".equals(this.getStatus())) {
			errorMessagePS.flush();
			return baos.toString();
		}
		return null;
	}

	private Exception exception;

	public Exception getException() {
		if ("failure".equals(this.getStatus())) {
			return this.exception;
		}
		return null;
	}

	private class TalendException extends Exception {

		private static final long serialVersionUID = 1L;

		private java.util.Map<String, Object> globalMap = null;
		private Exception e = null;
		private String currentComponent = null;
		private String virtualComponentName = null;

		public void setVirtualComponentName(String virtualComponentName) {
			this.virtualComponentName = virtualComponentName;
		}

		private TalendException(Exception e, String errorComponent, final java.util.Map<String, Object> globalMap) {
			this.currentComponent = errorComponent;
			this.globalMap = globalMap;
			this.e = e;
		}

		public Exception getException() {
			return this.e;
		}

		public String getCurrentComponent() {
			return this.currentComponent;
		}

		public String getExceptionCauseMessage(Exception e) {
			Throwable cause = e;
			String message = null;
			int i = 10;
			while (null != cause && 0 < i--) {
				message = cause.getMessage();
				if (null == message) {
					cause = cause.getCause();
				} else {
					break;
				}
			}
			if (null == message) {
				message = e.getClass().getName();
			}
			return message;
		}

		@Override
		public void printStackTrace() {
			if (!(e instanceof TalendException || e instanceof TDieException)) {
				if (virtualComponentName != null && currentComponent.indexOf(virtualComponentName + "_") == 0) {
					globalMap.put(virtualComponentName + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				}
				globalMap.put(currentComponent + "_ERROR_MESSAGE", getExceptionCauseMessage(e));
				System.err.println("Exception in component " + currentComponent + " (" + jobName + ")");
			}
			if (!(e instanceof TDieException)) {
				if (e instanceof TalendException) {
					e.printStackTrace();
				} else {
					e.printStackTrace();
					e.printStackTrace(errorMessagePS);
					DW_FactStats2.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(DW_FactStats2.this, new Object[] { e, currentComponent, globalMap });
							break;
						}
					}

					if (!(e instanceof TDieException)) {
					}
				} catch (Exception e) {
					this.e.printStackTrace();
				}
			}
		}
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row4_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row5_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row6_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_1_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class InsertStruct implements routines.system.IPersistableRow<InsertStruct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int Moment_Start_FK;

		public int getMoment_Start_FK() {
			return this.Moment_Start_FK;
		}

		public int Moment_End_FK;

		public int getMoment_End_FK() {
			return this.Moment_End_FK;
		}

		public int Job_FK;

		public int getJob_FK() {
			return this.Job_FK;
		}

		public Integer Data_Volume;

		public Integer getData_Volume() {
			return this.Data_Volume;
		}

		public Integer Job_Duration;

		public Integer getJob_Duration() {
			return this.Job_Duration;
		}

		public String Job_Status;

		public String getJob_Status() {
			return this.Job_Status;
		}

		public int PK;

		public int getPK() {
			return this.PK;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.Moment_Start_FK;

				result = prime * result + (int) this.Moment_End_FK;

				result = prime * result + (int) this.Job_FK;

				result = prime * result + (int) this.PK;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final InsertStruct other = (InsertStruct) obj;

			if (this.Moment_Start_FK != other.Moment_Start_FK)
				return false;

			if (this.Moment_End_FK != other.Moment_End_FK)
				return false;

			if (this.Job_FK != other.Job_FK)
				return false;

			if (this.PK != other.PK)
				return false;

			return true;
		}

		public void copyDataTo(InsertStruct other) {

			other.Moment_Start_FK = this.Moment_Start_FK;
			other.Moment_End_FK = this.Moment_End_FK;
			other.Job_FK = this.Job_FK;
			other.Data_Volume = this.Data_Volume;
			other.Job_Duration = this.Job_Duration;
			other.Job_Status = this.Job_Status;
			other.PK = this.PK;

		}

		public void copyKeysDataTo(InsertStruct other) {

			other.Moment_Start_FK = this.Moment_Start_FK;
			other.Moment_End_FK = this.Moment_End_FK;
			other.Job_FK = this.Job_FK;
			other.PK = this.PK;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment_Start_FK = dis.readInt();

					this.Moment_End_FK = dis.readInt();

					this.Job_FK = dis.readInt();

					this.Data_Volume = readInteger(dis);

					this.Job_Duration = readInteger(dis);

					this.Job_Status = readString(dis);

					this.PK = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment_Start_FK = dis.readInt();

					this.Moment_End_FK = dis.readInt();

					this.Job_FK = dis.readInt();

					this.Data_Volume = readInteger(dis);

					this.Job_Duration = readInteger(dis);

					this.Job_Status = readString(dis);

					this.PK = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.Moment_Start_FK);

				// int

				dos.writeInt(this.Moment_End_FK);

				// int

				dos.writeInt(this.Job_FK);

				// Integer

				writeInteger(this.Data_Volume, dos);

				// Integer

				writeInteger(this.Job_Duration, dos);

				// String

				writeString(this.Job_Status, dos);

				// int

				dos.writeInt(this.PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.Moment_Start_FK);

				// int

				dos.writeInt(this.Moment_End_FK);

				// int

				dos.writeInt(this.Job_FK);

				// Integer

				writeInteger(this.Data_Volume, dos);

				// Integer

				writeInteger(this.Job_Duration, dos);

				// String

				writeString(this.Job_Status, dos);

				// int

				dos.writeInt(this.PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Moment_Start_FK=" + String.valueOf(Moment_Start_FK));
			sb.append(",Moment_End_FK=" + String.valueOf(Moment_End_FK));
			sb.append(",Job_FK=" + String.valueOf(Job_FK));
			sb.append(",Data_Volume=" + String.valueOf(Data_Volume));
			sb.append(",Job_Duration=" + String.valueOf(Job_Duration));
			sb.append(",Job_Status=" + Job_Status);
			sb.append(",PK=" + String.valueOf(PK));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(InsertStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Moment_Start_FK, other.Moment_Start_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Moment_End_FK, other.Moment_End_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Job_FK, other.Job_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.PK, other.PK);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class UpdateStruct implements routines.system.IPersistableRow<UpdateStruct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int Moment_Start_FK;

		public int getMoment_Start_FK() {
			return this.Moment_Start_FK;
		}

		public int Moment_End_FK;

		public int getMoment_End_FK() {
			return this.Moment_End_FK;
		}

		public int Job_FK;

		public int getJob_FK() {
			return this.Job_FK;
		}

		public Integer Data_Volume;

		public Integer getData_Volume() {
			return this.Data_Volume;
		}

		public Integer Job_Duration;

		public Integer getJob_Duration() {
			return this.Job_Duration;
		}

		public String Job_Status;

		public String getJob_Status() {
			return this.Job_Status;
		}

		public int PK;

		public int getPK() {
			return this.PK;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.Moment_Start_FK;

				result = prime * result + (int) this.Moment_End_FK;

				result = prime * result + (int) this.Job_FK;

				result = prime * result + (int) this.PK;

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final UpdateStruct other = (UpdateStruct) obj;

			if (this.Moment_Start_FK != other.Moment_Start_FK)
				return false;

			if (this.Moment_End_FK != other.Moment_End_FK)
				return false;

			if (this.Job_FK != other.Job_FK)
				return false;

			if (this.PK != other.PK)
				return false;

			return true;
		}

		public void copyDataTo(UpdateStruct other) {

			other.Moment_Start_FK = this.Moment_Start_FK;
			other.Moment_End_FK = this.Moment_End_FK;
			other.Job_FK = this.Job_FK;
			other.Data_Volume = this.Data_Volume;
			other.Job_Duration = this.Job_Duration;
			other.Job_Status = this.Job_Status;
			other.PK = this.PK;

		}

		public void copyKeysDataTo(UpdateStruct other) {

			other.Moment_Start_FK = this.Moment_Start_FK;
			other.Moment_End_FK = this.Moment_End_FK;
			other.Job_FK = this.Job_FK;
			other.PK = this.PK;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment_Start_FK = dis.readInt();

					this.Moment_End_FK = dis.readInt();

					this.Job_FK = dis.readInt();

					this.Data_Volume = readInteger(dis);

					this.Job_Duration = readInteger(dis);

					this.Job_Status = readString(dis);

					this.PK = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment_Start_FK = dis.readInt();

					this.Moment_End_FK = dis.readInt();

					this.Job_FK = dis.readInt();

					this.Data_Volume = readInteger(dis);

					this.Job_Duration = readInteger(dis);

					this.Job_Status = readString(dis);

					this.PK = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.Moment_Start_FK);

				// int

				dos.writeInt(this.Moment_End_FK);

				// int

				dos.writeInt(this.Job_FK);

				// Integer

				writeInteger(this.Data_Volume, dos);

				// Integer

				writeInteger(this.Job_Duration, dos);

				// String

				writeString(this.Job_Status, dos);

				// int

				dos.writeInt(this.PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.Moment_Start_FK);

				// int

				dos.writeInt(this.Moment_End_FK);

				// int

				dos.writeInt(this.Job_FK);

				// Integer

				writeInteger(this.Data_Volume, dos);

				// Integer

				writeInteger(this.Job_Duration, dos);

				// String

				writeString(this.Job_Status, dos);

				// int

				dos.writeInt(this.PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Moment_Start_FK=" + String.valueOf(Moment_Start_FK));
			sb.append(",Moment_End_FK=" + String.valueOf(Moment_End_FK));
			sb.append(",Job_FK=" + String.valueOf(Job_FK));
			sb.append(",Data_Volume=" + String.valueOf(Data_Volume));
			sb.append(",Job_Duration=" + String.valueOf(Job_Duration));
			sb.append(",Job_Status=" + Job_Status);
			sb.append(",PK=" + String.valueOf(PK));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(UpdateStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Moment_Start_FK, other.Moment_Start_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Moment_End_FK, other.Moment_End_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Job_FK, other.Job_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.PK, other.PK);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row2Struct implements routines.system.IPersistableRow<row2Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public java.util.Date moment_debut;

		public java.util.Date getMoment_debut() {
			return this.moment_debut;
		}

		public java.util.Date moment_fin;

		public java.util.Date getMoment_fin() {
			return this.moment_fin;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public Integer duration;

		public Integer getDuration() {
			return this.duration;
		}

		public Integer volume;

		public Integer getVolume() {
			return this.volume;
		}

		public String status;

		public String getStatus() {
			return this.status;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.pid = readString(dis);

					this.moment_debut = readDate(dis);

					this.moment_fin = readDate(dis);

					this.job = readString(dis);

					this.duration = readInteger(dis);

					this.volume = readInteger(dis);

					this.status = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.pid = readString(dis);

					this.moment_debut = readDate(dis);

					this.moment_fin = readDate(dis);

					this.job = readString(dis);

					this.duration = readInteger(dis);

					this.volume = readInteger(dis);

					this.status = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.pid, dos);

				// java.util.Date

				writeDate(this.moment_debut, dos);

				// java.util.Date

				writeDate(this.moment_fin, dos);

				// String

				writeString(this.job, dos);

				// Integer

				writeInteger(this.duration, dos);

				// Integer

				writeInteger(this.volume, dos);

				// String

				writeString(this.status, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.pid, dos);

				// java.util.Date

				writeDate(this.moment_debut, dos);

				// java.util.Date

				writeDate(this.moment_fin, dos);

				// String

				writeString(this.job, dos);

				// Integer

				writeInteger(this.duration, dos);

				// Integer

				writeInteger(this.volume, dos);

				// String

				writeString(this.status, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("pid=" + pid);
			sb.append(",moment_debut=" + String.valueOf(moment_debut));
			sb.append(",moment_fin=" + String.valueOf(moment_fin));
			sb.append(",job=" + job);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",volume=" + String.valueOf(volume));
			sb.append(",status=" + status);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row2Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class row1Struct implements routines.system.IPersistableRow<row1Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public java.util.Date moment_debut;

		public java.util.Date getMoment_debut() {
			return this.moment_debut;
		}

		public java.util.Date moment_fin;

		public java.util.Date getMoment_fin() {
			return this.moment_fin;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public Integer duration;

		public Integer getDuration() {
			return this.duration;
		}

		public Integer volume;

		public Integer getVolume() {
			return this.volume;
		}

		public String status;

		public String getStatus() {
			return this.status;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.pid = readString(dis);

					this.moment_debut = readDate(dis);

					this.moment_fin = readDate(dis);

					this.job = readString(dis);

					this.duration = readInteger(dis);

					this.volume = readInteger(dis);

					this.status = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.pid = readString(dis);

					this.moment_debut = readDate(dis);

					this.moment_fin = readDate(dis);

					this.job = readString(dis);

					this.duration = readInteger(dis);

					this.volume = readInteger(dis);

					this.status = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.pid, dos);

				// java.util.Date

				writeDate(this.moment_debut, dos);

				// java.util.Date

				writeDate(this.moment_fin, dos);

				// String

				writeString(this.job, dos);

				// Integer

				writeInteger(this.duration, dos);

				// Integer

				writeInteger(this.volume, dos);

				// String

				writeString(this.status, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.pid, dos);

				// java.util.Date

				writeDate(this.moment_debut, dos);

				// java.util.Date

				writeDate(this.moment_fin, dos);

				// String

				writeString(this.job, dos);

				// Integer

				writeInteger(this.duration, dos);

				// Integer

				writeInteger(this.volume, dos);

				// String

				writeString(this.status, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("pid=" + pid);
			sb.append(",moment_debut=" + String.valueOf(moment_debut));
			sb.append(",moment_fin=" + String.valueOf(moment_fin));
			sb.append(",job=" + job);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",volume=" + String.valueOf(volume));
			sb.append(",status=" + status);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public static class after_tDBInput_1Struct implements routines.system.IPersistableRow<after_tDBInput_1Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public java.util.Date moment_debut;

		public java.util.Date getMoment_debut() {
			return this.moment_debut;
		}

		public java.util.Date moment_fin;

		public java.util.Date getMoment_fin() {
			return this.moment_fin;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public Integer duration;

		public Integer getDuration() {
			return this.duration;
		}

		public Integer volume;

		public Integer getVolume() {
			return this.volume;
		}

		public String status;

		public String getStatus() {
			return this.status;
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.pid = readString(dis);

					this.moment_debut = readDate(dis);

					this.moment_fin = readDate(dis);

					this.job = readString(dis);

					this.duration = readInteger(dis);

					this.volume = readInteger(dis);

					this.status = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.pid = readString(dis);

					this.moment_debut = readDate(dis);

					this.moment_fin = readDate(dis);

					this.job = readString(dis);

					this.duration = readInteger(dis);

					this.volume = readInteger(dis);

					this.status = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.pid, dos);

				// java.util.Date

				writeDate(this.moment_debut, dos);

				// java.util.Date

				writeDate(this.moment_fin, dos);

				// String

				writeString(this.job, dos);

				// Integer

				writeInteger(this.duration, dos);

				// Integer

				writeInteger(this.volume, dos);

				// String

				writeString(this.status, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.pid, dos);

				// java.util.Date

				writeDate(this.moment_debut, dos);

				// java.util.Date

				writeDate(this.moment_fin, dos);

				// String

				writeString(this.job, dos);

				// Integer

				writeInteger(this.duration, dos);

				// Integer

				writeInteger(this.volume, dos);

				// String

				writeString(this.status, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("pid=" + pid);
			sb.append(",moment_debut=" + String.valueOf(moment_debut));
			sb.append(",moment_fin=" + String.valueOf(moment_fin));
			sb.append(",job=" + job);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",volume=" + String.valueOf(volume));
			sb.append(",status=" + status);
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tDBInput_1Struct other) {

			int returnValue = -1;

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_1Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				tDBInput_2Process(globalMap);
				tDBInput_3Process(globalMap);
				tDBInput_4Process(globalMap);
				tDBInput_5Process(globalMap);

				row1Struct row1 = new row1Struct();
				row2Struct row2 = new row2Struct();
				InsertStruct Insert = new InsertStruct();
				UpdateStruct Update = new UpdateStruct();

				/**
				 * [tDBOutput_1 begin ] start
				 */

				ok_Hash.put("tDBOutput_1", false);
				start_Hash.put("tDBOutput_1", System.currentTimeMillis());

				currentComponent = "tDBOutput_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "Insert");
				}

				int tos_count_tDBOutput_1 = 0;

				String dbschema_tDBOutput_1 = null;
				dbschema_tDBOutput_1 = "public";

				String tableName_tDBOutput_1 = null;
				if (dbschema_tDBOutput_1 == null || dbschema_tDBOutput_1.trim().length() == 0) {
					tableName_tDBOutput_1 = ("FactStats");
				} else {
					tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "\".\"" + ("FactStats");
				}

				int nb_line_tDBOutput_1 = 0;
				int nb_line_update_tDBOutput_1 = 0;
				int nb_line_inserted_tDBOutput_1 = 0;
				int nb_line_deleted_tDBOutput_1 = 0;
				int nb_line_rejected_tDBOutput_1 = 0;

				int deletedCount_tDBOutput_1 = 0;
				int updatedCount_tDBOutput_1 = 0;
				int insertedCount_tDBOutput_1 = 0;
				int rowsToCommitCount_tDBOutput_1 = 0;
				int rejectedCount_tDBOutput_1 = 0;

				boolean whetherReject_tDBOutput_1 = false;

				java.sql.Connection conn_tDBOutput_1 = null;
				String dbUser_tDBOutput_1 = null;

				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI";
				dbUser_tDBOutput_1 = "postgres";

				final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:QPGAmnLl9znpHtlR93KJKjp9HquWiDTG96HzMgp1eM2X");

				String dbPwd_tDBOutput_1 = decryptedPassword_tDBOutput_1;

				conn_tDBOutput_1 = java.sql.DriverManager.getConnection(url_tDBOutput_1, dbUser_tDBOutput_1,
						dbPwd_tDBOutput_1);

				resourceMap.put("conn_tDBOutput_1", conn_tDBOutput_1);
				conn_tDBOutput_1.setAutoCommit(false);
				int commitEvery_tDBOutput_1 = 10000;
				int commitCounter_tDBOutput_1 = 0;

				int batchSize_tDBOutput_1 = 10000;
				int batchSizeCounter_tDBOutput_1 = 0;

				int count_tDBOutput_1 = 0;
				String insert_tDBOutput_1 = "INSERT INTO \"" + tableName_tDBOutput_1
						+ "\" (\"Moment_Start_FK\",\"Moment_End_FK\",\"Job_FK\",\"Data_Volume\",\"Job_Duration\",\"Job_Status\",\"PK\") VALUES (?,?,?,?,?,?,?)";

				java.sql.PreparedStatement pstmt_tDBOutput_1 = conn_tDBOutput_1.prepareStatement(insert_tDBOutput_1);
				resourceMap.put("pstmt_tDBOutput_1", pstmt_tDBOutput_1);

				/**
				 * [tDBOutput_1 begin ] stop
				 */

				/**
				 * [tDBOutput_2 begin ] start
				 */

				ok_Hash.put("tDBOutput_2", false);
				start_Hash.put("tDBOutput_2", System.currentTimeMillis());

				currentComponent = "tDBOutput_2";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "Update");
				}

				int tos_count_tDBOutput_2 = 0;

				String dbschema_tDBOutput_2 = null;
				dbschema_tDBOutput_2 = "public";

				String tableName_tDBOutput_2 = null;
				if (dbschema_tDBOutput_2 == null || dbschema_tDBOutput_2.trim().length() == 0) {
					tableName_tDBOutput_2 = ("FactStats");
				} else {
					tableName_tDBOutput_2 = dbschema_tDBOutput_2 + "\".\"" + ("FactStats");
				}

				int updateKeyCount_tDBOutput_2 = 4;
				if (updateKeyCount_tDBOutput_2 < 1) {
					throw new RuntimeException("For update, Schema must have a key");
				} else if (updateKeyCount_tDBOutput_2 == 7 && true) {
					throw new RuntimeException("For update, every Schema column can not be a key");
				}

				int nb_line_tDBOutput_2 = 0;
				int nb_line_update_tDBOutput_2 = 0;
				int nb_line_inserted_tDBOutput_2 = 0;
				int nb_line_deleted_tDBOutput_2 = 0;
				int nb_line_rejected_tDBOutput_2 = 0;

				int deletedCount_tDBOutput_2 = 0;
				int updatedCount_tDBOutput_2 = 0;
				int insertedCount_tDBOutput_2 = 0;
				int rowsToCommitCount_tDBOutput_2 = 0;
				int rejectedCount_tDBOutput_2 = 0;

				boolean whetherReject_tDBOutput_2 = false;

				java.sql.Connection conn_tDBOutput_2 = null;
				String dbUser_tDBOutput_2 = null;

				java.lang.Class.forName("org.postgresql.Driver");

				String url_tDBOutput_2 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI";
				dbUser_tDBOutput_2 = "postgres";

				final String decryptedPassword_tDBOutput_2 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:Ut4FHxxekcGahIOtWuCD86d4dHgB8dWkoZzSCIYwiL0U");

				String dbPwd_tDBOutput_2 = decryptedPassword_tDBOutput_2;

				conn_tDBOutput_2 = java.sql.DriverManager.getConnection(url_tDBOutput_2, dbUser_tDBOutput_2,
						dbPwd_tDBOutput_2);

				resourceMap.put("conn_tDBOutput_2", conn_tDBOutput_2);
				conn_tDBOutput_2.setAutoCommit(false);
				int commitEvery_tDBOutput_2 = 10000;
				int commitCounter_tDBOutput_2 = 0;

				int batchSize_tDBOutput_2 = 10000;
				int batchSizeCounter_tDBOutput_2 = 0;

				int count_tDBOutput_2 = 0;
				String update_tDBOutput_2 = "UPDATE \"" + tableName_tDBOutput_2
						+ "\" SET \"Data_Volume\" = ?,\"Job_Duration\" = ?,\"Job_Status\" = ? WHERE \"Moment_Start_FK\" = ? AND \"Moment_End_FK\" = ? AND \"Job_FK\" = ? AND \"PK\" = ?";
				java.sql.PreparedStatement pstmt_tDBOutput_2 = conn_tDBOutput_2.prepareStatement(update_tDBOutput_2);
				resourceMap.put("pstmt_tDBOutput_2", pstmt_tDBOutput_2);

				/**
				 * [tDBOutput_2 begin ] stop
				 */

				/**
				 * [tMap_1 begin ] start
				 */

				ok_Hash.put("tMap_1", false);
				start_Hash.put("tMap_1", System.currentTimeMillis());

				currentComponent = "tMap_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row2");
				}

				int tos_count_tMap_1 = 0;

// ###############################
// # Lookup's keys initialization

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct>) globalMap
						.get("tHash_Lookup_row3"));

				row3Struct row3HashKey = new row3Struct();
				row3Struct row3Default = new row3Struct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct>) globalMap
						.get("tHash_Lookup_row4"));

				row4Struct row4HashKey = new row4Struct();
				row4Struct row4Default = new row4Struct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct> tHash_Lookup_row5 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct>) globalMap
						.get("tHash_Lookup_row5"));

				row5Struct row5HashKey = new row5Struct();
				row5Struct row5Default = new row5Struct();

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct> tHash_Lookup_row6 = (org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct>) ((org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct>) globalMap
						.get("tHash_Lookup_row6"));

				row6Struct row6HashKey = new row6Struct();
				row6Struct row6Default = new row6Struct();
// ###############################        

// ###############################
// # Vars initialization
				class Var__tMap_1__Struct {
				}
				Var__tMap_1__Struct Var__tMap_1 = new Var__tMap_1__Struct();
// ###############################

// ###############################
// # Outputs initialization
				InsertStruct Insert_tmp = new InsertStruct();
				UpdateStruct Update_tmp = new UpdateStruct();
// ###############################

				/**
				 * [tMap_1 begin ] stop
				 */

				/**
				 * [tUniqRow_1 begin ] start
				 */

				ok_Hash.put("tUniqRow_1", false);
				start_Hash.put("tUniqRow_1", System.currentTimeMillis());

				currentComponent = "tUniqRow_1";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row1");
				}

				int tos_count_tUniqRow_1 = 0;

				class KeyStruct_tUniqRow_1 {

					private static final int DEFAULT_HASHCODE = 1;
					private static final int PRIME = 31;
					private int hashCode = DEFAULT_HASHCODE;
					public boolean hashCodeDirty = true;

					String pid;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.pid == null) ? 0 : this.pid.hashCode());

							this.hashCode = result;
							this.hashCodeDirty = false;
						}
						return this.hashCode;
					}

					@Override
					public boolean equals(Object obj) {
						if (this == obj)
							return true;
						if (obj == null)
							return false;
						if (getClass() != obj.getClass())
							return false;
						final KeyStruct_tUniqRow_1 other = (KeyStruct_tUniqRow_1) obj;

						if (this.pid == null) {
							if (other.pid != null)
								return false;

						} else if (!this.pid.equals(other.pid))

							return false;

						return true;
					}

				}

				int nb_uniques_tUniqRow_1 = 0;
				int nb_duplicates_tUniqRow_1 = 0;
				KeyStruct_tUniqRow_1 finder_tUniqRow_1 = new KeyStruct_tUniqRow_1();
				java.util.Set<KeyStruct_tUniqRow_1> keystUniqRow_1 = new java.util.HashSet<KeyStruct_tUniqRow_1>();

				/**
				 * [tUniqRow_1 begin ] stop
				 */

				/**
				 * [tDBInput_1 begin ] start
				 */

				ok_Hash.put("tDBInput_1", false);
				start_Hash.put("tDBInput_1", System.currentTimeMillis());

				currentComponent = "tDBInput_1";

				int tos_count_tDBInput_1 = 0;

				int nb_line_tDBInput_1 = 0;
				java.sql.Connection conn_tDBInput_1 = null;
				String driverClass_tDBInput_1 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_1 = java.lang.Class.forName(driverClass_tDBInput_1);
				String dbUser_tDBInput_1 = "postgres";

				final String decryptedPassword_tDBInput_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:Wo/d13mhieDTq0pokCMD8HIy4Y4PtXoHmldsTniqLv8r");

				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

				String url_tDBInput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI_SA";

				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
						dbPwd_tDBInput_1);

				conn_tDBInput_1.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n    flows1.\"pid\",\n    stats1.\"moment\",\n    stats1.\"moment\" + (stats2.\"duration\" || ' seconds')::INTE"
						+ "RVAL,\n    stats1.\"job\",\n	stats2.\"duration\",\n    (select SUM(flows2.\"count\") from public.\"flows\" flows2 where "
						+ "flows1.\"pid\"=flows2.\"pid\"),\n    stats2.\"message\"\n\nFROM public.\"stats\" stats1\nJOIN public.\"stats\" stats2 ON"
						+ " (stats1.\"pid\" = stats2.\"pid\" AND stats1.\"job\" = stats2.\"job\" AND stats2.message_type = 'end')\nJOIN public.\"fl"
						+ "ows\" flows1 ON (flows1.\"job\" = stats1.\"job\" AND flows1.\"count\" != 0)\nWHERE stats1.message_type = 'begin';";

				globalMap.put("tDBInput_1_QUERY", dbquery_tDBInput_1);
				java.sql.ResultSet rs_tDBInput_1 = null;

				try {
					rs_tDBInput_1 = stmt_tDBInput_1.executeQuery(dbquery_tDBInput_1);
					java.sql.ResultSetMetaData rsmd_tDBInput_1 = rs_tDBInput_1.getMetaData();
					int colQtyInRs_tDBInput_1 = rsmd_tDBInput_1.getColumnCount();

					String tmpContent_tDBInput_1 = null;

					while (rs_tDBInput_1.next()) {
						nb_line_tDBInput_1++;

						if (colQtyInRs_tDBInput_1 < 1) {
							row1.pid = null;
						} else {

							row1.pid = routines.system.JDBCUtil.getString(rs_tDBInput_1, 1, false);
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row1.moment_debut = null;
						} else {

							row1.moment_debut = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 2);
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row1.moment_fin = null;
						} else {

							row1.moment_fin = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 3);
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row1.job = null;
						} else {

							row1.job = routines.system.JDBCUtil.getString(rs_tDBInput_1, 4, false);
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row1.duration = null;
						} else {

							row1.duration = rs_tDBInput_1.getInt(5);
							if (rs_tDBInput_1.wasNull()) {
								row1.duration = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row1.volume = null;
						} else {

							row1.volume = rs_tDBInput_1.getInt(6);
							if (rs_tDBInput_1.wasNull()) {
								row1.volume = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row1.status = null;
						} else {

							row1.status = routines.system.JDBCUtil.getString(rs_tDBInput_1, 7, false);
						}

						/**
						 * [tDBInput_1 begin ] stop
						 */

						/**
						 * [tDBInput_1 main ] start
						 */

						currentComponent = "tDBInput_1";

						tos_count_tDBInput_1++;

						/**
						 * [tDBInput_1 main ] stop
						 */

						/**
						 * [tDBInput_1 process_data_begin ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_begin ] stop
						 */

						/**
						 * [tUniqRow_1 main ] start
						 */

						currentComponent = "tUniqRow_1";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row1"

							);
						}

						row2 = null;
						if (row1.pid == null) {
							finder_tUniqRow_1.pid = null;
						} else {
							finder_tUniqRow_1.pid = row1.pid.toLowerCase();
						}
						finder_tUniqRow_1.hashCodeDirty = true;
						if (!keystUniqRow_1.contains(finder_tUniqRow_1)) {
							KeyStruct_tUniqRow_1 new_tUniqRow_1 = new KeyStruct_tUniqRow_1();

							if (row1.pid == null) {
								new_tUniqRow_1.pid = null;
							} else {
								new_tUniqRow_1.pid = row1.pid.toLowerCase();
							}

							keystUniqRow_1.add(new_tUniqRow_1);
							if (row2 == null) {

								row2 = new row2Struct();
							}
							row2.pid = row1.pid;
							row2.moment_debut = row1.moment_debut;
							row2.moment_fin = row1.moment_fin;
							row2.job = row1.job;
							row2.duration = row1.duration;
							row2.volume = row1.volume;
							row2.status = row1.status;
							nb_uniques_tUniqRow_1++;
						} else {
							nb_duplicates_tUniqRow_1++;
						}

						tos_count_tUniqRow_1++;

						/**
						 * [tUniqRow_1 main ] stop
						 */

						/**
						 * [tUniqRow_1 process_data_begin ] start
						 */

						currentComponent = "tUniqRow_1";

						/**
						 * [tUniqRow_1 process_data_begin ] stop
						 */
// Start of branch "row2"
						if (row2 != null) {

							/**
							 * [tMap_1 main ] start
							 */

							currentComponent = "tMap_1";

							if (execStat) {
								runStat.updateStatOnConnection(iterateId, 1, 1

										, "row2"

								);
							}

							boolean hasCasePrimitiveKeyWithNull_tMap_1 = false;

							// ###############################
							// # Input tables (lookups)
							boolean rejectedInnerJoin_tMap_1 = false;
							boolean mainRowRejected_tMap_1 = false;

							///////////////////////////////////////////////
							// Starting Lookup Table "row3"
							///////////////////////////////////////////////

							boolean forceLooprow3 = false;

							row3Struct row3ObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_1 = false;

								row3HashKey.Job_Code = row2.job;

								row3HashKey.hashCodeDirty = true;

								tHash_Lookup_row3.lookup(row3HashKey);

								if (!tHash_Lookup_row3.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_1 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row3 != null && tHash_Lookup_row3.getCount(row3HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row3'
								// and it contains more one result from keys : row3.Job_Code = '" +
								// row3HashKey.Job_Code + "'");
							} // G 071

							row3Struct row3 = null;

							row3Struct fromLookup_row3 = null;
							row3 = row3Default;

							if (tHash_Lookup_row3 != null && tHash_Lookup_row3.hasNext()) { // G 099

								fromLookup_row3 = tHash_Lookup_row3.next();

							} // G 099

							if (fromLookup_row3 != null) {
								row3 = fromLookup_row3;
							}

							///////////////////////////////////////////////
							// Starting Lookup Table "row4"
							///////////////////////////////////////////////

							boolean forceLooprow4 = false;

							row4Struct row4ObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_1 = false;

								row4HashKey.Moment = row2.moment_debut == null ? null
										: new java.util.Date(row2.moment_debut.getTime());

								row4HashKey.hashCodeDirty = true;

								tHash_Lookup_row4.lookup(row4HashKey);

								if (!tHash_Lookup_row4.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_1 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row4 != null && tHash_Lookup_row4.getCount(row4HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row4'
								// and it contains more one result from keys : row4.Moment = '" +
								// row4HashKey.Moment + "'");
							} // G 071

							row4Struct row4 = null;

							row4Struct fromLookup_row4 = null;
							row4 = row4Default;

							if (tHash_Lookup_row4 != null && tHash_Lookup_row4.hasNext()) { // G 099

								fromLookup_row4 = tHash_Lookup_row4.next();

							} // G 099

							if (fromLookup_row4 != null) {
								row4 = fromLookup_row4;
							}

							///////////////////////////////////////////////
							// Starting Lookup Table "row5"
							///////////////////////////////////////////////

							boolean forceLooprow5 = false;

							row5Struct row5ObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_1 = false;

								row5HashKey.Moment = row2.moment_fin == null ? null
										: new java.util.Date(row2.moment_fin.getTime());

								row5HashKey.hashCodeDirty = true;

								tHash_Lookup_row5.lookup(row5HashKey);

								if (!tHash_Lookup_row5.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_1 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row5 != null && tHash_Lookup_row5.getCount(row5HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row5'
								// and it contains more one result from keys : row5.Moment = '" +
								// row5HashKey.Moment + "'");
							} // G 071

							row5Struct row5 = null;

							row5Struct fromLookup_row5 = null;
							row5 = row5Default;

							if (tHash_Lookup_row5 != null && tHash_Lookup_row5.hasNext()) { // G 099

								fromLookup_row5 = tHash_Lookup_row5.next();

							} // G 099

							if (fromLookup_row5 != null) {
								row5 = fromLookup_row5;
							}

							///////////////////////////////////////////////
							// Starting Lookup Table "row6"
							///////////////////////////////////////////////

							boolean forceLooprow6 = false;

							row6Struct row6ObjectFromLookup = null;

							if (!rejectedInnerJoin_tMap_1) { // G_TM_M_020

								hasCasePrimitiveKeyWithNull_tMap_1 = false;

								Object exprKeyValue_row6__Moment_Start_FK = row4.Moment_PK;
								if (exprKeyValue_row6__Moment_Start_FK == null) {
									hasCasePrimitiveKeyWithNull_tMap_1 = true;
								} else {
									row6HashKey.Moment_Start_FK = (int) (Integer) exprKeyValue_row6__Moment_Start_FK;
								}

								Object exprKeyValue_row6__Moment_End_FK = row5.Moment_PK;
								if (exprKeyValue_row6__Moment_End_FK == null) {
									hasCasePrimitiveKeyWithNull_tMap_1 = true;
								} else {
									row6HashKey.Moment_End_FK = (int) (Integer) exprKeyValue_row6__Moment_End_FK;
								}

								Object exprKeyValue_row6__Job_FK = row3.Job_PK;
								if (exprKeyValue_row6__Job_FK == null) {
									hasCasePrimitiveKeyWithNull_tMap_1 = true;
								} else {
									row6HashKey.Job_FK = (int) (Integer) exprKeyValue_row6__Job_FK;
								}

								row6HashKey.Data_Volume = row2.volume;

								row6HashKey.Job_Duration = row2.duration;

								row6HashKey.Job_Status = row2.status;

								row6HashKey.hashCodeDirty = true;

								if (!hasCasePrimitiveKeyWithNull_tMap_1) { // G_TM_M_091

									tHash_Lookup_row6.lookup(row6HashKey);

								} // G_TM_M_091

								if (hasCasePrimitiveKeyWithNull_tMap_1 || !tHash_Lookup_row6.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_1 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row6 != null && tHash_Lookup_row6.getCount(row6HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row6'
								// and it contains more one result from keys : row6.Moment_Start_FK = '" +
								// row6HashKey.Moment_Start_FK + "', row6.Moment_End_FK = '" +
								// row6HashKey.Moment_End_FK + "', row6.Job_FK = '" + row6HashKey.Job_FK + "',
								// row6.Data_Volume = '" + row6HashKey.Data_Volume + "', row6.Job_Duration = '"
								// + row6HashKey.Job_Duration + "', row6.Job_Status = '" +
								// row6HashKey.Job_Status + "'");
							} // G 071

							row6Struct row6 = null;

							row6Struct fromLookup_row6 = null;
							row6 = row6Default;

							if (tHash_Lookup_row6 != null && tHash_Lookup_row6.hasNext()) { // G 099

								fromLookup_row6 = tHash_Lookup_row6.next();

							} // G 099

							if (fromLookup_row6 != null) {
								row6 = fromLookup_row6;
							}

							// ###############################
							{ // start of Var scope

								// ###############################
								// # Vars tables

								Var__tMap_1__Struct Var = Var__tMap_1;// ###############################
								// ###############################
								// # Output tables

								Update = null;
								Insert = null;

								if (!rejectedInnerJoin_tMap_1) {

// # Output table : 'Update'
									Update_tmp.Moment_Start_FK = row4.Moment_PK;
									Update_tmp.Moment_End_FK = row5.Moment_PK;
									Update_tmp.Job_FK = row3.Job_PK;
									Update_tmp.Data_Volume = row2.volume;
									Update_tmp.Job_Duration = row2.duration;
									Update_tmp.Job_Status = row2.status;
									Update_tmp.PK = row6.PK;
									Update = Update_tmp;
								} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'Insert'
// # Filter conditions 
								if (rejectedInnerJoin_tMap_1) {
									Insert_tmp.Moment_Start_FK = row4.Moment_PK;
									Insert_tmp.Moment_End_FK = row5.Moment_PK;
									Insert_tmp.Job_FK = row3.Job_PK;
									Insert_tmp.Data_Volume = row2.volume;
									Insert_tmp.Job_Duration = row2.duration;
									Insert_tmp.Job_Status = row2.status;
									Insert_tmp.PK = Numeric.sequence("s1", 1, 1);
									Insert = Insert_tmp;
								} // closing filter/reject
// ###############################

							} // end of Var scope

							rejectedInnerJoin_tMap_1 = false;

							tos_count_tMap_1++;

							/**
							 * [tMap_1 main ] stop
							 */

							/**
							 * [tMap_1 process_data_begin ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_begin ] stop
							 */
// Start of branch "Insert"
							if (Insert != null) {

								/**
								 * [tDBOutput_1 main ] start
								 */

								currentComponent = "tDBOutput_1";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "Insert"

									);
								}

								whetherReject_tDBOutput_1 = false;
								pstmt_tDBOutput_1.setInt(1, Insert.Moment_Start_FK);

								pstmt_tDBOutput_1.setInt(2, Insert.Moment_End_FK);

								pstmt_tDBOutput_1.setInt(3, Insert.Job_FK);

								if (Insert.Data_Volume == null) {
									pstmt_tDBOutput_1.setNull(4, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(4, Insert.Data_Volume);
								}

								if (Insert.Job_Duration == null) {
									pstmt_tDBOutput_1.setNull(5, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setInt(5, Insert.Job_Duration);
								}

								if (Insert.Job_Status == null) {
									pstmt_tDBOutput_1.setNull(6, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(6, Insert.Job_Status);
								}

								pstmt_tDBOutput_1.setInt(7, Insert.PK);

								pstmt_tDBOutput_1.addBatch();
								nb_line_tDBOutput_1++;

								batchSizeCounter_tDBOutput_1++;

								if ((batchSize_tDBOutput_1 > 0)
										&& (batchSize_tDBOutput_1 <= batchSizeCounter_tDBOutput_1)) {
									try {
										int countSum_tDBOutput_1 = 0;

										for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
													: countEach_tDBOutput_1);
										}
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

										insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

										batchSizeCounter_tDBOutput_1 = 0;
									} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
										globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
										java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
												sqle_tDBOutput_1 = null;
										String errormessage_tDBOutput_1;
										if (ne_tDBOutput_1 != null) {
											// build new exception to provide the original cause
											sqle_tDBOutput_1 = new java.sql.SQLException(
													e_tDBOutput_1.getMessage() + "\ncaused by: "
															+ ne_tDBOutput_1.getMessage(),
													ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(),
													ne_tDBOutput_1);
											errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
										} else {
											errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
										}

										int countSum_tDBOutput_1 = 0;
										for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
											countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
													: countEach_tDBOutput_1);
										}
										rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

										insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

										System.err.println(errormessage_tDBOutput_1);

									}
								}

								commitCounter_tDBOutput_1++;
								if (commitEvery_tDBOutput_1 <= commitCounter_tDBOutput_1) {
									if ((batchSize_tDBOutput_1 > 0) && (batchSizeCounter_tDBOutput_1 > 0)) {
										try {
											int countSum_tDBOutput_1 = 0;

											for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
												countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
														: countEach_tDBOutput_1);
											}
											rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

											insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

											batchSizeCounter_tDBOutput_1 = 0;
										} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
											globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
											java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(),
													sqle_tDBOutput_1 = null;
											String errormessage_tDBOutput_1;
											if (ne_tDBOutput_1 != null) {
												// build new exception to provide the original cause
												sqle_tDBOutput_1 = new java.sql.SQLException(
														e_tDBOutput_1.getMessage() + "\ncaused by: "
																+ ne_tDBOutput_1.getMessage(),
														ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(),
														ne_tDBOutput_1);
												errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
											} else {
												errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
											}

											int countSum_tDBOutput_1 = 0;
											for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
												countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0
														: countEach_tDBOutput_1);
											}
											rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

											insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

											System.err.println(errormessage_tDBOutput_1);

										}
									}
									if (rowsToCommitCount_tDBOutput_1 != 0) {

									}
									conn_tDBOutput_1.commit();
									if (rowsToCommitCount_tDBOutput_1 != 0) {

										rowsToCommitCount_tDBOutput_1 = 0;
									}
									commitCounter_tDBOutput_1 = 0;
								}

								tos_count_tDBOutput_1++;

								/**
								 * [tDBOutput_1 main ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_begin ] start
								 */

								currentComponent = "tDBOutput_1";

								/**
								 * [tDBOutput_1 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_1 process_data_end ] start
								 */

								currentComponent = "tDBOutput_1";

								/**
								 * [tDBOutput_1 process_data_end ] stop
								 */

							} // End of branch "Insert"

// Start of branch "Update"
							if (Update != null) {

								/**
								 * [tDBOutput_2 main ] start
								 */

								currentComponent = "tDBOutput_2";

								if (execStat) {
									runStat.updateStatOnConnection(iterateId, 1, 1

											, "Update"

									);
								}

								whetherReject_tDBOutput_2 = false;
								if (Update.Data_Volume == null) {
									pstmt_tDBOutput_2.setNull(1, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_2.setInt(1, Update.Data_Volume);
								}

								if (Update.Job_Duration == null) {
									pstmt_tDBOutput_2.setNull(2, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_2.setInt(2, Update.Job_Duration);
								}

								if (Update.Job_Status == null) {
									pstmt_tDBOutput_2.setNull(3, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(3, Update.Job_Status);
								}

								pstmt_tDBOutput_2.setInt(4 + count_tDBOutput_2, Update.Moment_Start_FK);

								pstmt_tDBOutput_2.setInt(5 + count_tDBOutput_2, Update.Moment_End_FK);

								pstmt_tDBOutput_2.setInt(6 + count_tDBOutput_2, Update.Job_FK);

								pstmt_tDBOutput_2.setInt(7 + count_tDBOutput_2, Update.PK);

								pstmt_tDBOutput_2.addBatch();
								nb_line_tDBOutput_2++;

								batchSizeCounter_tDBOutput_2++;

								if ((batchSize_tDBOutput_2 > 0)
										&& (batchSize_tDBOutput_2 <= batchSizeCounter_tDBOutput_2)) {
									try {
										int countSum_tDBOutput_2 = 0;

										for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
											countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
													: countEach_tDBOutput_2);
										}
										rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

										updatedCount_tDBOutput_2 += countSum_tDBOutput_2;

										batchSizeCounter_tDBOutput_2 = 0;
									} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
										globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
										java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(),
												sqle_tDBOutput_2 = null;
										String errormessage_tDBOutput_2;
										if (ne_tDBOutput_2 != null) {
											// build new exception to provide the original cause
											sqle_tDBOutput_2 = new java.sql.SQLException(
													e_tDBOutput_2.getMessage() + "\ncaused by: "
															+ ne_tDBOutput_2.getMessage(),
													ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(),
													ne_tDBOutput_2);
											errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
										} else {
											errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
										}

										int countSum_tDBOutput_2 = 0;
										for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
											countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
													: countEach_tDBOutput_2);
										}
										rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

										updatedCount_tDBOutput_2 += countSum_tDBOutput_2;

										System.err.println(errormessage_tDBOutput_2);

									}
								}

								commitCounter_tDBOutput_2++;
								if (commitEvery_tDBOutput_2 <= commitCounter_tDBOutput_2) {
									if ((batchSize_tDBOutput_2 > 0) && (batchSizeCounter_tDBOutput_2 > 0)) {
										try {
											int countSum_tDBOutput_2 = 0;

											for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
												countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
														: countEach_tDBOutput_2);
											}
											rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

											updatedCount_tDBOutput_2 += countSum_tDBOutput_2;

											batchSizeCounter_tDBOutput_2 = 0;
										} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
											globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
											java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(),
													sqle_tDBOutput_2 = null;
											String errormessage_tDBOutput_2;
											if (ne_tDBOutput_2 != null) {
												// build new exception to provide the original cause
												sqle_tDBOutput_2 = new java.sql.SQLException(
														e_tDBOutput_2.getMessage() + "\ncaused by: "
																+ ne_tDBOutput_2.getMessage(),
														ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(),
														ne_tDBOutput_2);
												errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
											} else {
												errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
											}

											int countSum_tDBOutput_2 = 0;
											for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
												countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0
														: countEach_tDBOutput_2);
											}
											rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

											updatedCount_tDBOutput_2 += countSum_tDBOutput_2;

											System.err.println(errormessage_tDBOutput_2);

										}
									}
									if (rowsToCommitCount_tDBOutput_2 != 0) {

									}
									conn_tDBOutput_2.commit();
									if (rowsToCommitCount_tDBOutput_2 != 0) {

										rowsToCommitCount_tDBOutput_2 = 0;
									}
									commitCounter_tDBOutput_2 = 0;
								}

								tos_count_tDBOutput_2++;

								/**
								 * [tDBOutput_2 main ] stop
								 */

								/**
								 * [tDBOutput_2 process_data_begin ] start
								 */

								currentComponent = "tDBOutput_2";

								/**
								 * [tDBOutput_2 process_data_begin ] stop
								 */

								/**
								 * [tDBOutput_2 process_data_end ] start
								 */

								currentComponent = "tDBOutput_2";

								/**
								 * [tDBOutput_2 process_data_end ] stop
								 */

							} // End of branch "Update"

							/**
							 * [tMap_1 process_data_end ] start
							 */

							currentComponent = "tMap_1";

							/**
							 * [tMap_1 process_data_end ] stop
							 */

						} // End of branch "row2"

						/**
						 * [tUniqRow_1 process_data_end ] start
						 */

						currentComponent = "tUniqRow_1";

						/**
						 * [tUniqRow_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 process_data_end ] start
						 */

						currentComponent = "tDBInput_1";

						/**
						 * [tDBInput_1 process_data_end ] stop
						 */

						/**
						 * [tDBInput_1 end ] start
						 */

						currentComponent = "tDBInput_1";

					}
				} finally {
					if (rs_tDBInput_1 != null) {
						rs_tDBInput_1.close();
					}
					if (stmt_tDBInput_1 != null) {
						stmt_tDBInput_1.close();
					}
					if (conn_tDBInput_1 != null && !conn_tDBInput_1.isClosed()) {

						conn_tDBInput_1.commit();

						conn_tDBInput_1.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_1_NB_LINE", nb_line_tDBInput_1);

				ok_Hash.put("tDBInput_1", true);
				end_Hash.put("tDBInput_1", System.currentTimeMillis());

				/**
				 * [tDBInput_1 end ] stop
				 */

				/**
				 * [tUniqRow_1 end ] start
				 */

				currentComponent = "tUniqRow_1";

				globalMap.put("tUniqRow_1_NB_UNIQUES", nb_uniques_tUniqRow_1);
				globalMap.put("tUniqRow_1_NB_DUPLICATES", nb_duplicates_tUniqRow_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row1");
				}

				ok_Hash.put("tUniqRow_1", true);
				end_Hash.put("tUniqRow_1", System.currentTimeMillis());

				/**
				 * [tUniqRow_1 end ] stop
				 */

				/**
				 * [tMap_1 end ] start
				 */

				currentComponent = "tMap_1";

// ###############################
// # Lookup hashes releasing
				if (tHash_Lookup_row3 != null) {
					tHash_Lookup_row3.endGet();
				}
				globalMap.remove("tHash_Lookup_row3");

				if (tHash_Lookup_row4 != null) {
					tHash_Lookup_row4.endGet();
				}
				globalMap.remove("tHash_Lookup_row4");

				if (tHash_Lookup_row5 != null) {
					tHash_Lookup_row5.endGet();
				}
				globalMap.remove("tHash_Lookup_row5");

				if (tHash_Lookup_row6 != null) {
					tHash_Lookup_row6.endGet();
				}
				globalMap.remove("tHash_Lookup_row6");

// ###############################      

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row2");
				}

				ok_Hash.put("tMap_1", true);
				end_Hash.put("tMap_1", System.currentTimeMillis());

				/**
				 * [tMap_1 end ] stop
				 */

				/**
				 * [tDBOutput_1 end ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					int countSum_tDBOutput_1 = 0;
					if (pstmt_tDBOutput_1 != null && batchSizeCounter_tDBOutput_1 > 0) {

						for (int countEach_tDBOutput_1 : pstmt_tDBOutput_1.executeBatch()) {
							countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
						}
						rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					}

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

				} catch (java.sql.BatchUpdateException e_tDBOutput_1) {
					globalMap.put("tDBOutput_1_ERROR_MESSAGE", e_tDBOutput_1.getMessage());
					java.sql.SQLException ne_tDBOutput_1 = e_tDBOutput_1.getNextException(), sqle_tDBOutput_1 = null;
					String errormessage_tDBOutput_1;
					if (ne_tDBOutput_1 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_1 = new java.sql.SQLException(
								e_tDBOutput_1.getMessage() + "\ncaused by: " + ne_tDBOutput_1.getMessage(),
								ne_tDBOutput_1.getSQLState(), ne_tDBOutput_1.getErrorCode(), ne_tDBOutput_1);
						errormessage_tDBOutput_1 = sqle_tDBOutput_1.getMessage();
					} else {
						errormessage_tDBOutput_1 = e_tDBOutput_1.getMessage();
					}

					int countSum_tDBOutput_1 = 0;
					for (int countEach_tDBOutput_1 : e_tDBOutput_1.getUpdateCounts()) {
						countSum_tDBOutput_1 += (countEach_tDBOutput_1 < 0 ? 0 : countEach_tDBOutput_1);
					}
					rowsToCommitCount_tDBOutput_1 += countSum_tDBOutput_1;

					insertedCount_tDBOutput_1 += countSum_tDBOutput_1;

					System.err.println(errormessage_tDBOutput_1);

				}

				if (pstmt_tDBOutput_1 != null) {

					pstmt_tDBOutput_1.close();
					resourceMap.remove("pstmt_tDBOutput_1");
				}
				resourceMap.put("statementClosed_tDBOutput_1", true);
				if (rowsToCommitCount_tDBOutput_1 != 0) {

				}
				conn_tDBOutput_1.commit();
				if (rowsToCommitCount_tDBOutput_1 != 0) {

					rowsToCommitCount_tDBOutput_1 = 0;
				}
				commitCounter_tDBOutput_1 = 0;

				conn_tDBOutput_1.close();

				resourceMap.put("finish_tDBOutput_1", true);

				nb_line_deleted_tDBOutput_1 = nb_line_deleted_tDBOutput_1 + deletedCount_tDBOutput_1;
				nb_line_update_tDBOutput_1 = nb_line_update_tDBOutput_1 + updatedCount_tDBOutput_1;
				nb_line_inserted_tDBOutput_1 = nb_line_inserted_tDBOutput_1 + insertedCount_tDBOutput_1;
				nb_line_rejected_tDBOutput_1 = nb_line_rejected_tDBOutput_1 + rejectedCount_tDBOutput_1;

				globalMap.put("tDBOutput_1_NB_LINE", nb_line_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_UPDATED", nb_line_update_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_DELETED", nb_line_deleted_tDBOutput_1);
				globalMap.put("tDBOutput_1_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_1);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "Insert");
				}

				ok_Hash.put("tDBOutput_1", true);
				end_Hash.put("tDBOutput_1", System.currentTimeMillis());

				/**
				 * [tDBOutput_1 end ] stop
				 */

				/**
				 * [tDBOutput_2 end ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					int countSum_tDBOutput_2 = 0;
					if (pstmt_tDBOutput_2 != null && batchSizeCounter_tDBOutput_2 > 0) {

						for (int countEach_tDBOutput_2 : pstmt_tDBOutput_2.executeBatch()) {
							countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
						}
						rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					}

					updatedCount_tDBOutput_2 += countSum_tDBOutput_2;

				} catch (java.sql.BatchUpdateException e_tDBOutput_2) {
					globalMap.put("tDBOutput_2_ERROR_MESSAGE", e_tDBOutput_2.getMessage());
					java.sql.SQLException ne_tDBOutput_2 = e_tDBOutput_2.getNextException(), sqle_tDBOutput_2 = null;
					String errormessage_tDBOutput_2;
					if (ne_tDBOutput_2 != null) {
						// build new exception to provide the original cause
						sqle_tDBOutput_2 = new java.sql.SQLException(
								e_tDBOutput_2.getMessage() + "\ncaused by: " + ne_tDBOutput_2.getMessage(),
								ne_tDBOutput_2.getSQLState(), ne_tDBOutput_2.getErrorCode(), ne_tDBOutput_2);
						errormessage_tDBOutput_2 = sqle_tDBOutput_2.getMessage();
					} else {
						errormessage_tDBOutput_2 = e_tDBOutput_2.getMessage();
					}

					int countSum_tDBOutput_2 = 0;
					for (int countEach_tDBOutput_2 : e_tDBOutput_2.getUpdateCounts()) {
						countSum_tDBOutput_2 += (countEach_tDBOutput_2 < 0 ? 0 : countEach_tDBOutput_2);
					}
					rowsToCommitCount_tDBOutput_2 += countSum_tDBOutput_2;

					updatedCount_tDBOutput_2 += countSum_tDBOutput_2;

					System.err.println(errormessage_tDBOutput_2);

				}

				if (pstmt_tDBOutput_2 != null) {

					pstmt_tDBOutput_2.close();
					resourceMap.remove("pstmt_tDBOutput_2");
				}
				resourceMap.put("statementClosed_tDBOutput_2", true);
				if (rowsToCommitCount_tDBOutput_2 != 0) {

				}
				conn_tDBOutput_2.commit();
				if (rowsToCommitCount_tDBOutput_2 != 0) {

					rowsToCommitCount_tDBOutput_2 = 0;
				}
				commitCounter_tDBOutput_2 = 0;

				conn_tDBOutput_2.close();

				resourceMap.put("finish_tDBOutput_2", true);

				nb_line_deleted_tDBOutput_2 = nb_line_deleted_tDBOutput_2 + deletedCount_tDBOutput_2;
				nb_line_update_tDBOutput_2 = nb_line_update_tDBOutput_2 + updatedCount_tDBOutput_2;
				nb_line_inserted_tDBOutput_2 = nb_line_inserted_tDBOutput_2 + insertedCount_tDBOutput_2;
				nb_line_rejected_tDBOutput_2 = nb_line_rejected_tDBOutput_2 + rejectedCount_tDBOutput_2;

				globalMap.put("tDBOutput_2_NB_LINE", nb_line_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_UPDATED", nb_line_update_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_INSERTED", nb_line_inserted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_DELETED", nb_line_deleted_tDBOutput_2);
				globalMap.put("tDBOutput_2_NB_LINE_REJECTED", nb_line_rejected_tDBOutput_2);

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "Update");
				}

				ok_Hash.put("tDBOutput_2", true);
				end_Hash.put("tDBOutput_2", System.currentTimeMillis());

				/**
				 * [tDBOutput_2 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row3");

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row4");

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row5");

			// free memory for "tMap_1"
			globalMap.remove("tHash_Lookup_row6");

			try {

				/**
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
				 */

				/**
				 * [tUniqRow_1 finally ] start
				 */

				currentComponent = "tUniqRow_1";

				/**
				 * [tUniqRow_1 finally ] stop
				 */

				/**
				 * [tMap_1 finally ] start
				 */

				currentComponent = "tMap_1";

				/**
				 * [tMap_1 finally ] stop
				 */

				/**
				 * [tDBOutput_1 finally ] start
				 */

				currentComponent = "tDBOutput_1";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_1") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_1 = null;
						if ((pstmtToClose_tDBOutput_1 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_1")) != null) {
							pstmtToClose_tDBOutput_1.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_1") == null) {
						java.sql.Connection ctn_tDBOutput_1 = null;
						if ((ctn_tDBOutput_1 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_1")) != null) {
							try {
								ctn_tDBOutput_1.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_1) {
								String errorMessage_tDBOutput_1 = "failed to close the connection in tDBOutput_1 :"
										+ sqlEx_tDBOutput_1.getMessage();
								System.err.println(errorMessage_tDBOutput_1);
							}
						}
					}
				}

				/**
				 * [tDBOutput_1 finally ] stop
				 */

				/**
				 * [tDBOutput_2 finally ] start
				 */

				currentComponent = "tDBOutput_2";

				try {
					if (resourceMap.get("statementClosed_tDBOutput_2") == null) {
						java.sql.PreparedStatement pstmtToClose_tDBOutput_2 = null;
						if ((pstmtToClose_tDBOutput_2 = (java.sql.PreparedStatement) resourceMap
								.remove("pstmt_tDBOutput_2")) != null) {
							pstmtToClose_tDBOutput_2.close();
						}
					}
				} finally {
					if (resourceMap.get("finish_tDBOutput_2") == null) {
						java.sql.Connection ctn_tDBOutput_2 = null;
						if ((ctn_tDBOutput_2 = (java.sql.Connection) resourceMap.get("conn_tDBOutput_2")) != null) {
							try {
								ctn_tDBOutput_2.close();
							} catch (java.sql.SQLException sqlEx_tDBOutput_2) {
								String errorMessage_tDBOutput_2 = "failed to close the connection in tDBOutput_2 :"
										+ sqlEx_tDBOutput_2.getMessage();
								System.err.println(errorMessage_tDBOutput_2);
							}
						}
					}
				}

				/**
				 * [tDBOutput_2 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
	}

	public static class row3Struct implements routines.system.IPersistableComparableLookupRow<row3Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int Job_PK;

		public int getJob_PK() {
			return this.Job_PK;
		}

		public String Job_Code;

		public String getJob_Code() {
			return this.Job_Code;
		}

		public String Pid;

		public String getPid() {
			return this.Pid;
		}

		public String Job_Version;

		public String getJob_Version() {
			return this.Job_Version;
		}

		public Integer Project_FK;

		public Integer getProject_FK() {
			return this.Project_FK;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Job_Code == null) ? 0 : this.Job_Code.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row3Struct other = (row3Struct) obj;

			if (this.Job_Code == null) {
				if (other.Job_Code != null)
					return false;

			} else if (!this.Job_Code.equals(other.Job_Code))

				return false;

			return true;
		}

		public void copyDataTo(row3Struct other) {

			other.Job_PK = this.Job_PK;
			other.Job_Code = this.Job_Code;
			other.Pid = this.Pid;
			other.Job_Version = this.Job_Version;
			other.Project_FK = this.Project_FK;

		}

		public void copyKeysDataTo(row3Struct other) {

			other.Job_Code = this.Job_Code;

		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private String readString(DataInputStream dis, ObjectInputStream ois) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				dis.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private String readString(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				byte[] byteArray = new byte[length];
				unmarshaller.read(byteArray);
				strReturn = new String(byteArray, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		private void writeString(String str, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Job_Code = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Job_Code = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// String

				writeString(this.Job_Code, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// String

				writeString(this.Job_Code, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.Job_PK = dis.readInt();

				this.Pid = readString(dis, ois);

				this.Job_Version = readString(dis, ois);

				this.Project_FK = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.Job_PK = objectIn.readInt();

				this.Pid = readString(dis, objectIn);

				this.Job_Version = readString(dis, objectIn);

				this.Project_FK = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.Job_PK);

				writeString(this.Pid, dos, oos);

				writeString(this.Job_Version, dos, oos);

				writeInteger(this.Project_FK, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.Job_PK);

				writeString(this.Pid, dos, objectOut);

				writeString(this.Job_Version, dos, objectOut);

				writeInteger(this.Project_FK, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Job_PK=" + String.valueOf(Job_PK));
			sb.append(",Job_Code=" + Job_Code);
			sb.append(",Pid=" + Pid);
			sb.append(",Job_Version=" + Job_Version);
			sb.append(",Project_FK=" + String.valueOf(Project_FK));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Job_Code, other.Job_Code);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_2Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row3Struct row3 = new row3Struct();

				/**
				 * [tAdvancedHash_row3 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row3", false);
				start_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row3";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row3");
				}

				int tos_count_tAdvancedHash_row3 = 0;

				// connection name:row3
				// source node:tDBInput_2 - inputs:(after_tDBInput_1) outputs:(row3,row3) |
				// target node:tAdvancedHash_row3 - inputs:(row3) outputs:()
				// linked node: tMap_1 - inputs:(row2,row3,row4,row5,row6)
				// outputs:(Insert,Update)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row3 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row3Struct>getLookup(matchingModeEnum_row3);

				globalMap.put("tHash_Lookup_row3", tHash_Lookup_row3);

				/**
				 * [tAdvancedHash_row3 begin ] stop
				 */

				/**
				 * [tDBInput_2 begin ] start
				 */

				ok_Hash.put("tDBInput_2", false);
				start_Hash.put("tDBInput_2", System.currentTimeMillis());

				currentComponent = "tDBInput_2";

				int tos_count_tDBInput_2 = 0;

				int nb_line_tDBInput_2 = 0;
				java.sql.Connection conn_tDBInput_2 = null;
				String driverClass_tDBInput_2 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_2 = java.lang.Class.forName(driverClass_tDBInput_2);
				String dbUser_tDBInput_2 = "postgres";

				final String decryptedPassword_tDBInput_2 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:WZ14/rXORLvLFJrJzioBQhX9funfVvjVNyoEHb/i72PB");

				String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;

				String url_tDBInput_2 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI";

				conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2, dbUser_tDBInput_2,
						dbPwd_tDBInput_2);

				conn_tDBInput_2.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

				String dbquery_tDBInput_2 = "SELECT \n  \"public\".\"DimJob\".\"Job_PK\", \n  \"public\".\"DimJob\".\"Job_Code\", \n  \"public\".\"DimJob\".\"Pid\", \n "
						+ " \"public\".\"DimJob\".\"Job_Version\", \n  \"public\".\"DimJob\".\"Project_FK\"\nFROM \"public\".\"DimJob\"";

				globalMap.put("tDBInput_2_QUERY", dbquery_tDBInput_2);
				java.sql.ResultSet rs_tDBInput_2 = null;

				try {
					rs_tDBInput_2 = stmt_tDBInput_2.executeQuery(dbquery_tDBInput_2);
					java.sql.ResultSetMetaData rsmd_tDBInput_2 = rs_tDBInput_2.getMetaData();
					int colQtyInRs_tDBInput_2 = rsmd_tDBInput_2.getColumnCount();

					String tmpContent_tDBInput_2 = null;

					while (rs_tDBInput_2.next()) {
						nb_line_tDBInput_2++;

						if (colQtyInRs_tDBInput_2 < 1) {
							row3.Job_PK = 0;
						} else {

							row3.Job_PK = rs_tDBInput_2.getInt(1);
							if (rs_tDBInput_2.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_2 < 2) {
							row3.Job_Code = null;
						} else {

							row3.Job_Code = routines.system.JDBCUtil.getString(rs_tDBInput_2, 2, false);
						}
						if (colQtyInRs_tDBInput_2 < 3) {
							row3.Pid = null;
						} else {

							row3.Pid = routines.system.JDBCUtil.getString(rs_tDBInput_2, 3, false);
						}
						if (colQtyInRs_tDBInput_2 < 4) {
							row3.Job_Version = null;
						} else {

							row3.Job_Version = routines.system.JDBCUtil.getString(rs_tDBInput_2, 4, false);
						}
						if (colQtyInRs_tDBInput_2 < 5) {
							row3.Project_FK = null;
						} else {

							row3.Project_FK = rs_tDBInput_2.getInt(5);
							if (rs_tDBInput_2.wasNull()) {
								row3.Project_FK = null;
							}
						}

						/**
						 * [tDBInput_2 begin ] stop
						 */

						/**
						 * [tDBInput_2 main ] start
						 */

						currentComponent = "tDBInput_2";

						tos_count_tDBInput_2++;

						/**
						 * [tDBInput_2 main ] stop
						 */

						/**
						 * [tDBInput_2 process_data_begin ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row3 main ] start
						 */

						currentComponent = "tAdvancedHash_row3";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row3"

							);
						}

						row3Struct row3_HashRow = new row3Struct();

						row3_HashRow.Job_PK = row3.Job_PK;

						row3_HashRow.Job_Code = row3.Job_Code;

						row3_HashRow.Pid = row3.Pid;

						row3_HashRow.Job_Version = row3.Job_Version;

						row3_HashRow.Project_FK = row3.Project_FK;

						tHash_Lookup_row3.put(row3_HashRow);

						tos_count_tAdvancedHash_row3++;

						/**
						 * [tAdvancedHash_row3 main ] stop
						 */

						/**
						 * [tAdvancedHash_row3 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row3";

						/**
						 * [tAdvancedHash_row3 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row3 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row3";

						/**
						 * [tAdvancedHash_row3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 process_data_end ] start
						 */

						currentComponent = "tDBInput_2";

						/**
						 * [tDBInput_2 process_data_end ] stop
						 */

						/**
						 * [tDBInput_2 end ] start
						 */

						currentComponent = "tDBInput_2";

					}
				} finally {
					if (rs_tDBInput_2 != null) {
						rs_tDBInput_2.close();
					}
					if (stmt_tDBInput_2 != null) {
						stmt_tDBInput_2.close();
					}
					if (conn_tDBInput_2 != null && !conn_tDBInput_2.isClosed()) {

						conn_tDBInput_2.commit();

						conn_tDBInput_2.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_2_NB_LINE", nb_line_tDBInput_2);

				ok_Hash.put("tDBInput_2", true);
				end_Hash.put("tDBInput_2", System.currentTimeMillis());

				/**
				 * [tDBInput_2 end ] stop
				 */

				/**
				 * [tAdvancedHash_row3 end ] start
				 */

				currentComponent = "tAdvancedHash_row3";

				tHash_Lookup_row3.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row3");
				}

				ok_Hash.put("tAdvancedHash_row3", true);
				end_Hash.put("tAdvancedHash_row3", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row3 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_2 finally ] start
				 */

				currentComponent = "tDBInput_2";

				/**
				 * [tDBInput_2 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row3 finally ] start
				 */

				currentComponent = "tAdvancedHash_row3";

				/**
				 * [tAdvancedHash_row3 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}

	public static class row4Struct implements routines.system.IPersistableComparableLookupRow<row4Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int Moment_PK;

		public int getMoment_PK() {
			return this.Moment_PK;
		}

		public java.util.Date Moment;

		public java.util.Date getMoment() {
			return this.Moment;
		}

		public Integer Year;

		public Integer getYear() {
			return this.Year;
		}

		public Integer Month;

		public Integer getMonth() {
			return this.Month;
		}

		public Integer Day;

		public Integer getDay() {
			return this.Day;
		}

		public Integer Hour;

		public Integer getHour() {
			return this.Hour;
		}

		public Integer Minute;

		public Integer getMinute() {
			return this.Minute;
		}

		public Integer Second;

		public Integer getSecond() {
			return this.Second;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Moment == null) ? 0 : this.Moment.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row4Struct other = (row4Struct) obj;

			if (this.Moment == null) {
				if (other.Moment != null)
					return false;

			} else if (!this.Moment.equals(other.Moment))

				return false;

			return true;
		}

		public void copyDataTo(row4Struct other) {

			other.Moment_PK = this.Moment_PK;
			other.Moment = this.Moment;
			other.Year = this.Year;
			other.Month = this.Month;
			other.Day = this.Day;
			other.Hour = this.Hour;
			other.Minute = this.Minute;
			other.Second = this.Second;

		}

		public void copyKeysDataTo(row4Struct other) {

			other.Moment = this.Moment;

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.Moment, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.Moment, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.Moment_PK = dis.readInt();

				this.Year = readInteger(dis, ois);

				this.Month = readInteger(dis, ois);

				this.Day = readInteger(dis, ois);

				this.Hour = readInteger(dis, ois);

				this.Minute = readInteger(dis, ois);

				this.Second = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.Moment_PK = objectIn.readInt();

				this.Year = readInteger(dis, objectIn);

				this.Month = readInteger(dis, objectIn);

				this.Day = readInteger(dis, objectIn);

				this.Hour = readInteger(dis, objectIn);

				this.Minute = readInteger(dis, objectIn);

				this.Second = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.Moment_PK);

				writeInteger(this.Year, dos, oos);

				writeInteger(this.Month, dos, oos);

				writeInteger(this.Day, dos, oos);

				writeInteger(this.Hour, dos, oos);

				writeInteger(this.Minute, dos, oos);

				writeInteger(this.Second, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.Moment_PK);

				writeInteger(this.Year, dos, objectOut);

				writeInteger(this.Month, dos, objectOut);

				writeInteger(this.Day, dos, objectOut);

				writeInteger(this.Hour, dos, objectOut);

				writeInteger(this.Minute, dos, objectOut);

				writeInteger(this.Second, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Moment_PK=" + String.valueOf(Moment_PK));
			sb.append(",Moment=" + String.valueOf(Moment));
			sb.append(",Year=" + String.valueOf(Year));
			sb.append(",Month=" + String.valueOf(Month));
			sb.append(",Day=" + String.valueOf(Day));
			sb.append(",Hour=" + String.valueOf(Hour));
			sb.append(",Minute=" + String.valueOf(Minute));
			sb.append(",Second=" + String.valueOf(Second));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row4Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Moment, other.Moment);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_3Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row4Struct row4 = new row4Struct();

				/**
				 * [tAdvancedHash_row4 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row4", false);
				start_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row4";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row4");
				}

				int tos_count_tAdvancedHash_row4 = 0;

				// connection name:row4
				// source node:tDBInput_3 - inputs:(after_tDBInput_1) outputs:(row4,row4) |
				// target node:tAdvancedHash_row4 - inputs:(row4) outputs:()
				// linked node: tMap_1 - inputs:(row2,row3,row4,row5,row6)
				// outputs:(Insert,Update)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row4 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row4Struct> tHash_Lookup_row4 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row4Struct>getLookup(matchingModeEnum_row4);

				globalMap.put("tHash_Lookup_row4", tHash_Lookup_row4);

				/**
				 * [tAdvancedHash_row4 begin ] stop
				 */

				/**
				 * [tDBInput_3 begin ] start
				 */

				ok_Hash.put("tDBInput_3", false);
				start_Hash.put("tDBInput_3", System.currentTimeMillis());

				currentComponent = "tDBInput_3";

				int tos_count_tDBInput_3 = 0;

				int nb_line_tDBInput_3 = 0;
				java.sql.Connection conn_tDBInput_3 = null;
				String driverClass_tDBInput_3 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_3 = java.lang.Class.forName(driverClass_tDBInput_3);
				String dbUser_tDBInput_3 = "postgres";

				final String decryptedPassword_tDBInput_3 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:xsJ7VTqjJU/9r/dR5AYhlSsTbBsS9xA4mpvn3twy7G92");

				String dbPwd_tDBInput_3 = decryptedPassword_tDBInput_3;

				String url_tDBInput_3 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI";

				conn_tDBInput_3 = java.sql.DriverManager.getConnection(url_tDBInput_3, dbUser_tDBInput_3,
						dbPwd_tDBInput_3);

				conn_tDBInput_3.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_3 = conn_tDBInput_3.createStatement();

				String dbquery_tDBInput_3 = "SELECT \n  \"public\".\"DimTime\".\"Moment_PK\", \n  \"public\".\"DimTime\".\"Moment\", \n  \"public\".\"DimTime\".\"Year"
						+ "\", \n  \"public\".\"DimTime\".\"Month\", \n  \"public\".\"DimTime\".\"Day\", \n  \"public\".\"DimTime\".\"Hour\", \n  \"pub"
						+ "lic\".\"DimTime\".\"Minute\", \n  \"public\".\"DimTime\".\"Second\"\nFROM \"public\".\"DimTime\"";

				globalMap.put("tDBInput_3_QUERY", dbquery_tDBInput_3);
				java.sql.ResultSet rs_tDBInput_3 = null;

				try {
					rs_tDBInput_3 = stmt_tDBInput_3.executeQuery(dbquery_tDBInput_3);
					java.sql.ResultSetMetaData rsmd_tDBInput_3 = rs_tDBInput_3.getMetaData();
					int colQtyInRs_tDBInput_3 = rsmd_tDBInput_3.getColumnCount();

					String tmpContent_tDBInput_3 = null;

					while (rs_tDBInput_3.next()) {
						nb_line_tDBInput_3++;

						if (colQtyInRs_tDBInput_3 < 1) {
							row4.Moment_PK = 0;
						} else {

							row4.Moment_PK = rs_tDBInput_3.getInt(1);
							if (rs_tDBInput_3.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_3 < 2) {
							row4.Moment = null;
						} else {

							row4.Moment = routines.system.JDBCUtil.getDate(rs_tDBInput_3, 2);
						}
						if (colQtyInRs_tDBInput_3 < 3) {
							row4.Year = null;
						} else {

							row4.Year = rs_tDBInput_3.getInt(3);
							if (rs_tDBInput_3.wasNull()) {
								row4.Year = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 4) {
							row4.Month = null;
						} else {

							row4.Month = rs_tDBInput_3.getInt(4);
							if (rs_tDBInput_3.wasNull()) {
								row4.Month = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 5) {
							row4.Day = null;
						} else {

							row4.Day = rs_tDBInput_3.getInt(5);
							if (rs_tDBInput_3.wasNull()) {
								row4.Day = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 6) {
							row4.Hour = null;
						} else {

							row4.Hour = rs_tDBInput_3.getInt(6);
							if (rs_tDBInput_3.wasNull()) {
								row4.Hour = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 7) {
							row4.Minute = null;
						} else {

							row4.Minute = rs_tDBInput_3.getInt(7);
							if (rs_tDBInput_3.wasNull()) {
								row4.Minute = null;
							}
						}
						if (colQtyInRs_tDBInput_3 < 8) {
							row4.Second = null;
						} else {

							row4.Second = rs_tDBInput_3.getInt(8);
							if (rs_tDBInput_3.wasNull()) {
								row4.Second = null;
							}
						}

						/**
						 * [tDBInput_3 begin ] stop
						 */

						/**
						 * [tDBInput_3 main ] start
						 */

						currentComponent = "tDBInput_3";

						tos_count_tDBInput_3++;

						/**
						 * [tDBInput_3 main ] stop
						 */

						/**
						 * [tDBInput_3 process_data_begin ] start
						 */

						currentComponent = "tDBInput_3";

						/**
						 * [tDBInput_3 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row4 main ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row4"

							);
						}

						row4Struct row4_HashRow = new row4Struct();

						row4_HashRow.Moment_PK = row4.Moment_PK;

						row4_HashRow.Moment = row4.Moment;

						row4_HashRow.Year = row4.Year;

						row4_HashRow.Month = row4.Month;

						row4_HashRow.Day = row4.Day;

						row4_HashRow.Hour = row4.Hour;

						row4_HashRow.Minute = row4.Minute;

						row4_HashRow.Second = row4.Second;

						tHash_Lookup_row4.put(row4_HashRow);

						tos_count_tAdvancedHash_row4++;

						/**
						 * [tAdvancedHash_row4 main ] stop
						 */

						/**
						 * [tAdvancedHash_row4 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						/**
						 * [tAdvancedHash_row4 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row4 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row4";

						/**
						 * [tAdvancedHash_row4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 process_data_end ] start
						 */

						currentComponent = "tDBInput_3";

						/**
						 * [tDBInput_3 process_data_end ] stop
						 */

						/**
						 * [tDBInput_3 end ] start
						 */

						currentComponent = "tDBInput_3";

					}
				} finally {
					if (rs_tDBInput_3 != null) {
						rs_tDBInput_3.close();
					}
					if (stmt_tDBInput_3 != null) {
						stmt_tDBInput_3.close();
					}
					if (conn_tDBInput_3 != null && !conn_tDBInput_3.isClosed()) {

						conn_tDBInput_3.commit();

						conn_tDBInput_3.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_3_NB_LINE", nb_line_tDBInput_3);

				ok_Hash.put("tDBInput_3", true);
				end_Hash.put("tDBInput_3", System.currentTimeMillis());

				/**
				 * [tDBInput_3 end ] stop
				 */

				/**
				 * [tAdvancedHash_row4 end ] start
				 */

				currentComponent = "tAdvancedHash_row4";

				tHash_Lookup_row4.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row4");
				}

				ok_Hash.put("tAdvancedHash_row4", true);
				end_Hash.put("tAdvancedHash_row4", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row4 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_3 finally ] start
				 */

				currentComponent = "tDBInput_3";

				/**
				 * [tDBInput_3 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row4 finally ] start
				 */

				currentComponent = "tAdvancedHash_row4";

				/**
				 * [tAdvancedHash_row4 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_3_SUBPROCESS_STATE", 1);
	}

	public static class row5Struct implements routines.system.IPersistableComparableLookupRow<row5Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int Moment_PK;

		public int getMoment_PK() {
			return this.Moment_PK;
		}

		public java.util.Date Moment;

		public java.util.Date getMoment() {
			return this.Moment;
		}

		public Integer Year;

		public Integer getYear() {
			return this.Year;
		}

		public Integer Month;

		public Integer getMonth() {
			return this.Month;
		}

		public Integer Day;

		public Integer getDay() {
			return this.Day;
		}

		public Integer Hour;

		public Integer getHour() {
			return this.Hour;
		}

		public Integer Minute;

		public Integer getMinute() {
			return this.Minute;
		}

		public Integer Second;

		public Integer getSecond() {
			return this.Second;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.Moment == null) ? 0 : this.Moment.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row5Struct other = (row5Struct) obj;

			if (this.Moment == null) {
				if (other.Moment != null)
					return false;

			} else if (!this.Moment.equals(other.Moment))

				return false;

			return true;
		}

		public void copyDataTo(row5Struct other) {

			other.Moment_PK = this.Moment_PK;
			other.Moment = this.Moment;
			other.Year = this.Year;
			other.Month = this.Month;
			other.Day = this.Day;
			other.Hour = this.Hour;
			other.Minute = this.Minute;
			other.Second = this.Second;

		}

		public void copyKeysDataTo(row5Struct other) {

			other.Moment = this.Moment;

		}

		private java.util.Date readDate(ObjectInputStream dis) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(dis.readLong());
			}
			return dateReturn;
		}

		private java.util.Date readDate(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			java.util.Date dateReturn = null;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				dateReturn = null;
			} else {
				dateReturn = new Date(unmarshaller.readLong());
			}
			return dateReturn;
		}

		private void writeDate(java.util.Date date1, ObjectOutputStream dos) throws IOException {
			if (date1 == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeLong(date1.getTime());
			}
		}

		private void writeDate(java.util.Date date1, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (date1 == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeLong(date1.getTime());
			}
		}

		private Integer readInteger(DataInputStream dis, ObjectInputStream ois) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(DataInputStream dis, org.jboss.marshalling.Unmarshaller unmarshaller)
				throws IOException {
			Integer intReturn;
			int length = 0;
			length = unmarshaller.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = unmarshaller.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, ObjectOutputStream oos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, DataOutputStream dos, org.jboss.marshalling.Marshaller marshaller)
				throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment = readDate(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.Moment, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.Moment, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.Moment_PK = dis.readInt();

				this.Year = readInteger(dis, ois);

				this.Month = readInteger(dis, ois);

				this.Day = readInteger(dis, ois);

				this.Hour = readInteger(dis, ois);

				this.Minute = readInteger(dis, ois);

				this.Second = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.Moment_PK = objectIn.readInt();

				this.Year = readInteger(dis, objectIn);

				this.Month = readInteger(dis, objectIn);

				this.Day = readInteger(dis, objectIn);

				this.Hour = readInteger(dis, objectIn);

				this.Minute = readInteger(dis, objectIn);

				this.Second = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.Moment_PK);

				writeInteger(this.Year, dos, oos);

				writeInteger(this.Month, dos, oos);

				writeInteger(this.Day, dos, oos);

				writeInteger(this.Hour, dos, oos);

				writeInteger(this.Minute, dos, oos);

				writeInteger(this.Second, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.Moment_PK);

				writeInteger(this.Year, dos, objectOut);

				writeInteger(this.Month, dos, objectOut);

				writeInteger(this.Day, dos, objectOut);

				writeInteger(this.Hour, dos, objectOut);

				writeInteger(this.Minute, dos, objectOut);

				writeInteger(this.Second, dos, objectOut);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Moment_PK=" + String.valueOf(Moment_PK));
			sb.append(",Moment=" + String.valueOf(Moment));
			sb.append(",Year=" + String.valueOf(Year));
			sb.append(",Month=" + String.valueOf(Month));
			sb.append(",Day=" + String.valueOf(Day));
			sb.append(",Hour=" + String.valueOf(Hour));
			sb.append(",Minute=" + String.valueOf(Minute));
			sb.append(",Second=" + String.valueOf(Second));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row5Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Moment, other.Moment);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_4Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row5Struct row5 = new row5Struct();

				/**
				 * [tAdvancedHash_row5 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row5", false);
				start_Hash.put("tAdvancedHash_row5", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row5";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row5");
				}

				int tos_count_tAdvancedHash_row5 = 0;

				// connection name:row5
				// source node:tDBInput_4 - inputs:(after_tDBInput_1) outputs:(row5,row5) |
				// target node:tAdvancedHash_row5 - inputs:(row5) outputs:()
				// linked node: tMap_1 - inputs:(row2,row3,row4,row5,row6)
				// outputs:(Insert,Update)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row5 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row5Struct> tHash_Lookup_row5 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row5Struct>getLookup(matchingModeEnum_row5);

				globalMap.put("tHash_Lookup_row5", tHash_Lookup_row5);

				/**
				 * [tAdvancedHash_row5 begin ] stop
				 */

				/**
				 * [tDBInput_4 begin ] start
				 */

				ok_Hash.put("tDBInput_4", false);
				start_Hash.put("tDBInput_4", System.currentTimeMillis());

				currentComponent = "tDBInput_4";

				int tos_count_tDBInput_4 = 0;

				int nb_line_tDBInput_4 = 0;
				java.sql.Connection conn_tDBInput_4 = null;
				String driverClass_tDBInput_4 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_4 = java.lang.Class.forName(driverClass_tDBInput_4);
				String dbUser_tDBInput_4 = "postgres";

				final String decryptedPassword_tDBInput_4 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:4oBFss7MrAbD7O7O9MY/XahUrYgQ5fif5tbzklu2WidN");

				String dbPwd_tDBInput_4 = decryptedPassword_tDBInput_4;

				String url_tDBInput_4 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI";

				conn_tDBInput_4 = java.sql.DriverManager.getConnection(url_tDBInput_4, dbUser_tDBInput_4,
						dbPwd_tDBInput_4);

				conn_tDBInput_4.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_4 = conn_tDBInput_4.createStatement();

				String dbquery_tDBInput_4 = "SELECT \n  \"public\".\"DimTime\".\"Moment_PK\", \n  \"public\".\"DimTime\".\"Moment\", \n  \"public\".\"DimTime\".\"Year"
						+ "\", \n  \"public\".\"DimTime\".\"Month\", \n  \"public\".\"DimTime\".\"Day\", \n  \"public\".\"DimTime\".\"Hour\", \n  \"pub"
						+ "lic\".\"DimTime\".\"Minute\", \n  \"public\".\"DimTime\".\"Second\"\nFROM \"public\".\"DimTime\"";

				globalMap.put("tDBInput_4_QUERY", dbquery_tDBInput_4);
				java.sql.ResultSet rs_tDBInput_4 = null;

				try {
					rs_tDBInput_4 = stmt_tDBInput_4.executeQuery(dbquery_tDBInput_4);
					java.sql.ResultSetMetaData rsmd_tDBInput_4 = rs_tDBInput_4.getMetaData();
					int colQtyInRs_tDBInput_4 = rsmd_tDBInput_4.getColumnCount();

					String tmpContent_tDBInput_4 = null;

					while (rs_tDBInput_4.next()) {
						nb_line_tDBInput_4++;

						if (colQtyInRs_tDBInput_4 < 1) {
							row5.Moment_PK = 0;
						} else {

							row5.Moment_PK = rs_tDBInput_4.getInt(1);
							if (rs_tDBInput_4.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_4 < 2) {
							row5.Moment = null;
						} else {

							row5.Moment = routines.system.JDBCUtil.getDate(rs_tDBInput_4, 2);
						}
						if (colQtyInRs_tDBInput_4 < 3) {
							row5.Year = null;
						} else {

							row5.Year = rs_tDBInput_4.getInt(3);
							if (rs_tDBInput_4.wasNull()) {
								row5.Year = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 4) {
							row5.Month = null;
						} else {

							row5.Month = rs_tDBInput_4.getInt(4);
							if (rs_tDBInput_4.wasNull()) {
								row5.Month = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 5) {
							row5.Day = null;
						} else {

							row5.Day = rs_tDBInput_4.getInt(5);
							if (rs_tDBInput_4.wasNull()) {
								row5.Day = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 6) {
							row5.Hour = null;
						} else {

							row5.Hour = rs_tDBInput_4.getInt(6);
							if (rs_tDBInput_4.wasNull()) {
								row5.Hour = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 7) {
							row5.Minute = null;
						} else {

							row5.Minute = rs_tDBInput_4.getInt(7);
							if (rs_tDBInput_4.wasNull()) {
								row5.Minute = null;
							}
						}
						if (colQtyInRs_tDBInput_4 < 8) {
							row5.Second = null;
						} else {

							row5.Second = rs_tDBInput_4.getInt(8);
							if (rs_tDBInput_4.wasNull()) {
								row5.Second = null;
							}
						}

						/**
						 * [tDBInput_4 begin ] stop
						 */

						/**
						 * [tDBInput_4 main ] start
						 */

						currentComponent = "tDBInput_4";

						tos_count_tDBInput_4++;

						/**
						 * [tDBInput_4 main ] stop
						 */

						/**
						 * [tDBInput_4 process_data_begin ] start
						 */

						currentComponent = "tDBInput_4";

						/**
						 * [tDBInput_4 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row5 main ] start
						 */

						currentComponent = "tAdvancedHash_row5";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row5"

							);
						}

						row5Struct row5_HashRow = new row5Struct();

						row5_HashRow.Moment_PK = row5.Moment_PK;

						row5_HashRow.Moment = row5.Moment;

						row5_HashRow.Year = row5.Year;

						row5_HashRow.Month = row5.Month;

						row5_HashRow.Day = row5.Day;

						row5_HashRow.Hour = row5.Hour;

						row5_HashRow.Minute = row5.Minute;

						row5_HashRow.Second = row5.Second;

						tHash_Lookup_row5.put(row5_HashRow);

						tos_count_tAdvancedHash_row5++;

						/**
						 * [tAdvancedHash_row5 main ] stop
						 */

						/**
						 * [tAdvancedHash_row5 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row5";

						/**
						 * [tAdvancedHash_row5 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row5 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row5";

						/**
						 * [tAdvancedHash_row5 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 process_data_end ] start
						 */

						currentComponent = "tDBInput_4";

						/**
						 * [tDBInput_4 process_data_end ] stop
						 */

						/**
						 * [tDBInput_4 end ] start
						 */

						currentComponent = "tDBInput_4";

					}
				} finally {
					if (rs_tDBInput_4 != null) {
						rs_tDBInput_4.close();
					}
					if (stmt_tDBInput_4 != null) {
						stmt_tDBInput_4.close();
					}
					if (conn_tDBInput_4 != null && !conn_tDBInput_4.isClosed()) {

						conn_tDBInput_4.commit();

						conn_tDBInput_4.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_4_NB_LINE", nb_line_tDBInput_4);

				ok_Hash.put("tDBInput_4", true);
				end_Hash.put("tDBInput_4", System.currentTimeMillis());

				/**
				 * [tDBInput_4 end ] stop
				 */

				/**
				 * [tAdvancedHash_row5 end ] start
				 */

				currentComponent = "tAdvancedHash_row5";

				tHash_Lookup_row5.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row5");
				}

				ok_Hash.put("tAdvancedHash_row5", true);
				end_Hash.put("tAdvancedHash_row5", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row5 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_4 finally ] start
				 */

				currentComponent = "tDBInput_4";

				/**
				 * [tDBInput_4 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row5 finally ] start
				 */

				currentComponent = "tAdvancedHash_row5";

				/**
				 * [tAdvancedHash_row5 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_4_SUBPROCESS_STATE", 1);
	}

	public static class row6Struct implements routines.system.IPersistableComparableLookupRow<row6Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_DW_FactStats2 = new byte[0];
		static byte[] commonByteArray_PROJETBI_DW_FactStats2 = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public int Moment_Start_FK;

		public int getMoment_Start_FK() {
			return this.Moment_Start_FK;
		}

		public int Moment_End_FK;

		public int getMoment_End_FK() {
			return this.Moment_End_FK;
		}

		public int Job_FK;

		public int getJob_FK() {
			return this.Job_FK;
		}

		public Integer Data_Volume;

		public Integer getData_Volume() {
			return this.Data_Volume;
		}

		public Integer Job_Duration;

		public Integer getJob_Duration() {
			return this.Job_Duration;
		}

		public String Job_Status;

		public String getJob_Status() {
			return this.Job_Status;
		}

		public int PK;

		public int getPK() {
			return this.PK;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.Moment_Start_FK;

				result = prime * result + (int) this.Moment_End_FK;

				result = prime * result + (int) this.Job_FK;

				result = prime * result + ((this.Data_Volume == null) ? 0 : this.Data_Volume.hashCode());

				result = prime * result + ((this.Job_Duration == null) ? 0 : this.Job_Duration.hashCode());

				result = prime * result + ((this.Job_Status == null) ? 0 : this.Job_Status.hashCode());

				this.hashCode = result;
				this.hashCodeDirty = false;
			}
			return this.hashCode;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final row6Struct other = (row6Struct) obj;

			if (this.Moment_Start_FK != other.Moment_Start_FK)
				return false;

			if (this.Moment_End_FK != other.Moment_End_FK)
				return false;

			if (this.Job_FK != other.Job_FK)
				return false;

			if (this.Data_Volume == null) {
				if (other.Data_Volume != null)
					return false;

			} else if (!this.Data_Volume.equals(other.Data_Volume))

				return false;

			if (this.Job_Duration == null) {
				if (other.Job_Duration != null)
					return false;

			} else if (!this.Job_Duration.equals(other.Job_Duration))

				return false;

			if (this.Job_Status == null) {
				if (other.Job_Status != null)
					return false;

			} else if (!this.Job_Status.equals(other.Job_Status))

				return false;

			return true;
		}

		public void copyDataTo(row6Struct other) {

			other.Moment_Start_FK = this.Moment_Start_FK;
			other.Moment_End_FK = this.Moment_End_FK;
			other.Job_FK = this.Job_FK;
			other.Data_Volume = this.Data_Volume;
			other.Job_Duration = this.Job_Duration;
			other.Job_Status = this.Job_Status;
			other.PK = this.PK;

		}

		public void copyKeysDataTo(row6Struct other) {

			other.Moment_Start_FK = this.Moment_Start_FK;
			other.Moment_End_FK = this.Moment_End_FK;
			other.Job_FK = this.Job_FK;
			other.Data_Volume = this.Data_Volume;
			other.Job_Duration = this.Job_Duration;
			other.Job_Status = this.Job_Status;

		}

		private Integer readInteger(ObjectInputStream dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private Integer readInteger(org.jboss.marshalling.Unmarshaller dis) throws IOException {
			Integer intReturn;
			int length = 0;
			length = dis.readByte();
			if (length == -1) {
				intReturn = null;
			} else {
				intReturn = dis.readInt();
			}
			return intReturn;
		}

		private void writeInteger(Integer intNum, ObjectOutputStream dos) throws IOException {
			if (intNum == null) {
				dos.writeByte(-1);
			} else {
				dos.writeByte(0);
				dos.writeInt(intNum);
			}
		}

		private void writeInteger(Integer intNum, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (intNum == null) {
				marshaller.writeByte(-1);
			} else {
				marshaller.writeByte(0);
				marshaller.writeInt(intNum);
			}
		}

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private String readString(org.jboss.marshalling.Unmarshaller unmarshaller) throws IOException {
			String strReturn = null;
			int length = 0;
			length = unmarshaller.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_DW_FactStats2.length) {
					if (length < 1024 && commonByteArray_PROJETBI_DW_FactStats2.length == 0) {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[1024];
					} else {
						commonByteArray_PROJETBI_DW_FactStats2 = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_DW_FactStats2, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_DW_FactStats2, 0, length, utf8Charset);
			}
			return strReturn;
		}

		private void writeString(String str, ObjectOutputStream dos) throws IOException {
			if (str == null) {
				dos.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				dos.writeInt(byteArray.length);
				dos.write(byteArray);
			}
		}

		private void writeString(String str, org.jboss.marshalling.Marshaller marshaller) throws IOException {
			if (str == null) {
				marshaller.writeInt(-1);
			} else {
				byte[] byteArray = str.getBytes(utf8Charset);
				marshaller.writeInt(byteArray.length);
				marshaller.write(byteArray);
			}
		}

		public void readKeysData(ObjectInputStream dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment_Start_FK = dis.readInt();

					this.Moment_End_FK = dis.readInt();

					this.Job_FK = dis.readInt();

					this.Data_Volume = readInteger(dis);

					this.Job_Duration = readInteger(dis);

					this.Job_Status = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_DW_FactStats2) {

				try {

					int length = 0;

					this.Moment_Start_FK = dis.readInt();

					this.Moment_End_FK = dis.readInt();

					this.Job_FK = dis.readInt();

					this.Data_Volume = readInteger(dis);

					this.Job_Duration = readInteger(dis);

					this.Job_Status = readString(dis);

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// int

				dos.writeInt(this.Moment_Start_FK);

				// int

				dos.writeInt(this.Moment_End_FK);

				// int

				dos.writeInt(this.Job_FK);

				// Integer

				writeInteger(this.Data_Volume, dos);

				// Integer

				writeInteger(this.Job_Duration, dos);

				// String

				writeString(this.Job_Status, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// int

				dos.writeInt(this.Moment_Start_FK);

				// int

				dos.writeInt(this.Moment_End_FK);

				// int

				dos.writeInt(this.Job_FK);

				// Integer

				writeInteger(this.Data_Volume, dos);

				// Integer

				writeInteger(this.Job_Duration, dos);

				// String

				writeString(this.Job_Status, dos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		/**
		 * Fill Values data by reading ObjectInputStream.
		 */
		public void readValuesData(DataInputStream dis, ObjectInputStream ois) {
			try {

				int length = 0;

				this.PK = dis.readInt();

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.PK = objectIn.readInt();

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				dos.writeInt(this.PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				objectOut.writeInt(this.PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}

		public boolean supportMarshaller() {
			return true;
		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("Moment_Start_FK=" + String.valueOf(Moment_Start_FK));
			sb.append(",Moment_End_FK=" + String.valueOf(Moment_End_FK));
			sb.append(",Job_FK=" + String.valueOf(Job_FK));
			sb.append(",Data_Volume=" + String.valueOf(Data_Volume));
			sb.append(",Job_Duration=" + String.valueOf(Job_Duration));
			sb.append(",Job_Status=" + Job_Status);
			sb.append(",PK=" + String.valueOf(PK));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row6Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.Moment_Start_FK, other.Moment_Start_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Moment_End_FK, other.Moment_End_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Job_FK, other.Job_FK);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Data_Volume, other.Data_Volume);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Job_Duration, other.Job_Duration);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.Job_Status, other.Job_Status);
			if (returnValue != 0) {
				return returnValue;
			}

			return returnValue;
		}

		private int checkNullsAndCompare(Object object1, Object object2) {
			int returnValue = 0;
			if (object1 instanceof Comparable && object2 instanceof Comparable) {
				returnValue = ((Comparable) object1).compareTo(object2);
			} else if (object1 != null && object2 != null) {
				returnValue = compareStrings(object1.toString(), object2.toString());
			} else if (object1 == null && object2 != null) {
				returnValue = 1;
			} else if (object1 != null && object2 == null) {
				returnValue = -1;
			} else {
				returnValue = 0;
			}

			return returnValue;
		}

		private int compareStrings(String string1, String string2) {
			return string1.compareTo(string2);
		}

	}

	public void tDBInput_5Process(final java.util.Map<String, Object> globalMap) throws TalendException {
		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 0);

		final boolean execStat = this.execStat;

		String iterateId = "";

		String currentComponent = "";
		java.util.Map<String, Object> resourceMap = new java.util.HashMap<String, Object>();

		try {
			// TDI-39566 avoid throwing an useless Exception
			boolean resumeIt = true;
			if (globalResumeTicket == false && resumeEntryMethodName != null) {
				String currentMethodName = new java.lang.Exception().getStackTrace()[0].getMethodName();
				resumeIt = resumeEntryMethodName.equals(currentMethodName);
			}
			if (resumeIt || globalResumeTicket) { // start the resume
				globalResumeTicket = true;

				row6Struct row6 = new row6Struct();

				/**
				 * [tAdvancedHash_row6 begin ] start
				 */

				ok_Hash.put("tAdvancedHash_row6", false);
				start_Hash.put("tAdvancedHash_row6", System.currentTimeMillis());

				currentComponent = "tAdvancedHash_row6";

				if (execStat) {
					runStat.updateStatOnConnection(resourceMap, iterateId, 0, 0, "row6");
				}

				int tos_count_tAdvancedHash_row6 = 0;

				// connection name:row6
				// source node:tDBInput_5 - inputs:(after_tDBInput_1) outputs:(row6,row6) |
				// target node:tAdvancedHash_row6 - inputs:(row6) outputs:()
				// linked node: tMap_1 - inputs:(row2,row3,row4,row5,row6)
				// outputs:(Insert,Update)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row6 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row6Struct> tHash_Lookup_row6 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row6Struct>getLookup(matchingModeEnum_row6);

				globalMap.put("tHash_Lookup_row6", tHash_Lookup_row6);

				/**
				 * [tAdvancedHash_row6 begin ] stop
				 */

				/**
				 * [tDBInput_5 begin ] start
				 */

				ok_Hash.put("tDBInput_5", false);
				start_Hash.put("tDBInput_5", System.currentTimeMillis());

				currentComponent = "tDBInput_5";

				int tos_count_tDBInput_5 = 0;

				int nb_line_tDBInput_5 = 0;
				java.sql.Connection conn_tDBInput_5 = null;
				String driverClass_tDBInput_5 = "org.postgresql.Driver";
				java.lang.Class jdbcclazz_tDBInput_5 = java.lang.Class.forName(driverClass_tDBInput_5);
				String dbUser_tDBInput_5 = "postgres";

				final String decryptedPassword_tDBInput_5 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:IDrLz8Pbjra/81kjbCTNvQ1wmvw39CmkNztF8vNJB0Hh");

				String dbPwd_tDBInput_5 = decryptedPassword_tDBInput_5;

				String url_tDBInput_5 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI";

				conn_tDBInput_5 = java.sql.DriverManager.getConnection(url_tDBInput_5, dbUser_tDBInput_5,
						dbPwd_tDBInput_5);

				conn_tDBInput_5.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_5 = conn_tDBInput_5.createStatement();

				String dbquery_tDBInput_5 = "SELECT \n  \"public\".\"FactStats\".\"Moment_Start_FK\", \n  \"public\".\"FactStats\".\"Moment_End_FK\", \n  \"public\".\""
						+ "FactStats\".\"Job_FK\", \n  \"public\".\"FactStats\".\"Data_Volume\", \n  \"public\".\"FactStats\".\"Job_Duration\", \n  \""
						+ "public\".\"FactStats\".\"Job_Status\", \n  \"public\".\"FactStats\".\"PK\"\nFROM \"public\".\"FactStats\"";

				globalMap.put("tDBInput_5_QUERY", dbquery_tDBInput_5);
				java.sql.ResultSet rs_tDBInput_5 = null;

				try {
					rs_tDBInput_5 = stmt_tDBInput_5.executeQuery(dbquery_tDBInput_5);
					java.sql.ResultSetMetaData rsmd_tDBInput_5 = rs_tDBInput_5.getMetaData();
					int colQtyInRs_tDBInput_5 = rsmd_tDBInput_5.getColumnCount();

					String tmpContent_tDBInput_5 = null;

					while (rs_tDBInput_5.next()) {
						nb_line_tDBInput_5++;

						if (colQtyInRs_tDBInput_5 < 1) {
							row6.Moment_Start_FK = 0;
						} else {

							row6.Moment_Start_FK = rs_tDBInput_5.getInt(1);
							if (rs_tDBInput_5.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_5 < 2) {
							row6.Moment_End_FK = 0;
						} else {

							row6.Moment_End_FK = rs_tDBInput_5.getInt(2);
							if (rs_tDBInput_5.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_5 < 3) {
							row6.Job_FK = 0;
						} else {

							row6.Job_FK = rs_tDBInput_5.getInt(3);
							if (rs_tDBInput_5.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_5 < 4) {
							row6.Data_Volume = null;
						} else {

							row6.Data_Volume = rs_tDBInput_5.getInt(4);
							if (rs_tDBInput_5.wasNull()) {
								row6.Data_Volume = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 5) {
							row6.Job_Duration = null;
						} else {

							row6.Job_Duration = rs_tDBInput_5.getInt(5);
							if (rs_tDBInput_5.wasNull()) {
								row6.Job_Duration = null;
							}
						}
						if (colQtyInRs_tDBInput_5 < 6) {
							row6.Job_Status = null;
						} else {

							row6.Job_Status = routines.system.JDBCUtil.getString(rs_tDBInput_5, 6, false);
						}
						if (colQtyInRs_tDBInput_5 < 7) {
							row6.PK = 0;
						} else {

							row6.PK = rs_tDBInput_5.getInt(7);
							if (rs_tDBInput_5.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}

						/**
						 * [tDBInput_5 begin ] stop
						 */

						/**
						 * [tDBInput_5 main ] start
						 */

						currentComponent = "tDBInput_5";

						tos_count_tDBInput_5++;

						/**
						 * [tDBInput_5 main ] stop
						 */

						/**
						 * [tDBInput_5 process_data_begin ] start
						 */

						currentComponent = "tDBInput_5";

						/**
						 * [tDBInput_5 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row6 main ] start
						 */

						currentComponent = "tAdvancedHash_row6";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row6"

							);
						}

						row6Struct row6_HashRow = new row6Struct();

						row6_HashRow.Moment_Start_FK = row6.Moment_Start_FK;

						row6_HashRow.Moment_End_FK = row6.Moment_End_FK;

						row6_HashRow.Job_FK = row6.Job_FK;

						row6_HashRow.Data_Volume = row6.Data_Volume;

						row6_HashRow.Job_Duration = row6.Job_Duration;

						row6_HashRow.Job_Status = row6.Job_Status;

						row6_HashRow.PK = row6.PK;

						tHash_Lookup_row6.put(row6_HashRow);

						tos_count_tAdvancedHash_row6++;

						/**
						 * [tAdvancedHash_row6 main ] stop
						 */

						/**
						 * [tAdvancedHash_row6 process_data_begin ] start
						 */

						currentComponent = "tAdvancedHash_row6";

						/**
						 * [tAdvancedHash_row6 process_data_begin ] stop
						 */

						/**
						 * [tAdvancedHash_row6 process_data_end ] start
						 */

						currentComponent = "tAdvancedHash_row6";

						/**
						 * [tAdvancedHash_row6 process_data_end ] stop
						 */

						/**
						 * [tDBInput_5 process_data_end ] start
						 */

						currentComponent = "tDBInput_5";

						/**
						 * [tDBInput_5 process_data_end ] stop
						 */

						/**
						 * [tDBInput_5 end ] start
						 */

						currentComponent = "tDBInput_5";

					}
				} finally {
					if (rs_tDBInput_5 != null) {
						rs_tDBInput_5.close();
					}
					if (stmt_tDBInput_5 != null) {
						stmt_tDBInput_5.close();
					}
					if (conn_tDBInput_5 != null && !conn_tDBInput_5.isClosed()) {

						conn_tDBInput_5.commit();

						conn_tDBInput_5.close();

						if ("com.mysql.cj.jdbc.Driver".equals((String) globalMap.get("driverClass_"))
								&& routines.system.BundleUtils.inOSGi()) {
							Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread")
									.getMethod("checkedShutdown").invoke(null, (Object[]) null);
						}

					}

				}
				globalMap.put("tDBInput_5_NB_LINE", nb_line_tDBInput_5);

				ok_Hash.put("tDBInput_5", true);
				end_Hash.put("tDBInput_5", System.currentTimeMillis());

				/**
				 * [tDBInput_5 end ] stop
				 */

				/**
				 * [tAdvancedHash_row6 end ] start
				 */

				currentComponent = "tAdvancedHash_row6";

				tHash_Lookup_row6.endPut();

				if (execStat) {
					runStat.updateStat(resourceMap, iterateId, 2, 0, "row6");
				}

				ok_Hash.put("tAdvancedHash_row6", true);
				end_Hash.put("tAdvancedHash_row6", System.currentTimeMillis());

				/**
				 * [tAdvancedHash_row6 end ] stop
				 */

			} // end the resume

		} catch (java.lang.Exception e) {

			TalendException te = new TalendException(e, currentComponent, globalMap);

			throw te;
		} catch (java.lang.Error error) {

			runStat.stopThreadStat();

			throw error;
		} finally {

			try {

				/**
				 * [tDBInput_5 finally ] start
				 */

				currentComponent = "tDBInput_5";

				/**
				 * [tDBInput_5 finally ] stop
				 */

				/**
				 * [tAdvancedHash_row6 finally ] start
				 */

				currentComponent = "tAdvancedHash_row6";

				/**
				 * [tAdvancedHash_row6 finally ] stop
				 */

			} catch (java.lang.Exception e) {
				// ignore
			} catch (java.lang.Error error) {
				// ignore
			}
			resourceMap = null;
		}

		globalMap.put("tDBInput_5_SUBPROCESS_STATE", 1);
	}

	public String resuming_logs_dir_path = null;
	public String resuming_checkpoint_path = null;
	public String parent_part_launcher = null;
	private String resumeEntryMethodName = null;
	private boolean globalResumeTicket = false;

	public boolean watch = false;
	// portStats is null, it means don't execute the statistics
	public Integer portStats = null;
	public int portTraces = 4334;
	public String clientHost;
	public String defaultClientHost = "localhost";
	public String contextStr = "Default";
	public boolean isDefaultContext = true;
	public String pid = "0";
	public String rootPid = null;
	public String fatherPid = null;
	public String fatherNode = null;
	public long startTime = 0;
	public boolean isChildJob = false;
	public String log4jLevel = "";

	private boolean enableLogStash;

	private boolean execStat = true;

	private ThreadLocal<java.util.Map<String, String>> threadLocal = new ThreadLocal<java.util.Map<String, String>>() {
		protected java.util.Map<String, String> initialValue() {
			java.util.Map<String, String> threadRunResultMap = new java.util.HashMap<String, String>();
			threadRunResultMap.put("errorCode", null);
			threadRunResultMap.put("status", "");
			return threadRunResultMap;
		};
	};

	protected PropertiesWithType context_param = new PropertiesWithType();
	public java.util.Map<String, Object> parentContextMap = new java.util.HashMap<String, Object>();

	public String status = "";

	public static void main(String[] args) {
		final DW_FactStats2 DW_FactStats2Class = new DW_FactStats2();

		int exitCode = DW_FactStats2Class.runJobInTOS(args);

		System.exit(exitCode);
	}

	public String[][] runJob(String[] args) {

		int exitCode = runJobInTOS(args);
		String[][] bufferValue = new String[][] { { Integer.toString(exitCode) } };

		return bufferValue;
	}

	public boolean hastBufferOutputComponent() {
		boolean hastBufferOutput = false;

		return hastBufferOutput;
	}

	public int runJobInTOS(String[] args) {
		// reset status
		status = "";

		String lastStr = "";
		for (String arg : args) {
			if (arg.equalsIgnoreCase("--context_param")) {
				lastStr = arg;
			} else if (lastStr.equals("")) {
				evalParam(arg);
			} else {
				evalParam(lastStr + " " + arg);
				lastStr = "";
			}
		}
		enableLogStash = "true".equalsIgnoreCase(System.getProperty("audit.enabled"));

		if (clientHost == null) {
			clientHost = defaultClientHost;
		}

		if (pid == null || "0".equals(pid)) {
			pid = TalendString.getAsciiRandomString(6);
		}

		if (rootPid == null) {
			rootPid = pid;
		}
		if (fatherPid == null) {
			fatherPid = pid;
		} else {
			isChildJob = true;
		}

		if (portStats != null) {
			// portStats = -1; //for testing
			if (portStats < 0 || portStats > 65535) {
				// issue:10869, the portStats is invalid, so this client socket can't open
				System.err.println("The statistics socket port " + portStats + " is invalid.");
				execStat = false;
			}
		} else {
			execStat = false;
		}
		boolean inOSGi = routines.system.BundleUtils.inOSGi();

		if (inOSGi) {
			java.util.Dictionary<String, Object> jobProperties = routines.system.BundleUtils.getJobProperties(jobName);

			if (jobProperties != null && jobProperties.get("context") != null) {
				contextStr = (String) jobProperties.get("context");
			}
		}

		try {
			// call job/subjob with an existing context, like: --context=production. if
			// without this parameter, there will use the default context instead.
			java.io.InputStream inContext = DW_FactStats2.class.getClassLoader()
					.getResourceAsStream("projetbi/dw_factstats2_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = DW_FactStats2.class.getClassLoader()
						.getResourceAsStream("config/contexts/" + contextStr + ".properties");
			}
			if (inContext != null) {
				try {
					// defaultProps is in order to keep the original context value
					if (context != null && context.isEmpty()) {
						defaultProps.load(inContext);
						context = new ContextProperties(defaultProps);
					}
				} finally {
					inContext.close();
				}
			} else if (!isDefaultContext) {
				// print info and job continue to run, for case: context_param is not empty.
				System.err.println("Could not find the context " + contextStr);
			}

			if (!context_param.isEmpty()) {
				context.putAll(context_param);
				// set types for params from parentJobs
				for (Object key : context_param.keySet()) {
					String context_key = key.toString();
					String context_type = context_param.getContextType(context_key);
					context.setContextType(context_key, context_type);

				}
			}
			class ContextProcessing {
				private void processContext_0() {
				}

				public void processAllContext() {
					processContext_0();
				}
			}

			new ContextProcessing().processAllContext();
		} catch (java.io.IOException ie) {
			System.err.println("Could not load context " + contextStr);
			ie.printStackTrace();
		}

		// get context value from parent directly
		if (parentContextMap != null && !parentContextMap.isEmpty()) {
		}

		// Resume: init the resumeUtil
		resumeEntryMethodName = ResumeUtil.getResumeEntryMethodName(resuming_checkpoint_path);
		resumeUtil = new ResumeUtil(resuming_logs_dir_path, isChildJob, rootPid);
		resumeUtil.initCommonInfo(pid, rootPid, fatherPid, projectName, jobName, contextStr, jobVersion);

		List<String> parametersToEncrypt = new java.util.ArrayList<String>();
		// Resume: jobStart
		resumeUtil.addLog("JOB_STARTED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "",
				"", "", "", "", resumeUtil.convertToJsonText(context, parametersToEncrypt));

		if (execStat) {
			try {
				runStat.openSocket(!isChildJob);
				runStat.setAllPID(rootPid, fatherPid, pid, jobName);
				runStat.startThreadStat(clientHost, portStats);
				runStat.updateStatOnJob(RunStat.JOBSTART, fatherNode);
			} catch (java.io.IOException ioException) {
				ioException.printStackTrace();
			}
		}

		java.util.concurrent.ConcurrentHashMap<Object, Object> concurrentHashMap = new java.util.concurrent.ConcurrentHashMap<Object, Object>();
		globalMap.put("concurrentHashMap", concurrentHashMap);

		long startUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		long endUsedMemory = 0;
		long end = 0;

		startTime = System.currentTimeMillis();

		this.globalResumeTicket = true;// to run tPreJob

		this.globalResumeTicket = false;// to run others jobs

		try {
			errorCode = null;
			tDBInput_1Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_1) {
			globalMap.put("tDBInput_1_SUBPROCESS_STATE", -1);

			e_tDBInput_1.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out
					.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : DW_FactStats2");
		}

		if (execStat) {
			runStat.updateStatOnJob(RunStat.JOBEND, fatherNode);
			runStat.stopThreadStat();
		}
		int returnCode = 0;

		if (errorCode == null) {
			returnCode = status != null && status.equals("failure") ? 1 : 0;
		} else {
			returnCode = errorCode.intValue();
		}
		resumeUtil.addLog("JOB_ENDED", "JOB:" + jobName, parent_part_launcher, Thread.currentThread().getId() + "", "",
				"" + returnCode, "", "", "");

		return returnCode;

	}

	// only for OSGi env
	public void destroy() {

	}

	private java.util.Map<String, Object> getSharedConnections4REST() {
		java.util.Map<String, Object> connections = new java.util.HashMap<String, Object>();

		return connections;
	}

	private void evalParam(String arg) {
		if (arg.startsWith("--resuming_logs_dir_path")) {
			resuming_logs_dir_path = arg.substring(25);
		} else if (arg.startsWith("--resuming_checkpoint_path")) {
			resuming_checkpoint_path = arg.substring(27);
		} else if (arg.startsWith("--parent_part_launcher")) {
			parent_part_launcher = arg.substring(23);
		} else if (arg.startsWith("--watch")) {
			watch = true;
		} else if (arg.startsWith("--stat_port=")) {
			String portStatsStr = arg.substring(12);
			if (portStatsStr != null && !portStatsStr.equals("null")) {
				portStats = Integer.parseInt(portStatsStr);
			}
		} else if (arg.startsWith("--trace_port=")) {
			portTraces = Integer.parseInt(arg.substring(13));
		} else if (arg.startsWith("--client_host=")) {
			clientHost = arg.substring(14);
		} else if (arg.startsWith("--context=")) {
			contextStr = arg.substring(10);
			isDefaultContext = false;
		} else if (arg.startsWith("--father_pid=")) {
			fatherPid = arg.substring(13);
		} else if (arg.startsWith("--root_pid=")) {
			rootPid = arg.substring(11);
		} else if (arg.startsWith("--father_node=")) {
			fatherNode = arg.substring(14);
		} else if (arg.startsWith("--pid=")) {
			pid = arg.substring(6);
		} else if (arg.startsWith("--context_type")) {
			String keyValue = arg.substring(15);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.setContextType(keyValue.substring(0, index),
							replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.setContextType(keyValue.substring(0, index), keyValue.substring(index + 1));
				}

			}

		} else if (arg.startsWith("--context_param")) {
			String keyValue = arg.substring(16);
			int index = -1;
			if (keyValue != null && (index = keyValue.indexOf('=')) > -1) {
				if (fatherPid == null) {
					context_param.put(keyValue.substring(0, index), replaceEscapeChars(keyValue.substring(index + 1)));
				} else { // the subjob won't escape the especial chars
					context_param.put(keyValue.substring(0, index), keyValue.substring(index + 1));
				}
			}
		} else if (arg.startsWith("--log4jLevel=")) {
			log4jLevel = arg.substring(13);
		} else if (arg.startsWith("--audit.enabled") && arg.contains("=")) {// for trunjob call
			final int equal = arg.indexOf('=');
			final String key = arg.substring("--".length(), equal);
			System.setProperty(key, arg.substring(equal + 1));
		}
	}

	private static final String NULL_VALUE_EXPRESSION_IN_COMMAND_STRING_FOR_CHILD_JOB_ONLY = "<TALEND_NULL>";

	private final String[][] escapeChars = { { "\\\\", "\\" }, { "\\n", "\n" }, { "\\'", "\'" }, { "\\r", "\r" },
			{ "\\f", "\f" }, { "\\b", "\b" }, { "\\t", "\t" } };

	private String replaceEscapeChars(String keyValue) {

		if (keyValue == null || ("").equals(keyValue.trim())) {
			return keyValue;
		}

		StringBuilder result = new StringBuilder();
		int currIndex = 0;
		while (currIndex < keyValue.length()) {
			int index = -1;
			// judege if the left string includes escape chars
			for (String[] strArray : escapeChars) {
				index = keyValue.indexOf(strArray[0], currIndex);
				if (index >= 0) {

					result.append(keyValue.substring(currIndex, index + strArray[0].length()).replace(strArray[0],
							strArray[1]));
					currIndex = index + strArray[0].length();
					break;
				}
			}
			// if the left string doesn't include escape chars, append the left into the
			// result
			if (index < 0) {
				result.append(keyValue.substring(currIndex));
				currIndex = currIndex + keyValue.length();
			}
		}

		return result.toString();
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public String getStatus() {
		return status;
	}

	ResumeUtil resumeUtil = null;
}
/************************************************************************************************
 * 219133 characters generated by Talend Open Studio for Data Integration on the
 * 6 avril 2023 à 11:53:08 WAT
 ************************************************************************************************/