package org.netarchivesuite.bitrepositoryrestclient;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.PushbackInputStream;

import com.antiaction.common.json.JSONDecoder;
import com.antiaction.common.json.JSONEncoding;
import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectMappings;

public class RestClient {

	protected HttpClientWrapper htw;

	protected JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();

	protected JSONObjectMappings json_objectmappings = new JSONObjectMappings();

	protected RestClient() {
		try {
			json_objectmappings.register(IntegrityStatus[].class);
			json_objectmappings.register(WorkflowSetup[].class);
			json_objectmappings.register(CollectionInformation.class);
			json_objectmappings.register(ComponentStatus[].class);
			json_objectmappings.register(ConfigOption[].class);
			json_objectmappings.register(Alarm[].class);
		}
		catch (JSONException e) {
			e.printStackTrace();
		}
	}

	public static RestClient getInstance(String hostname, int port, String userName, String password) {
		RestClient restClient = new RestClient();
		restClient.htw = HttpClientWrapper.getInstance( hostname, port, userName, password);
		return restClient;
	}

	public static RestClient getInstance(String hostname, int port, File keystoreFile, String keyStorePassword, String userName, String password) {
		RestClient restClient = new RestClient();
		restClient.htw = HttpClientWrapper.getInstance( hostname, port, keystoreFile, keyStorePassword, userName, password);
		return restClient;
	}

	/*
	 * IntegrityService.
	 */

	public String integrityBase = "/bitrepository-integrity-service/integrity/IntegrityService/";

	public HttpResult getChecksumErrorFileIDs(String collectionID, String pillarID, long pageNumber, long pageSize) {
		HttpResult httpResult = htw.get(integrityBase+ "getChecksumErrorFileIDs/?collectionID=" + collectionID + "&pillarID=" + pillarID + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize);
		return httpResult;
	}

	public HttpResult getMissingFileIDs(String collectionID, String pillarID, long pageNumber, long pageSize) {
		HttpResult httpResult = htw.get(integrityBase+ "getMissingFileIDs/?collectionID=" + collectionID + "&pillarID=" + pillarID + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize);
		return httpResult;
	}

	public HttpResult getMissingChecksumsFileIDs(String collectionID, String pillarID, long pageNumber, long pageSize) {
		HttpResult httpResult = htw.get(integrityBase+ "getMissingChecksumsFileIDs/?collectionID=" + collectionID + "&pillarID=" + pillarID + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize);
		return httpResult;
	}

	public HttpResult getObsoleteChecksumsFileIDs(String collectionID, String pillarID, long pageNumber, long pageSize) {
		HttpResult httpResult = htw.get(integrityBase+ "getObsoleteChecksumsFileIDs/?collectionID=" + collectionID + "&pillarID=" + pillarID + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize);
		return httpResult;
	}

	public HttpResult getAllFileIDs(String collectionID, String pillarID, long pageNumber, long pageSize) {
		HttpResult httpResult = htw.get(integrityBase+ "getAllFileIDs/?collectionID=" + collectionID + "&pillarID=" + pillarID + "&pageNumber=" + pageNumber + "&pageSize=" + pageSize);
		return httpResult;
	}

