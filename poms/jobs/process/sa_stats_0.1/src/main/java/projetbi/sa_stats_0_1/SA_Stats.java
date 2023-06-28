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

package projetbi.sa_stats_0_1;

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
 * Job: SA_Stats Purpose: <br>
 * Description: <br>
 * 
 * @author user@talend.com
 * @version 8.0.1.20211109_1610
 * @status
 */
public class SA_Stats implements TalendJob {

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
	private final String jobName = "SA_Stats";
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
					SA_Stats.this.exception = e;
				}
			}
			if (!(e instanceof TalendException)) {
				try {
					for (java.lang.reflect.Method m : this.getClass().getEnclosingClass().getMethods()) {
						if (m.getName().compareTo(currentComponent + "_error") == 0) {
							m.invoke(SA_Stats.this, new Object[] { e, currentComponent, globalMap });
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

	public void tDBInput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tUniqRow_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tMap_1_error(Exception exception, String errorComponent, final java.util.Map<String, Object> globalMap)
			throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBOutput_2_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_1_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tAdvancedHash_row3_error(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		end_Hash.put(errorComponent, System.currentTimeMillis());

		status = "failure";

		tDBInput_2_onSubJobError(exception, errorComponent, globalMap);
	}

	public void tDBInput_2_onSubJobError(Exception exception, String errorComponent,
			final java.util.Map<String, Object> globalMap) throws TalendException {

		resumeUtil.addLog("SYSTEM_LOG", "NODE:" + errorComponent, "", Thread.currentThread().getId() + "", "FATAL", "",
				exception.getMessage(), ResumeUtil.getExceptionStackTrace(exception), "");

	}

	public static class InsertStruct implements routines.system.IPersistableRow<InsertStruct> {
		final static byte[] commonByteArrayLock_PROJETBI_SA_Stats = new byte[0];
		static byte[] commonByteArray_PROJETBI_SA_Stats = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public int stats_PK;

		public int getStats_PK() {
			return this.stats_PK;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.stats_PK;

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

			if (this.stats_PK != other.stats_PK)
				return false;

			return true;
		}

		public void copyDataTo(InsertStruct other) {

			other.moment = this.moment;
			other.pid = this.pid;
			other.father_pid = this.father_pid;
			other.root_pid = this.root_pid;
			other.system_pid = this.system_pid;
			other.project = this.project;
			other.job = this.job;
			other.job_repository_id = this.job_repository_id;
			other.job_version = this.job_version;
			other.context = this.context;
			other.origin = this.origin;
			other.message_type = this.message_type;
			other.message = this.message;
			other.duration = this.duration;
			other.stats_PK = this.stats_PK;

		}

		public void copyKeysDataTo(InsertStruct other) {

			other.stats_PK = this.stats_PK;

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.stats_PK = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.stats_PK = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// int

				dos.writeInt(this.stats_PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// int

				dos.writeInt(this.stats_PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",stats_PK=" + String.valueOf(stats_PK));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(InsertStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.stats_PK, other.stats_PK);
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
		final static byte[] commonByteArrayLock_PROJETBI_SA_Stats = new byte[0];
		static byte[] commonByteArray_PROJETBI_SA_Stats = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public int stats_PK;

		public int getStats_PK() {
			return this.stats_PK;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + (int) this.stats_PK;

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

			if (this.stats_PK != other.stats_PK)
				return false;

			return true;
		}

		public void copyDataTo(UpdateStruct other) {

			other.moment = this.moment;
			other.pid = this.pid;
			other.father_pid = this.father_pid;
			other.root_pid = this.root_pid;
			other.system_pid = this.system_pid;
			other.project = this.project;
			other.job = this.job;
			other.job_repository_id = this.job_repository_id;
			other.job_version = this.job_version;
			other.context = this.context;
			other.origin = this.origin;
			other.message_type = this.message_type;
			other.message = this.message;
			other.duration = this.duration;
			other.stats_PK = this.stats_PK;

		}

		public void copyKeysDataTo(UpdateStruct other) {

			other.stats_PK = this.stats_PK;

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.stats_PK = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

					this.stats_PK = dis.readInt();

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// int

				dos.writeInt(this.stats_PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

				// int

				dos.writeInt(this.stats_PK);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",stats_PK=" + String.valueOf(stats_PK));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(UpdateStruct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.stats_PK, other.stats_PK);
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
		final static byte[] commonByteArrayLock_PROJETBI_SA_Stats = new byte[0];
		static byte[] commonByteArray_PROJETBI_SA_Stats = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
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
		final static byte[] commonByteArrayLock_PROJETBI_SA_Stats = new byte[0];
		static byte[] commonByteArray_PROJETBI_SA_Stats = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
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

	public static class after_tDBInput_2Struct implements routines.system.IPersistableRow<after_tDBInput_2Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_SA_Stats = new byte[0];
		static byte[] commonByteArray_PROJETBI_SA_Stats = new byte[0];

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.origin = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.origin, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public String toString() {

			StringBuilder sb = new StringBuilder();
			sb.append(super.toString());
			sb.append("[");
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(after_tDBInput_2Struct other) {

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

				tDBInput_1Process(globalMap);

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
					tableName_tDBOutput_1 = ("stats");
				} else {
					tableName_tDBOutput_1 = dbschema_tDBOutput_1 + "\".\"" + ("stats");
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

				String url_tDBOutput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI_SA";
				dbUser_tDBOutput_1 = "postgres";

				final String decryptedPassword_tDBOutput_1 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:BgyN3btBTLwHy+Ndn9KXJ4Z6WAiqeIbM2e6qFHKFtYDt");

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
						+ "\" (\"moment\",\"pid\",\"father_pid\",\"root_pid\",\"system_pid\",\"project\",\"job\",\"job_repository_id\",\"job_version\",\"context\",\"origin\",\"message_type\",\"message\",\"duration\",\"stats_PK\") VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

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
					tableName_tDBOutput_2 = ("stats");
				} else {
					tableName_tDBOutput_2 = dbschema_tDBOutput_2 + "\".\"" + ("stats");
				}

				int updateKeyCount_tDBOutput_2 = 1;
				if (updateKeyCount_tDBOutput_2 < 1) {
					throw new RuntimeException("For update, Schema must have a key");
				} else if (updateKeyCount_tDBOutput_2 == 15 && true) {
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

				String url_tDBOutput_2 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI_SA";
				dbUser_tDBOutput_2 = "postgres";

				final String decryptedPassword_tDBOutput_2 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:9UfsiEj1jHPbz908XkH68Pl4h/oKQEO+wZRaTO/9BAoe");

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
						+ "\" SET \"moment\" = ?,\"pid\" = ?,\"father_pid\" = ?,\"root_pid\" = ?,\"system_pid\" = ?,\"project\" = ?,\"job\" = ?,\"job_repository_id\" = ?,\"job_version\" = ?,\"context\" = ?,\"origin\" = ?,\"message_type\" = ?,\"message\" = ?,\"duration\" = ? WHERE \"stats_PK\" = ?";
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

					java.util.Date moment;
					String pid;
					String father_pid;
					String root_pid;
					Long system_pid;
					String project;
					String job;
					String job_repository_id;
					String job_version;
					String context;
					String origin;
					String message_type;
					String message;
					Long duration;

					@Override
					public int hashCode() {
						if (this.hashCodeDirty) {
							final int prime = PRIME;
							int result = DEFAULT_HASHCODE;

							result = prime * result + ((this.moment == null) ? 0 : this.moment.hashCode());

							result = prime * result + ((this.pid == null) ? 0 : this.pid.hashCode());

							result = prime * result + ((this.father_pid == null) ? 0 : this.father_pid.hashCode());

							result = prime * result + ((this.root_pid == null) ? 0 : this.root_pid.hashCode());

							result = prime * result + ((this.system_pid == null) ? 0 : this.system_pid.hashCode());

							result = prime * result + ((this.project == null) ? 0 : this.project.hashCode());

							result = prime * result + ((this.job == null) ? 0 : this.job.hashCode());

							result = prime * result
									+ ((this.job_repository_id == null) ? 0 : this.job_repository_id.hashCode());

							result = prime * result + ((this.job_version == null) ? 0 : this.job_version.hashCode());

							result = prime * result + ((this.context == null) ? 0 : this.context.hashCode());

							result = prime * result + ((this.origin == null) ? 0 : this.origin.hashCode());

							result = prime * result + ((this.message_type == null) ? 0 : this.message_type.hashCode());

							result = prime * result + ((this.message == null) ? 0 : this.message.hashCode());

							result = prime * result + ((this.duration == null) ? 0 : this.duration.hashCode());

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

						if (this.moment == null) {
							if (other.moment != null)
								return false;

						} else if (!this.moment.equals(other.moment))

							return false;

						if (this.pid == null) {
							if (other.pid != null)
								return false;

						} else if (!this.pid.equals(other.pid))

							return false;

						if (this.father_pid == null) {
							if (other.father_pid != null)
								return false;

						} else if (!this.father_pid.equals(other.father_pid))

							return false;

						if (this.root_pid == null) {
							if (other.root_pid != null)
								return false;

						} else if (!this.root_pid.equals(other.root_pid))

							return false;

						if (this.system_pid == null) {
							if (other.system_pid != null)
								return false;

						} else if (!this.system_pid.equals(other.system_pid))

							return false;

						if (this.project == null) {
							if (other.project != null)
								return false;

						} else if (!this.project.equals(other.project))

							return false;

						if (this.job == null) {
							if (other.job != null)
								return false;

						} else if (!this.job.equals(other.job))

							return false;

						if (this.job_repository_id == null) {
							if (other.job_repository_id != null)
								return false;

						} else if (!this.job_repository_id.equals(other.job_repository_id))

							return false;

						if (this.job_version == null) {
							if (other.job_version != null)
								return false;

						} else if (!this.job_version.equals(other.job_version))

							return false;

						if (this.context == null) {
							if (other.context != null)
								return false;

						} else if (!this.context.equals(other.context))

							return false;

						if (this.origin == null) {
							if (other.origin != null)
								return false;

						} else if (!this.origin.equals(other.origin))

							return false;

						if (this.message_type == null) {
							if (other.message_type != null)
								return false;

						} else if (!this.message_type.equals(other.message_type))

							return false;

						if (this.message == null) {
							if (other.message != null)
								return false;

						} else if (!this.message.equals(other.message))

							return false;

						if (this.duration == null) {
							if (other.duration != null)
								return false;

						} else if (!this.duration.equals(other.duration))

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
				 * [tDBInput_2 begin ] start
				 */

				ok_Hash.put("tDBInput_2", false);
				start_Hash.put("tDBInput_2", System.currentTimeMillis());

				currentComponent = "tDBInput_2";

				int tos_count_tDBInput_2 = 0;

				java.util.Calendar calendar_tDBInput_2 = java.util.Calendar.getInstance();
				calendar_tDBInput_2.set(0, 0, 0, 0, 0, 0);
				java.util.Date year0_tDBInput_2 = calendar_tDBInput_2.getTime();
				int nb_line_tDBInput_2 = 0;
				java.sql.Connection conn_tDBInput_2 = null;
				String driverClass_tDBInput_2 = "org.mariadb.jdbc.Driver";
				java.lang.Class jdbcclazz_tDBInput_2 = java.lang.Class.forName(driverClass_tDBInput_2);
				String dbUser_tDBInput_2 = "root";

				final String decryptedPassword_tDBInput_2 = routines.system.PasswordEncryptUtil
						.decryptPassword("enc:routine.encryption.key.v1:n2B79p69fbJywOeh+kpOFNkowfnYALfPSRB2UA==");

				String dbPwd_tDBInput_2 = decryptedPassword_tDBInput_2;

				String properties_tDBInput_2 = "noDatetimeStringSync=true&enabledTLSProtocols=TLSv1.2,TLSv1.1,TLSv1";
				if (properties_tDBInput_2 == null || properties_tDBInput_2.trim().length() == 0) {
					properties_tDBInput_2 = "";
				}
				String url_tDBInput_2 = "jdbc:mariadb://" + "localhost" + ":" + "3306" + "/" + "projetbi" + "?"
						+ properties_tDBInput_2;

				conn_tDBInput_2 = java.sql.DriverManager.getConnection(url_tDBInput_2, dbUser_tDBInput_2,
						dbPwd_tDBInput_2);

				java.sql.Statement stmt_tDBInput_2 = conn_tDBInput_2.createStatement();

				String dbquery_tDBInput_2 = "SELECT \n  `stats`.`moment`, \n  `stats`.`pid`, \n  `stats`.`father_pid`, \n  `stats`.`root_pid`, \n  `stats`.`system_pid`, "
						+ "\n  `stats`.`project`, \n  `stats`.`job`, \n  `stats`.`job_repository_id`, \n  `stats`.`job_version`, \n  `stats`.`context`, "
						+ "\n  `stats`.`origin`, \n  `stats`.`message_type`, \n  `stats`.`message`, \n  `stats`.`duration`\nFROM `stats`";

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
							row1.moment = null;
						} else {

							if (rs_tDBInput_2.getString(1) != null) {
								String dateString_tDBInput_2 = rs_tDBInput_2.getString(1);
								if (!("0000-00-00").equals(dateString_tDBInput_2)
										&& !("0000-00-00 00:00:00").equals(dateString_tDBInput_2)) {
									row1.moment = rs_tDBInput_2.getTimestamp(1);
								} else {
									row1.moment = (java.util.Date) year0_tDBInput_2.clone();
								}
							} else {
								row1.moment = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 2) {
							row1.pid = null;
						} else {

							row1.pid = routines.system.JDBCUtil.getString(rs_tDBInput_2, 2, false);
						}
						if (colQtyInRs_tDBInput_2 < 3) {
							row1.father_pid = null;
						} else {

							row1.father_pid = routines.system.JDBCUtil.getString(rs_tDBInput_2, 3, false);
						}
						if (colQtyInRs_tDBInput_2 < 4) {
							row1.root_pid = null;
						} else {

							row1.root_pid = routines.system.JDBCUtil.getString(rs_tDBInput_2, 4, false);
						}
						if (colQtyInRs_tDBInput_2 < 5) {
							row1.system_pid = null;
						} else {

							row1.system_pid = rs_tDBInput_2.getLong(5);
							if (rs_tDBInput_2.wasNull()) {
								row1.system_pid = null;
							}
						}
						if (colQtyInRs_tDBInput_2 < 6) {
							row1.project = null;
						} else {

							row1.project = routines.system.JDBCUtil.getString(rs_tDBInput_2, 6, false);
						}
						if (colQtyInRs_tDBInput_2 < 7) {
							row1.job = null;
						} else {

							row1.job = routines.system.JDBCUtil.getString(rs_tDBInput_2, 7, false);
						}
						if (colQtyInRs_tDBInput_2 < 8) {
							row1.job_repository_id = null;
						} else {

							row1.job_repository_id = routines.system.JDBCUtil.getString(rs_tDBInput_2, 8, false);
						}
						if (colQtyInRs_tDBInput_2 < 9) {
							row1.job_version = null;
						} else {

							row1.job_version = routines.system.JDBCUtil.getString(rs_tDBInput_2, 9, false);
						}
						if (colQtyInRs_tDBInput_2 < 10) {
							row1.context = null;
						} else {

							row1.context = routines.system.JDBCUtil.getString(rs_tDBInput_2, 10, false);
						}
						if (colQtyInRs_tDBInput_2 < 11) {
							row1.origin = null;
						} else {

							row1.origin = routines.system.JDBCUtil.getString(rs_tDBInput_2, 11, false);
						}
						if (colQtyInRs_tDBInput_2 < 12) {
							row1.message_type = null;
						} else {

							row1.message_type = routines.system.JDBCUtil.getString(rs_tDBInput_2, 12, false);
						}
						if (colQtyInRs_tDBInput_2 < 13) {
							row1.message = null;
						} else {

							row1.message = routines.system.JDBCUtil.getString(rs_tDBInput_2, 13, false);
						}
						if (colQtyInRs_tDBInput_2 < 14) {
							row1.duration = null;
						} else {

							row1.duration = rs_tDBInput_2.getLong(14);
							if (rs_tDBInput_2.wasNull()) {
								row1.duration = null;
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
						 * [tUniqRow_1 main ] start
						 */

						currentComponent = "tUniqRow_1";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row1"

							);
						}

						row2 = null;
						finder_tUniqRow_1.moment = row1.moment;
						if (row1.pid == null) {
							finder_tUniqRow_1.pid = null;
						} else {
							finder_tUniqRow_1.pid = row1.pid.toLowerCase();
						}
						if (row1.father_pid == null) {
							finder_tUniqRow_1.father_pid = null;
						} else {
							finder_tUniqRow_1.father_pid = row1.father_pid.toLowerCase();
						}
						if (row1.root_pid == null) {
							finder_tUniqRow_1.root_pid = null;
						} else {
							finder_tUniqRow_1.root_pid = row1.root_pid.toLowerCase();
						}
						finder_tUniqRow_1.system_pid = row1.system_pid;
						if (row1.project == null) {
							finder_tUniqRow_1.project = null;
						} else {
							finder_tUniqRow_1.project = row1.project.toLowerCase();
						}
						if (row1.job == null) {
							finder_tUniqRow_1.job = null;
						} else {
							finder_tUniqRow_1.job = row1.job.toLowerCase();
						}
						if (row1.job_repository_id == null) {
							finder_tUniqRow_1.job_repository_id = null;
						} else {
							finder_tUniqRow_1.job_repository_id = row1.job_repository_id.toLowerCase();
						}
						if (row1.job_version == null) {
							finder_tUniqRow_1.job_version = null;
						} else {
							finder_tUniqRow_1.job_version = row1.job_version.toLowerCase();
						}
						if (row1.context == null) {
							finder_tUniqRow_1.context = null;
						} else {
							finder_tUniqRow_1.context = row1.context.toLowerCase();
						}
						if (row1.origin == null) {
							finder_tUniqRow_1.origin = null;
						} else {
							finder_tUniqRow_1.origin = row1.origin.toLowerCase();
						}
						if (row1.message_type == null) {
							finder_tUniqRow_1.message_type = null;
						} else {
							finder_tUniqRow_1.message_type = row1.message_type.toLowerCase();
						}
						if (row1.message == null) {
							finder_tUniqRow_1.message = null;
						} else {
							finder_tUniqRow_1.message = row1.message.toLowerCase();
						}
						finder_tUniqRow_1.duration = row1.duration;
						finder_tUniqRow_1.hashCodeDirty = true;
						if (!keystUniqRow_1.contains(finder_tUniqRow_1)) {
							KeyStruct_tUniqRow_1 new_tUniqRow_1 = new KeyStruct_tUniqRow_1();

							new_tUniqRow_1.moment = row1.moment;
							if (row1.pid == null) {
								new_tUniqRow_1.pid = null;
							} else {
								new_tUniqRow_1.pid = row1.pid.toLowerCase();
							}
							if (row1.father_pid == null) {
								new_tUniqRow_1.father_pid = null;
							} else {
								new_tUniqRow_1.father_pid = row1.father_pid.toLowerCase();
							}
							if (row1.root_pid == null) {
								new_tUniqRow_1.root_pid = null;
							} else {
								new_tUniqRow_1.root_pid = row1.root_pid.toLowerCase();
							}
							new_tUniqRow_1.system_pid = row1.system_pid;
							if (row1.project == null) {
								new_tUniqRow_1.project = null;
							} else {
								new_tUniqRow_1.project = row1.project.toLowerCase();
							}
							if (row1.job == null) {
								new_tUniqRow_1.job = null;
							} else {
								new_tUniqRow_1.job = row1.job.toLowerCase();
							}
							if (row1.job_repository_id == null) {
								new_tUniqRow_1.job_repository_id = null;
							} else {
								new_tUniqRow_1.job_repository_id = row1.job_repository_id.toLowerCase();
							}
							if (row1.job_version == null) {
								new_tUniqRow_1.job_version = null;
							} else {
								new_tUniqRow_1.job_version = row1.job_version.toLowerCase();
							}
							if (row1.context == null) {
								new_tUniqRow_1.context = null;
							} else {
								new_tUniqRow_1.context = row1.context.toLowerCase();
							}
							if (row1.origin == null) {
								new_tUniqRow_1.origin = null;
							} else {
								new_tUniqRow_1.origin = row1.origin.toLowerCase();
							}
							if (row1.message_type == null) {
								new_tUniqRow_1.message_type = null;
							} else {
								new_tUniqRow_1.message_type = row1.message_type.toLowerCase();
							}
							if (row1.message == null) {
								new_tUniqRow_1.message = null;
							} else {
								new_tUniqRow_1.message = row1.message.toLowerCase();
							}
							new_tUniqRow_1.duration = row1.duration;

							keystUniqRow_1.add(new_tUniqRow_1);
							if (row2 == null) {

								row2 = new row2Struct();
							}
							row2.moment = row1.moment;
							row2.pid = row1.pid;
							row2.father_pid = row1.father_pid;
							row2.root_pid = row1.root_pid;
							row2.system_pid = row1.system_pid;
							row2.project = row1.project;
							row2.job = row1.job;
							row2.job_repository_id = row1.job_repository_id;
							row2.job_version = row1.job_version;
							row2.context = row1.context;
							row2.origin = row1.origin;
							row2.message_type = row1.message_type;
							row2.message = row1.message;
							row2.duration = row1.duration;
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

								row3HashKey.moment = row2.moment == null ? null
										: new java.util.Date(row2.moment.getTime());

								row3HashKey.pid = row2.pid;

								row3HashKey.father_pid = row2.father_pid;

								row3HashKey.root_pid = row2.root_pid;

								row3HashKey.system_pid = row2.system_pid;

								row3HashKey.project = row2.project;

								row3HashKey.job = row2.job;

								row3HashKey.job_repository_id = row2.job_repository_id;

								row3HashKey.job_version = row2.job_version;

								row3HashKey.context = row2.context;

								row3HashKey.message_type = row2.message_type;

								row3HashKey.message = row2.message;

								row3HashKey.duration = row2.duration;

								row3HashKey.hashCodeDirty = true;

								tHash_Lookup_row3.lookup(row3HashKey);

								if (!tHash_Lookup_row3.hasNext()) { // G_TM_M_090

									rejectedInnerJoin_tMap_1 = true;

								} // G_TM_M_090

							} // G_TM_M_020

							if (tHash_Lookup_row3 != null && tHash_Lookup_row3.getCount(row3HashKey) > 1) { // G 071

								// System.out.println("WARNING: UNIQUE MATCH is configured for the lookup 'row3'
								// and it contains more one result from keys : row3.moment = '" +
								// row3HashKey.moment + "', row3.pid = '" + row3HashKey.pid + "',
								// row3.father_pid = '" + row3HashKey.father_pid + "', row3.root_pid = '" +
								// row3HashKey.root_pid + "', row3.system_pid = '" + row3HashKey.system_pid +
								// "', row3.project = '" + row3HashKey.project + "', row3.job = '" +
								// row3HashKey.job + "', row3.job_repository_id = '" +
								// row3HashKey.job_repository_id + "', row3.job_version = '" +
								// row3HashKey.job_version + "', row3.context = '" + row3HashKey.context + "',
								// row3.message_type = '" + row3HashKey.message_type + "', row3.message = '" +
								// row3HashKey.message + "', row3.duration = '" + row3HashKey.duration + "'");
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
									Update_tmp.moment = row2.moment;
									Update_tmp.pid = row2.pid;
									Update_tmp.father_pid = row2.father_pid;
									Update_tmp.root_pid = row2.root_pid;
									Update_tmp.system_pid = row2.system_pid;
									Update_tmp.project = row2.project;
									Update_tmp.job = row2.job;
									Update_tmp.job_repository_id = row2.job_repository_id;
									Update_tmp.job_version = row2.job_version;
									Update_tmp.context = row2.context;
									Update_tmp.origin = new String[] { "tDie_5",

											"tS3Put_1",

											"tDie_4",

											"JBL_REF_CHECK_PREJOB_STATUT_1_tDie_3",

											"tFileDelete_1",

											"tFileInputDelimited_1",

											"tJDBCOutput_1",

											"tPostgresqlConnection_1",

											"tFileOutputDelimited_1",

											"tExtractJSONFields_1",

											"tJDBCInput_3",

											"tDie_2",

											"tWarn_2",

											"tS3Connection_1",

											"tJava_12",

											"tWarn_4",

											"tOracleRow_4",

											"tWarn_31",

											"JBL_REF_PREJOB_1_tSendMail_3",

											"tS3Delete_1",

											"tMap_3",

											"tOracleInput_4",

											"tOracleConnection_2",

											"tOracleOutputBulkExec_3_tOBE",

											"tREST_2",

											"tWarn_1",

											"tFTPConnection_1",

											"tXMLMap_1_TXMLMAP_OUT",

											"tFTPFileList_1",

											"tFTPPut_4",

											"tJava_13",

											"JBL_REF_GZIP_2_tDie_1",

											"tOracleOutputBulkExec_2_tOOB",

											"tOracleOutput_1",

											"tRunJob_32",

											"tESBConsumer_1",

											"tJavaRow_2",

											"tJDBCInput_1",

											"tOracleOutputBulkExec_8_tOBE",

											"tPostgresqlInput_1",

											"tOracleInput_3",

											"tOracleOutputBulkExec_4_tOBE",

											"tJava_14",

											"JBL_REF_BASE_CTRL_CONNEXION_1_tDie_1",

											"tOracleOutputBulkExec_12_tOBE",

											"tOracleOutputBulkExec_14_tOBE",

											"tMap_1",

											"tOracleOutputBulkExec_2_tOBE",

											"tMysqlInput_7",

											"tOracleConnection_1",

											"tFileDelete_2",

											"tFileUnarchive_1",

											"JBL_REF_GZIP_2_tFileDelete_2",

											"tREST_1",

											"tOracleOutput_4",

											"tMysqlInput_2",

											"tJava_3",

											"tOracleOutput_5",

											"tRunJob_1",

											"tJava_21",

											"tOracleInput_1",

											"tOracleOutputBulkExec_8_tOOB",

											"tFTPPut_1",

											"tAccessInput_1",

											"tOracleInput_2",

											"tFileCopy_2",

											"tOracleOutput_2",

											"tSalesforceOutputBulkExec_1_tSBE",

											"tMysqlInput_1",

											"tFTPConnection_2",

											"tJDBCConnection_3",

											"tOracleBulkExec_1",

											"tFileCopy_3",

											"tDie_7",

											"JBL_REF_PREJOB_1_tDie_2",

											"tJava_6",

											"tOracleOutputBulkExec_25_tOBE",

											"tFTPGet_1",

											"tDie_6",

											"tDie_1",

											"tOracleOutputBulkExec_30_tOBE",

											"tDie_3",

											"tFTPDelete_1",

											"tRESTClient_2",

											"tOracleInput_53",

											"tOracleOutputBulkExec_3_tOOB",

											"tOracleOutput_41",

											"JBL_REF_BASE_CTRL_CONNEXION_1_tOracleOutput_1",

											"tOracleOutput_3",

											"JBL_REF_BASE_CTRL_CONNEXION_1_tOracleConnection_11",

											"tJDBCConnection_1" }[(int) (Math.random() * 91)];
									Update_tmp.message_type = row2.message_type;
									Update_tmp.message = row2.message;
									Update_tmp.duration = row2.duration;
									Update_tmp.stats_PK = row3.stats_PK;
									Update = Update_tmp;
								} // closing inner join bracket (1)
// ###### START REJECTS ##### 

// # Output reject table : 'Insert'
// # Filter conditions 
								if (rejectedInnerJoin_tMap_1) {
									Insert_tmp.moment = row2.moment;
									Insert_tmp.pid = row2.pid;
									Insert_tmp.father_pid = row2.father_pid;
									Insert_tmp.root_pid = row2.root_pid;
									Insert_tmp.system_pid = row2.system_pid;
									Insert_tmp.project = row2.project;
									Insert_tmp.job = row2.job;
									Insert_tmp.job_repository_id = row2.job_repository_id;
									Insert_tmp.job_version = row2.job_version;
									Insert_tmp.context = row2.context;
									Insert_tmp.origin = new String[] { "tDie_5",

											"tS3Put_1",

											"tDie_4",

											"JBL_REF_CHECK_PREJOB_STATUT_1_tDie_3",

											"tFileDelete_1",

											"tFileInputDelimited_1",

											"tJDBCOutput_1",

											"tPostgresqlConnection_1",

											"tFileOutputDelimited_1",

											"tExtractJSONFields_1",

											"tJDBCInput_3",

											"tDie_2",

											"tWarn_2",

											"tS3Connection_1",

											"tJava_12",

											"tWarn_4",

											"tOracleRow_4",

											"tWarn_31",

											"JBL_REF_PREJOB_1_tSendMail_3",

											"tS3Delete_1",

											"tMap_3",

											"tOracleInput_4",

											"tOracleConnection_2",

											"tOracleOutputBulkExec_3_tOBE",

											"tREST_2",

											"tWarn_1",

											"tFTPConnection_1",

											"tXMLMap_1_TXMLMAP_OUT",

											"tFTPFileList_1",

											"tFTPPut_4",

											"tJava_13",

											"JBL_REF_GZIP_2_tDie_1",

											"tOracleOutputBulkExec_2_tOOB",

											"tOracleOutput_1",

											"tRunJob_32",

											"tESBConsumer_1",

											"tJavaRow_2",

											"tJDBCInput_1",

											"tOracleOutputBulkExec_8_tOBE",

											"tPostgresqlInput_1",

											"tOracleInput_3",

											"tOracleOutputBulkExec_4_tOBE",

											"tJava_14",

											"JBL_REF_BASE_CTRL_CONNEXION_1_tDie_1",

											"tOracleOutputBulkExec_12_tOBE",

											"tOracleOutputBulkExec_14_tOBE",

											"tMap_1",

											"tOracleOutputBulkExec_2_tOBE",

											"tMysqlInput_7",

											"tOracleConnection_1",

											"tFileDelete_2",

											"tFileUnarchive_1",

											"JBL_REF_GZIP_2_tFileDelete_2",

											"tREST_1",

											"tOracleOutput_4",

											"tMysqlInput_2",

											"tJava_3",

											"tOracleOutput_5",

											"tRunJob_1",

											"tJava_21",

											"tOracleInput_1",

											"tOracleOutputBulkExec_8_tOOB",

											"tFTPPut_1",

											"tAccessInput_1",

											"tOracleInput_2",

											"tFileCopy_2",

											"tOracleOutput_2",

											"tSalesforceOutputBulkExec_1_tSBE",

											"tMysqlInput_1",

											"tFTPConnection_2",

											"tJDBCConnection_3",

											"tOracleBulkExec_1",

											"tFileCopy_3",

											"tDie_7",

											"JBL_REF_PREJOB_1_tDie_2",

											"tJava_6",

											"tOracleOutputBulkExec_25_tOBE",

											"tFTPGet_1",

											"tDie_6",

											"tDie_1",

											"tOracleOutputBulkExec_30_tOBE",

											"tDie_3",

											"tFTPDelete_1",

											"tRESTClient_2",

											"tOracleInput_53",

											"tOracleOutputBulkExec_3_tOOB",

											"tOracleOutput_41",

											"JBL_REF_BASE_CTRL_CONNEXION_1_tOracleOutput_1",

											"tOracleOutput_3",

											"JBL_REF_BASE_CTRL_CONNEXION_1_tOracleConnection_11",

											"tJDBCConnection_1" }[(int) (Math.random() * 91)];
									Insert_tmp.message_type = row2.message_type;
									Insert_tmp.message = row2.message;
									Insert_tmp.duration = row2.duration;
									Insert_tmp.stats_PK = Numeric.sequence("s1", 1, 1);
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
								if (Insert.moment != null) {
									pstmt_tDBOutput_1.setTimestamp(1, new java.sql.Timestamp(Insert.moment.getTime()));
								} else {
									pstmt_tDBOutput_1.setNull(1, java.sql.Types.TIMESTAMP);
								}

								if (Insert.pid == null) {
									pstmt_tDBOutput_1.setNull(2, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(2, Insert.pid);
								}

								if (Insert.father_pid == null) {
									pstmt_tDBOutput_1.setNull(3, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(3, Insert.father_pid);
								}

								if (Insert.root_pid == null) {
									pstmt_tDBOutput_1.setNull(4, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(4, Insert.root_pid);
								}

								if (Insert.system_pid == null) {
									pstmt_tDBOutput_1.setNull(5, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setLong(5, Insert.system_pid);
								}

								if (Insert.project == null) {
									pstmt_tDBOutput_1.setNull(6, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(6, Insert.project);
								}

								if (Insert.job == null) {
									pstmt_tDBOutput_1.setNull(7, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(7, Insert.job);
								}

								if (Insert.job_repository_id == null) {
									pstmt_tDBOutput_1.setNull(8, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(8, Insert.job_repository_id);
								}

								if (Insert.job_version == null) {
									pstmt_tDBOutput_1.setNull(9, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(9, Insert.job_version);
								}

								if (Insert.context == null) {
									pstmt_tDBOutput_1.setNull(10, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(10, Insert.context);
								}

								if (Insert.origin == null) {
									pstmt_tDBOutput_1.setNull(11, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(11, Insert.origin);
								}

								if (Insert.message_type == null) {
									pstmt_tDBOutput_1.setNull(12, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(12, Insert.message_type);
								}

								if (Insert.message == null) {
									pstmt_tDBOutput_1.setNull(13, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_1.setString(13, Insert.message);
								}

								if (Insert.duration == null) {
									pstmt_tDBOutput_1.setNull(14, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_1.setLong(14, Insert.duration);
								}

								pstmt_tDBOutput_1.setInt(15, Insert.stats_PK);

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
								if (Update.moment != null) {
									pstmt_tDBOutput_2.setTimestamp(1, new java.sql.Timestamp(Update.moment.getTime()));
								} else {
									pstmt_tDBOutput_2.setNull(1, java.sql.Types.TIMESTAMP);
								}

								if (Update.pid == null) {
									pstmt_tDBOutput_2.setNull(2, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(2, Update.pid);
								}

								if (Update.father_pid == null) {
									pstmt_tDBOutput_2.setNull(3, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(3, Update.father_pid);
								}

								if (Update.root_pid == null) {
									pstmt_tDBOutput_2.setNull(4, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(4, Update.root_pid);
								}

								if (Update.system_pid == null) {
									pstmt_tDBOutput_2.setNull(5, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_2.setLong(5, Update.system_pid);
								}

								if (Update.project == null) {
									pstmt_tDBOutput_2.setNull(6, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(6, Update.project);
								}

								if (Update.job == null) {
									pstmt_tDBOutput_2.setNull(7, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(7, Update.job);
								}

								if (Update.job_repository_id == null) {
									pstmt_tDBOutput_2.setNull(8, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(8, Update.job_repository_id);
								}

								if (Update.job_version == null) {
									pstmt_tDBOutput_2.setNull(9, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(9, Update.job_version);
								}

								if (Update.context == null) {
									pstmt_tDBOutput_2.setNull(10, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(10, Update.context);
								}

								if (Update.origin == null) {
									pstmt_tDBOutput_2.setNull(11, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(11, Update.origin);
								}

								if (Update.message_type == null) {
									pstmt_tDBOutput_2.setNull(12, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(12, Update.message_type);
								}

								if (Update.message == null) {
									pstmt_tDBOutput_2.setNull(13, java.sql.Types.VARCHAR);
								} else {
									pstmt_tDBOutput_2.setString(13, Update.message);
								}

								if (Update.duration == null) {
									pstmt_tDBOutput_2.setNull(14, java.sql.Types.INTEGER);
								} else {
									pstmt_tDBOutput_2.setLong(14, Update.duration);
								}

								pstmt_tDBOutput_2.setInt(15 + count_tDBOutput_2, Update.stats_PK);

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

			try {

				/**
				 * [tDBInput_2 finally ] start
				 */

				currentComponent = "tDBInput_2";

				/**
				 * [tDBInput_2 finally ] stop
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

		globalMap.put("tDBInput_2_SUBPROCESS_STATE", 1);
	}

	public static class row3Struct implements routines.system.IPersistableComparableLookupRow<row3Struct> {
		final static byte[] commonByteArrayLock_PROJETBI_SA_Stats = new byte[0];
		static byte[] commonByteArray_PROJETBI_SA_Stats = new byte[0];
		protected static final int DEFAULT_HASHCODE = 1;
		protected static final int PRIME = 31;
		protected int hashCode = DEFAULT_HASHCODE;
		public boolean hashCodeDirty = true;

		public String loopKey;

		public java.util.Date moment;

		public java.util.Date getMoment() {
			return this.moment;
		}

		public String pid;

		public String getPid() {
			return this.pid;
		}

		public String father_pid;

		public String getFather_pid() {
			return this.father_pid;
		}

		public String root_pid;

		public String getRoot_pid() {
			return this.root_pid;
		}

		public Long system_pid;

		public Long getSystem_pid() {
			return this.system_pid;
		}

		public String project;

		public String getProject() {
			return this.project;
		}

		public String job;

		public String getJob() {
			return this.job;
		}

		public String job_repository_id;

		public String getJob_repository_id() {
			return this.job_repository_id;
		}

		public String job_version;

		public String getJob_version() {
			return this.job_version;
		}

		public String context;

		public String getContext() {
			return this.context;
		}

		public String origin;

		public String getOrigin() {
			return this.origin;
		}

		public String message_type;

		public String getMessage_type() {
			return this.message_type;
		}

		public String message;

		public String getMessage() {
			return this.message;
		}

		public Long duration;

		public Long getDuration() {
			return this.duration;
		}

		public int stats_PK;

		public int getStats_PK() {
			return this.stats_PK;
		}

		public Integer count;

		public Integer getCount() {
			return this.count;
		}

		@Override
		public int hashCode() {
			if (this.hashCodeDirty) {
				final int prime = PRIME;
				int result = DEFAULT_HASHCODE;

				result = prime * result + ((this.moment == null) ? 0 : this.moment.hashCode());

				result = prime * result + ((this.pid == null) ? 0 : this.pid.hashCode());

				result = prime * result + ((this.father_pid == null) ? 0 : this.father_pid.hashCode());

				result = prime * result + ((this.root_pid == null) ? 0 : this.root_pid.hashCode());

				result = prime * result + ((this.system_pid == null) ? 0 : this.system_pid.hashCode());

				result = prime * result + ((this.project == null) ? 0 : this.project.hashCode());

				result = prime * result + ((this.job == null) ? 0 : this.job.hashCode());

				result = prime * result + ((this.job_repository_id == null) ? 0 : this.job_repository_id.hashCode());

				result = prime * result + ((this.job_version == null) ? 0 : this.job_version.hashCode());

				result = prime * result + ((this.context == null) ? 0 : this.context.hashCode());

				result = prime * result + ((this.message_type == null) ? 0 : this.message_type.hashCode());

				result = prime * result + ((this.message == null) ? 0 : this.message.hashCode());

				result = prime * result + ((this.duration == null) ? 0 : this.duration.hashCode());

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

			if (this.moment == null) {
				if (other.moment != null)
					return false;

			} else if (!this.moment.equals(other.moment))

				return false;

			if (this.pid == null) {
				if (other.pid != null)
					return false;

			} else if (!this.pid.equals(other.pid))

				return false;

			if (this.father_pid == null) {
				if (other.father_pid != null)
					return false;

			} else if (!this.father_pid.equals(other.father_pid))

				return false;

			if (this.root_pid == null) {
				if (other.root_pid != null)
					return false;

			} else if (!this.root_pid.equals(other.root_pid))

				return false;

			if (this.system_pid == null) {
				if (other.system_pid != null)
					return false;

			} else if (!this.system_pid.equals(other.system_pid))

				return false;

			if (this.project == null) {
				if (other.project != null)
					return false;

			} else if (!this.project.equals(other.project))

				return false;

			if (this.job == null) {
				if (other.job != null)
					return false;

			} else if (!this.job.equals(other.job))

				return false;

			if (this.job_repository_id == null) {
				if (other.job_repository_id != null)
					return false;

			} else if (!this.job_repository_id.equals(other.job_repository_id))

				return false;

			if (this.job_version == null) {
				if (other.job_version != null)
					return false;

			} else if (!this.job_version.equals(other.job_version))

				return false;

			if (this.context == null) {
				if (other.context != null)
					return false;

			} else if (!this.context.equals(other.context))

				return false;

			if (this.message_type == null) {
				if (other.message_type != null)
					return false;

			} else if (!this.message_type.equals(other.message_type))

				return false;

			if (this.message == null) {
				if (other.message != null)
					return false;

			} else if (!this.message.equals(other.message))

				return false;

			if (this.duration == null) {
				if (other.duration != null)
					return false;

			} else if (!this.duration.equals(other.duration))

				return false;

			return true;
		}

		public void copyDataTo(row3Struct other) {

			other.moment = this.moment;
			other.pid = this.pid;
			other.father_pid = this.father_pid;
			other.root_pid = this.root_pid;
			other.system_pid = this.system_pid;
			other.project = this.project;
			other.job = this.job;
			other.job_repository_id = this.job_repository_id;
			other.job_version = this.job_version;
			other.context = this.context;
			other.origin = this.origin;
			other.message_type = this.message_type;
			other.message = this.message;
			other.duration = this.duration;
			other.stats_PK = this.stats_PK;
			other.count = this.count;

		}

		public void copyKeysDataTo(row3Struct other) {

			other.moment = this.moment;
			other.pid = this.pid;
			other.father_pid = this.father_pid;
			other.root_pid = this.root_pid;
			other.system_pid = this.system_pid;
			other.project = this.project;
			other.job = this.job;
			other.job_repository_id = this.job_repository_id;
			other.job_version = this.job_version;
			other.context = this.context;
			other.message_type = this.message_type;
			other.message = this.message;
			other.duration = this.duration;

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

		private String readString(ObjectInputStream dis) throws IOException {
			String strReturn = null;
			int length = 0;
			length = dis.readInt();
			if (length == -1) {
				strReturn = null;
			} else {
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				dis.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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
				if (length > commonByteArray_PROJETBI_SA_Stats.length) {
					if (length < 1024 && commonByteArray_PROJETBI_SA_Stats.length == 0) {
						commonByteArray_PROJETBI_SA_Stats = new byte[1024];
					} else {
						commonByteArray_PROJETBI_SA_Stats = new byte[2 * length];
					}
				}
				unmarshaller.readFully(commonByteArray_PROJETBI_SA_Stats, 0, length);
				strReturn = new String(commonByteArray_PROJETBI_SA_Stats, 0, length, utf8Charset);
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

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void readKeysData(org.jboss.marshalling.Unmarshaller dis) {

			synchronized (commonByteArrayLock_PROJETBI_SA_Stats) {

				try {

					int length = 0;

					this.moment = readDate(dis);

					this.pid = readString(dis);

					this.father_pid = readString(dis);

					this.root_pid = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.system_pid = null;
					} else {
						this.system_pid = dis.readLong();
					}

					this.project = readString(dis);

					this.job = readString(dis);

					this.job_repository_id = readString(dis);

					this.job_version = readString(dis);

					this.context = readString(dis);

					this.message_type = readString(dis);

					this.message = readString(dis);

					length = dis.readByte();
					if (length == -1) {
						this.duration = null;
					} else {
						this.duration = dis.readLong();
					}

				} catch (IOException e) {
					throw new RuntimeException(e);

				}

			}

		}

		public void writeKeysData(ObjectOutputStream dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeKeysData(org.jboss.marshalling.Marshaller dos) {
			try {

				// java.util.Date

				writeDate(this.moment, dos);

				// String

				writeString(this.pid, dos);

				// String

				writeString(this.father_pid, dos);

				// String

				writeString(this.root_pid, dos);

				// Long

				if (this.system_pid == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.system_pid);
				}

				// String

				writeString(this.project, dos);

				// String

				writeString(this.job, dos);

				// String

				writeString(this.job_repository_id, dos);

				// String

				writeString(this.job_version, dos);

				// String

				writeString(this.context, dos);

				// String

				writeString(this.message_type, dos);

				// String

				writeString(this.message, dos);

				// Long

				if (this.duration == null) {
					dos.writeByte(-1);
				} else {
					dos.writeByte(0);
					dos.writeLong(this.duration);
				}

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

				this.origin = readString(dis, ois);

				this.stats_PK = dis.readInt();

				this.count = readInteger(dis, ois);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		public void readValuesData(DataInputStream dis, org.jboss.marshalling.Unmarshaller objectIn) {
			try {
				int length = 0;

				this.origin = readString(dis, objectIn);

				this.stats_PK = objectIn.readInt();

				this.count = readInteger(dis, objectIn);

			} catch (IOException e) {
				throw new RuntimeException(e);

			}

		}

		/**
		 * Return a byte array which represents Values data.
		 */
		public void writeValuesData(DataOutputStream dos, ObjectOutputStream oos) {
			try {

				writeString(this.origin, dos, oos);

				dos.writeInt(this.stats_PK);

				writeInteger(this.count, dos, oos);

			} catch (IOException e) {
				throw new RuntimeException(e);
			}

		}

		public void writeValuesData(DataOutputStream dos, org.jboss.marshalling.Marshaller objectOut) {
			try {

				writeString(this.origin, dos, objectOut);

				objectOut.writeInt(this.stats_PK);

				writeInteger(this.count, dos, objectOut);

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
			sb.append("moment=" + String.valueOf(moment));
			sb.append(",pid=" + pid);
			sb.append(",father_pid=" + father_pid);
			sb.append(",root_pid=" + root_pid);
			sb.append(",system_pid=" + String.valueOf(system_pid));
			sb.append(",project=" + project);
			sb.append(",job=" + job);
			sb.append(",job_repository_id=" + job_repository_id);
			sb.append(",job_version=" + job_version);
			sb.append(",context=" + context);
			sb.append(",origin=" + origin);
			sb.append(",message_type=" + message_type);
			sb.append(",message=" + message);
			sb.append(",duration=" + String.valueOf(duration));
			sb.append(",stats_PK=" + String.valueOf(stats_PK));
			sb.append(",count=" + String.valueOf(count));
			sb.append("]");

			return sb.toString();
		}

		/**
		 * Compare keys
		 */
		public int compareTo(row3Struct other) {

			int returnValue = -1;

			returnValue = checkNullsAndCompare(this.moment, other.moment);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.pid, other.pid);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.father_pid, other.father_pid);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.root_pid, other.root_pid);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.system_pid, other.system_pid);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.project, other.project);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.job, other.job);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.job_repository_id, other.job_repository_id);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.job_version, other.job_version);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.context, other.context);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.message_type, other.message_type);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.message, other.message);
			if (returnValue != 0) {
				return returnValue;
			}

			returnValue = checkNullsAndCompare(this.duration, other.duration);
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
				// source node:tDBInput_1 - inputs:(after_tDBInput_2) outputs:(row3,row3) |
				// target node:tAdvancedHash_row3 - inputs:(row3) outputs:()
				// linked node: tMap_1 - inputs:(row2,row3) outputs:(Insert,Update)

				org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE matchingModeEnum_row3 = org.talend.designer.components.lookup.common.ICommonLookup.MATCHING_MODE.UNIQUE_MATCH;

				org.talend.designer.components.lookup.memory.AdvancedMemoryLookup<row3Struct> tHash_Lookup_row3 = org.talend.designer.components.lookup.memory.AdvancedMemoryLookup
						.<row3Struct>getLookup(matchingModeEnum_row3);

				globalMap.put("tHash_Lookup_row3", tHash_Lookup_row3);

				/**
				 * [tAdvancedHash_row3 begin ] stop
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
						.decryptPassword("enc:routine.encryption.key.v1:E8n3DUbMC6q463m64h1P1jKeVR1ius/9yQ13TS1V9gCU");

				String dbPwd_tDBInput_1 = decryptedPassword_tDBInput_1;

				String url_tDBInput_1 = "jdbc:postgresql://" + "localhost" + ":" + "5432" + "/" + "ProjetBI_SA";

				conn_tDBInput_1 = java.sql.DriverManager.getConnection(url_tDBInput_1, dbUser_tDBInput_1,
						dbPwd_tDBInput_1);

				conn_tDBInput_1.setAutoCommit(false);

				java.sql.Statement stmt_tDBInput_1 = conn_tDBInput_1.createStatement();

				String dbquery_tDBInput_1 = "SELECT \n  \"public\".\"stats\".\"moment\", \n  \"public\".\"stats\".\"pid\", \n  \"public\".\"stats\".\"father_pid\", \n  "
						+ "\"public\".\"stats\".\"root_pid\", \n  \"public\".\"stats\".\"system_pid\", \n  \"public\".\"stats\".\"project\", \n  \"pub"
						+ "lic\".\"stats\".\"job\", \n  \"public\".\"stats\".\"job_repository_id\", \n  \"public\".\"stats\".\"job_version\", \n  \"pu"
						+ "blic\".\"stats\".\"context\", \n  \"public\".\"stats\".\"origin\", \n  \"public\".\"stats\".\"message_type\", \n  \"public"
						+ "\".\"stats\".\"message\", \n  \"public\".\"stats\".\"duration\", \n  \"public\".\"stats\".\"stats_PK\", \n  \"public\".\"st"
						+ "ats\".\"count\"\nFROM \"public\".\"stats\"";

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
							row3.moment = null;
						} else {

							row3.moment = routines.system.JDBCUtil.getDate(rs_tDBInput_1, 1);
						}
						if (colQtyInRs_tDBInput_1 < 2) {
							row3.pid = null;
						} else {

							row3.pid = routines.system.JDBCUtil.getString(rs_tDBInput_1, 2, false);
						}
						if (colQtyInRs_tDBInput_1 < 3) {
							row3.father_pid = null;
						} else {

							row3.father_pid = routines.system.JDBCUtil.getString(rs_tDBInput_1, 3, false);
						}
						if (colQtyInRs_tDBInput_1 < 4) {
							row3.root_pid = null;
						} else {

							row3.root_pid = routines.system.JDBCUtil.getString(rs_tDBInput_1, 4, false);
						}
						if (colQtyInRs_tDBInput_1 < 5) {
							row3.system_pid = null;
						} else {

							row3.system_pid = rs_tDBInput_1.getLong(5);
							if (rs_tDBInput_1.wasNull()) {
								row3.system_pid = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 6) {
							row3.project = null;
						} else {

							row3.project = routines.system.JDBCUtil.getString(rs_tDBInput_1, 6, false);
						}
						if (colQtyInRs_tDBInput_1 < 7) {
							row3.job = null;
						} else {

							row3.job = routines.system.JDBCUtil.getString(rs_tDBInput_1, 7, false);
						}
						if (colQtyInRs_tDBInput_1 < 8) {
							row3.job_repository_id = null;
						} else {

							row3.job_repository_id = routines.system.JDBCUtil.getString(rs_tDBInput_1, 8, false);
						}
						if (colQtyInRs_tDBInput_1 < 9) {
							row3.job_version = null;
						} else {

							row3.job_version = routines.system.JDBCUtil.getString(rs_tDBInput_1, 9, false);
						}
						if (colQtyInRs_tDBInput_1 < 10) {
							row3.context = null;
						} else {

							row3.context = routines.system.JDBCUtil.getString(rs_tDBInput_1, 10, false);
						}
						if (colQtyInRs_tDBInput_1 < 11) {
							row3.origin = null;
						} else {

							row3.origin = routines.system.JDBCUtil.getString(rs_tDBInput_1, 11, false);
						}
						if (colQtyInRs_tDBInput_1 < 12) {
							row3.message_type = null;
						} else {

							row3.message_type = routines.system.JDBCUtil.getString(rs_tDBInput_1, 12, false);
						}
						if (colQtyInRs_tDBInput_1 < 13) {
							row3.message = null;
						} else {

							row3.message = routines.system.JDBCUtil.getString(rs_tDBInput_1, 13, false);
						}
						if (colQtyInRs_tDBInput_1 < 14) {
							row3.duration = null;
						} else {

							row3.duration = rs_tDBInput_1.getLong(14);
							if (rs_tDBInput_1.wasNull()) {
								row3.duration = null;
							}
						}
						if (colQtyInRs_tDBInput_1 < 15) {
							row3.stats_PK = 0;
						} else {

							row3.stats_PK = rs_tDBInput_1.getInt(15);
							if (rs_tDBInput_1.wasNull()) {
								throw new RuntimeException("Null value in non-Nullable column");
							}
						}
						if (colQtyInRs_tDBInput_1 < 16) {
							row3.count = null;
						} else {

							row3.count = rs_tDBInput_1.getInt(16);
							if (rs_tDBInput_1.wasNull()) {
								row3.count = null;
							}
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
						 * [tAdvancedHash_row3 main ] start
						 */

						currentComponent = "tAdvancedHash_row3";

						if (execStat) {
							runStat.updateStatOnConnection(iterateId, 1, 1

									, "row3"

							);
						}

						row3Struct row3_HashRow = new row3Struct();

						row3_HashRow.moment = row3.moment;

						row3_HashRow.pid = row3.pid;

						row3_HashRow.father_pid = row3.father_pid;

						row3_HashRow.root_pid = row3.root_pid;

						row3_HashRow.system_pid = row3.system_pid;

						row3_HashRow.project = row3.project;

						row3_HashRow.job = row3.job;

						row3_HashRow.job_repository_id = row3.job_repository_id;

						row3_HashRow.job_version = row3.job_version;

						row3_HashRow.context = row3.context;

						row3_HashRow.origin = row3.origin;

						row3_HashRow.message_type = row3.message_type;

						row3_HashRow.message = row3.message;

						row3_HashRow.duration = row3.duration;

						row3_HashRow.stats_PK = row3.stats_PK;

						row3_HashRow.count = row3.count;

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
				 * [tDBInput_1 finally ] start
				 */

				currentComponent = "tDBInput_1";

				/**
				 * [tDBInput_1 finally ] stop
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

		globalMap.put("tDBInput_1_SUBPROCESS_STATE", 1);
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
		final SA_Stats SA_StatsClass = new SA_Stats();

		int exitCode = SA_StatsClass.runJobInTOS(args);

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
			java.io.InputStream inContext = SA_Stats.class.getClassLoader()
					.getResourceAsStream("projetbi/sa_stats_0_1/contexts/" + contextStr + ".properties");
			if (inContext == null) {
				inContext = SA_Stats.class.getClassLoader()
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
			tDBInput_2Process(globalMap);
			if (!"failure".equals(status)) {
				status = "end";
			}
		} catch (TalendException e_tDBInput_2) {
			globalMap.put("tDBInput_2_SUBPROCESS_STATE", -1);

			e_tDBInput_2.printStackTrace();

		}

		this.globalResumeTicket = true;// to run tPostJob

		end = System.currentTimeMillis();

		if (watch) {
			System.out.println((end - startTime) + " milliseconds");
		}

		endUsedMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		if (false) {
			System.out.println((endUsedMemory - startUsedMemory) + " bytes memory increase when running : SA_Stats");
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
 * 193367 characters generated by Talend Open Studio for Data Integration on the
 * 5 avril 2023 à 15:12:31 WAT
 ************************************************************************************************/