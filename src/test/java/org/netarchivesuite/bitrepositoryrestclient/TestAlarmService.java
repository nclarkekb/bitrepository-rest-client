package org.netarchivesuite.bitrepositoryrestclient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PushbackInputStream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.antiaction.common.json.JSONDecoder;
import com.antiaction.common.json.JSONEncoding;
import com.antiaction.common.json.JSONException;
import com.antiaction.common.json.JSONObjectMappings;

@RunWith(JUnit4.class)
public class TestAlarmService {

	@Test
	public void test_alarmservice() {
		File jsonFile;

		PushbackInputStream pbin;
		int encoding;

		JSONEncoding json_encoding = JSONEncoding.getJSONEncoding();
		JSONDecoder json_decoder;

		JSONObjectMappings json_objectmappings = new JSONObjectMappings();

		try {
			json_objectmappings.register(Alarm[].class);
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}

		try {

			/*
			 * getAlarmList().
			 */

			Object[][] cases = new Object[][] {
					{
						1447341769653L,
						"INTEGRITY_ISSUE",
						"integrity-service",
						"The following integrity issues were found:\nfile2-pillar is missing 1 file.",
						"netark",
						null
					},
					{
						1447341562614L,
						"COMPONENT_FAILURE",
						"file2-pillar",
						"The file 'bitrepository-integration-1.5-quickstart.tar' has been removed from the archive without it being removed from index. Removing it from index.",
						null,
						"bitrepository-integration-1.5-quickstart.tar"
					},
					{
						1447340675243L,
						"INTEGRITY_ISSUE",
						"integrity-service",
						"The following integrity issues were found:Detected 1 files as removed from the collection.\nfile2-pillar is missing 1 file.",
						"netark",
						null
					},
					{
						1447338112154L,
						"INTEGRITY_ISSUE",
						"integrity-service",
						"Integrity check aborted while getting fileIDs due to failed contributors: [kbdisk01]",
						"netark",
						null
					},
					{
						1447329056464L,
						"COMPONENT_FAILURE",
						"audittrail-service",
						"Failed to collect audit trails. Error was: 'org.bitrepository.client.exceptions.NegativeResponseException: Failed operation. Cause(s):\n[58c582e3: GET_AUDIT_TRAILS: COMPONENT_FAILED: ContributorID kbdisk01 ResponseCode: null, Timeout for identifying contributor]'",
						"netark",
						null
					},
					{
						1447327064182L,
						"INTEGRITY_ISSUE",
						"integrity-service",
						"Integrity check aborted while getting fileIDs due to failed contributors: [kbdisk01]",
						"netark",
						null
					},
					{
						1447325096449L,
						"COMPONENT_FAILURE",
						"audittrail-service",
						"Failed to collect audit trails. Error was: 'org.bitrepository.client.exceptions.NegativeResponseException: Failed operation. Cause(s):\n[db0eead8: GET_AUDIT_TRAILS: COMPONENT_FAILED: ContributorID kbdisk01 ResponseCode: null, Timeout for identifying contributor]'",
						"netark",
						null
					},
					{
						1447323224168L,
						"INTEGRITY_ISSUE",
						"integrity-service",
						"Integrity check aborted while getting fileIDs due to failed contributors: [kbdisk01]",
						"netark",
						null
					},
					{
						1447321041799L,
						"COMPONENT_FAILURE",
						"audittrail-service",
						"Failed to collect audit trails. Error was: 'org.bitrepository.client.exceptions.NegativeResponseException: Failed operation. Cause(s):\n[17fc9a29: GET_AUDIT_TRAILS: COMPONENT_FAILED: ContributorID kbdisk01 ResponseCode: null, Timeout for identifying contributor]'",
						"netark",
						null
					},
					{
						1447320861227L,
						"COMPONENT_FAILURE",
						"audittrail-service",
						"Failed to collect audit trails. Error was: 'org.bitrepository.client.exceptions.NegativeResponseException: Failed operation. Cause(s):\n[0084abe2: GET_AUDIT_TRAILS: COMPONENT_FAILED: ContributorID kbdisk01 ResponseCode: null, Timeout for identifying contributor]'",
						"netark",
						null
					}
			};

			Alarm[] alarms;

			jsonFile = TestHelpers.getTestResourceFile("AlarmService.getAlarmList-compact.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			alarms = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, Alarm[].class);

			Assert.assertEquals(cases.length, alarms.length);
			for (int i=0; i<cases.length; ++i) {
				Assert.assertEquals(cases[i][0], alarms[i].OrigDateTime);
				Assert.assertEquals(cases[i][1], alarms[i].AlarmCode);
				Assert.assertEquals(cases[i][2], alarms[i].AlarmRaiser);
				Assert.assertEquals(cases[i][3], alarms[i].AlarmText);
				Assert.assertEquals(cases[i][4], alarms[i].CollectionID);
				Assert.assertEquals(cases[i][5], alarms[i].FileID);
			}

			jsonFile = TestHelpers.getTestResourceFile("AlarmService.getAlarmList-pretty.json");

			pbin = new PushbackInputStream(new FileInputStream(jsonFile), 4);
			encoding = JSONEncoding.encoding(pbin);
			Assert.assertEquals(JSONEncoding.E_UTF8, encoding);
			json_decoder = json_encoding.getJSONDecoder(encoding);
			Assert.assertNotNull(json_decoder);

			alarms = json_objectmappings.getStreamUnmarshaller().toObject(pbin, json_decoder, Alarm[].class);

			Assert.assertEquals(cases.length, alarms.length);
			for (int i=0; i<cases.length; ++i) {
				Assert.assertEquals(cases[i][0], alarms[i].OrigDateTime);
				Assert.assertEquals(cases[i][1], alarms[i].AlarmCode);
				Assert.assertEquals(cases[i][2], alarms[i].AlarmRaiser);
				Assert.assertEquals(cases[i][3], alarms[i].AlarmText);
				Assert.assertEquals(cases[i][4], alarms[i].CollectionID);
				Assert.assertEquals(cases[i][5], alarms[i].FileID);
			}
		}
		catch (IOException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}
		catch (JSONException e) {
			e.printStackTrace();
			Assert.fail("Unexpected exception!");
		}

	}

}