	public IntegrityStatusResponse getIntegrityStatus(String collectionID) {
		HttpResult httpResult = htw.get(integrityBase+ "getIntegrityStatus/?collectionID=" + collectionID);
		IntegrityStatusResponse integrityStatusResponse = new IntegrityStatusResponse();
		integrityStatusResponse.httpResult = httpResult;
		try {
			PushbackInputStream pbin = new PushbackInputStream(new ByteArrayInputStream(httpResult.response), 4);
			int encoding = JSONEncoding.encoding(pbin);
			JSONDecoder json_decoder = json_encoding.getJSONDecoder(encoding);
			integrityStatusResponse.integrityStatuses = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, IntegrityStatus[].class);
		}
		catch (IOException e) {
			integrityStatusResponse.httpResult.t = e;
			integrityStatusResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		catch (JSONException e) {
			integrityStatusResponse.httpResult.t = e;
			integrityStatusResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		return integrityStatusResponse;
	}

	public WorkflowSetupResponse getWorkflowSetup(String collectionID) {
		HttpResult httpResult = htw.get(integrityBase+ "getWorkflowSetup/?collectionID=" + collectionID);
		WorkflowSetupResponse workflowSetupResponse = new WorkflowSetupResponse();
		workflowSetupResponse.httpResult = httpResult;
		try {
			PushbackInputStream pbin = new PushbackInputStream(new ByteArrayInputStream(httpResult.response), 4);
			int encoding = JSONEncoding.encoding(pbin);
			JSONDecoder json_decoder = json_encoding.getJSONDecoder(encoding);
			workflowSetupResponse.workflowSetups = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, WorkflowSetup[].class);
		}
		catch (IOException e) {
			workflowSetupResponse.httpResult.t = e;
			workflowSetupResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		catch (JSONException e) {
			workflowSetupResponse.httpResult.t = e;
			workflowSetupResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		return workflowSetupResponse;
	}

	public HttpResult getWorkflowList(String collectionID) {
		HttpResult httpResult = htw.get(integrityBase+ "getWorkflowList/?collectionID=" + collectionID);
		return httpResult;
	}

	public HttpResult getLatestIntegrityReport(String collectionID, String workflowID) {
		HttpResult httpResult = htw.get(integrityBase+ "getLatestIntegrityReport/?collectionID=" + collectionID + "&workflowID=" + workflowID);
		return httpResult;
	}

	public CollectionInformationResponse getCollectionInformation(String collectionID) {
		HttpResult httpResult = htw.get(integrityBase+ "getCollectionInformation/?collectionID=" + collectionID);
		CollectionInformationResponse collectionInformationResponse = new CollectionInformationResponse();
		collectionInformationResponse.httpResult = httpResult;
		try {
			PushbackInputStream pbin = new PushbackInputStream(new ByteArrayInputStream(httpResult.response), 4);
			int encoding = JSONEncoding.encoding(pbin);
			JSONDecoder json_decoder = json_encoding.getJSONDecoder(encoding);
			collectionInformationResponse.collectionInformation = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, CollectionInformation.class);
		}
		catch (IOException e) {
			collectionInformationResponse.httpResult.t = e;
			collectionInformationResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		catch (JSONException e) {
			collectionInformationResponse.httpResult.t = e;
			collectionInformationResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		return collectionInformationResponse;
	}

	/*
	 * MonitoringService.
	 */

	public String monitorBase = "/bitrepository-monitoring-service/monitoring/MonitoringService/";

	public ConfigOptionsResponse getMonitoringConfiguration() {
		HttpResult httpResult = htw.get(monitorBase + "getMonitoringConfiguration/");
		ConfigOptionsResponse configOptionsResponse = new ConfigOptionsResponse();
		configOptionsResponse.httpResult = httpResult;
		try {
			PushbackInputStream pbin = new PushbackInputStream(new ByteArrayInputStream(httpResult.response), 4);
			int encoding = JSONEncoding.encoding(pbin);
			JSONDecoder json_decoder = json_encoding.getJSONDecoder(encoding);
			configOptionsResponse.configOptions = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, ConfigOption[].class);
		}
		catch (IOException e) {
			configOptionsResponse.httpResult.t = e;
			configOptionsResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		catch (JSONException e) {
			configOptionsResponse.httpResult.t = e;
			configOptionsResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		return configOptionsResponse;
	}

	public ComponentStatusResponse getComponentStatus() {
		HttpResult httpResult = htw.get(monitorBase + "getComponentStatus/");
		ComponentStatusResponse componentStatusResponse = new ComponentStatusResponse();
		componentStatusResponse.httpResult = httpResult;
		try {
			PushbackInputStream pbin = new PushbackInputStream(new ByteArrayInputStream(httpResult.response), 4);
			int encoding = JSONEncoding.encoding(pbin);
			JSONDecoder json_decoder = json_encoding.getJSONDecoder(encoding);
			componentStatusResponse.ComponentStatuses = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, ComponentStatus[].class);
		}
		catch (IOException e) {
			componentStatusResponse.httpResult.t = e;
			componentStatusResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		catch (JSONException e) {
			componentStatusResponse.httpResult.t = e;
			componentStatusResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		return componentStatusResponse;
	}

	/*
	 * AlarmService.
	 */

	public String alarmBase = "/bitrepository-alarm-service/alarm/AlarmService/";

	public AlarmsResponse getShortAlarmList() {
		HttpResult httpResult = htw.get(alarmBase + "getShortAlarmList/");
		return unmarshallAlarms(httpResult);
	}

	public AlarmsResponse getFullAlarmList() {
		HttpResult httpResult = htw.get(alarmBase + "getFullAlarmList/");
		return unmarshallAlarms(httpResult);
	}

	public AlarmsResponse unmarshallAlarms(HttpResult httpResult) {
		AlarmsResponse alarmsResponse = new AlarmsResponse();
		alarmsResponse.httpResult = httpResult;
		try {
			PushbackInputStream pbin = new PushbackInputStream(new ByteArrayInputStream(httpResult.response), 4);
			int encoding = JSONEncoding.encoding(pbin);
			JSONDecoder json_decoder = json_encoding.getJSONDecoder(encoding);
			alarmsResponse.alarms = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, Alarm[].class);
		}
		catch (IOException e) {
			alarmsResponse.httpResult.t = e;
			alarmsResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		catch (JSONException e) {
			alarmsResponse.httpResult.t = e;
			alarmsResponse.httpResult.status = HttpResultStatus.EXCEPTION;
		}
		return alarmsResponse;
	}

}
