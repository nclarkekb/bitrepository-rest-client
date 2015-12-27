package org.netarchivesuite.bitrepositoryrestclient;


public class TestRestClient {

	public static void main(String[] args) {
		RestClient restClient = RestClient.getInstance( "localhost", 28080, null, null);

		HttpResult httpResult1 = restClient.getChecksumErrorFileIDs("netark", "kbdisk01", 1, 100);
		HttpResult httpResult2 = restClient.getMissingFileIDs("netark", "kbdisk01", 1, 100);
		HttpResult httpResult3 = restClient.getMissingChecksumsFileIDs("netark", "kbdisk01", 1, 100);
		HttpResult httpResult4 = restClient.getObsoleteChecksumsFileIDs("netark", "kbdisk01", 1, 100);
		HttpResult httpResult5 = restClient.getAllFileIDs("netark", "kbdisk01", 1, 100);
		IntegrityStatusResponse integrityStatusResponse = restClient.getIntegrityStatus("books");
		WorkflowSetupResponse workflowSetupResponse = restClient.getWorkflowSetup("books");
		HttpResult httpResult8 = restClient.getWorkflowList("books");
		HttpResult httpResult9 = restClient.getLatestIntegrityReport("books", "CompleteIntegrityCheck");
		CollectionInformationResponse collectionInformationResponse = restClient.getCollectionInformation("netark");

		ConfigOptionsResponse configOptionsResponse = restClient.getMonitoringConfiguration();
		ComponentStatusResponse componentStatusResponse = restClient.getComponentStatus();

		AlarmsResponse alarmsResponse1 = restClient.getShortAlarmList();
		AlarmsResponse alarmsResponse2 = restClient.getFullAlarmList();

		System.out.println(new String(httpResult1.response));
		System.out.println(new String(httpResult2.response));
		System.out.println(new String(httpResult3.response));
		System.out.println(new String(httpResult4.response));
		System.out.println(new String(httpResult5.response));
		System.out.println(new String(integrityStatusResponse.httpResult.response));
		for (int i=0; i<integrityStatusResponse.integrityStatuses.length; ++i) {
			System.out.println(integrityStatusResponse.integrityStatuses[i].pillarID + " - " + integrityStatusResponse.integrityStatuses[i].totalFileCount + " - " + integrityStatusResponse.integrityStatuses[i].missingFilesCount + " - " + integrityStatusResponse.integrityStatuses[i].checksumErrorCount + " - " + integrityStatusResponse.integrityStatuses[i].obsoleteChecksumsCount + " - " + integrityStatusResponse.integrityStatuses[i].missingChecksumsCount);
		}
		System.out.println(new String(workflowSetupResponse.httpResult.response));
		for (int i=0; i<workflowSetupResponse.workflowSetups.length; ++i) {
			System.out.println(workflowSetupResponse.workflowSetups[i].workflowID + " - " + workflowSetupResponse.workflowSetups[i].workflowDescription + " - " + workflowSetupResponse.workflowSetups[i].nextRun + " - " + workflowSetupResponse.workflowSetups[i].lastRun + " - " + workflowSetupResponse.workflowSetups[i].executionInterval + " - " + workflowSetupResponse.workflowSetups[i].currentState);
			System.out.println(workflowSetupResponse.workflowSetups[i].lastRunDetails);
		}
		System.out.println(new String(httpResult8.response));
		System.out.println(new String(httpResult9.response));
		System.out.println(new String(collectionInformationResponse.httpResult.response));
		System.out.println(new String(collectionInformationResponse.collectionInformation.lastIngest + " - " + collectionInformationResponse.collectionInformation.collectionSize + " - " + collectionInformationResponse.collectionInformation.numberOfFiles));

		System.out.println(new String(configOptionsResponse.httpResult.response));
		for (int i=0; i<configOptionsResponse.configOptions.length; ++i) {
			System.out.println(configOptionsResponse.configOptions[i].confOption + " = " + configOptionsResponse.configOptions[i].confValue);
		}

		System.out.println(new String(componentStatusResponse.httpResult.response));
		for (int i=0; i<componentStatusResponse.ComponentStatuses.length; ++i) {
			System.out.println(componentStatusResponse.ComponentStatuses[i].timeStamp + " - " + componentStatusResponse.ComponentStatuses[i].componentID + " - " + componentStatusResponse.ComponentStatuses[i].status + " - " + componentStatusResponse.ComponentStatuses[i].info);
		}

		System.out.println(new String(alarmsResponse1.httpResult.response));
		System.out.println(alarmsResponse1.alarms.length);
		for (int i=0; i<alarmsResponse1.alarms.length; ++i) {
			System.out.println(alarmsResponse1.alarms[i].OrigDateTime + " - " + alarmsResponse1.alarms[i].AlarmCode + " - " + alarmsResponse1.alarms[i].AlarmRaiser + " - " + alarmsResponse1.alarms[i].AlarmText + " - " + alarmsResponse1.alarms[i].CollectionID + " - " + alarmsResponse1.alarms[i].FileID);
		}
		System.out.println(new String(alarmsResponse2.httpResult.response));
		System.out.println(alarmsResponse2.alarms.length);
		for (int i=0; i<10; ++i) {
			System.out.println(alarmsResponse2.alarms[i].OrigDateTime + " - " + alarmsResponse2.alarms[i].AlarmCode + " - " + alarmsResponse2.alarms[i].AlarmRaiser + " - " + alarmsResponse2.alarms[i].AlarmText + " - " + alarmsResponse2.alarms[i].CollectionID + " - " + alarmsResponse2.alarms[i].FileID);
		}
	}

}
